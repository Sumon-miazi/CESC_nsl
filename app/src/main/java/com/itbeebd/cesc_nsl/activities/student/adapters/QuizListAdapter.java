package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.QuizListViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.LiveQuiz;

public class QuizListAdapter extends GenericRecyclerAdapter<LiveQuiz, OnRecyclerObjectClickListener<LiveQuiz>, QuizListViewHolder> {
    private final Context context;

    public QuizListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public QuizListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuizListViewHolder(inflate(R.layout.single_quiz_list_view, parent), context);
    }

}
