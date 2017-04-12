package com.example.artman2111.thenewmdb.activity.tmdb;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class FilmModalAccept extends BaseModal {
    public static int page = 1;
    private int size = 0;
    private int position = 0;
    static final String API_KEY = "94edada748dbcb0dbb6dded2fe0d5c82";

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
            result[position][0] = id;
            String poster = movie.getString("poster_path");
            result[position][1] = poster;
            String overview = movie.getString("overview");
            result[position][2] = overview;
            String title = movie.getString("title");
            result[position][3] = title;
            String release_date = movie.getString("release_date");
            result[position][4] = release_date;
            position++;
        }
        return result;

    }
    //TODO Volley+ use this ;

}