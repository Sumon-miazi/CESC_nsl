package com.itbeebd.cesc_nsl.activities.teacher;

import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.adapters.AttendanceAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.AttendanceApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.utils.dummy.AttendanceList;
import com.itbeebd.cesc_nsl.utils.dummy.ClassAttendance;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class AttendanceViewOrEditActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<ClassAttendance> {

    private RecyclerView attendanceRecyclerView;

    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private TextView a_dateViewId;
    private TextView monthNameViewId;
    private TextInputLayout remarksId;
    private SwitchMaterial attendanceEditId;

    private Button attendanceSubmitBtnId;

    private TeacherDao teacherDao;
    private ArrayList<ClassAttendance> attendances;
    private AttendanceAdapter attendanceAdapter;

    private AttendanceList attendanceList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance);

        Toolbar mToolBar = findViewById(R.id.attendance_ToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("ATTENDANCE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        attendances = new ArrayList<>();

        attendanceRecyclerView = findViewById(R.id.attendanceRecyclerViewId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);
        monthNameViewId = findViewById(R.id.monthNameViewId);
        a_dateViewId = findViewById(R.id.a_dateViewId);
        remarksId = findViewById(R.id.remarksId);
        attendanceEditId = findViewById(R.id.attendanceEditId);

        attendanceSubmitBtnId = findViewById(R.id.attendanceSubmitBtnId);
        attendanceSubmitBtnId.setText("update");
        attendanceEditId.setVisibility(View.VISIBLE);
        remarksId.setEnabled(false);

        teacherDao = new TeacherDao();

        attendanceSubmitBtnId.setOnClickListener(view -> callAttendanceApi());

        attendanceEditId.setOnClickListener((view) -> {
            attendanceSubmitBtnId.setVisibility(attendanceEditId.isChecked()? View.VISIBLE : View.GONE);
            remarksId.setEnabled(attendanceEditId.isChecked());

            if(attendanceEditId.isChecked()){ CustomSharedPref.getInstance(this).setUserModeNo(2); }
            else CustomSharedPref.getInstance(this).setUserModeNo(1);
            enableOrDisableCheckbox();
        });

        if(getIntent().hasExtra("AttendanceList")){
            this.attendanceList = (AttendanceList) getIntent().getSerializableExtra("AttendanceList");
            setHeaderValue();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        CustomSharedPref.getInstance(this).setUserModeNo(1);
    }

    private void setHeaderValue() {
        a_classViewId.setText(attendanceList.getClassName());
        a_sectionViewId.setText(attendanceList.getSectionName().substring(attendanceList.getSectionName().lastIndexOf("-") + 1));

        String[] dateSplit = attendanceList.getDate().split("-");
        a_dateViewId.setText(dateSplit[2]);
        monthNameViewId.setText(String.format("%s, %s", getMonth(Integer.parseInt(dateSplit[1])), dateSplit[0]));

        remarksId.getEditText().setText(attendanceList.getRemarks());

        getStudents();
    }

    private void callAttendanceApi() {
        new AttendanceApi(this, "Sending...").studentAttendance(
                CustomSharedPref.getInstance(this).getAuthToken(),
                generateJson(),
                (isSuccess, message) -> {
                    if(isSuccess){
                        Toast.makeText(this, "Attendance updated successfully", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }

        );
    }

    private JsonObject generateJson() {

        JSONArray jsonArray = new JSONArray();

        for (int i = 0; i < attendances.size(); i++) {
            ClassAttendance attendance = attendances.get(i);
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("id", attendance.getId());
                jsonObject.put("present", attendance.isPresent());
//                jsonObject.put("name", attendance.getName());
                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        JSONObject temp = new JSONObject();
        try {
            temp.put("std_class_id", attendanceList.getClassId());
            temp.put("section_id", attendanceList.getSectionId());
            temp.put("attendance_date", attendanceList.getDate());
            temp.put("remarks", remarksId.getEditText().getText().toString().trim());
            temp.put("student_has_attendance", jsonArray);

            System.out.println("selectedClass " + attendanceList.getClassName());
            System.out.println("selectedSection " + attendanceList.getSectionName());
            System.out.println("selectedClass Id " + attendanceList.getClassId());
            System.out.println("selectedSection id " + attendanceList.getSectionId());

        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(temp.toString());
        return gsonObject;
    }

    private void setAdapter() {
        attendanceAdapter = new AttendanceAdapter(this);
        attendanceAdapter.setListener(this);
        attendanceAdapter.setItems(attendances);
        attendanceRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        attendanceRecyclerView.setAdapter(attendanceAdapter);
    }

    private void getStudents() {
        new AttendanceApi(this, "Loading...").getAttendanceByAttendanceId(
                CustomSharedPref.getInstance(this).getAuthToken(),
                attendanceList.getId(),
                (object, message) -> {
                    if (object != null) {
                        this.attendances = (ArrayList<ClassAttendance>) object;
                      //  attendanceSubmitBtnId.setVisibility(View.VISIBLE);
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

    @Override
    public void onItemClicked(ClassAttendance item, View view) {
     //   if(attendanceEditId.isChecked()){
            for (int i = 0; i < attendances.size(); i++) {
                if (item.getStudentid() == attendances.get(i).getStudentid()) {
                    ClassAttendance classAttendance = attendances.get(i);
                    classAttendance.setPresent();

                    attendances.set(i, classAttendance);
                    //    attendanceAdapter.notifyItemChanged(i);
                    refreshAdapter(i);

                //    Toast.makeText(this, attendances.get(i).getRoll(), Toast.LENGTH_SHORT).show();
                    System.out.println("Roll " + attendances.get(i).getRoll());
                    break;
                }
            }
     //   }
    }

    private void refreshAdapter(int i) {
        attendanceRecyclerView.post(() -> {
            attendanceAdapter.notifyItemChanged(i);
        });
    }

    private void enableOrDisableCheckbox() {
        setAdapter();
    }

    private String getMonth(int index) {

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