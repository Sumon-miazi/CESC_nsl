package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.adapters.StudentListAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.GuidedStudentApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.sugarClass.Student;

import java.util.ArrayList;
import java.util.Calendar;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class StudentListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Student> {

    private CardView a_yearCardId;
    private CardView a_classCardId;
    private CardView a_sectionCardId;
    private CardView statusCardId;
    private CardView a_filterCardId;

    private TextView a_yearViewId;
    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private TextView a_statusViewId;
    private TextView a_filterViewId;
    private TextInputLayout filterBoxId;
    private Button searchBtnId;
    private RecyclerView a_studentListRecyclerViewId;
    private StudentListAdapter studentListAdapter;

    private String[] classes;
    private String[] sections;
    private String[] years;
    private String[] status;
    private String[] filter;

    private String selectedSection;
    private String selectedClass;
    private String selectedYear;
    private String selectedStatus;
    private String selectedFilter;

    private TeacherDao teacherDao;
    private ArrayList<Student> students;

    private final int initialYear = 2002;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_list);

        Toolbar mToolBar =  findViewById(R.id.studentList_ToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("STUDENT LIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a_yearCardId = findViewById(R.id.a_yearCardId);
        a_classCardId = findViewById(R.id.a_classCardId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        statusCardId = findViewById(R.id.statusCardId);
        a_filterCardId = findViewById(R.id.a_filterCardId);

        a_yearViewId = findViewById(R.id.a_yearViewId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);
        a_statusViewId = findViewById(R.id.a_statusViewId);
        a_filterViewId = findViewById(R.id.a_filterViewId);

        filterBoxId = findViewById(R.id.filterBoxId);
        searchBtnId = findViewById(R.id.searchBtnId);
        a_studentListRecyclerViewId = findViewById(R.id.a_studentListRecyclerViewId);

        searchBtnId.setOnClickListener(this::filterStudent);

        setInitialView();

        a_yearCardId.setOnClickListener(view -> initYearSpinner());

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

        statusCardId.setOnClickListener(view -> initStatusSpinner());

        a_filterCardId.setOnClickListener(view -> initFilterSpinner());

        getWindow().setSoftInputMode(
                WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN
        );


        // call api
        filterStudent(null);
    }

    private void setInitialView() {
        teacherDao = new TeacherDao();
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        generateYear(mYear);

        a_yearViewId.setText(years[0]);
        selectedYear = years[0];

        classes = teacherDao.getAllClasses();
        a_classViewId.setText(classes.length > 0? classes[0] : "Select");
        selectedClass = classes.length > 0? classes[0] : null;

        sections = teacherDao.getAllSectionByClassName(selectedClass);
        selectedSection = sections.length > 0? sections[0] : null;
        a_sectionViewId.setText(selectedSection != null? selectedSection.substring(selectedSection.lastIndexOf("-") + 1) : "Select");


        filter = new String[]{
                "Student Name",
                "Student ID",
                "Roll No",
                "Email",
                "Mother Name",
                "Father Name"
        };

        a_filterViewId.setText(String.format(filter[0]));
        selectedFilter = "name";

        status = new String[]{"None", "Active", "Inactive", "Alumni"};
        selectedStatus = status[0];
        a_statusViewId.setText(selectedStatus);
    }

    private void initYearSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a year");

        b.setItems(years, (dialog, which) -> {
            System.out.println(">>>>>> years " + which);
            selectedYear = years[which];
            a_yearViewId.setText(selectedYear);
        });

        b.show();
    }

    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");
        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);

            sections = teacherDao.getAllSectionByClassName(selectedClass);
            selectedSection = sections.length > 0? sections[0] : null;
            a_sectionViewId.setText(selectedSection != null? selectedSection.substring(selectedSection.lastIndexOf("-") + 1) : "Select");

            System.out.println(">>>>>> class " + which);

          //  setToolTip(a_sectionCardId, "Select a section");
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
        });

        b.show();
    }

    private void initFilterSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select one");

        b.setItems(filter, (dialog, which) -> {
            System.out.println(">>>>>> filter " + which);

            switch (filter[which]) {
                case "Student Name":
                    selectedFilter = "name";
                    break;
                case "Student ID":
                    selectedFilter = "studentid";
                    break;
                case "Roll No":
                    selectedFilter = "roll";
                    break;
                case "Email":
                    selectedFilter = "email";
                    break;
                case "Mother Name":
                    selectedFilter = "mother_info.name";
                    break;
                case "Father Name":
                    selectedFilter = "father_info.name";
                    break;
            }

            a_filterViewId.setText(filter[which]);

            filterBoxId.setHint("Enter " + filter[which]);
        });

        b.show();
    }

    private void initStatusSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a status");

        b.setItems(status, (dialog, which) -> {
            System.out.println(">>>>>> status " + which);
            selectedStatus = status[which];
            a_statusViewId.setText(selectedStatus);
        });

        b.show();
    }

    private void filterStudent(View view) {

        new GuidedStudentApi(this, "Loading...").getStudentList(
                CustomSharedPref.getInstance(this).getAuthToken(),
                selectedYear,
                teacherDao.getClassIdByName(selectedClass),
                teacherDao.getSectionIdByName(selectedSection),
                selectedStatus.equals("None")? null : selectedStatus,
                selectedFilter,
                filterBoxId.getEditText().getText().toString(),
                (object, message) -> {

                    if(students != null) students.clear();

                    if(object != null){
                        students = (ArrayList<Student>) object;
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                    setAdapter();
                }
        );

    }

    private void setAdapter(){
        if(students == null) return;
        studentListAdapter = new StudentListAdapter(this);
        studentListAdapter.setListener(this);
        studentListAdapter.setItems(students);
        a_studentListRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        a_studentListRecyclerViewId.setAdapter(studentListAdapter);
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

    private void generateYear(int year){
        int diff = (year + 1) - initialYear;
        years = new String[diff];
        int j = 0;
        for(int i = diff - 1; i >= 0; i--)
            years[j++] = String.valueOf(initialYear + i);

    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        //  System.out.println("ev.toString() " + ev.toString());
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
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