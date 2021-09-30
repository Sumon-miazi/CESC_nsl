package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.itbeebd.cesc_nsl.R;

public class QuizArchiveActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_archive);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.quizArchiveToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("QUIZ ARCHIVE");
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