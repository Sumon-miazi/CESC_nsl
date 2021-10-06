package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.AttendanceListViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.AttendanceList;

public class AttendanceListAdapter  extends GenericRecyclerAdapter<AttendanceList, OnRecyclerObjectClickListener<AttendanceList>, AttendanceListViewHolder> {

    private Context context;

    public AttendanceListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AttendanceListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AttendanceListViewHolder(inflate(R.layout.single_attendance_list_view, parent), context);
    }

}