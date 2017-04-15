package com.example.artman2111.thenewmdb.activity.tmdb;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artman2111.thenewmdb.activity.models.Base_Gallery;
import com.example.artman2111.thenewmdb.activity.models.Gallery_Acept;
import com.example.artman2111.thenewmdb.activity.models.MySingleton;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FilmModalAccept extends BaseModal {
    public static int page = 1;
    Context context;
    private List<Base_Gallery> dbGallery;
    static final String API_KEY = "94edada748dbcb0dbb6dded2fe0d5c82";

    public FilmModalAccept(Context context){
        this.context = context;
        dbGallery = new ArrayList<>();
    }

    public String[][] getPathsFromAPI(boolean sortbypop){
        while (true){
            HttpURLConnection urlConnection = null;
            BufferedReader reader = null;
            String JSONResult;
            try {
                String urlString = null;
                if (sortbypop){

                    urlString = "https://api.themoviedb.org/3/movie/popular?api_key="+API_KEY+"&language=ru&page="+page;

                }else {
                    urlString = "https://api.themoviedb.org/3/movie/top_rated?api_key="+API_KEY+"&language=ru&page="+page;
                }
                URL url = new URL(urlString);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(10000);
                urlConnection.setRequestMethod("GET");
                urlConnection.setRequestProperty( "Content-Type", "application/json");
                urlConnection.setRequestProperty( "charset", "utf-8");
                urlConnection.setDoInput(true);
                urlConnection.connect();
                InputStream inputStream;
                inputStream = urlConnection.getInputStream();
                StringBuffer buffer = new StringBuffer();
                if (inputStream == null){
                    return null;
                }
                reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                while ((line = reader.readLine())!=null){
                    buffer.append(line+"\n");

                }
                if (buffer.length()==0){
                    return null;
                }
                JSONResult = buffer.toString();

                try {
                    return getPathsFromJSON(JSONResult);

                }catch (JSONException e){
                    return null;
                }
            }catch (Exception e){
                Log.d("artman","Exception" + e );
                continue;
            }finally {
                if (urlConnection!=null){
                    urlConnection.disconnect();
                }
                if (reader!=null){
                    try {
                        reader.close();
                    }catch (final IOException e ){

                    }
                }
            }
        }
    }
    public String[][] getPathsFromJSON(String JSONStringParam) throws JSONException{
        JSONObject jsonString = new JSONObject(JSONStringParam);
        JSONArray moviesArr = jsonString.getJSONArray("results");
        String[][] result = new String[20][20];
        for (int i = 0; i<20; i ++ ){
            JSONObject movie = moviesArr.getJSONObject(i);
            String id = movie.getString("id");
            result[i][0] = id;
            String poster = movie.getString("poster_path");
            result[i][1] = poster;
            String overview = movie.getString("overview");
            result[i][2] = overview;
            String title = movie.getString("title");
            result[i][3] = title;
            String release_date = movie.getString("release_date");
            result[i][4] = release_date;
        }
        return result;

    }
    public List<Base_Gallery> getGalleryFromAPI(boolean sortbypop) {
        String urlString = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=ru&page="+page;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlString,null,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray("results");

                            for (int i = 0; i < 20; i++) {
                                JSONObject object = array.getJSONObject(i);
                                String poster = object.getString("poster_path");
                                String id = object.getString("id");
                                Gallery_Acept data = new Gallery_Acept(object.getString("poster_path"));
                                dbGallery.add(data);
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);

        return dbGallery;
    }
}