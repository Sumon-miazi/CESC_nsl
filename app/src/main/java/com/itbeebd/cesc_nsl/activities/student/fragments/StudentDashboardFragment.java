package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.LessonPlanActivity;
import com.itbeebd.cesc_nsl.activities.student.StudentAllNotificationActivity;
import com.itbeebd.cesc_nsl.activities.student.adapters.ClassRoutineAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.LessonPlanAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.ClassRoutineApi;
import com.itbeebd.cesc_nsl.api.studentApi.DashboardApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.utils.Attendance;
import com.itbeebd.cesc_nsl.utils.ClassRoutine;
import com.itbeebd.cesc_nsl.utils.DashboardHeaderObj;
import com.itbeebd.cesc_nsl.utils.LessonPlan;
import com.itbeebd.cesc_nsl.utils.NotificationObj;
import com.parassidhu.simpledate.SimpleDateKt;

import java.util.ArrayList;
import java.util.Date;


public class StudentDashboardFragment extends Fragment implements OnRecyclerObjectClickListener<NotificationObj>, View.OnClickListener {

    private CardView quizBlock;
    private TextView quizBlockNumber;

    private CardView quizArchiveBlock;
    private TextView quizArchiveBlockNumber;

    private CardView lessonBlock;
    private TextView lessonBlockNumber;

    private CardView onlineClassBlock;
    private TextView onlineClassBlockNumber;

    private RecyclerView studentNotificationRecyclerView;
    private TextView notificationHint;
    private TextView notificationSeeAll;

    private ImageView filterClassRoutineBtnId;
    private RecyclerView classRoutineRecyclerView;

    private RecyclerView lessonPlanRecyclerView;
    private TextView lessonPlanCountHint;
    private TextView lessonPlanSeeAll;

    private ArrayList<NotificationObj> notificationObjs;
    private ArrayList<LessonPlan> lessonPlans;

    private TextView totalNotificationHindId;
    private ImageView studentProfileViewId;
    private TextView userNameViewId;
    private TextView todayDateViewId;
    private ImageView studentNotificationAlarmViewId;

    private TextView attendancePresentViewId;
    private TextView attendanceAbsentViewId;
    private ImageView filterAttendanceBtnId;
    private Guideline guideline;

    public StudentDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_dashboard, container, false);

        studentProfileViewId = view.findViewById(R.id.studentProfileViewId);
        userNameViewId = view.findViewById(R.id.userNameViewId);
        todayDateViewId = view.findViewById(R.id.todayDateViewId);
        totalNotificationHindId = view.findViewById(R.id.totalNotificationHindId);
        studentNotificationAlarmViewId = view.findViewById(R.id.studentNotificationAlarmViewId);

        quizBlock = view.findViewById(R.id.quizCardId);
        quizBlockNumber = view.findViewById(R.id.quizCardHeaderSectionId);

        quizArchiveBlock = view.findViewById(R.id.quizArchiveCardId);
        quizArchiveBlockNumber = view.findViewById(R.id.quizArchiveHeaderSectionId);

        lessonBlock = view.findViewById(R.id.lessonCardId);
        lessonBlockNumber = view.findViewById(R.id.lessonHeaderSectionId);

        onlineClassBlock = view.findViewById(R.id.onlineClassCardId);
        onlineClassBlockNumber = view.findViewById(R.id.onlineClassHeaderSectionId);

        attendancePresentViewId = view.findViewById(R.id.attendanceBlockId).findViewById(R.id.attendancePresentViewId);
        attendanceAbsentViewId = view.findViewById(R.id.attendanceBlockId).findViewById(R.id.attendanceAbsentViewId);
        filterAttendanceBtnId = view.findViewById(R.id.attendanceBlockId).findViewById(R.id.filterAttendanceBtnId);
        guideline = view.findViewById(R.id.attendanceBlockId).findViewById(R.id.guideline6);


//        studentNotificationRecyclerView = view.findViewById(R.id.notificationBlockId).findViewById(R.id.studentNotificationRecyclerViewId);
//        notificationHint = view.findViewById(R.id.notificationBlockId).findViewById(R.id.notificationHintId);
//        notificationSeeAll = view.findViewById(R.id.notificationBlockId).findViewById(R.id.notificationSeeAllId);
//        notificationSeeAll.setVisibility(View.VISIBLE);


        filterClassRoutineBtnId = view.findViewById(R.id.classRoutineBlockId).findViewById(R.id.filterClassRoutineBtnId);
        classRoutineRecyclerView = view.findViewById(R.id.classRoutineBlockId).findViewById(R.id.classRoutineRecyclerViewId);

        lessonPlanRecyclerView = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanRecyclerViewId);
        lessonPlanCountHint = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonCountHintId);
        lessonPlanSeeAll = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanSeeAllId);

        quizBlock.setOnClickListener(this::gotoQuizView);
        quizArchiveBlock.setOnClickListener(this::gotoQuizArchiveView);
        lessonBlock.setOnClickListener(this::gotoLessonView);
        onlineClassBlock.setOnClickListener(this::gotoOnlineView);

        filterClassRoutineBtnId.setOnClickListener(this::filterClassRoutine);

   //     notificationSeeAll.setOnClickListener(this);
        studentNotificationAlarmViewId.setOnClickListener(this);
        lessonPlanSeeAll.setOnClickListener(this);

        setDashboardComponentValues();
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Student stdObj = new StudentDao().getStudent(getContext());
        String imageUrl = stdObj.getImage();

        Date date = new Date();
        todayDateViewId.setText(SimpleDateKt.toDateEMY(date));

        userNameViewId.setText(stdObj.getName().substring(stdObj.getName().lastIndexOf(" ")+1));

        if( imageUrl != null){
            Glide.with(this)
                    .load(ApiUrls.BASE_IMAGE_URL + imageUrl)
                    .into(studentProfileViewId);
        }
    }

    private void setDashboardComponentValues() {
        new DashboardApi(getContext()).getDashboardHeaderInfo(
                CustomSharedPref.getInstance(getContext()).getAuthToken(),
                (object, message) -> {
                    if(object != null){
                        setDashboardData((DashboardHeaderObj) object);
                    }
                    else {
                        try { Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show(); }catch (Exception ignore){}
                    }
                }
        );


    }

    private void setDashboardData(DashboardHeaderObj object){
        quizBlockNumber.setText(object.getTotalQuiz());
        quizArchiveBlockNumber.setText(object.getTotalQuizArchive());
        lessonBlockNumber.setText(object.getTotalLessonPlan());
        onlineClassBlockNumber.setText(object.getTotalOnlineClass());
        System.out.println("<><><><><><><> " + object.getTotalOnlineClass());

        if(object.getTotalNotifications().equals("0")){
            totalNotificationHindId.setVisibility(View.GONE);
        }
        else {
            totalNotificationHindId.setVisibility(View.VISIBLE);
            String total = object.getTotalNotifications();
            if (total.length() == 1) {
                total = " " + total + " ";
            }
            totalNotificationHindId.setText(total);
        }


        if(object.getNotificationObjArrayList().size() != 0){
            notificationObjs = object.getNotificationObjArrayList();
         //   setNotificationAdapter();
        }

        if(object.getLessonPlanArrayList().size() != 0){
            lessonPlans = object.getLessonPlanArrayList();
            setLessonPlanAdapter();
        }

        if(object.getAttendance() != null){
            setAttendanceGraph(object.getAttendance());
        }

        if(object.getClassRoutineArrayList() != null){
            setClassRoutineAdapter(object.getClassRoutineArrayList());
        }


    }

    @SuppressLint("DefaultLocale")
    private void setAttendanceGraph(Attendance attendance) {
        int present = attendance.getPresent();
        int absent = attendance.getAbsent();
        int sum = present + absent;

        System.out.println("++++++ " + present);
        System.out.println("++++++ " + absent);
        System.out.println("++++++ " + sum);

        if(sum == 0 || present == absent) return;

        attendancePresentViewId.setText(String.format("Present: %d Day(s)", present));
        attendanceAbsentViewId.setText(String.format("Absent: %d Day(s)", absent));

        if(present == 0) {
            guideline.setGuidelinePercent(0.0f);
            return;
        }
        else if(absent == 0) {
            guideline.setGuidelinePercent(1.0f);
            return;
        }

        float onePercentage = (float) sum / 10;
        float presentPercentage = (float)present * onePercentage;
        float absentPercentage = (float)absent * onePercentage;

        System.out.println("++++++ " + onePercentage);
        System.out.println("++++++ " + presentPercentage);
        System.out.println("++++++ " + absentPercentage);

        if(presentPercentage > absentPercentage) guideline.setGuidelinePercent(presentPercentage);
        else guideline.setGuidelinePercent(1 - absentPercentage);


    }

    private void setNotificationAdapter(){
        String notificationSize = "See All(" + notificationObjs.size() + ")";
        notificationHint.setVisibility(View.VISIBLE);
        notificationSeeAll.setText(notificationSize);

        StudentNotificationAdapter notificationAdapter = new StudentNotificationAdapter(getContext());
        notificationAdapter.setItems(notificationObjs.subList(0,2));
        studentNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //    studentNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        studentNotificationRecyclerView.setAdapter(notificationAdapter);
    }

    private void setLessonPlanAdapter() {
        String lessonPlansSize = "See All(" + lessonPlans.size() + ")";

        if(lessonPlans.size() != 0){
            lessonPlanCountHint.setVisibility(View.VISIBLE);
            lessonPlanSeeAll.setVisibility(View.VISIBLE);
        }

        lessonPlanSeeAll.setText(lessonPlansSize);

        LessonPlanAdapter lessonPlanAdapter = new LessonPlanAdapter(getContext());
        lessonPlanAdapter.setItems(lessonPlans.subList(0,2));
        lessonPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lessonPlanRecyclerView.setAdapter(lessonPlanAdapter);
    }

    private void setClassRoutineAdapter(ArrayList<ClassRoutine> classRoutineArrayList) {
        ClassRoutineAdapter classRoutineAdapter = new ClassRoutineAdapter(getContext());
        classRoutineAdapter.setItems(classRoutineArrayList);
        classRoutineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        classRoutineRecyclerView.setAdapter(classRoutineAdapter);
    }

    @Override
    public void onItemClicked(NotificationObj item, View view) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if(view.getId() == R.id.notificationSeeAllId || view.getId() == R.id.studentNotificationAlarmViewId){
            intent = new Intent(getActivity(), StudentAllNotificationActivity.class);
            intent.putExtra("notifications", notificationObjs);
            totalNotificationHindId.setVisibility(View.GONE);
        }
        if(view.getId() == R.id.lessonPlanSeeAllId){
            intent = new Intent(getActivity(), LessonPlanActivity.class);
            intent.putExtra("lessonPlan",  lessonPlans);
        }
        getActivity().startActivity(intent);
    }


    private void gotoQuizView(View view){
        System.out.println(">>>>>. " + view.getId());
    }

    private void gotoOnlineView(View view) {
        System.out.println(">>>>>. " + view.getId());
    }

    private void gotoLessonView(View view) {
        if(lessonPlans == null || lessonPlans.size() == 0) return;
        System.out.println(">>>>>. " + view.getId());
        Intent intent = new Intent(getActivity(), LessonPlanActivity.class);
        intent.putExtra("lessonPlan",  lessonPlans);
        getActivity().startActivity(intent);
    }

    private void gotoQuizArchiveView(View view) {
        System.out.println(">>>>>. " + view.getId());
    }


    private void filterClassRoutine(View view) {
        showBottomSheetDialogForClassRoutine();
    }

    private void showBottomSheetDialogForClassRoutine() {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.weekly_days_view);

        TextView saturdayId = bottomSheetDialog.findViewById(R.id.saturdayId);
        TextView sundayId = bottomSheetDialog.findViewById(R.id.sundayId);
        TextView mondayId = bottomSheetDialog.findViewById(R.id.mondayId);
        TextView tuesdayId = bottomSheetDialog.findViewById(R.id.tuesdayId);
        TextView wednesdayId = bottomSheetDialog.findViewById(R.id.wednesdayId);
        TextView thursdayId = bottomSheetDialog.findViewById(R.id.thursdayId);

        assert saturdayId != null;
        saturdayId.setOnClickListener(view -> { getClassRoutineByDayName(saturdayId.getText().toString());bottomSheetDialog.dismiss(); });

        assert sundayId != null;
        sundayId.setOnClickListener(view -> { getClassRoutineByDayName(sundayId.getText().toString());bottomSheetDialog.dismiss(); });

        assert mondayId != null;
        mondayId.setOnClickListener(view -> { getClassRoutineByDayName(mondayId.getText().toString());bottomSheetDialog.dismiss(); });

        assert tuesdayId != null;
        tuesdayId.setOnClickListener(view -> { getClassRoutineByDayName(tuesdayId.getText().toString()); bottomSheetDialog.dismiss(); });

        assert wednesdayId != null;
        wednesdayId.setOnClickListener(view -> { getClassRoutineByDayName(wednesdayId.getText().toString()); bottomSheetDialog.dismiss(); });

        assert thursdayId != null;
        thursdayId.setOnClickListener(view -> { getClassRoutineByDayName(thursdayId.getText().toString()); bottomSheetDialog.dismiss(); });

        bottomSheetDialog.show();
    }

    private void getClassRoutineByDayName(String name){
        new ClassRoutineApi(getContext()).getResult(
                name,
                CustomSharedPref.getInstance(getContext()).getAuthToken(),
                (object, message) -> {
                    try {
                        if(object != null){
                            setClassRoutineAdapter((ArrayList<ClassRoutine>) object);
                        }
                        else Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception ignore){}
                }
        );
    }

}