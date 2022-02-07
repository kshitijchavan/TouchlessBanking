package com.kshitij.touchlessbanking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class Login extends AppCompatActivity {

    private EditText email,password;
    private Button login,signUp;
    private ProgressBar progressBar;
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Binding to xml
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        login = findViewById(R.id.login);
        signUp = findViewById(R.id.signUp);
        progressBar = findViewById(R.id.progressBarLogin);

        //init Firebase
        auth = FirebaseAuth.getInstance();

        //signUp button onClick
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Register.class));
                finish();
            }
        });

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Data Fetching
                String vEmail = email.getText().toString().trim();
                String vPassword = password.getText().toString().trim();

                //Data validation
                if (TextUtils.isEmpty(vEmail)) {
                    email.setError("Email required");
                    return;
                }
                if (!vEmail.contains("@") || !vEmail.contains(".com")) {
                    email.setError("Email invalid");
                    return;
                }
                if (TextUtils.isEmpty(vPassword)) {
                    password.setError("Password required");
                    return;
                }
                if (password.length() < 6) {
                    password.setError("Password too short (min 6 chars)");
                    return;
                }

                //Progress bar
                progressBar.setVisibility(View.VISIBLE);

                //Auth User
                auth.signInWithEmailAndPassword(vEmail,vPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            Toast.makeText(Login.this, "User logged in successfully", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(getApplicationContext(),MainActivity.class));
                        }else{
                            Toast.makeText(Login.this, "Error in login: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                            progressBar.setVisibility(View.GONE);
                        }
                    }
                });

            }
        });
    }
}