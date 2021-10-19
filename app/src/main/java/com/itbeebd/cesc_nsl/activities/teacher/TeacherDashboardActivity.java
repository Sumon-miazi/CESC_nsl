package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.itbeebd.cesc_nsl.MainActivity;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.teacherApi.LoginApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.sugarClass.Teacher;
import com.parassidhu.simpledate.SimpleDateKt;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Date;

public class TeacherDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private LinearLayout t_dashboardLayoutId;
    private ExpandableLayout academicExpandableLayout;
    private ExpandableLayout studentExpandableLayout;
    private ExpandableLayout resultExpandableLayout;
    private ExpandableLayout reportExpandableLayout;
    private ExpandableLayout lessonPlan_expandable_layout;

    private Button t_onlineClassViewId;
    private Button t_classRoutineViewId;
    private Button t_teacherRoutineViewId;
    private Button t_onlineExamViewId;

    private Button t_addAttendanceViewId;
    private Button t_attendanceListViewId;
    private Button t_absentFeeViewId;
    private Button t_studentListViewId;

    private Button t_lessonPlanListId;
    private Button t_addLessonPlanId;

    private TextView todayDateViewId;
    private TextView userNameViewId;
    private TextView designationViewId;
    private TextView deptViewId;
    private ImageView teacherProfileViewId;
    private TeacherDao teacherDao;
    private long time;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        t_dashboardLayoutId = findViewById(R.id.t_dashboardLayoutId);
        academicExpandableLayout = findViewById(R.id.academic_expandable_layout);
        studentExpandableLayout = findViewById(R.id.student_expandable_layout);
        resultExpandableLayout = findViewById(R.id.result_expandable_layout);
        reportExpandableLayout = findViewById(R.id.report_expandable_layout);
        lessonPlan_expandable_layout = findViewById(R.id.lessonPlan_expandable_layout);

        t_onlineClassViewId = findViewById(R.id.t_onlineClassViewId);
        t_classRoutineViewId = findViewById(R.id.t_classRoutineViewId);
        t_teacherRoutineViewId = findViewById(R.id.t_teacherRoutineViewId);
        t_onlineExamViewId = findViewById(R.id.t_onlineExamViewId);

        t_addAttendanceViewId = findViewById(R.id.t_addAttendanceViewId);
        t_attendanceListViewId = findViewById(R.id.t_attendanceListViewId);
        t_absentFeeViewId = findViewById(R.id.t_absentFeeViewId);
        t_studentListViewId = findViewById(R.id.t_studentListViewId);

        t_lessonPlanListId = findViewById(R.id.t_lessonPlanListId);
        t_addLessonPlanId = findViewById(R.id.t_addLessonPlanId);

        todayDateViewId = findViewById(R.id.todayDateViewId);
        userNameViewId = findViewById(R.id.userNameViewId);
        designationViewId = findViewById(R.id.designationViewId);
        deptViewId = findViewById(R.id.deptViewId);
        teacherProfileViewId = findViewById(R.id.teacherProfileViewId);

        teacherDao = new TeacherDao();

        teacherDao.clearGuidedStudentDetails();

        t_teacherRoutineViewId.setOnClickListener(view -> {
            startActivity(new Intent(this, TeacherRoutineActivity.class));
        });

        t_onlineExamViewId.setOnClickListener(view -> {
            startActivity(new Intent(this, OnlineExamListActivity.class));
        });

        t_addAttendanceViewId.setOnClickListener(this);
        t_attendanceListViewId.setOnClickListener(this);
        t_absentFeeViewId.setOnClickListener(this);
        t_studentListViewId.setOnClickListener(this);

        t_addLessonPlanId.setOnClickListener(this::teacherLessonPlan);
        t_lessonPlanListId.setOnClickListener(this::teacherLessonPlan);

        setupTeacherProfile();

        teacherProfileViewId.setOnClickListener(this::showLogoutSnackbar);

    }

    private void teacherLessonPlan(View view) {
        if(view.getId() == R.id.t_addLessonPlanId){
            startActivity(new Intent(this, TeacherLessonPlanActivity.class));
        }
        else if(view.getId() == R.id.t_lessonPlanListId){
            startActivity(new Intent(this, TeacherLessonPlanListActivity.class));
        }
    }

    private void setupTeacherProfile() {
        Date date = new Date();
        todayDateViewId.setText(SimpleDateKt.toDateEMY(date));
        Teacher teacher = teacherDao.getTeacher(this);
        String greetings = "Hello, " + teacher.getName().substring(teacher.getName().lastIndexOf(" ") + 1);
        userNameViewId.setText(greetings);
        designationViewId.setText(teacher.getDesignation());
        deptViewId.setText(String.format("Department: %s", teacher.getDepartment()));

        if (teacher.getImage() != null) {
            setProfileImage(teacherProfileViewId, teacher.getImage());
        }
    }

    public void cardViewClicked(View view) {
        if(view.getId() == R.id.academicCardId){
            academicExpandableLayout.toggle();
           // startActivity(new Intent(this, TeacherRoutineActivity.class));
        }
        else if(view.getId() == R.id.studentCardId){
            studentExpandableLayout.toggle();
        }
        else if(view.getId() == R.id.lessonPlanCardId){
            lessonPlan_expandable_layout.toggle();
           // startActivity(new Intent(this, TeacherLessonPlanActivity.class));
        }
        else if(view.getId() == R.id.resultCardId){
            resultExpandableLayout.toggle();
        }
        else if(view.getId() == R.id.sendNotiCardId){
            startActivity(new Intent(this, SendNotificationActivity.class));
           // reportExpandableLayout.toggle();
        }
        else if(view.getId() == R.id.guidedStudentCardId){
            startActivity(new Intent(this, GuideStudentListActivity.class));
        }
    }

    @Override
    public void onClick(View view) {

        if(view.getId() == R.id.t_addAttendanceViewId){
            startActivity(new Intent(this, AttendanceActivity.class));
        }
        else if(view.getId() == R.id.t_attendanceListViewId){
            startActivity(new Intent(this, AttendanceListActivity.class));
        }
        else if(view.getId() == R.id.t_absentFeeViewId){
            startActivity(new Intent(this, AbsentFeeActivity.class));
        }
        else if(view.getId() == R.id.t_studentListViewId){
            startActivity(new Intent(this, StudentListActivity.class));
        }
    }

    private void setProfileImage(ImageView imageView, String imageUrl) {
        Glide.with(this)
                .load(ApiUrls.BASE_IMAGE_URL + imageUrl)
                .placeholder(R.drawable.default_male)
                .error(R.drawable.default_male)
                .fallback(R.drawable.default_male)
                .into(imageView);
    }

    @Override
    public void onBackPressed() {
      //  super.onBackPressed();
        if (time + 2000 > System.currentTimeMillis()) {
           // startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            time = System.currentTimeMillis();
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    private void showLogoutSnackbar(View view){
        Snackbar snackbar = Snackbar
                .make(t_dashboardLayoutId, "Do you want to logout?", Snackbar.LENGTH_LONG)
                .setAction("YES", view1 -> {
                  //  Toast.makeText(this, "Yes clicked", Toast.LENGTH_SHORT).show();
                    callLogoutApi();
                });

        snackbar.setActionTextColor(Color.RED);
        View sbView = snackbar.getView();
        TextView textView = (TextView) sbView.findViewById(R.id.snackbar_text);
        textView.setTextColor(Color.YELLOW);
        snackbar.show();
    }

    private void callLogoutApi() {
        new LoginApi(this, "Logging out...").logout(
                CustomSharedPref.getInstance(this).getAuthToken(),
                (isSuccess, message) -> {
                    if(isSuccess) {
                        CustomSharedPref.getInstance(getApplicationContext()).setUserLoggedInOrNot(false);
                        startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        finish();
                    }
                    else if(message.equals("Unauthorized")){
                        CustomSharedPref.getInstance(this).setUserLoggedInOrNot(false);
                        startActivity(new Intent(getApplicationContext(), TeacherLoginActivity.class));
                        finish();
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );
    }
}