package com.kshitij.touchlessbanking;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class Browser extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browser);

        //creating webview
        webView = (WebView) findViewById(R.id.webBrowser);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl("https://www.google.com");

    }
    //backpress doesnt exit app
    @Override
    public void onBackPressed() {
        if(webView.canGoBack()){
            webView.goBack();
        }else{
            super.onBackPressed();
        }
    }

    public void back(View view) {
        startActivity(new Intent(getApplicationContext(),MainActivity.class));
        finish();
    }
}