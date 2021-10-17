package com.itbeebd.cesc_nsl.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;

public class ForgetPasswordActivity extends AppCompatActivity {

    private TextInputLayout userId;
    private RadioGroup emailOrPhoneId;
    private Button sendBtn;
    private Button submitBtn;
    private ImageView backBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        userId = findViewById(R.id.userId);
        emailOrPhoneId = findViewById(R.id.checkedEmailOrPhoneId);

        sendBtn = findViewById(R.id.otpCodeSendBtnId);
        submitBtn = findViewById(R.id.submitButtonId);
        backBtnId = findViewById(R.id.backBtnId);

        backBtnId.setOnClickListener(view -> {
            super.onBackPressed();
        });

    }
}