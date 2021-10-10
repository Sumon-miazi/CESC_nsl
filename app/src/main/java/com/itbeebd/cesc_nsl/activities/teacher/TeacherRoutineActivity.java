package com.itbeebd.cesc_nsl.activities.teacher;

import android.os.Bundle;
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
import com.itbeebd.cesc_nsl.activities.teacher.adapters.TeacherRoutineAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.TeacherRoutineApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.dummy.TeacherRoutine;

import java.util.ArrayList;
import java.util.Map;

public class TeacherRoutineActivity extends AppCompatActivity {

    private CardView weeklyDayCardId;
    private TextView weeklyDayId;
    private RecyclerView weeklyDayRecyclerViewId;

    private String[] weeklyDays;
    private String selectedDay;
    private TeacherRoutineAdapter routineAdapter;
    private Map<String, ArrayList<TeacherRoutine>> weeklyRoutine;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_routine);

        Toolbar mToolBar =  findViewById(R.id.classRoutine_ToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("CLASS ROUTINE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        weeklyDayCardId = findViewById(R.id.weeklyDayCardId);
        weeklyDayId = findViewById(R.id.weeklyDayId);
        weeklyDayRecyclerViewId = findViewById(R.id.weeklyDayRecyclerViewId);

        weeklyDayCardId.setOnClickListener(view -> intiDaySpinner());

        setupView();
    }

    private void setupView(){

        weeklyDays = new String[]{
                "Saturday",
                "Sunday",
                "Monday",
                "Tuesday",
                "Wednesday",
                "Thursday",
                "Friday"
        };

        selectedDay = "SAT";
        weeklyDayId.setText(weeklyDays[0]);

        callApi();
    }

    private void callApi() {
        new TeacherRoutineApi(this, "Loading...").getTeacherRoutine(
                CustomSharedPref.getInstance(this).getAuthToken(),
                (object, message) -> {
                    if(object != null){
                        weeklyRoutine = (Map<String, ArrayList<TeacherRoutine>>) object;
                        setAdapter(selectedDay);
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );
    }

    private void intiDaySpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a day");
        b.setItems(weeklyDays, (dialog, which) -> {
            weeklyDayId.setText(weeklyDays[which]);
            switch (weeklyDays[which]) {
                case "Saturday":
                    selectedDay = "SAT";
                    break;
                case "Sunday":
                    selectedDay = "SUN";
                    break;
                case "Monday":
                    selectedDay = "MON";
                    break;
                case "Tuesday":
                    selectedDay = "TUE";
                    break;
                case "Wednesday":
                    selectedDay = "WED";
                    break;
                case "Thursday":
                    selectedDay = "THUR";
                    break;
                case "Friday":
                    selectedDay = "FRI";
                    break;
            }

            setAdapter(selectedDay);
        });

        b.show();
    }

    private void setAdapter(String day){
        if(weeklyRoutine == null) {
            Toast.makeText(this, "No data found!", Toast.LENGTH_SHORT).show();
            return;
        }
        if(weeklyRoutine.get(day) == null || weeklyRoutine.get(day).size() == 0){
            Toast.makeText(this, "No routine found!", Toast.LENGTH_SHORT).show();
        }
        routineAdapter = new TeacherRoutineAdapter(this);
        routineAdapter.setItems(weeklyRoutine.get(day));
        weeklyDayRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        weeklyDayRecyclerViewId.setAdapter(routineAdapter);
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