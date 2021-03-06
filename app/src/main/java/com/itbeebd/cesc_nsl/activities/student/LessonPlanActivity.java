package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.LessonPlanAdapter;
import com.itbeebd.cesc_nsl.utils.dummy.LessonPlan;

import java.util.ArrayList;

public class LessonPlanActivity extends AppCompatActivity  {

    private RecyclerView lessonPlanRecyclerView;
    private ArrayList<LessonPlan> lessonPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_plan);

        Toolbar mToolBar =  findViewById(R.id.lessonPlanToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("LESSON PLAN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lessonPlanRecyclerView = findViewById(R.id.lessonPlanBlockId2).findViewById(R.id.lessonPlanRecyclerViewId);

        if(getIntent().hasExtra("lessonPlan")){
            lessonPlans = (ArrayList<LessonPlan>) getIntent().getSerializableExtra("lessonPlan");

            if(lessonPlans != null) setLessonPlanAdapter();
        }
    }

    private void setLessonPlanAdapter(){
        LessonPlanAdapter lessonPlanAdapter = new LessonPlanAdapter(this);
        lessonPlanAdapter.setItems(lessonPlans);
        lessonPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        lessonPlanRecyclerView.setAdapter(lessonPlanAdapter);
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