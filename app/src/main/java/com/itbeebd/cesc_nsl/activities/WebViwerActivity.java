package com.itbeebd.cesc_nsl.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebView;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.utils.AppWebViewClients;

public class WebViwerActivity extends AppCompatActivity {

    WebView urlWebView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_viwer);

        urlWebView = (WebView)findViewById(R.id.webview);
        urlWebView.setWebViewClient(new AppWebViewClients());
        urlWebView.getSettings().setJavaScriptEnabled(true);
        urlWebView.getSettings().setUseWideViewPort(true);

        if(getIntent().hasExtra("url")){
            System.out.println("url >>>>> " + getIntent().getStringExtra("url"));
            open(getIntent().getStringExtra("url"));
        }
    }

    private void open(String url){
        urlWebView.loadUrl(url);
    }
}