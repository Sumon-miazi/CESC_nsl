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
import com.itbeebd.cesc_nsl.activities.teacher.adapters.AttendanceListAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.AttendanceApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.utils.dummy.AttendanceList;

import java.util.ArrayList;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class AttendanceListActivity extends AppCompatActivity {

    private RecyclerView attendanceListRecyclerViewId;
    private AttendanceListAdapter listAdapter;
    private CardView a_classCardId;
    private CardView a_sectionCardId;

    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private String[] classes;
    private String[] sections;

    private String selectedSection;
    private String selectedClass;

    private TeacherDao teacherDao;

    private ArrayList<AttendanceList> attendanceLists;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_list);

        Toolbar mToolBar =  findViewById(R.id.attendance_list_ToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("ATTENDANCE LIST");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        attendanceListRecyclerViewId = findViewById(R.id.attendanceListRecyclerViewId);
        a_classCardId = findViewById(R.id.a_classCardId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);

    //    callAttendanceListApi();

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

        setToolTip(a_classCardId, "Select a class");
    }

    private void callAttendanceListApi() {
        new AttendanceApi(this, "Loading...").getAttendanceList(
                CustomSharedPref.getInstance(this).getAuthToken(),
                teacherDao.getClassIdByName(selectedClass),
                teacherDao.getSectionIdByName(selectedSection),
                (object, message) -> {
                    if(object != null){
                        this.attendanceLists = (ArrayList<AttendanceList>) object;
                        setAdapter();
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }

        );
    }

    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");

        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);
            a_sectionViewId.setText("Select");

            sections = teacherDao.getAllSectionByClassName(selectedClass);

            if(attendanceLists != null) attendanceLists.clear();
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
            if(this.attendanceLists != null) attendanceLists.clear();
            setAdapter();

            callAttendanceListApi();

        });

        b.show();
    }

    private void setAdapter(){
        if(attendanceLists == null) return;

        listAdapter = new AttendanceListAdapter(this);
     //   listAdapter.setListener(this);
        listAdapter.setItems(attendanceLists);
        attendanceListRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        attendanceListRecyclerViewId.setAdapter(listAdapter);
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