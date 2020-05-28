package com.example.newsreader;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class website_activity extends AppCompatActivity {

    private WebView webview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_website_activity);

        Intent intent = getIntent();

        if(null != intent)
        {
            String url= intent.getStringExtra("url");
            if(null != url)
            {
                webview= findViewById(R.id.webview);
                webview.setWebViewClient(new WebViewClient());
                webview.getSettings().setJavaScriptEnabled(true);
                webview.loadUrl(url);
            }
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if(webview.canGoBack())
        {
            getObbDir();
        }
        else{

            super.onBackPressed();
        }
    }
}
