package com.kshitij.touchlessbanking;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;

public class Register extends AppCompatActivity {

    public static final String TAG = "TAG";
    private EditText userName,email,password,phone;
    private Button register,signIn;
    private ProgressBar progressBar;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //Binding to xml
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        phone = findViewById(R.id.phone);
        register = findViewById(R.id.register);
        signIn = findViewById(R.id.signIn);
        progressBar = findViewById(R.id.progressBarRegister);

        //init firebase
        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        //if User is already logged in
        if(null != auth.getCurrentUser()){
            startActivity(new Intent(getApplicationContext(),MainActivity.class));
            finish();
        }

        //SignIn button onClick
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(),Login.class));
                finish();
            }
        });

        //Register button onClick
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Data Fetching
                final String vUserName = userName.getText().toString().trim();
                final String vEmail = email.getText().toString().trim();
                String vPassword = password.getText().toString().trim();
                final String vPhone = phone.getText().toString().trim();

                //Data validation
                if(TextUtils.isEmpty(vUserName)){
                    userName.setError("User Name required");
                    return;
                }
                if(TextUtils.isEmpty(vEmail)){
                    email.setError("Email required");
                    return;
                }
                if (!vEmail.contains("@") || !vEmail.contains(".com")){
                    email.setError("Email invalid");
                    return;
                }
                if(TextUtils.isEmpty(vPassword)){
                    password.setError("Password required");
                    return;
                }
                if(password.length()<6){
                    password.setError("Password too short (min 6 chars)");
                    return;
                }
                if(TextUtils.isEmpty(vPhone)){
                    phone.setError("Phone required");
                    return;
                }
                if(phone.length()<9){
                    phone.setError("Phone invalid");
                    return;
                }

                //Progress bar
                progressBar.setVisibility(View.VISIBLE);

                //Registering User
                auth.createUserWithEmailAndPassword(vEmail,vPassword)
                        .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(Register.this,"User "+vUserName+" successfully registered",Toast.LENGTH_SHORT).show();
                                    //fetching auto-gen UID
                                    userId = auth.getCurrentUser().getUid();
                                    //Creating/Inserting Collection and a new Document
                                    DocumentReference documentReference = db.collection("Users").document(userId);
                                    //Setting User Data
                                    HashMap<String,Object> user = new HashMap<>();
                                    user.put("userName",vUserName);
                                    user.put("email",vEmail);
                                    user.put("phone",vPhone);
                                    //Adding User data to UserID Document
                                    documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d(TAG,"User "+userId+" Added to FireStore");
                                            Toast.makeText(Register.this, "User "+userId+" Created", Toast.LENGTH_SHORT).show();
                                        }
                                    }).addOnFailureListener(new OnFailureListener() {
                                        @Override
                                        public void onFailure(@NonNull Exception e) {
                                            Log.d(TAG," Error: "+e.getMessage());
                                        }
                                    });
                                    startActivity(new Intent(getApplicationContext(),MainActivity.class));      //Redirect to MainActivity
                                }else{
                                    Toast.makeText(Register.this, "Error in registration: "+task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                    progressBar.setVisibility(View.GONE);
                                }
                            }
                        });
            }
        });
    }
}