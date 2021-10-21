package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.QuizArchiveViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.QuizArchive;

public class QuizArchiveAdapter extends GenericRecyclerAdapter<QuizArchive, OnRecyclerObjectClickListener<QuizArchive>, QuizArchiveViewHolder> {

    private final Context context;

    public QuizArchiveAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public QuizArchiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new QuizArchiveViewHolder(inflate(R.layout.single_quiz_archive_view, parent), context);
    }

}