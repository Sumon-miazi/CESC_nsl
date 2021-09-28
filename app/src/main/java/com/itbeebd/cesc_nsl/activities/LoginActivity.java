package com.itbeebd.cesc_nsl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.StudentDashboardActivity;
import com.itbeebd.cesc_nsl.api.studentApi.LoginApi;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout userId;
    private TextInputLayout userPassword;
    private Button loginBtn;
    private RadioGroup userCategoryRadioId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userId = findViewById(R.id.userId);
        userPassword = findViewById(R.id.userPasswordId);
        loginBtn = findViewById(R.id.loginBtnId);

        userCategoryRadioId = findViewById(R.id.userCategoryRadioId);

        userCategoryRadioId.setOnCheckedChangeListener((radioGroup, i) -> {
            System.out.println(">>>>> radio group i " + i);
        });

        loginBtn.setOnClickListener(view -> {
            loginCredentialValidate(userId.getEditText().getText().toString().trim(), userPassword.getEditText().getText().toString().trim());
        });
    }

    private void loginCredentialValidate(String userId, String password) {
        if (userId.isEmpty()) Toast.makeText(this, "Enter user id", Toast.LENGTH_SHORT).show();
        if (password.isEmpty()) Toast.makeText(this, "Enter user password", Toast.LENGTH_SHORT).show();

        login(userId, password);
    }

    private void login(String userId, String password) {
        new LoginApi(this).studentLogin(userId, password, "123456", ((isSuccess, message) -> {
            if(isSuccess){
                this.startActivity(new Intent(this, StudentDashboardActivity.class));
                finish();
            }
            else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }));
    }
}