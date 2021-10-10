package com.itbeebd.cesc_nsl.activities.teacher.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.TeacherRoutine;

public class TeacherRoutineViewHolder   extends BaseViewHolder<TeacherRoutine, OnRecyclerObjectClickListener<TeacherRoutine>> {

    private final Context context;
    private final TextView tr_subjectNameId;
    private final TextView tr_classNameId;
    private final TextView cl_winterTimeViewId;
    private final TextView cl_summerTimeViewId;

    public TeacherRoutineViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        tr_subjectNameId = itemView.findViewById(R.id.tr_subjectNameId);
        tr_classNameId = itemView.findViewById(R.id.tr_classNameId);
        cl_winterTimeViewId = itemView.findViewById(R.id.cl_winterTimeViewId);
        cl_summerTimeViewId = itemView.findViewById(R.id.cl_summerTimeViewId);
    }

    @Override
    public void onBind(TeacherRoutine item, @Nullable OnRecyclerObjectClickListener<TeacherRoutine> listener) {
        tr_classNameId.setText(item.getClassName());
        tr_subjectNameId.setText(item.getSubjectName());
        cl_winterTimeViewId.setText(String.format("Winter start-end: %s", item.getWinterTime()));
        cl_summerTimeViewId.setText(String.format("Summer start-end: %s", item.getSummerTime()));
    }
}
