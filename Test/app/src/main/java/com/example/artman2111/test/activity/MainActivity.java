package com.example.artman2111.test.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.artman2111.test.R;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText login,password,email;
    private RequestQueue queue;
    private boolean registrarion = true;
    private boolean log;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.enter).setOnClickListener(this);
        findViewById(R.id.registration).setOnClickListener(this);
        findViewById(R.id.inDatabase).setOnClickListener(this);
        login = (EditText) findViewById(R.id.login);
        password = (EditText) findViewById(R.id.password);
        email = (EditText) findViewById(R.id.email);
        queue = Volley.newRequestQueue(this);
        log = false;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.enter:
                login();
                if (login.length()==0||password.length()==0){
                    Toast toast = Toast.makeText(this,"empty login or password",Toast.LENGTH_LONG);
                    toast.show();
                }else {
                    if (log) {
                        Toast toast = Toast.makeText(MainActivity.this,"incorrect login or password",
                                Toast.LENGTH_LONG);
                        toast.show();
                    }else {
                        UserActivity.inData = false;
                        Intent intent = new Intent(MainActivity.this, UserActivity.class);
                        startActivity(intent);
                    }
                }
                break;
            case R.id.registration:
                if (registrarion){
                    email.setVisibility(View.VISIBLE);
                    registrarion = false;
                }else {
                    if (login.length() == 0 || password.length() == 0) {
                        Toast toast = Toast.makeText(this, "error login or password", Toast.LENGTH_LONG);
                        toast.show();
                    } else {
//
                        registration();
                        Toast toast = Toast.makeText(this, "You " + login.getText().toString() +
                                        " complete"
                                , Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
                break;
            case R.id.inDatabase:
                UserActivity.inData = true;
                Intent intent = new Intent(MainActivity.this, UserActivity.class);
                startActivity(intent);

        }

    }
    public void registration(){
        String url;
        url = "http://174.138.54.52/authorization/registration";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        Log.d("Response", response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response","aa");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("username", login.getText().toString());
                params.put("password", password.getText().toString());
                params.put("email", email.getText().toString());

                return params;
            }
        };
        queue.add(postRequest);
    }
    public void login(){
        String url;
        url = "http://174.138.54.52/authorization/login/";
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        log = true;

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Error.Response","aa");
                    }
                }
        ) {
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<>();
                params.put("username", login.getText().toString());
                params.put("password", password.getText().toString());
                return params;
            }
        };
        queue.add(postRequest);
    }

}
