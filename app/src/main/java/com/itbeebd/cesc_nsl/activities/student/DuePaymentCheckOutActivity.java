package com.itbeebd.cesc_nsl.activities.student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;

public class DuePaymentCheckOutActivity extends AppCompatActivity {

    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_invoice);

        webView = findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(100);

        if(getIntent().hasExtra("invoiceNo")){
            String url = ApiUrls.INVOICE_URL + getIntent().getStringExtra("invoiceNo");

            webView.setWebViewClient(new WebViewClient() {

//                @Override
//                public void onPageFinished(WebView view, String url)
//                {
//                    view.loadUrl("javascript:(function() { " +
//                            "document.getElementsByTagName('footer')[0].style.display='none';})()");
//
//                }

                @Override
                public void onLoadResource(WebView view, String url) {
                    super.onLoadResource(view, url);
                    // Removes element which id = 'mastHead'
                    view.loadUrl("javascript:(function() { " +
                            "(elem = document.getElementsByTagName('footer')[0]).parentNode.removeChild(elem); " +
                            "})()");
                }


                @Override
                public boolean shouldOverrideUrlLoading(WebView webView, String url) {
                    if(url.contains(ApiUrls.BASE_URL)) return false;
                    openExternalLinkInBrowser(url);
                    return true;
                }
            });

                webView.loadUrl(url);

        }
    }

    private void openExternalLinkInBrowser(String url){
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        this.startActivity(intent);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(webView != null){
            if ((keyCode == KeyEvent.KEYCODE_BACK) && this.webView.canGoBack()) {
                this.webView.goBack();
                return true;
            }
        }

        return super.onKeyDown(keyCode, event);
    }
}


// String postData = null;
//           try {
//                postData = "studentid=" + URLEncoder.encode("321005170", "UTF-8")
//                        + "&password=" + URLEncoder.encode("123456", "UTF-8")
//                        + "&fcm_token=" + URLEncoder.encode("156", "UTF-8");
// webView.postUrl(url,postData.getBytes());
//            } catch (UnsupportedEncodingException e) {
//                e.printStackTrace();
//            }