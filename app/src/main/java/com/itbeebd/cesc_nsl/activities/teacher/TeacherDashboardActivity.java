package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.sugarClass.Teacher;
import com.parassidhu.simpledate.SimpleDateKt;

import net.cachapa.expandablelayout.ExpandableLayout;

import java.util.Date;

public class TeacherDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private ExpandableLayout academicExpandableLayout;
    private ExpandableLayout studentExpandableLayout;
    private ExpandableLayout resultExpandableLayout;
    private ExpandableLayout reportExpandableLayout;

    private Button t_addAttendanceViewId;
    private Button t_attendanceListViewId;

    private TextView todayDateViewId;
    private TextView userNameViewId;
    private ImageView teacherProfileViewId;
    private TeacherDao teacherDao;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard);

        academicExpandableLayout = findViewById(R.id.academic_expandable_layout);
        studentExpandableLayout = findViewById(R.id.student_expandable_layout);
        resultExpandableLayout = findViewById(R.id.result_expandable_layout);
        reportExpandableLayout = findViewById(R.id.report_expandable_layout);

        t_addAttendanceViewId = findViewById(R.id.t_addAttendanceViewId);
        t_attendanceListViewId = findViewById(R.id.t_attendanceListViewId);

        todayDateViewId = findViewById(R.id.todayDateViewId);
        userNameViewId = findViewById(R.id.userNameViewId);
        teacherProfileViewId = findViewById(R.id.teacherProfileViewId);

        teacherDao = new TeacherDao();

        teacherDao.clearGuidedStudentDetails();

        t_addAttendanceViewId.setOnClickListener(this);
        t_attendanceListViewId.setOnClickListener(this);

        setupTeacherProfile();

    }

    private void setupTeacherProfile() {
        Date date = new Date();
        todayDateViewId.setText(SimpleDateKt.toDateEMY(date));
        Teacher teacher = teacherDao.getTeacher(this);

        userNameViewId.setText(teacher.getName().substring(teacher.getName().lastIndexOf(" ") + 1));

        if (teacher.getImage() != null) {
            setProfileImage(teacherProfileViewId, teacher.getImage());
        }
    }

    public void cardViewClicked(View view) {
        if(view.getId() == R.id.academicCardId){
           // academicExpandableLayout.toggle();
        }
        else if(view.getId() == R.id.studentCardId){
            studentExpandableLayout.toggle();
        }
        else if(view.getId() == R.id.lessonPlanCardId){
            startActivity(new Intent(this, TeacherLessonPlanActivity.class));
        }
        else if(view.getId() == R.id.resultCardId){
            resultExpandableLayout.toggle();
        }
        else if(view.getId() == R.id.reportCardId){
            reportExpandableLayout.toggle();
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
    }

    private void setProfileImage(ImageView imageView, String imageUrl) {
        Glide.with(this)
                .load(ApiUrls.BASE_IMAGE_URL + imageUrl)
                .placeholder(R.drawable.default_male)
                .error(R.drawable.default_male)
                .fallback(R.drawable.default_male)
                .into(imageView);
    }
}