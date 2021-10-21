package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.ResultBoardViewHolder;
import com.itbeebd.cesc_nsl.sugarClass.ResultObj;

public class ResultBoardAdapter  extends GenericRecyclerAdapter<ResultObj, OnRecyclerObjectClickListener<ResultObj>, ResultBoardViewHolder> {

    private final Context context;

    public ResultBoardAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ResultBoardViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultBoardViewHolder(inflate(R.layout.single_subject_result_view, parent), context);
    }

}