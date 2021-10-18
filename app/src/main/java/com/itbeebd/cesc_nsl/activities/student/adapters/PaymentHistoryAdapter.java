package com.itbeebd.cesc_nsl.activities.student.adapters;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.GenericRecyclerAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.viewHolders.PaymentHistoryViewHolder;
import com.itbeebd.cesc_nsl.utils.dummy.Payment;

public class PaymentHistoryAdapter  extends GenericRecyclerAdapter<Payment, OnRecyclerObjectClickListener<Payment>, PaymentHistoryViewHolder> {

    private Context context;

    public PaymentHistoryAdapter(Context context) {
        super(context);
        this.context = context;
    }

    @NonNull
    @Override
    public PaymentHistoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new PaymentHistoryViewHolder(inflate(R.layout.single_payment_history_view, parent), context);
    }

}