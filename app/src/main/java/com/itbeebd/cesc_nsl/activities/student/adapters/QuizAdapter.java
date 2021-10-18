package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.QuizViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.Quiz;

public class QuizAdapter extends GenericRecyclerAdapter<Quiz, OnRecyclerObjectClickListener<Quiz>, QuizViewHolder> {

    private Context context;

    public QuizAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public QuizViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuizViewHolder(inflate(R.layout.single_quiz_view, parent), context);
    }

}