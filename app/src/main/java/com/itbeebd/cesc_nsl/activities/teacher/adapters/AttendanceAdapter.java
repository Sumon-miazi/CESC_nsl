package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.AttendanceViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.ClassAttendance;

public class AttendanceAdapter extends GenericRecyclerAdapter<ClassAttendance, OnRecyclerObjectClickListener<ClassAttendance>, AttendanceViewHolder> {

    private Context context;

    public AttendanceAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AttendanceViewHolder(inflate(R.layout.single_attendance_view, parent), context);
    }

}