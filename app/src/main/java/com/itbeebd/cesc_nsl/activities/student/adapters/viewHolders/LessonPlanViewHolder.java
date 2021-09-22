package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.LessonPlan;

public class LessonPlanViewHolder extends BaseViewHolder<LessonPlan, OnRecyclerObjectClickListener<LessonPlan>> {

    private final TextView teacherName;
    private final TextView subjectName;
    private final TextView lessonTitle;
    private final TextView lastUpdated;

    private Context context;

    public LessonPlanViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        teacherName = itemView.findViewById(R.id.lp_teacherNameId);
        subjectName = itemView.findViewById(R.id.lp_subjectNameId);
        lessonTitle = itemView.findViewById(R.id.lp_lessonTitleId);
        lastUpdated = itemView.findViewById(R.id.lp_lessonUpdateDatesId);
    }

    @Override
    public void onBind(LessonPlan item, @Nullable OnRecyclerObjectClickListener<LessonPlan> listener) {
        System.out.println("<<<< " + item.getTeacherName());
        teacherName.setText(item.getTeacherName());
        subjectName.setText(item.getSubjectName());
        lessonTitle.setText(item.getLessonTile());
        lastUpdated.setText(item.getLastUpdated());
    }
}