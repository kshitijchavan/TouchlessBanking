package com.kshitij.touchlessbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class User extends AppCompatActivity {

    private EditText user;
    private Button setUser,getUser;
    private ProgressBar progressBar;
    private String URL = "http://3.129.176.178:8080/tfb/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        user = findViewById(R.id.user);
        setUser = findViewById(R.id.setUser);
        getUser = findViewById(R.id.getUser);
        progressBar = findViewById(R.id.progressBar);

        getUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        URL+"getUser",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                user.setText(response.toString());
                                Toast.makeText(User.this, "User fetched", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(User.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                );
                requestQueue.add(request);
            }
        });
        setUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        URL+"setUser/"+user.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(User.this, "User set successfully", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(User.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                );
                requestQueue.add(request);
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}