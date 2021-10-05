package com.itbeebd.cesc_nsl.activities.teacher;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.itbeebd.cesc_nsl.R;

public class TeacherLessonPlanActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lesson_plan2);

        Toolbar mToolBar =  findViewById(R.id.guideStudentListToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("GUIDE STUDENT LIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


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