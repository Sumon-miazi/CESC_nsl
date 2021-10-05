package com.itbeebd.cesc_nsl.activities.teacher;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.adapters.AttendanceAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.AttendanceApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.utils.dummy.ClassAttendance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

public class AttendanceActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<ClassAttendance> {

    private RecyclerView attendanceRecyclerView;
    private CardView a_classCardId;
    private CardView a_sectionCardId;
    private CardView a_dateCardId;

    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private TextView a_dateViewId;
    private TextView monthNameViewId;

    private Button attendanceSubmitBtnId;

    private String[] classes;
    private String[] sections;

    private String selectedSection;
    private String selectedClass;

    private TeacherDao teacherDao;
    private ArrayList<ClassAttendance> attendances =  new ArrayList<>();
    private AttendanceAdapter attendanceAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Toolbar mToolBar =  findViewById(R.id.attendance_ToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("ATTENDANCE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        attendanceRecyclerView = findViewById(R.id.attendanceRecyclerViewId);
        a_classCardId = findViewById(R.id.a_classCardId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);
        monthNameViewId = findViewById(R.id.monthNameViewId);
        a_dateViewId = findViewById(R.id.a_dateViewId);
        a_dateCardId = findViewById(R.id.a_dateCardId);
        attendanceSubmitBtnId = findViewById(R.id.attendanceSubmitBtnId);

        setCurrentDate();

        attendanceAdapter = new AttendanceAdapter(this);
        attendanceAdapter.setListener(this);

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

        a_dateCardId.setOnClickListener(view -> datePicker());
        attendanceSubmitBtnId.setOnClickListener(view -> callAttendanceApi());

    }

    private void callAttendanceApi() {
        new AttendanceApi(this, "Sending...").studentAttendance(
                CustomSharedPref.getInstance(this).getAuthToken(),
                generateJson(),
                (isSuccess, message) -> {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );
    }

    private JsonObject generateJson() {

        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i < attendances.size(); i++){
            ClassAttendance attendance = attendances.get(i);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("roll", attendance.getRoll());
                jsonObject.put("name", attendance.getName());
                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject temp = new JSONObject();
        try {
            temp.put("data", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(temp.toString());
        return gsonObject;
    }

    private void setAdapter(){
        attendanceAdapter.setItems(attendances);
        attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendanceRecyclerView.setAdapter(attendanceAdapter);
    }

    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");

        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);
            a_sectionViewId.setText("Select");

            sections = teacherDao.getAllSectionByClassName(selectedClass);
            attendanceSubmitBtnId.setVisibility(View.GONE);
            attendances.clear();
            setAdapter();

            System.out.println(">>>>>> class " + which);
        });

        b.show();
    }

    private void clearAttendanceArrayListAndRefreshAdapter(){
        if(attendances != null) attendances.clear();

        attendanceAdapter.notifyDataSetChanged();
    }

    private void initSectionSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a section");

        b.setItems(sections, (dialog, which) -> {
            System.out.println(">>>>>> section " + which);
            selectedSection = sections[which];
            a_sectionViewId.setText(selectedSection.substring(selectedSection.lastIndexOf("-") + 1));

            attendances = new ArrayList<>();
            attendances.add(new ClassAttendance("Arif", "1234"));
            attendances.add(new ClassAttendance("Suman", "32"));
            attendances.add(new ClassAttendance("Shawon", "234"));
            attendances.add(new ClassAttendance("Borhan", "876"));

            attendanceSubmitBtnId.setVisibility(View.VISIBLE);
            setAdapter();

        });

        b.show();
    }

    private void datePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {

                //    a_dateViewId.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    a_dateViewId.setText(String.valueOf(dayOfMonth));
                    monthNameViewId.setText(String.format("%s, %d", getMonth(monthOfYear), year));

                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
       // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void setCurrentDate(){
        final Calendar c = Calendar.getInstance();
        int mYear = c.get(Calendar.YEAR);
        int mMonth = c.get(Calendar.MONTH);
        int mDay = c.get(Calendar.DAY_OF_MONTH);

        a_dateViewId.setText(String.valueOf(mDay));
        monthNameViewId.setText(String.format("%s, %d", getMonth(mMonth), mYear));
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

    @Override
    public void onItemClicked(ClassAttendance item, View view) {
        for(int i = 0; i < attendances.size(); i++){
            if(item.getStudentId() == attendances.get(i).getStudentId()){
                attendances.get(i).setPresent();
                Toast.makeText(this, item.getRoll(), Toast.LENGTH_SHORT).show();
                System.out.println("Roll " + attendances.get(i).getRoll());
                break;
            }
        }
    }

    private String getMonth(int index){

        String[] month = {
                "January",
                "February",
                "March",
                "April",
                "May",
                "June",
                "July",
                "August",
                "September",
                "October",
                "November",
                "December"
        };

        return month[index];
    }
}