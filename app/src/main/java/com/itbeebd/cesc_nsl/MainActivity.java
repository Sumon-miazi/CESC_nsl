package com.itbeebd.cesc_nsl;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.cesc_nsl.activities.LoginActivity;
import com.itbeebd.cesc_nsl.activities.student.StudentDashboardActivity;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        boolean flag = CustomSharedPref.getInstance(this).getStudentLoggedInOrNot();
        if(flag) this.startActivity(new Intent(this, StudentDashboardActivity.class));
        else this.startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}