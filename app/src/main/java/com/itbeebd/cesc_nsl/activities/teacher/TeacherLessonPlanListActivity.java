package com.itbeebd.cesc_nsl.activities.teacher;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.teacher.adapters.TeacherLessonPlanListAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.LessonApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.utils.dummy.TeacherLessonPlan;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class TeacherLessonPlanListActivity extends AppCompatActivity {

    private CardView a_classCardId;
    private CardView a_sectionCardId;

    private TextView a_classViewId;
    private TextView a_sectionViewId;

    private String[] classes;
    private String[] sections;

    private String selectedSection;
    private String selectedClass;

    private TeacherDao teacherDao;

    private RecyclerView lessonPlanListRecyclerViewId;
    private TeacherLessonPlanListAdapter planListAdapter;

    private ArrayList<TeacherLessonPlan> teacherLessonPlans;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lesson_plan_list);

        CustomSharedPref.getInstance(this).setUserModeNo(1);

        Toolbar mToolBar =  findViewById(R.id.teacherLessonPlanListToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("LESSON PLAN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        lessonPlanListRecyclerViewId = findViewById(R.id.lessonPlanListRecyclerViewId);
        a_classCardId = findViewById(R.id.a_classCardId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);

        teacherDao = new TeacherDao();
        classes = teacherDao.getAllClasses();

        a_classCardId.setOnClickListener(view -> {
            if(classes == null){
                Toast.makeText(this, "No class found", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(classes.length == 0){
                Toast.makeText(this, "Class list is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                initClassSpinner();
            }
        });

        a_sectionCardId.setOnClickListener(view -> {
            if(sections == null){
                Toast.makeText(this, "Select a class first", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(sections.length == 0){
                Toast.makeText(this, "No section found", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                initSectionSpinner();
            }
        });

      //  getTeacherLessonPlanList();

        setToolTip(a_classCardId, "Select a class");

    }

    private void getTeacherLessonPlanList() {
        new LessonApi(this, "Loading...").getTeacherLessonPlan(
                CustomSharedPref.getInstance(this).getAuthToken(),
                teacherDao.getClassIdByName(selectedClass),
                teacherDao.getSectionIdByName(selectedSection),
                (object, message) -> {
                    if(object != null){
                        this.teacherLessonPlans = (ArrayList<TeacherLessonPlan>) object;
                        setAdapter();

                        if(teacherLessonPlans != null && this.teacherLessonPlans.isEmpty())
                            Toast.makeText(this, "No lesson plan found", Toast.LENGTH_SHORT).show();
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void setAdapter(){
        if(teacherLessonPlans == null) return;

        planListAdapter = new TeacherLessonPlanListAdapter(this);
      //  planListAdapter.setListener(this);
        planListAdapter.setItems(teacherLessonPlans);
        lessonPlanListRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        lessonPlanListRecyclerViewId.setAdapter(planListAdapter);
    }


    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");
        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);
            a_sectionViewId.setText("Select");

            sections = teacherDao.getAllSectionByClassName(selectedClass);

            if(teacherLessonPlans != null) teacherLessonPlans.clear();
            setAdapter();

            System.out.println(">>>>>> class " + which);

            setToolTip(a_sectionCardId, "Select a section");
        });

        b.show();
    }

    private void initSectionSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a section");

        b.setItems(sections, (dialog, which) -> {
            System.out.println(">>>>>> section " + which);
            selectedSection = sections[which];
            a_sectionViewId.setText(selectedSection.substring(selectedSection.lastIndexOf("-") + 1));

            getTeacherLessonPlanList();

        });

        b.show();
    }

    private void setToolTip(CardView view, String tip){
        new SimpleTooltip.Builder(this)
                .anchorView(view)
                .text(tip)
                .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
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