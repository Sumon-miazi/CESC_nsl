package com.itbeebd.cesc_nsl.activities.student.fragments;

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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.LessonPlanActivity;
import com.itbeebd.cesc_nsl.activities.student.StudentAllNotificationActivity;
import com.itbeebd.cesc_nsl.activities.student.adapters.LessonPlanAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.DashboardApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.sugarClass.Student;
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

    public StudentDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

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


        studentNotificationRecyclerView = view.findViewById(R.id.notificationBlockId).findViewById(R.id.studentNotificationRecyclerViewId);
        notificationHint = view.findViewById(R.id.notificationBlockId).findViewById(R.id.notificationHintId);
        notificationSeeAll = view.findViewById(R.id.notificationBlockId).findViewById(R.id.notificationSeeAllId);
        notificationSeeAll.setVisibility(View.VISIBLE);


        lessonPlanRecyclerView = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanRecyclerViewId);
        lessonPlanCountHint = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonCountHintId);
        lessonPlanSeeAll = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanSeeAllId);

        quizBlock.setOnClickListener(this::gotoQuizView);
        quizArchiveBlock.setOnClickListener(this::gotoQuizArchiveView);
        lessonBlock.setOnClickListener(this::gotoLessonView);
        onlineClassBlock.setOnClickListener(this::gotoOnlineView);

        notificationSeeAll.setOnClickListener(this);
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
        System.out.println(">>>>>. " + view.getId());
    }

    private void gotoQuizArchiveView(View view) {
        System.out.println(">>>>>. " + view.getId());
    }

}