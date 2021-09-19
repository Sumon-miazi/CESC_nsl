package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.PaymentHistoryAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.utils.Notification;
import com.itbeebd.cesc_nsl.utils.Payment;

import java.util.ArrayList;

public class PaymentHistoryActivity extends AppCompatActivity {

    private ArrayList<Payment> payments;
    private RecyclerView paymentHistoryRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_history);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.paymentToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("PAYMENT HISTORY");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        payments = new ArrayList<>();
        payments.add(new Payment());
        payments.add(new Payment());
        payments.add(new Payment());
        payments.add(new Payment());
        payments.add(new Payment());
        payments.add(new Payment());

        paymentHistoryRecyclerView = findViewById(R.id.paymentHistoryRecyclerViewId);

        setPaymentHistoryAdapter();
    }

    private void setPaymentHistoryAdapter() {
        PaymentHistoryAdapter paymentHistoryAdapter = new PaymentHistoryAdapter(this);
        paymentHistoryAdapter.setItems(payments);
        paymentHistoryRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //    studentNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
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

}
