package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;

import com.itbeebd.cesc_nsl.utils.Payment;

public class DuePaymentViewHolder extends BaseViewHolder<Payment, OnRecyclerObjectClickListener<Payment>> {

    private final TextView accountHead;
    private final TextView dueMonth;
    private final TextView amount;
    private final TextView paidAmount;
    private final TextView waiver;
    private final TextView dueAmount;

    private Context context;

    public DuePaymentViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        accountHead = itemView.findViewById(R.id.accountHeadId);
        dueMonth = itemView.findViewById(R.id.dueMonthId);
        amount = itemView.findViewById(R.id.amountId);
        paidAmount = itemView.findViewById(R.id.paidAmountId);
        waiver = itemView.findViewById(R.id.waiverId);
        dueAmount = itemView.findViewById(R.id.dueAmountId);
    }

    @Override
    public void onBind(Payment item, @Nullable OnRecyclerObjectClickListener<Payment> listener) {

    }
}