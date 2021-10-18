package com.itbeebd.cesc_nsl.activities.student.viewHolders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.ClassRoutine;

public class ClassRoutineViewHolder extends BaseViewHolder<ClassRoutine, OnRecyclerObjectClickListener<ClassRoutine>> {

    private TextView cl_teacherNameId;
    private TextView cl_subjectNameId;
    private TextView cl_winterTimeViewId;
    private TextView cl_summerTimeViewId;
    private Context context;

    public ClassRoutineViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        cl_teacherNameId = itemView.findViewById(R.id.cl_teacherNameId);
        cl_subjectNameId = itemView.findViewById(R.id.cl_subjectNameId);
        cl_winterTimeViewId = itemView.findViewById(R.id.cl_winterTimeViewId);
        cl_summerTimeViewId = itemView.findViewById(R.id.cl_summerTimeViewId);

    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBind(ClassRoutine item, @Nullable OnRecyclerObjectClickListener<ClassRoutine> listener) {
        cl_subjectNameId.setText(item.getSubjectName());
        cl_teacherNameId.setText(item.getTeacherName());
        cl_winterTimeViewId.setText(String.format("Winter start-end: %s", item.getWinterTime()));
        cl_summerTimeViewId.setText(String.format("Summer start-end: %s", item.getSummerTime()));
    }
}