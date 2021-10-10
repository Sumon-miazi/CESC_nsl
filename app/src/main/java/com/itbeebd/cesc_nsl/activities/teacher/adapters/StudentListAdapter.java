package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.StudentListViewHolder;
import com.itbeebd.cesc_nsl.sugarClass.Student;

public class StudentListAdapter  extends GenericRecyclerAdapter<Student, OnRecyclerObjectClickListener<Student>, StudentListViewHolder> {

    private Context context;

    public StudentListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public StudentListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new StudentListViewHolder(inflate(R.layout.single_student_view_for_student_list, parent), context);
    }

}