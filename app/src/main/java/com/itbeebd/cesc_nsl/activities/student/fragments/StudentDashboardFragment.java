package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.LessonPlanActivity;
import com.itbeebd.cesc_nsl.activities.student.StudentAllNotificationActivity;
import com.itbeebd.cesc_nsl.activities.student.adapters.LessonPlanAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.utils.LessonPlan;
import com.itbeebd.cesc_nsl.utils.Notification;

import java.util.ArrayList;


public class StudentDashboardFragment extends Fragment implements OnRecyclerObjectClickListener<Notification>, View.OnClickListener {

    private CardView quizBlock;
    private ImageView quizBlockImageView;
    private TextView quizBlockNumber;
    private TextView quizBlockName;

    private CardView quizArchiveBlock;
    private ImageView quizArchiveBlockImageView;
    private TextView quizArchiveBlockNumber;
    private TextView quizArchiveBlockName;

    private CardView lessonBlock;
    private ImageView lessonBlockImageView;
    private TextView lessonBlockNumber;
    private TextView lessonBlockName;

    private CardView onlineClassBlock;
    private ImageView onlineClassBlockImageView;
    private TextView onlineClassBlockNumber;
    private TextView onlineClassBlockName;

    private RecyclerView studentNotificationRecyclerView;
    private TextView notificationHint;
    private TextView notificationSeeAll;


    private RecyclerView lessonPlanRecyclerView;
    private TextView lessonPlanCountHint;
    private TextView lessonPlanSeeAll;

    private ArrayList<Notification> notifications;
    private ArrayList<LessonPlan> lessonPlans;

    public StudentDashboardFragment() {
        // Required empty public constructor
    }

    public static StudentDashboardFragment newInstance(String param1, String param2) {
        StudentDashboardFragment fragment = new StudentDashboardFragment();
        return fragment;
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

        studentNotificationRecyclerView = view.findViewById(R.id.notificationBlockId).findViewById(R.id.studentNotificationRecyclerViewId);
        notificationHint = view.findViewById(R.id.notificationBlockId).findViewById(R.id.notificationHintId);
        notificationSeeAll = view.findViewById(R.id.notificationBlockId).findViewById(R.id.notificationSeeAllId);
        notificationSeeAll.setVisibility(View.VISIBLE);


        lessonPlanRecyclerView = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanRecyclerViewId);
        lessonPlanCountHint = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonCountHintId);
        lessonPlanSeeAll = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanSeeAllId);
        lessonPlanSeeAll.setVisibility(View.VISIBLE);


        notificationSeeAll.setOnClickListener(this);
        lessonPlanSeeAll.setOnClickListener(this);

        setDashboardComponent(view);

        setNotificationAdapter();
        setLessonPlanAdapter();

        return view;
    }


    private void setDashboardComponent(View view) {
        quizBlock = view.findViewById(R.id.quizBlockId).findViewById(R.id.cardViewId);
        quizBlockImageView = view.findViewById(R.id.quizBlockId).findViewById(R.id.componentImageView);
        quizBlockNumber = view.findViewById(R.id.quizBlockId).findViewById(R.id.headerSectionId);
        quizBlockName = view.findViewById(R.id.quizBlockId).findViewById(R.id.headerSectionNameId);

        quizArchiveBlock = view.findViewById(R.id.quizArchiveBlockId).findViewById(R.id.cardViewId);
        quizArchiveBlockImageView = view.findViewById(R.id.quizArchiveBlockId).findViewById(R.id.componentImageView);
        quizArchiveBlockNumber = view.findViewById(R.id.quizArchiveBlockId).findViewById(R.id.headerSectionId);
        quizArchiveBlockName = view.findViewById(R.id.quizArchiveBlockId).findViewById(R.id.headerSectionNameId);

        lessonBlock = view.findViewById(R.id.lessonPlanBlockId).findViewById(R.id.cardViewId);
        lessonBlockImageView = view.findViewById(R.id.lessonPlanBlockId).findViewById(R.id.componentImageView);
        lessonBlockNumber = view.findViewById(R.id.lessonPlanBlockId).findViewById(R.id.headerSectionId);
        lessonBlockName = view.findViewById(R.id.lessonPlanBlockId).findViewById(R.id.headerSectionNameId);

        onlineClassBlock = view.findViewById(R.id.onlineClassBlockId).findViewById(R.id.cardViewId);
        onlineClassBlockImageView = view.findViewById(R.id.onlineClassBlockId).findViewById(R.id.componentImageView);
        onlineClassBlockNumber = view.findViewById(R.id.onlineClassBlockId).findViewById(R.id.headerSectionId);
        onlineClassBlockName = view.findViewById(R.id.onlineClassBlockId).findViewById(R.id.headerSectionNameId);

        setDashboardComponentValues();
    }

    private void setDashboardComponentValues() {
        quizBlockNumber.setText("100");
        quizBlockName.setText("Quiz");

        quizArchiveBlockNumber.setText("40");
        quizArchiveBlockName.setText("Quiz Archive");

        lessonBlockNumber.setText("70");
        lessonBlockName.setText("Lesson Plan");

        onlineClassBlockNumber.setText("10");
        onlineClassBlockName.setText("Online Class");
    }


    private void setNotificationAdapter(){

        notifications = new ArrayList<>();
        notifications.add(new Notification("this is title 1", getString(R.string.demo_notification_body)));
        notifications.add(new Notification("this is title 2", getString(R.string.demo_notification_body)));
        notifications.add(new Notification("this is title 3", getString(R.string.demo_notification_body)));
        notifications.add(new Notification("this is title 4", getString(R.string.demo_notification_body)));

        String notificationSize = "NOTIFICATION(" + notifications.size() + ")";
        notificationHint.setText(notificationSize);

        StudentNotificationAdapter notificationAdapter = new StudentNotificationAdapter(getContext());
        notificationAdapter.setItems(notifications.subList(0,2));
        studentNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //    studentNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        studentNotificationRecyclerView.setAdapter(notificationAdapter);
    }

    private void setLessonPlanAdapter() {

        lessonPlans = new ArrayList<>();
        lessonPlans.add(new LessonPlan("Arif", "Bangla", "Chapter one", "1 September, 2021"));
        lessonPlans.add(new LessonPlan("Mohian", "English", "Chapter one", "1 September, 2021"));
        lessonPlans.add(new LessonPlan("Borhan", "Bangla", "Chapter one", "1 September, 2021"));
        lessonPlans.add(new LessonPlan("Suman", "Higher Math", "Chapter one", "1 September, 2021"));
        lessonPlans.add(new LessonPlan("Pappu", "General Knowledge", "Chapter one", "1 September, 2021"));
        lessonPlans.add(new LessonPlan("Shihab", "English Grammer", "Chapter one", "1 September, 2021"));

        String lessonPlansSize = "LESSON PLAN(" + notifications.size() + ")";
        lessonPlanCountHint.setText(lessonPlansSize);

        LessonPlanAdapter lessonPlanAdapter = new LessonPlanAdapter(getContext());
        lessonPlanAdapter.setItems(lessonPlans.subList(0,2));
        lessonPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //    studentNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        lessonPlanRecyclerView.setAdapter(lessonPlanAdapter);
    }

    @Override
    public void onItemClicked(Notification item, View view) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if(view.getId() == R.id.notificationSeeAllId){
            intent = new Intent(getActivity(), StudentAllNotificationActivity.class);
            intent.putExtra("notifications",  notifications);

        }
        if(view.getId() == R.id.lessonPlanSeeAllId){
            intent = new Intent(getActivity(), LessonPlanActivity.class);
            intent.putExtra("lessonPlan",  lessonPlans);
        }
        getActivity().startActivity(intent);
    }
}