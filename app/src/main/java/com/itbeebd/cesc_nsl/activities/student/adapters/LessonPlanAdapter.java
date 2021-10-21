package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.LessonPlanViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.LessonPlan;

public class LessonPlanAdapter  extends GenericRecyclerAdapter<LessonPlan, OnRecyclerObjectClickListener<LessonPlan>, LessonPlanViewHolder> {

    private final Context context;

    public LessonPlanAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public LessonPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LessonPlanViewHolder(inflate(R.layout.single_lesson_plan_view, parent), context);
    }

}