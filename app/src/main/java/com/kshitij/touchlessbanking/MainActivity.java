package com.kshitij.touchlessbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseAuth Instance
        auth = FirebaseAuth.getInstance();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    //logout button
    public void logOut(View view) {
        auth.signOut();
        startActivity(new Intent(getApplicationContext(),Login.class));
        finish();
    }

    public void profile(View view) {
        startActivity(new Intent(getApplicationContext(),Profile.class));
        finish();
    }

    public void browser(View view) {
        startActivity(new Intent(getApplicationContext(),Browser.class));
        finish();
    }

    public void pin(View view) {
        startActivity(new Intent(getApplicationContext(),Pin.class));
        finish();
    }

    public void user(View view) {
        startActivity(new Intent(getApplicationContext(),User.class));
        finish();
    }

    public void withdraw(View view) {
        startActivity(new Intent(getApplicationContext(),Withdraw.class));
        finish();
    }

    public void deposit(View view) {
        startActivity(new Intent(getApplicationContext(),Deposit.class));
        finish();
    }
}