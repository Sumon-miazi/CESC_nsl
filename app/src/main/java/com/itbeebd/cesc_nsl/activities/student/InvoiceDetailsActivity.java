package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.utils.dummy.Invoice;
import com.itbeebd.cesc_nsl.utils.dummy.InvoiceHeader;
import com.parassidhu.simpledate.SimpleDateKt;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class InvoiceDetailsActivity extends AppCompatActivity {

    private InvoiceHeader invoiceHeader;
    private TextView invoiceNumId;
    private TextView invoiceDateId;
    private TextView payAmountId;
    private LinearLayout invoiceDetailsViewId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invoice_details);

        Toolbar mToolBar =  findViewById(R.id.invoice_ToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("PAYMENT INVOICE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        invoiceNumId = findViewById(R.id.invoiceNumId);
        invoiceDateId = findViewById(R.id.invoiceDateId);
        invoiceDetailsViewId = findViewById(R.id.invoiceDetailsViewId);
        payAmountId = findViewById(R.id.payAmountId);

        if(getIntent().hasExtra("invoiceDetails")){
            invoiceHeader = (InvoiceHeader) getIntent().getSerializableExtra("invoiceDetails");
            setupView();
        }
    }

    private void setupView() {
        invoiceNumId.setText(String.format("Trx ID: %s", invoiceHeader.getTransactionID()));
        invoiceDateId.setText(SimpleDateKt.toDateStandard(getDateFromString(invoiceHeader.getDate())));
        payAmountId.setText(String.format("%d/=", invoiceHeader.getTotal()));
        ArrayList<Invoice> invoices = invoiceHeader.getInvoices();

        if(invoices != null){
            for(int i = 0; i < invoices.size(); i++){
                setDetails(invoices.get(i), i + 1);
            }
        }
    }

    private void setDetails(Invoice item, int position) {

        View view = LayoutInflater.from(this).inflate(R.layout.single_invoice_info_view, invoiceDetailsViewId, false);

        ConstraintLayout due_row = view.findViewById(R.id.due_row);
        TextView dueNoId = view.findViewById(R.id.dueNoId);
        TextView descriptionViewId = view.findViewById(R.id.descriptionViewId);
        TextView monthNameViewId = view.findViewById(R.id.monthNameViewId);
        TextView statusViewId = view.findViewById(R.id.statusViewId);
        TextView amountViewId = view.findViewById(R.id.amountViewId);


        if(position % 2 == 0){
            due_row.setBackgroundColor(this.getResources().getColor(R.color.first_row));
        }
        else due_row.setBackgroundColor(this.getResources().getColor(R.color.second_row));

        dueNoId.setText(String.valueOf(position));

        descriptionViewId.setText(item.getAccount_head_name());
        descriptionViewId.setText(item.getAccount_head_name());

        String date = "" ;
        if(item.getCollected_month() <= 6 && invoiceHeader.getClassType().equals("College")){
            date = item.month() + " " + (item.getAcademic_year() + 1);
        }
        else date = item.month() + " " + item.getAcademic_year();

     //   date = item.month() + " " + item.getAcademic_year();

        monthNameViewId.setText(date);

        statusViewId.setText(item.getStatus());
        amountViewId.setText(String.valueOf(item.getDue_amount() + "/="));

        invoiceDetailsViewId.addView(view);
    }

    private Date getDateFromString(String dateString){
        System.out.println("????????? " + dateString);
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
            System.out.println("????????? " + date);
        } catch (Exception ignore) {
            ignore.printStackTrace();
            date = new Date();
        }
        return date;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}