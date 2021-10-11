package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.adapters.GuideStudentListAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.GuidedStudentApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.sugarClass.Student;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class GuideStudentListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Student> {

    private RecyclerView studentListRecyclerViewId;
    private CardView a_classCardId;
    private CardView a_sectionCardId;
    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private String[] classes;
    private String[] sections;
    private String selectedSection;
    private String selectedClass;
    private TeacherDao teacherDao;
    private ArrayList<Student> students;
    private GuideStudentListAdapter studentListAdapter;
    private boolean apiAlreadyCalled = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_student_list);

        Toolbar mToolBar =  findViewById(R.id.guideStudentListToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("GUIDE STUDENT LIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentListRecyclerViewId = findViewById(R.id.studentListRecyclerViewId);
        a_classCardId = findViewById(R.id.a_classCardId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);

        setToolTip(a_classCardId, "Select a class");

        studentListAdapter = new GuideStudentListAdapter(this);
        studentListAdapter.setListener(this);

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

    }

    private void setAdapter(){
        if(students == null) return;

        studentListAdapter.setItems(students);
        studentListRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        studentListRecyclerViewId.setAdapter(studentListAdapter);
    }

    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");

        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);
            a_sectionViewId.setText("Select");

            sections = teacherDao.getAllSectionByClassName(selectedClass);

            if(students != null) students.clear();
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

            System.out.println("ClassId " + teacherDao.getClassIdByName(selectedClass));
            System.out.println("SectionId " + teacherDao.getSectionIdByName(selectedSection));

             students = teacherDao.getAllGuidedStudentByClassSectionId(
                    teacherDao.getClassIdByName(selectedClass),
                    teacherDao.getSectionIdByName(selectedSection)
                    );

             if((students == null || students.isEmpty()) && !apiAlreadyCalled){
         //    if(students == null){
                 callGetGuidedStudentApi();
             }
             else if(students.isEmpty()){
                 Toast.makeText(this, "No student found", Toast.LENGTH_SHORT).show();
                 setAdapter();
             }
             else setAdapter();

        });

        b.show();
    }

    private void callGetGuidedStudentApi(){
        new GuidedStudentApi(this, "Loading...").getGuidedStudent(
                CustomSharedPref.getInstance(this).getAuthToken(),
                (isSuccess, message) -> {
                    if(isSuccess){
                        apiAlreadyCalled = true;
                        students = teacherDao.getAllGuidedStudentByClassSectionId(
                                teacherDao.getClassIdByName(selectedClass),
                                teacherDao.getSectionIdByName(selectedSection)
                        );
                        if(students != null && students.isEmpty())
                            Toast.makeText(this, "No student found!", Toast.LENGTH_SHORT).show();
                        setAdapter();
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );
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
    public void onItemClicked(Student item, View view) {

        new GuidedStudentApi(this, "Loading...").getStudentById(
                CustomSharedPref.getInstance(this).getAuthToken(),
                item.getStudentId(),
                (object, message) -> {
                    if(object != null){
                        System.out.println(">>>>>>> student name " + item.getName());
                        Intent intent = new Intent(this, GuidedStudentProfileActivity.class);
                        intent.putExtra("student", (Student)object);
                        startActivity(intent);
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );

    }
}