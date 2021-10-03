package com.itbeebd.cesc_nsl.activities.student;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.PaymentApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.utils.DBBL;
import com.parassidhu.simpledate.SimpleDateKt;

import java.util.Date;

public class CheckoutActivity extends AppCompatActivity {

    private RadioGroup dbblCardOptionId;
    private RadioGroup bankOpId;
    private Button payNowId;

    private boolean isTblSelected;
    private int dbblCardNo = 0;

    private Student student;
    private String TBL_API_URL;
    private String MERCHANT;
    private String SUCCESS_URL;

    private String amount;
    private String invoiceNo;
    private String transactionID;
    private String account_id;

    private TextView invoiceNoId;
    private TextView totalAmountId;
    private TextView studentNameId;
    private TextView studentId;
    private TextView dateId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        Toolbar mToolBar =  findViewById(R.id.checkout_ToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("CHECK OUT");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        invoiceNoId = findViewById(R.id.invoiceNoId);
        totalAmountId = findViewById(R.id.totalAmountId);
        studentNameId = findViewById(R.id.studentNameId);
        studentId = findViewById(R.id.studentId);
        dateId = findViewById(R.id.dateId);

        payNowId = findViewById(R.id.payNowId);
        bankOpId = findViewById(R.id.bankOpId);
        dbblCardOptionId = findViewById(R.id.dbblCardOptionId);

        setVariable();

        if(getIntent().hasExtra("amount")){
            this.amount = getIntent().getStringExtra("amount");
            this.invoiceNo = getIntent().getStringExtra("invoiceNo");
            this.transactionID = getIntent().getStringExtra("transactionID");
            this.account_id = getIntent().getStringExtra("account_id");

            setData();
        }

        bankOpId.setOnCheckedChangeListener((radioGroup, i) -> {
            System.out.println(">>>>>>> radio group " +  radioGroup.getCheckedRadioButtonId());
            if( radioGroup.getCheckedRadioButtonId() == R.id.tblBtnId){
                isTblSelected = true;
                dbblCardOptionId.setVisibility(View.GONE);
                payNowId.setVisibility(View.VISIBLE);
                dbblCardNo = 0;

            }
            else if( radioGroup.getCheckedRadioButtonId() == R.id.dbblBtnId){
                dbblCardOptionId.setVisibility(View.VISIBLE);
                isTblSelected = false;
                dbblCardOptionId.clearCheck();
                payNowId.setVisibility(View.INVISIBLE);
            }
        });

        dbblCardOptionId.setOnCheckedChangeListener((radioGroup, i) -> {
            payNowId.setVisibility(View.VISIBLE);

            if( radioGroup.getCheckedRadioButtonId() == R.id.dbblNexusId){
                dbblCardNo = 1;
            }
            else if( radioGroup.getCheckedRadioButtonId() == R.id.dbblMarterDebitBtnId){
                dbblCardNo = 2;
            }
            else if( radioGroup.getCheckedRadioButtonId() == R.id.dbblVisaDebitBtnId){
                dbblCardNo = 3;
            }
            else if( radioGroup.getCheckedRadioButtonId() == R.id.anyBVisaBtnId){
                dbblCardNo = 4;
            }
            else if( radioGroup.getCheckedRadioButtonId() == R.id.anyBMasterBtnId){
                dbblCardNo = 5;
            }
            else if( radioGroup.getCheckedRadioButtonId() == R.id.dbblRocketBtnId){
                dbblCardNo = 6;
            }

            System.out.println("DBBL card no >>>>> " + dbblCardNo);
        });

        payNowId.setOnClickListener(view -> {
            if(isTblSelected){
                if(transactionID != null && amount != null){
                    tblPayment(transactionID, amount);
                }
            }
            else{
                if(dbblCardNo != 0){
                    if(account_id != null && student != null){
                        DBBL dbbl = new DBBL(
                                amount,
                                String.valueOf(dbblCardNo),
                                String.valueOf( student.getStudentId()),
                                account_id
                        );
                        dbblPayment(dbbl);
                    }
                    else {
                        Toast.makeText(this, "Account id or Student info is null.", Toast.LENGTH_SHORT).show();
                    }
                }
                else Toast.makeText(this, "Select a card first", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void setData() {
        invoiceNoId.setText(String.format("Invoice no: %s", invoiceNo));
        totalAmountId.setText(String.format("%s Taka", amount));
        studentNameId.setText(student.getName());
        studentId.setText(String.format("Student ID: %s", student.getStudentId()));
        Date date = new Date();
        dateId.setText(SimpleDateKt.toDateEMY(date));
    }

    private void setVariable() {
        this.student = new StudentDao().getStudent(this);
        this.TBL_API_URL = ApiUrls.TBL_API_URL;
        this.MERCHANT = ApiUrls.MERCHANT;
        this.SUCCESS_URL = ApiUrls.SUCCESS_URL;
    }

    private void tblPayment(String transactionID, String amount){
        if(student == null){
            Toast.makeText(this, "Student info not found", Toast.LENGTH_SHORT).show();
            return;
        }
        String uri = Uri.parse(TBL_API_URL)
                .buildUpon()
                .appendQueryParameter("OrderID", transactionID)
                .appendQueryParameter("Amount", amount)
                .appendQueryParameter("FullName", /* StudentId */ String.valueOf(student.getStudentId()))
                .appendQueryParameter("Email", student.getEmail())
                .appendQueryParameter("MerchantID", this.MERCHANT )
                .appendQueryParameter("PaymentSuccessUrl", this.SUCCESS_URL)
                .build().toString();
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

        CustomSharedPref.getInstance(this).setPayNowClicked(true);

        startActivity(browserIntent);
    }

    private void dbblPayment(DBBL dbbl){

        new PaymentApi(this).getDbblUrl(
                CustomSharedPref.getInstance(this).getAuthToken(),
                dbbl,
                (isSuccess, message) -> {
                    if(isSuccess){
                        if(message != null){
                            String uri = Uri.parse(message)
                                    .buildUpon()
                                    .build().toString();
                            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));

                            CustomSharedPref.getInstance(this).setPayNowClicked(true);

                            startActivity(browserIntent);
                        }
                    }
                    else  Toast.makeText(this, message, Toast.LENGTH_LONG).show();
                }
        );
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