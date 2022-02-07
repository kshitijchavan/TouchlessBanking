package com.kshitij.touchlessbanking;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class Profile extends AppCompatActivity {

    private TextView userId,userName,email,phone;
    private FirebaseAuth auth;
    private FirebaseFirestore db;
    private String fuserId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //Binding to xml
        userId = findViewById(R.id.userId);
        userName = findViewById(R.id.userName);
        email = findViewById(R.id.email);
        phone = findViewById(R.id.phone);

        auth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        fuserId = auth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("Users").document(fuserId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                userId.setText(fuserId);
                userName.setText(value.getString("userName"));
                email.setText(value.getString("email"));
                phone.setText(value.getString("phone"));
            }
        });
    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}