package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MenuItem;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.LessonPlanAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.utils.LessonPlan;
import com.itbeebd.cesc_nsl.utils.Notification;

import java.util.ArrayList;

public class LessonPlanActivity extends AppCompatActivity {

    private RecyclerView lessonPlanRecyclerView;
    private ArrayList<LessonPlan> lessonPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lesson_plan);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.lessonPlanToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("LESSON PLAN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lessonPlanRecyclerView = findViewById(R.id.lessonPlanBlockId2).findViewById(R.id.lessonPlanRecyclerViewId);

        if(getIntent().hasExtra("lessonPlan")){
            lessonPlans = (ArrayList<LessonPlan>) getIntent().getSerializableExtra("lessonPlan");

            if(lessonPlans != null) setLessonPlanAdapter();
        }

        System.out.println(">>>>>>> lesson " + lessonPlans.size() );
    }

    private void setLessonPlanAdapter(){
        LessonPlanAdapter lessonPlanAdapter = new LessonPlanAdapter(this);
        lessonPlanAdapter.setItems(lessonPlans);
        lessonPlanRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //    lessonPlanRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        lessonPlanRecyclerView.setAdapter(lessonPlanAdapter);
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