package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.AttendanceViewHolder;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.GuideStudentListViewHolder;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.utils.dummy.ClassAttendance;

public class GuideStudentListAdapter extends GenericRecyclerAdapter<Student, OnRecyclerObjectClickListener<Student>, GuideStudentListViewHolder> {

    private Context context;

    public GuideStudentListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public GuideStudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new GuideStudentListViewHolder(inflate(R.layout.single_guide_student_view, parent), context);
    }

}