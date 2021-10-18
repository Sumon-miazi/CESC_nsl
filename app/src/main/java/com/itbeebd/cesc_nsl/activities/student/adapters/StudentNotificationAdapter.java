package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.StudentNotificationViewHolder;
import com.itbeebd.cesc_nsl.sugarClass.NotificationObj;

public class StudentNotificationAdapter extends GenericRecyclerAdapter<NotificationObj, OnRecyclerObjectClickListener<NotificationObj>, StudentNotificationViewHolder> {

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