package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.adapters.PaymentHistoryAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.api.studentApi.PaymentApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.Notification;
import com.itbeebd.cesc_nsl.utils.Payment;

import java.util.ArrayList;
import java.util.Objects;

public class PaymentHistoryActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Payment> {

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
                    if(payments.size() != 0)  {
                        setPaymentHistoryAdapter(payments);
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                })
        );
    }

    private void setPaymentHistoryAdapter(ArrayList<Payment> payments) {
        PaymentHistoryAdapter paymentHistoryAdapter = new PaymentHistoryAdapter(this);
        paymentHistoryAdapter.setItems(payments);
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

    @Override
    public void onItemClicked(Payment item, View view) {
        System.out.println("><><><><><>< " + item.getVoucher_no());
        Intent intent = new Intent(this, PaymentInvoiceActivity.class);
        intent.putExtra("paymentObj",item);
        startActivity(intent);
    }
}
