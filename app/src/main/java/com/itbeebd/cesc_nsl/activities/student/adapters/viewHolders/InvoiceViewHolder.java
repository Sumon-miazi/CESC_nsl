package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

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
import com.itbeebd.cesc_nsl.utils.dummy.Invoice;

public class InvoiceViewHolder extends BaseViewHolder<Invoice, OnRecyclerObjectClickListener<Invoice>> {

    private final ConstraintLayout due_row;
    private final TextView dueNoId;
    private final TextView descriptionViewId;
    private final TextView monthNameViewId;
    private final TextView statusViewId;
    private final TextView amountViewId;

    private Context context;

    public InvoiceViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        due_row = itemView.findViewById(R.id.due_row);
        dueNoId = itemView.findViewById(R.id.dueNoId);
        descriptionViewId = itemView.findViewById(R.id.descriptionViewId);
        monthNameViewId = itemView.findViewById(R.id.monthNameViewId);
        statusViewId = itemView.findViewById(R.id.statusViewId);
        amountViewId = itemView.findViewById(R.id.amountViewId);
    }

    @SuppressLint("DefaultLocale")
    @Override
    public void onBind(Invoice item, @Nullable OnRecyclerObjectClickListener<Invoice> listener) {

        if(this.getPosition() % 2 == 0){
            due_row.setBackgroundColor(context.getResources().getColor(R.color.first_row));
        }
        else due_row.setBackgroundColor(context.getResources().getColor(R.color.second_row));

        dueNoId.setText(String.valueOf(this.getPosition() + 1));

        descriptionViewId.setText(item.getAccount_head_name());
        descriptionViewId.setText(item.getAccount_head_name());

        String date = "" ;
//        if(item.getCollected_month() <= 6 && item.getClassType().equals("College")){
//            date = item.getMonth() + " " + (item.getAcademic_year() + 1);
//        }
//        else date = item.getMonth() + " " + item.getAcademic_year();

    //    dueMonth.setText(date);

        statusViewId.setText(item.getStatus());
        amountViewId.setText(String.valueOf(item.getPaid_amount()));
    }

}
