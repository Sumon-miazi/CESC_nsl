package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;

import com.itbeebd.cesc_nsl.utils.DueHistory;
import com.itbeebd.cesc_nsl.utils.Payment;

public class DuePaymentViewHolder extends BaseViewHolder<DueHistory, OnRecyclerObjectClickListener<DueHistory>> {

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

    @SuppressLint("DefaultLocale")
    @Override
    public void onBind(DueHistory item, @Nullable OnRecyclerObjectClickListener<DueHistory> listener) {
        accountHead.setText(item.getAccount_head_name());

        String date = "" ;
        if(item.getCollected_month() <= 6 && item.getClassType().equals("College")){
            date = item.getMonth() + " " + (item.getAcademic_year() + 1);
        }
        else date = item.getMonth() + " " + item.getAcademic_year();

        dueMonth.setText(date);
        System.out.println("++++++++ " + item.getDue_amount());
        amount.setText(String.format("Amount: %d", item.getAmount()));
        paidAmount.setText(String.format("Paid: %d", item.getPaidAmount()));
        dueAmount.setText(String.valueOf(item.getDue_amount()));
        waiver.setText(String.format("Waiver: %d", item.getWeiber()));
    }
}