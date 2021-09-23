package com.itbeebd.cesc_nsl.activities.student;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.PaymentHistoryAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.PaymentApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.Payment;

import java.util.ArrayList;

public class PaymentHistoryActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Payment> {

    private Dialog dialog;
    private RecyclerView paymentHistoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.paymentToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("PAYMENT HISTORY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        paymentHistoryRecyclerView = findViewById(R.id.paymentHistoryRecyclerViewId);

        callPaymentHistoryApi();
    }

    private void callPaymentHistoryApi() {
        new PaymentApi(this).getPaymentHistory(
                CustomSharedPref.getInstance(this).getAuthToken(),
                ((payments, message) -> {
                    if (payments.size() != 0) {
                        setPaymentHistoryAdapter(payments);
                    } else Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                })
        );
    }

    private void setPaymentHistoryAdapter(ArrayList<Payment> payments) {
        PaymentHistoryAdapter paymentHistoryAdapter = new PaymentHistoryAdapter(this);
        paymentHistoryAdapter.setItems(payments);
        paymentHistoryAdapter.setListener(this);
        paymentHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        paymentHistoryRecyclerView.setAdapter(paymentHistoryAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onItemClicked(Payment item, View view) {

        dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);

        dialog.setContentView(R.layout.invoice_details_view);

        WebView webView = dialog.findViewById(R.id.webview);


        String url = ApiUrls.INVOICE_URL + item.getVoucher_no();
      //  String url = ("https://www.google.com");

        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.setInitialScale(100);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onLoadResource(WebView view, String url) {
                super.onLoadResource(view, url);
                view.loadUrl("javascript:(function() { " +
                        "(elem = document.getElementsByTagName('footer')[0]).parentNode.removeChild(elem); " +
                        "})()");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.show();
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url2) {
                if (url2.equals(url)) {
                    dialog.dismiss();
                    return false;
                } else {
                    return true;
                }
            }
        });

        webView.loadUrl(url);
    }
}
