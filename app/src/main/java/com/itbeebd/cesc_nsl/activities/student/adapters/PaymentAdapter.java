package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders.LessonPlanViewHolder;
import com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders.PaymentViewHolder;
import com.itbeebd.cesc_nsl.utils.LessonPlan;
import com.itbeebd.cesc_nsl.utils.Payment;

public class PaymentAdapter extends GenericRecyclerAdapter<Payment, OnRecyclerObjectClickListener<Payment>, PaymentViewHolder> {

    private Context context;

    public PaymentAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PaymentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaymentViewHolder(inflate(R.layout.single_due_amount_view, parent), context);
    }

}