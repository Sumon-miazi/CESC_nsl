package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.ClassRoutineViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.ClassRoutine;

public class ClassRoutineAdapter extends GenericRecyclerAdapter<ClassRoutine, OnRecyclerObjectClickListener<ClassRoutine>, ClassRoutineViewHolder> {

    private final Context context;

    public ClassRoutineAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ClassRoutineViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ClassRoutineViewHolder(inflate(R.layout.single_class_routine, parent), context);
    }

}