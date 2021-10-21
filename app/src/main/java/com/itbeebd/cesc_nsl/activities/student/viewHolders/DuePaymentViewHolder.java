package com.itbeebd.cesc_nsl.activities.student.viewHolders;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;

import com.itbeebd.cesc_nsl.utils.dummy.DueHistory;

public class DuePaymentViewHolder extends BaseViewHolder<DueHistory, OnRecyclerObjectClickListener<DueHistory>> {

    private final ConstraintLayout due_row;
    private final TextView dueNoId;
    private final TextView dueTitleId;
    private final TextView dueMonth;
    private final TextView totalDueAmountId;
  //  private final TextView paidAmountId;
    private final TextView waiverId;
    private final TextView totalRemainingDueId;

    private final Context context;

    public DuePaymentViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        due_row = itemView.findViewById(R.id.due_row);
        dueNoId = itemView.findViewById(R.id.dueNoId);
        dueTitleId = itemView.findViewById(R.id.dueTitleId);
        dueMonth = itemView.findViewById(R.id.monthNameId);
        totalDueAmountId = itemView.findViewById(R.id.totalDueAmountId);
      //  paidAmountId = itemView.findViewById(R.id.paidAmountId);
        waiverId = itemView.findViewById(R.id.waiverId);
        totalRemainingDueId = itemView.findViewById(R.id.totalRemainingDueId);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBind(DueHistory item, @Nullable OnRecyclerObjectClickListener<DueHistory> listener) {

        if(this.getPosition() % 2 == 0){
            due_row.setBackgroundColor(context.getResources().getColor(R.color.first_row));
        }
        else due_row.setBackgroundColor(context.getResources().getColor(R.color.second_row));

        dueNoId.setText(String.valueOf(this.getPosition() + 1));
        dueTitleId.setText(item.getAccount_head_name());

        String date = "" ;
        if(item.getCollected_month() <= 6 && item.getClassType().equals("College")){
            date = item.getMonth() + " " + (item.getAcademic_year() + 1);
        }
        else date = item.getMonth() + " " + item.getAcademic_year();

        dueMonth.setText(date);
     //   System.out.println("++++++++ " + item.getDue_amount());
        totalDueAmountId.setText(String.valueOf(item.getAmount()));
     //   paidAmountId.setText(String.valueOf(item.getPaidAmount()));
        totalRemainingDueId.setText(String.valueOf(item.getDue_amount()));
        waiverId.setText(String.valueOf(item.getWeiber()));
    }
}