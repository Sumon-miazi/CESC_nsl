package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.teacherApi.LoginApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;

public class TeacherLoginActivity extends AppCompatActivity {

    private TextInputLayout userId;
    private TextInputLayout userPassword;
    private Button loginBtn;
 //   private RadioGroup userCategoryRadioId;
    private ImageView signInBackBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_login);

        userId = findViewById(R.id.userId);
        userPassword = findViewById(R.id.userPasswordId);
        loginBtn = findViewById(R.id.loginBtnId);

     //   userCategoryRadioId = findViewById(R.id.userCategoryRadioId);
        signInBackBtnId = findViewById(R.id.signInBackBtnId);


        loginBtn.setOnClickListener(view -> {
            loginCredentialValidate(userId.getEditText().getText().toString().trim(), userPassword.getEditText().getText().toString().trim());
        });

        signInBackBtnId.setOnClickListener(view -> {
            super.onBackPressed();
        //    startActivity(new Intent(this, MainActivity.class));
         //   finish();
        });

        //    new NotificationReminder(this).sendNotification("Hee", "hi", "/storage/emulated/0/CESC/LessonPlanFiles/fother.jpg");
    }

    private void loginCredentialValidate(String userId, String password) {
        if (userId.isEmpty()) Toast.makeText(this, "Enter user id", Toast.LENGTH_SHORT).show();
        if (password.isEmpty()) Toast.makeText(this, "Enter user password", Toast.LENGTH_SHORT).show();

        login(userId, password);
    }

    private void login(String userId, String password) {
        new LoginApi(this, "Signing in...").teacherLogin(userId, password, "123456", ((isSuccess, message) -> {
            if(isSuccess){
                CustomSharedPref.getInstance(this).setUserLoggedInOrNot(true);
                Intent intent = new Intent(this, TeacherDashboardActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                this.startActivity(intent);
                finish();
            }
            else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
        }));
    }

//    @Override
//    public void onBackPressed() {
//        // super.onBackPressed();
//        startActivity(new Intent(this, MainActivity.class));
//     //   finish();
//    }
}