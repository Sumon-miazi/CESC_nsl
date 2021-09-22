package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders.StudentNotificationViewHolder;
import com.itbeebd.cesc_nsl.utils.Notification;

public class StudentNotificationAdapter extends GenericRecyclerAdapter<Notification, OnRecyclerObjectClickListener<Notification>, StudentNotificationViewHolder> {

    private Context context;

    public StudentNotificationAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public StudentNotificationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentNotificationViewHolder(inflate(R.layout.single_student_notification_view, parent), context);
    }

}