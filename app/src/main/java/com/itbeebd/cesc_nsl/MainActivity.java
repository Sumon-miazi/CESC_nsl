package com.itbeebd.cesc_nsl;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.cesc_nsl.activities.teacher.TeacherDashboardActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.startActivity(new Intent(this, TeacherDashboardActivity.class));
        finish();
    }
}