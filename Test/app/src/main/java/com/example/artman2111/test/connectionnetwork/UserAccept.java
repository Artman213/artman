package com.example.artman2111.test.connectionnetwork;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.artman2111.test.models.UserList;

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

/**
 * Created by artman2111 on 26.05.17.
 */

public class UserAccept {
    private Context context;
    private List<UserList> userlists = new ArrayList<>();
    public UserAccept(Context context){
        this.context=context;
    }
    public String getURL(String url){
        String baseurl =  "http://174.138.54.52"+url;
        return baseurl;
    }

public List<UserList> getPathsFromAPI() {
    HttpURLConnection urlConnection = null;
    BufferedReader reader = null;
    String JSONResult;
    try {
        String urlString = null;
        urlString = getURL("/authorization/allUsers");
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
        StringBuilder buffer = new StringBuilder();
        if (inputStream == null) {
            return null;
        }
        reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while ((line = reader.readLine()) != null) {
            buffer.append(line).append("\n");

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
                Toast toast = Toast.makeText(context,"Ups "+e ,Toast.LENGTH_LONG);
                toast.show();
            }
        }
    }
    return null;
}


    private List<UserList> getPathsFromJSON(String JSONStringParam) throws JSONException {
        JSONArray json = new JSONArray(JSONStringParam);
        for (int i = 0; i < json.length(); i++) {
            JSONObject movie = json.getJSONObject(i);
            UserList user_accept = new UserList(movie.getString("username"),movie.getString
                    ("last_login"));
            userlists.add(user_accept);
        }
        return userlists;

    }

}
