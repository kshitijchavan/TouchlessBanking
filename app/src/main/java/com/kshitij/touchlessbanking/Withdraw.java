package com.kshitij.touchlessbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class Withdraw extends AppCompatActivity {

    private TextView balance;
    private EditText amount,pin;
    private Button submit;
    private ProgressBar progressBar;
    private String URL = "http://3.129.176.178:8080/tfb/";
    private String sPin;
    private Integer sBalance;
    private Integer sAmount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_withdraw);

        //binding to xml
        balance = findViewById(R.id.balance);
        amount = findViewById(R.id.amount);
        pin = findViewById(R.id.pin);
        submit = findViewById(R.id.button);
        progressBar = findViewById(R.id.progressBar);

        balance.setText("50000");

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (TextUtils.isEmpty(amount.getText())) {
                    amount.setError("Amount is empty");
                    return;
                }
                if (TextUtils.isEmpty(pin.getText())) {
                    pin.setError("Pin is empty");
                    return;
                }

                progressBar.setVisibility(View.VISIBLE);
                sBalance = Integer.parseInt(balance.getText().toString());
                sAmount = Integer.parseInt(amount.getText().toString());

                RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                StringRequest request = new StringRequest(
                        Request.Method.GET,
                        URL + "getPin",
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                sPin = response.toString();
                                if (TextUtils.equals(pin.getText().toString(),sPin)) {
                                    if(sBalance >= sAmount){
                                        sBalance = sBalance-sAmount;
                                        balance.setText(sBalance.toString());
                                        Toast.makeText(Withdraw.this, "Amount "+sAmount+" withdrawn", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(Withdraw.this, "Insufficient funds", Toast.LENGTH_SHORT).show();
                                    }
                                    progressBar.setVisibility(View.GONE);
                                } else {
                                    pin.setError("Incorrect Pin");
                                    progressBar.setVisibility(View.GONE);
                                    return;
                                }
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                Toast.makeText(Withdraw.this, "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
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