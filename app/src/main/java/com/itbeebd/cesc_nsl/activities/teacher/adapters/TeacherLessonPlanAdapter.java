package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.TeacherLessonPlanViewHolder;

public class TeacherLessonPlanAdapter  extends GenericRecyclerAdapter<String, OnRecyclerObjectClickListener<String>, TeacherLessonPlanViewHolder>{
        private final Context context;

        public TeacherLessonPlanAdapter(Context context) {
            super(context);
            this.context = context;
        }

        @NonNull
        @Override
        public TeacherLessonPlanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new TeacherLessonPlanViewHolder(inflate(R.layout.single_lesson_plan_file_view, parent), context);
        }
}
