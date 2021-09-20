package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;

import com.itbeebd.cesc_nsl.R;

public class EditStudentProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);

        Toolbar mToolBar = findViewById(R.id.editProfileToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("EDIT PROFILE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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