package com.itbeebd.cesc_nsl.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.teacher.TeacherLoginActivity;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.LoginApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;

public class ChangePasswordActivity extends AppCompatActivity {

    private TextInputLayout currentPasswordId;
    private TextInputLayout newPasswordId;
    private TextInputLayout newPassword2Id;
    private ImageView signInBackBtnId;
    private Button changePassBtnId;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        currentPasswordId = findViewById(R.id.currentPasswordId);
        newPasswordId = findViewById(R.id.newPasswordId);
        newPassword2Id = findViewById(R.id.newPassword2Id);
        changePassBtnId = findViewById(R.id.changePassBtnId);
        signInBackBtnId = findViewById(R.id.signInBackBtnId);

        changePassBtnId.setOnClickListener(this::verifyData);

        signInBackBtnId.setOnClickListener(view -> {
            super.onBackPressed();
            //   startActivity(new Intent(this, MainActivity.class));
            //   finish();
        });
    }

    private void verifyData(View view) {
        String currentPass = currentPasswordId.getEditText().getText().toString().trim();
        String newPassword = newPasswordId.getEditText().getText().toString().trim();
        String newPassword2 = newPassword2Id.getEditText().getText().toString().trim();

        if(!checkEmpty(currentPasswordId, "Enter current password")) return;
        if(!checkEmpty(newPasswordId, "Enter new password")) return;
        if(!checkEmpty(newPassword2Id, "Enter new password again")) return;

        if(!currentPass.equals(CustomSharedPref.getInstance(this).getUserPassword())){
            currentPasswordId.setError("Wrong password");
            return;
        }

        if(!newPassword.equals(newPassword2)){
            newPassword2Id.setError("Doesn't match");
            newPassword2Id.getEditText().setText("");
            return;
        }

        checkUserType();
    }

    private void checkUserType(){
        if(CustomSharedPref.getInstance(this).getUserType().equals("student")){
           intent = new Intent(this, LoginActivity.class);
           callChangePasswordApi(ApiUrls.S_CHANGE_PASSWORD);
        }
        else if(CustomSharedPref.getInstance(this).getUserType().equals("teacher")){
            intent = new Intent(this, TeacherLoginActivity.class);
            callChangePasswordApi(ApiUrls.T_CHANGE_PASSWORD);
        }
    }

    private void callChangePasswordApi(String path) {

        String newPassword = newPasswordId.getEditText().getText().toString().trim();

        new LoginApi(this, "Updating password").changePassword(
                CustomSharedPref.getInstance(this).getAuthToken(),
                path,
                newPassword,
                (isSuccess, message) -> {
                    if(isSuccess){
                        changeActivity();
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void changeActivity(){
        CustomSharedPref.getInstance(this).setUserLoggedInOrNot(false);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        this.startActivity(intent);
        finish();
    }

    private boolean checkEmpty(TextInputLayout textInputLayout, String errorMessage){
        System.out.println(errorMessage);
        if(textInputLayout.getEditText().getText().toString().isEmpty()) {
            textInputLayout.setError(errorMessage);
            return false;
        }
        else {
            textInputLayout.setErrorEnabled(false);
            return true;
        }
    }

}