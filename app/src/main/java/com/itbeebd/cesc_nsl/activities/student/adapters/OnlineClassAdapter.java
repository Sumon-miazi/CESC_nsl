package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.OnlineClassViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.OnlineClass;

public class OnlineClassAdapter extends GenericRecyclerAdapter<OnlineClass, OnRecyclerObjectClickListener<OnlineClass>, OnlineClassViewHolder> {

    private final Context context;

    public OnlineClassAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public OnlineClassViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new OnlineClassViewHolder(inflate(R.layout.single_online_class, parent), context);
    }

}