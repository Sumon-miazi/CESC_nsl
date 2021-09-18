package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.studentApi.LoginApi;

public class StudentLoginActivity extends AppCompatActivity {

    private EditText userId;
    private EditText userPassword;
    private Button loginBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_login);

        userId = findViewById(R.id.userId);
        userPassword = findViewById(R.id.userPasswordId);
        loginBtn = findViewById(R.id.loginBtnId);

        loginBtn.setOnClickListener(view -> {
            loginCredentialValidate(userId.getText().toString().trim(), userPassword.getText().toString().trim());
        });
    }

    private void loginCredentialValidate(String userId, String password) {
        if (userId.isEmpty()) Toast.makeText(this, "Enter user id", Toast.LENGTH_SHORT).show();
        if (password.isEmpty()) Toast.makeText(this, "Enter user password", Toast.LENGTH_SHORT).show();

        login(userId, password);
    }

    private void login(String userId, String password) {
        new LoginApi().studentLogin(userId, password, "123456", ((isSuccess, message) -> {
            if(isSuccess){
                this.startActivity(new Intent(this, StudentDashboardActivity.class));
                finish();
            }
            else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }));
    }
}