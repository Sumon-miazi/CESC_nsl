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
import com.itbeebd.cesc_nsl.activities.teacher.adapters.GuideStudentListAdapter;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.sugarClass.Student;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class GuideStudentListActivity extends AppCompatActivity {

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
    private ArrayList<Student> students =  new ArrayList<>();
    private GuideStudentListAdapter studentListAdapter;

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
     //   studentListAdapter.setListener(this);

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

            students.clear();
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

            students = new ArrayList<>();
            Student student = new Student();
            student.setName("Suman");
            student.setRoll(123);
            student.setStudentId(2345678);
            student.setMotherName("Jesmin");
            student.setMobile("098765432");
            student.setPresent_address("kjdflka dfljal fdlajkfla dslfakjdsfl aldf al flaksjdflaksdjf");


            students.add(student);
            students.add(student);
            students.add(student);
            students.add(student);
            students.add(student);
            students.add(student);
            students.add(student);

            setAdapter();

        });

        b.show();
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
}