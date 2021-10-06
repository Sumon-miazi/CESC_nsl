package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.dao.TeacherDao;

import net.cachapa.expandablelayout.ExpandableLayout;

public class TeacherDashboardActivity extends AppCompatActivity implements View.OnClickListener {

    private ExpandableLayout academicExpandableLayout;
    private ExpandableLayout studentExpandableLayout;
    private ExpandableLayout resultExpandableLayout;
    private ExpandableLayout reportExpandableLayout;

    private Button t_addAttendanceViewId;
    private Button t_attendanceListViewId;

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

        new TeacherDao().clearGuidedStudentDetails();

        t_addAttendanceViewId.setOnClickListener(this);
        t_attendanceListViewId.setOnClickListener(this);
    }

    public void cardViewClicked(View view) {
        if(view.getId() == R.id.academicCardId){
            academicExpandableLayout.toggle();
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
}