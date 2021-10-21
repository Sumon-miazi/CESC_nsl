package com.itbeebd.cesc_nsl.activities.teacher.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.viewHolders.AbsentFeeViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.AbsentFeeObject;

public class AbsentFeeAdapter  extends GenericRecyclerAdapter<AbsentFeeObject, OnRecyclerObjectClickListener<AbsentFeeObject>, AbsentFeeViewHolder> {

    private final Context context;

    public AbsentFeeAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public AbsentFeeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new AbsentFeeViewHolder(inflate(R.layout.single_absent_fee_view, parent), context);
    }

}