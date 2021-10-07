package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.TeacherLessonPlanListViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.TeacherLessonPlan;

public class TeacherLessonPlanListAdapter   extends GenericRecyclerAdapter<TeacherLessonPlan, OnRecyclerObjectClickListener<TeacherLessonPlan>, TeacherLessonPlanListViewHolder> {
    private Context context;

    public TeacherLessonPlanListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherLessonPlanListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherLessonPlanListViewHolder(inflate(R.layout.single_teacher_lesson_plan_list_view, parent), context);
    }
}
