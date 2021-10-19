package com.itbeebd.cesc_nsl.activities.teacher;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.teacherApi.OnlineExamApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class OnlineExamActivity extends AppCompatActivity {

    private CardView a_classCardId;
    private CardView fromTimeCardId;
    private CardView toTimeCardId;
    private CardView publishStatusCardId;
    private CardView resultStatusCardId;
    private CardView a_religionCardId;

    private TextView publishStatusId;
    private TextView resultStatusId;
    private TextView a_religionViewId;
    private TextView fromTimeId;
    private TextView toTimeId;
    private TextView a_classViewId;

    private CardView a_subjectCardId;
    private TextView a_subjectViewId;

    private CardView a_dateCardId;
    private TextView a_dateViewId;
    private TextView monthNameViewId;

    private TextInputLayout q_examTitleId;
    private TextInputLayout q_TotalMarkId;
    private TextInputLayout q_TotalQuestionId;
    private TextInputLayout q_PerQuestionMarkId;
    private TextInputLayout q_PassMarkId;
    private Button submitOnlineQuizQuestionBtnId;

    private String[] classes;
    private String selectedClass;

    private String[] religion;
    private String selectedReligion;

    private String[] subjects;
    private String selectedSubject;

    private TeacherDao teacherDao;

    private LinearLayout questionLayoutId;
    private Button addMoreQuestionBtnId;
    private ArrayList<View> views;
    private int questionNo = 0;
    private int mYear;
    private int mMonth;
    private int mDay;
    private String date;
    private String startTime;
    private String endTime;
    private String publishStatus = "p";
    private String resultPublishStatus = "no";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_exam);

        views = new ArrayList<>();

        CustomSharedPref.getInstance(this).setUserModeNo(1);

        Toolbar mToolBar =  findViewById(R.id.addQuizToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("ONLINE EXAM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        init();

        teacherDao = new TeacherDao();
        classes = teacherDao.getAllClasses();

        addMoreQuestionBtnId.setOnClickListener(view -> addQuestionTemp());


        fromTimeCardId.setOnClickListener(view -> timePicker("startTime"));
        toTimeCardId.setOnClickListener(view -> timePicker("endTime"));

        a_dateCardId.setOnClickListener(view -> datePicker());

        a_religionCardId.setOnClickListener(view -> initReligionSpinner());

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

        a_subjectCardId.setOnClickListener(view -> {
            if(selectedClass == null){
                setToolTip(a_classCardId, "Select a class");
                return;
            }
            else if(subjects == null){
                Toast.makeText(this, "No subject found", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(subjects.length == 0){
                Toast.makeText(this, "Subject list is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                initSubjectSpinner();
            }
        });

        resultStatusCardId.setOnClickListener(view -> {
            if (resultStatusId.getText().toString().trim().equals("Pending")) {
                resultStatusId.setText("Publish");
                resultPublishStatus = "yes";
            } else {
                resultStatusId.setText("Pending");
                resultPublishStatus = "no";
            }
        });

        publishStatusCardId.setOnClickListener(view -> {
            if (publishStatusId.getText().toString().trim().equals("Pending")) {
                publishStatusId.setText("Draft");
                publishStatus = "d";
            } else {
                publishStatusId.setText("Pending");
                publishStatus = "p";
            }
        });

        Objects.requireNonNull(q_TotalMarkId.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(q_TotalMarkId.getEditText().getText().toString().trim().isEmpty() || q_TotalQuestionId.getEditText().getText().toString().trim().isEmpty()) return;

                float totalMark = Float.parseFloat(q_TotalMarkId.getEditText().getText().toString().trim());
                float totalQuestion = Float.parseFloat(q_TotalQuestionId.getEditText().getText().toString().trim());

                float ave = totalMark / totalQuestion;
                q_PerQuestionMarkId.getEditText().setText(String.format("%.2f", ave));
            }
        });

        Objects.requireNonNull(q_TotalQuestionId.getEditText()).addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if(q_TotalMarkId.getEditText().getText().toString().trim().isEmpty() || q_TotalQuestionId.getEditText().getText().toString().trim().isEmpty()){
                    return;
                }

                float totalMark = Float.parseFloat(q_TotalMarkId.getEditText().getText().toString().trim());
                float totalQuestion = Float.parseFloat(q_TotalQuestionId.getEditText().getText().toString().trim());

                float ave = totalMark / totalQuestion;
                q_PerQuestionMarkId.getEditText().setText(String.format("%.2f", ave));
            }
        });

        submitOnlineQuizQuestionBtnId.setOnClickListener(view -> {
            if(!verifyData()) return;
            callAddOnlineExamApi(generateJson());
        });

        addQuestionTemp();
    }

    private void callAddOnlineExamApi(JsonObject generateJson) {
        new OnlineExamApi(this, "Sending...").addOnlineExam(
                CustomSharedPref.getInstance(this).getAuthToken(),
                generateJson,
                (isSuccess, message) -> {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    if(isSuccess) finish();
                }
        );
    }

    private void init(){
        publishStatusCardId = findViewById(R.id.publishStatusCardId);
        a_classCardId = findViewById(R.id.a_classCardId);
        fromTimeCardId = findViewById(R.id.fromTimeCardId);
        toTimeCardId = findViewById(R.id.toTimeCardId);
        a_religionCardId = findViewById(R.id.a_religionCardId);
        resultStatusCardId = findViewById(R.id.resultStatusCardId);

        publishStatusId = findViewById(R.id.publishStatusId);
        resultStatusId = findViewById(R.id.resultStatusId);
        fromTimeId = findViewById(R.id.fromTimeId);
        toTimeId = findViewById(R.id.toTimeId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_religionViewId = findViewById(R.id.a_religionViewId);

        a_subjectCardId = findViewById(R.id.a_subjectCardId);
        a_subjectViewId = findViewById(R.id.a_subjectViewId);

        monthNameViewId = findViewById(R.id.monthNameViewId);
        a_dateViewId = findViewById(R.id.a_dateViewId);
        a_dateCardId = findViewById(R.id.a_dateCardId);

        q_examTitleId = findViewById(R.id.q_examTitleId);
        q_TotalMarkId = findViewById(R.id.q_TotalMarkId);
        q_TotalQuestionId = findViewById(R.id.q_TotalQuestionId);
        q_PerQuestionMarkId = findViewById(R.id.q_PerQuestionMarkId);
        q_PassMarkId = findViewById(R.id.q_PassMarkId);

        questionLayoutId = findViewById(R.id.questionLayoutId);
        addMoreQuestionBtnId = findViewById(R.id.addMoreQuestionBtnId);
        submitOnlineQuizQuestionBtnId = findViewById(R.id.submitOnlineQuizQuestionBtnId);

        religion = new String[]{
                "Islam",
                "Hinduism",
                "Christianity",
                "Buddhism"
        };
    }
    private void initReligionSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select or skip");
        b.setItems(religion, (dialog, which) -> {
            selectedReligion = religion[which];
            a_religionViewId.setText(selectedReligion);
        });

        b.show();
    }

    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");
        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);

            subjects = teacherDao.getAllSubjectByClassName(selectedClass);
        });

        b.show();
    }

    private void initSubjectSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a subject");
        b.setItems(subjects, (dialog, which) -> {
            selectedSubject = subjects[which];
            a_subjectViewId.setText(selectedSubject.substring(selectedSubject.lastIndexOf("-") + 1));
        });

        b.show();
    }

    @SuppressLint("DefaultLocale")
    private void addQuestionTemp() {
        View view = LayoutInflater.from(this).inflate(R.layout.single_quiz_setup_view, questionLayoutId, false);
        TextView questionNoHintId = view.findViewById(R.id.questionNoHintId);
        ImageView removeQuestionBoxId = view.findViewById(R.id.removeQuestionBoxId);

        questionNo += 1;
        questionNoHintId.setText(String.format("Question No: %d", questionNo));

        removeQuestionBoxId.setOnClickListener(v -> {
            if(questionNo == 1) return;

            questionNo -= 1;
            ((ViewGroup)view.getParent()).removeView(view);
            views.remove(view);

            for(int i = 0; i < views.size(); i++){
                TextView qId = views.get(i).findViewById(R.id.questionNoHintId);
                qId.setText(String.format("Question No: %d", i + 1));
            }

            Toast.makeText(this, "Item removed!", Toast.LENGTH_SHORT).show();
        });

        views.add(view);
        questionLayoutId.addView(view);
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

    private void datePicker(){
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);


        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, monthOfYear, dayOfMonth) -> {
                    this.mDay = dayOfMonth;
                    this.mMonth = monthOfYear;
                    this.mYear = year;
                    date = mYear + "-" + mMonth + "-" + mDay;
                    a_dateViewId.setText(String.valueOf(dayOfMonth));
                    monthNameViewId.setText(String.format("%s, %d", getMonth(monthOfYear), year));

                }, mYear, mMonth, mDay);
        datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        // datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        datePickerDialog.show();
    }

    private void timePicker(String type){
        final Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        TimePickerDialog timePickerDialog = new TimePickerDialog(this, (view, hourOfDay, minute1) -> {
            if(type.equals("startTime")){
                startTime = hourOfDay + ":" + minute1 + " AM";
                if(hourOfDay > 12){
                    startTime = (hourOfDay - 12) + ":" + minute1 + " PM";
                }

                fromTimeId.setText(startTime);
                startTime = hourOfDay + ":" + minute1 + ":00";
            }
            else {
                endTime = hourOfDay + ":" + minute1 + " AM";
                if(hourOfDay > 12){
                    endTime = (hourOfDay - 12) + ":" + minute1 + " PM";
                }
                toTimeId.setText(endTime);
                endTime = hourOfDay + ":" + minute1 + ":00";
            }

            System.out.println("Start time >>>>>>> " + startTime);
            System.out.println("End time >>>>>>> " + endTime);

        }, hour, minute, false);

        timePickerDialog.show();
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

    private boolean verifyData(){
        if(selectedClass == null){
            setToolTip(a_classCardId, "Select a class");
            return false;
        }

        if(selectedSubject == null){
            setToolTip(a_subjectCardId, "Select a subject");
            return false;
        }

        if(date == null){
            setToolTip(a_dateCardId, "Select a date");
            return false;
        }

        if(startTime == null){
            setToolTip(fromTimeCardId, "Select exam start time");
            return false;
        }

        if(endTime == null){
            setToolTip(toTimeCardId, "Select exam end time");
            return false;
        }

        if(q_examTitleId.getEditText().getText().toString().trim().isEmpty()){
            q_examTitleId.setError("Enter exam title");
            return false;
        }

        if(q_TotalMarkId.getEditText().getText().toString().trim().isEmpty()){
            q_TotalMarkId.setError("Enter total mark");
            return false;
        }

        if(q_TotalQuestionId.getEditText().getText().toString().trim().isEmpty()){
            q_TotalQuestionId.setError("Enter total question");
            return false;
        }

        if(q_PassMarkId.getEditText().getText().toString().trim().isEmpty()){
            q_PassMarkId.setError("Enter pass mark");
            return false;
        }

        return true;
    }

    private JsonObject generateJson() {

        JSONArray jsonArray = new JSONArray();

        for(int i = 0; i < views.size(); i++){
            View view = views.get(i);

            EditText question = view.findViewById(R.id.questionBoxId);
            EditText ans1Id = view.findViewById(R.id.ans1Id);
            EditText ans2Id = view.findViewById(R.id.ans2Id);
            EditText ans3Id = view.findViewById(R.id.ans3Id);
            EditText ans4Id = view.findViewById(R.id.ans4Id);
            CheckBox op1Id = view.findViewById(R.id.op1Id);
            CheckBox op2Id = view.findViewById(R.id.op2Id);
            CheckBox op3Id = view.findViewById(R.id.op3Id);
            CheckBox op4Id = view.findViewById(R.id.op4Id);

            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("question", question.getText().toString().trim());
                jsonObject.put("option1", ans1Id.getText().toString().trim());
                jsonObject.put("option2", ans2Id.getText().toString().trim());
                jsonObject.put("option3", ans3Id.getText().toString().trim());
                jsonObject.put("option4", ans4Id.getText().toString().trim());

                if(op1Id.isChecked()) jsonObject.put("answer", 1);
                else if(op2Id.isChecked()) jsonObject.put("answer", 2);
                else if(op3Id.isChecked()) jsonObject.put("answer", 3);
                else if(op4Id.isChecked()) jsonObject.put("answer", 4);

                jsonArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONObject temp = new JSONObject();
        try {

            temp.put("id", null);
            temp.put("teacher_id", teacherDao.getTeacher(this).getId());
            temp.put("name", q_examTitleId.getEditText().getText().toString().trim());
            temp.put("date", date);
            temp.put("full_date", date + " " + startTime);
            temp.put("exam_end_time", date + " " + endTime);
            temp.put("time", startTime);
            temp.put("to_time", endTime);

            temp.put("total_question", Integer.parseInt(q_TotalQuestionId.getEditText().getText().toString().trim()));
            temp.put("total_mark", Integer.parseInt(q_TotalMarkId.getEditText().getText().toString().trim()));
            temp.put("question_mark", Float.parseFloat(q_PerQuestionMarkId.getEditText().getText().toString().trim()));
            temp.put("pass_mark", Float.parseFloat(q_PassMarkId.getEditText().getText().toString().trim()));

            temp.put("std_class_id", teacherDao.getClassIdByName(selectedClass));
            temp.put("subject_id", teacherDao.getSubjectIdByName(selectedSubject));
            temp.put("religion", selectedReligion);
            temp.put("status", publishStatus);
            temp.put("auto_publish", resultPublishStatus);
            temp.put("question", jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(temp.toString());
        return gsonObject;
    }

}