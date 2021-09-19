package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.StudentAllNotificationActivity;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.utils.Notification;

import java.util.ArrayList;


public class StudentDashboardFragment extends Fragment implements OnRecyclerObjectClickListener<Notification>, View.OnClickListener {

//    private String mParam1;
//    private String mParam2;

    private TextView notificationHint;
    private TextView notificationSeeAll;
    private RecyclerView studentNotificationRecyclerView;
    private ArrayList<Notification> notifications;

    public StudentDashboardFragment() {
        // Required empty public constructor
    }

    public static StudentDashboardFragment newInstance(String param1, String param2) {
        StudentDashboardFragment fragment = new StudentDashboardFragment();
//        Bundle args = new Bundle();
//        args.putString(ARG_PARAM1, param1);
//        args.putString(ARG_PARAM2, param2);
//        fragment.setArguments(args);
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
        notificationHint = view.findViewById(R.id.notificationHintId);
        notificationSeeAll = view.findViewById(R.id.notificationSeeAllId);
        notificationSeeAll.setVisibility(View.VISIBLE);
        studentNotificationRecyclerView = view.findViewById(R.id.studentNotificationRecyclerViewId);

        notificationSeeAll.setOnClickListener(this);

        setNotificationAdapter();

        return view;
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
        notificationAdapter.setItems(notifications.subList(0,3));
        studentNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //    studentNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        studentNotificationRecyclerView.setAdapter(notificationAdapter);
    }

    @Override
    public void onItemClicked(Notification item, View view) {

    }

    @Override
    public void onClick(View view) {
        if(view.getId() == R.id.notificationSeeAllId){
            Intent intent = new Intent(getActivity(), StudentAllNotificationActivity.class);
            intent.putExtra("notifications",  notifications);
            getActivity().startActivity(intent);
        }
    }
}