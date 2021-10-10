package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.TeacherRoutineViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.TeacherRoutine;

public class TeacherRoutineAdapter  extends GenericRecyclerAdapter<TeacherRoutine, OnRecyclerObjectClickListener<TeacherRoutine>, TeacherRoutineViewHolder> {

    private Context context;

    public TeacherRoutineAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public TeacherRoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new TeacherRoutineViewHolder(inflate(R.layout.single_teacher_routine_view, parent), context);
    }

}