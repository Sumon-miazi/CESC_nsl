package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.DuePaymentViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.DueHistory;

public class DuePaymentAdapter extends GenericRecyclerAdapter<DueHistory, OnRecyclerObjectClickListener<DueHistory>, DuePaymentViewHolder> {

    private final Context context;

    public DuePaymentAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public DuePaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DuePaymentViewHolder(inflate(R.layout.single_due_history_view, parent), context);
    }

}