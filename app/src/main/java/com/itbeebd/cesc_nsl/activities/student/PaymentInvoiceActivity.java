package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.utils.Payment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class PaymentInvoiceActivity extends AppCompatActivity {

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_invoice);

        WebView webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);

        if(getIntent().hasExtra("paymentObj")){
            String url = ApiUrls.BASE_WEB_URL + ((Payment)getIntent().getSerializableExtra("paymentObj")).getVoucher_no();

           // String postData = null;
 //           try {
//                postData = "studentid=" + URLEncoder.encode("321005170", "UTF-8")
//                        + "&password=" + URLEncoder.encode("123456", "UTF-8")
//                        + "&fcm_token=" + URLEncoder.encode("156", "UTF-8");
                webView.loadUrl(url);
               // webView.postUrl(url,postData.getBytes());
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }

        }

    }
}