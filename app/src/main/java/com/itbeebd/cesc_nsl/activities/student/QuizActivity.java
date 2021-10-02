package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.QuizAdapter;
import com.itbeebd.cesc_nsl.utils.dummy.Quiz;

import java.util.ArrayList;

public class QuizActivity extends AppCompatActivity {

    private RecyclerView quizRecyclerViewId;
    private ArrayList<Quiz> quizArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        Toolbar mToolBar =  findViewById(R.id.quizToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("QUIZ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quizRecyclerViewId = findViewById(R.id.quizRecyclerViewId);

        if(getIntent().hasExtra("quiz")){
            quizArrayList = (ArrayList<Quiz>) getIntent().getSerializableExtra("quiz");
            setupAdapter();
        }
    }

    private void setupAdapter() {
        if(quizArrayList != null){
            QuizAdapter quizAdapter = new QuizAdapter(this);
            quizAdapter.setItems(quizArrayList);
            quizRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
            quizRecyclerViewId.setAdapter(quizAdapter);
        }
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