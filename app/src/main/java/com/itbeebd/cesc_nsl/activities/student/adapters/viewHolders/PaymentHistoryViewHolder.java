package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.PaymentInvoiceActivity;
import com.itbeebd.cesc_nsl.utils.Payment;

public class PaymentHistoryViewHolder  extends BaseViewHolder<Payment, OnRecyclerObjectClickListener<Payment>> {

    private final TextView voucherNoViewId;
    private final TextView paymentMonthId;
    private final TextView paidAmountId;

    private Context context;

    public PaymentHistoryViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        voucherNoViewId = itemView.findViewById(R.id.voucherNoViewId);
        paymentMonthId = itemView.findViewById(R.id.paymentMonthId);
        paidAmountId = itemView.findViewById(R.id.paidAmountId);
    }

    @Override
    public void onBind(Payment item, @Nullable OnRecyclerObjectClickListener<Payment> listener) {
        voucherNoViewId.setText(item.getVoucher_no());
        paymentMonthId.setText(item.getDate());
        paidAmountId.setText(item.getAmount());

        voucherNoViewId.setOnClickListener(view -> {
            System.out.println("voucherNoViewId Clicked " + item.getVoucher_no());
            if(listener != null) listener.onItemClicked(item, view);
            else {
                Intent intent = new Intent(context, PaymentInvoiceActivity.class);
                intent.putExtra("paymentObj",item);
                context.startActivity(intent);
            }
        });
    }
}