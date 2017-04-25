package com.example.artman2111.thenewmdb.activity.tmdb;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.artman2111.thenewmdb.activity.models.Film_Accept;
import com.example.artman2111.thenewmdb.activity.models.Gallery_Accept;
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
    private Context context;
    private List<Gallery_Accept> dbGallery;
    private List<Film_Accept> dbFilm;
    public static int page = 1;


    public FilmModalAccept(Context context) {
        dbGallery = new ArrayList<>();
        dbFilm = new ArrayList<>();
        this.context = context;
    }
    public FilmModalAccept(List<Film_Accept> film_accepts) {
        dbFilm = film_accepts;
    }

    public List<Film_Accept> getPathsFromAPI(boolean sortbypop) {
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String JSONResult;
        try {
            String urlString = null;
            if (sortbypop) {
                Log.d("artman","page - >  " + page);
                urlString = getPopUrl()+page;

            } else {
                urlString = getTopUrl()+page;
            }
            URL url = new URL(urlString);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(1000);
            urlConnection.setConnectTimeout(1000);
            urlConnection.setRequestMethod("GET");
            urlConnection.setRequestProperty("Content-Type", "application/json");
            urlConnection.setRequestProperty("charset", "utf-8");
            urlConnection.setDoInput(true);
            urlConnection.connect();
            InputStream inputStream;
            inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null) {
                return null;
            }
            reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");

            }
            if (buffer.length() == 0) {
                return null;
            }
            JSONResult = buffer.toString();
            try {
                return getPathsFromJSON(JSONResult);

            } catch (JSONException e) {
                return null;
            }
        } catch (Exception e) {
            Log.d("artman", "Exception" + e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {

                }
            }
        }
        return null;
    }


    public List<Film_Accept> getPathsFromJSON(String JSONStringParam) throws JSONException {
        JSONObject jsonString = new JSONObject(JSONStringParam);
        JSONArray moviesArr = jsonString.getJSONArray("results");
        for (int i = 0; i < moviesArr.length(); i++) {
            JSONObject movie = moviesArr.getJSONObject(i);
            Film_Accept film_accept = new Film_Accept(movie.getString("poster_path"),movie.getString("id"),movie.getString("title"),movie.getString("overview"),movie.getString("release_date"));
            dbFilm.add(film_accept);
        }
        return dbFilm;

    }
    public List<Gallery_Accept> getGalleryFromAPI(String movieID, final String jsonArray) {
        String urlString = "https://api.themoviedb.org/3/movie/" + movieID + "/images?api_key=" + getApiKey();

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, urlString,

                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray array = response.getJSONArray(jsonArray);

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject object = array.getJSONObject(i);
                                Gallery_Accept data = new Gallery_Accept();
                                data.setPoster(object.getString("file_path"));
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