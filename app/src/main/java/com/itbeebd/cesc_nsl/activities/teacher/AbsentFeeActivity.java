package com.itbeebd.cesc_nsl.activities.teacher;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.teacher.adapters.AbsentFeeAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.AttendanceApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.utils.dummy.AbsentFeeObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class AbsentFeeActivity extends AppCompatActivity {

    private ConstraintLayout absentFeeHeaderInfoId;

    private CardView a_classCardId;
    private CardView a_sectionCardId;
    private CardView a_fromDateCardId;
    private CardView a_toDateCardId;

    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private TextView a_fromDateViewId;
    private TextView a_toDateViewId;

    private Button submitBtnId;
    private Button approvedBtnId;

    private RecyclerView absentListRecyclerViewId;

    private String[] classes;
    private String[] sections;

    private String selectedSection;
    private String selectedClass;
    private String fromDate;
    private String toDate;

    private TeacherDao teacherDao;
    private ArrayList<AbsentFeeObject> absentFeeObjects ;
    private AbsentFeeAdapter absentFeeAdapter;

    private int mYear;
    private int mMonth;
    private int mDay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_absent_fee);

        Toolbar mToolBar =  findViewById(R.id.absentFeeToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("ABSENT FEE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        absentFeeHeaderInfoId = findViewById(R.id.absentFeeHeaderInfoId);
        a_classCardId = findViewById(R.id.a_classCardId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        a_fromDateCardId = findViewById(R.id.a_fromDateCardId);
        a_toDateCardId = findViewById(R.id.a_toDateCardId);

        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);
        a_fromDateViewId = findViewById(R.id.a_fromDateViewId);
        a_toDateViewId = findViewById(R.id.a_toDateViewId);

        submitBtnId = findViewById(R.id.submitBtnId);
        approvedBtnId = findViewById(R.id.approvedBtnId);

        absentListRecyclerViewId = findViewById(R.id.absentListRecyclerViewId);

        submitBtnId.setOnClickListener(this::getAbsentReport);

        teacherDao = new TeacherDao();
        classes = teacherDao.getAllClasses();
        setCurrentDate();

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

        a_fromDateCardId.setOnClickListener(this::datePicker);

        a_toDateCardId.setOnClickListener(this::datePicker);

        approvedBtnId.setOnClickListener(this::approvedBtnClicked);

        setToolTip(a_classCardId, "Select a class");
    }

    private void approvedBtnClicked(View view) {
        new AttendanceApi(this, "Submitting...").attendanceFeeApproved(
                CustomSharedPref.getInstance(this).getAuthToken(),
                generateJson(),
                (isSuccess, message) -> {
                    if(isSuccess){
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void setAdapter(){
        if(absentFeeObjects == null) return;
        absentFeeAdapter = new AbsentFeeAdapter(this);
     //   attendanceAdapter.setListener(this);
        absentFeeAdapter.setItems(absentFeeObjects);
        absentListRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        absentListRecyclerViewId.setAdapter(absentFeeAdapter);

        absentFeeHeaderInfoId.setVisibility(absentFeeObjects.isEmpty()? View.GONE : View.VISIBLE);
        approvedBtnId.setVisibility(absentFeeObjects.isEmpty()? View.GONE : View.VISIBLE);
    }

    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");
        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);
            a_sectionViewId.setText("Select");

            sections = teacherDao.getAllSectionByClassName(selectedClass);

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
        });

        b.show();
    }

    private void datePicker(View v){

        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    this.mDay = dayOfMonth;
                    this.mMonth = monthOfYear;
                    this.mYear = year;

                    if(v.getId() == R.id.a_fromDateCardId){
                        fromDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        a_fromDateViewId.setText(fromDate);
                    }
                    else if(v.getId() == R.id.a_toDateCardId){
                        toDate = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
                        a_toDateViewId.setText(toDate);
                    }
                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void setCurrentDate(){
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        toDate = mYear + "-" + (mMonth + 1) + "-" + mDay;
        a_toDateViewId.setText(toDate);
    }

    private void getAbsentReport(View view) {
        if(selectedClass == null){
            Toast.makeText(this, "Select a class", Toast.LENGTH_SHORT).show();
            return;
        }
        if(selectedSection == null){
            Toast.makeText(this, "Select a section", Toast.LENGTH_SHORT).show();
            return;
        }
        if(fromDate == null){
            Toast.makeText(this, "Select from date", Toast.LENGTH_SHORT).show();
            return;
        }
        if(toDate == null){
            Toast.makeText(this, "Select to date", Toast.LENGTH_SHORT).show();
            return;
        }
        new AttendanceApi(this, "Loading...").getAttendanceSummery(
                CustomSharedPref.getInstance(this).getAuthToken(),
                teacherDao.getClassIdByName(selectedClass),
                teacherDao.getSectionIdByName(selectedSection),
                fromDate,
                toDate,
                (object, message) -> {
                    if(absentFeeObjects != null) absentFeeObjects.clear();
                    if(object != null){
                        absentFeeObjects = (ArrayList<AbsentFeeObject>) object;
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();

                    setAdapter();
                }

        );
    }

    private JsonObject generateJson() {

        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i < absentFeeObjects.size(); i++){
            AbsentFeeObject absentFeeObject = absentFeeObjects.get(i);
            if(absentFeeObject.getTotalAbsent() > 0){
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", absentFeeObject.getId());
                    jsonObject.put("name", absentFeeObject.getStudentName());
                    jsonObject.put("payment_category", absentFeeObject.getPaymentCategory());
                    jsonObject.put("studentid", absentFeeObject.getStudentId());
                    jsonObject.put("roll", absentFeeObject.getRoll());
                    jsonObject.put("total_working_day", absentFeeObject.getTotalDays());
                    jsonObject.put("total_present", absentFeeObject.getTotalPresent());
                    jsonObject.put("total_absent", absentFeeObject.getTotalAbsent());
                    jsonObject.put("weber", absentFeeObject.getWeber());
//                jsonObject.put("name", attendance.getName());
                    jsonArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
        JSONObject temp = new JSONObject();
        try {
            temp.put("std_class_id", teacherDao.getClassIdByName(selectedClass));
            temp.put("section_id", teacherDao.getSectionIdByName(selectedSection));
            temp.put("from_date", fromDate);
            temp.put("to_date", toDate);
            temp.put("student", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(temp.toString());
        return gsonObject;
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