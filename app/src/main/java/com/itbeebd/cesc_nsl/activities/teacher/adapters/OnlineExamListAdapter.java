package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.OnlineExamListViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.OnlineExam;

public class OnlineExamListAdapter  extends GenericRecyclerAdapter<OnlineExam, OnRecyclerObjectClickListener<OnlineExam>, OnlineExamListViewHolder> {
    private final Context context;

    public OnlineExamListAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public OnlineExamListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnlineExamListViewHolder(inflate(R.layout.single_online_exam_list_view, parent), context);
    }

}
