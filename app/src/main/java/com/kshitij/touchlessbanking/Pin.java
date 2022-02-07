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

public class Pin extends AppCompatActivity {

    private EditText pin;
    private Button setPin,getPin;
    private ProgressBar progressBar;
    private String URL = "http://3.129.176.178:8080/tfb/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        pin = findViewById(R.id.pin);
        setPin = findViewById(R.id.setPin);
        getPin = findViewById(R.id.getPin);
        progressBar = findViewById(R.id.progressBar);

        getPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        URL+"getPin",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pin.setText(response.toString());
                                Toast.makeText(Pin.this, "Pin fetched", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Pin.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        }
                );
                requestQueue.add(request);
            }
        });
        setPin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        URL+"processPin/"+pin.getText().toString(),
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Toast.makeText(Pin.this, "Pin set successfully", Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Pin.this, "Error: "+error.getMessage(), Toast.LENGTH_SHORT).show();
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