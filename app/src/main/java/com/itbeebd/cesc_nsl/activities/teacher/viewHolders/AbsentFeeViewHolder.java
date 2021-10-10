package com.itbeebd.cesc_nsl.activities.teacher.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.AbsentFeeObject;

public class AbsentFeeViewHolder extends BaseViewHolder<AbsentFeeObject, OnRecyclerObjectClickListener<AbsentFeeObject>> {

    private final Context context;
    private final ConstraintLayout row;
    private final TextView std_nameId;
    private final TextView std_IDId;
    private final TextView std_rollId;
    private final TextView totalDaysId;
    private final TextView presentDayId;
    private final TextView absentDayId;

    public AbsentFeeViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.row = itemView.findViewById(R.id.due_row);
        this.std_nameId = itemView.findViewById(R.id.std_nameId);
        this.std_IDId = itemView.findViewById(R.id.std_IDId);
        this.std_rollId = itemView.findViewById(R.id.std_rollId);
        this.totalDaysId = itemView.findViewById(R.id.totalDaysId);
        this.presentDayId = itemView.findViewById(R.id.presentDayId);
        this.absentDayId = itemView.findViewById(R.id.absentDayId);
    }

    @Override
    public void onBind(AbsentFeeObject item, @Nullable OnRecyclerObjectClickListener<AbsentFeeObject> listener) {

        if(this.getPosition() % 2 == 0){
            row.setBackgroundColor(context.getResources().getColor(R.color.first_row));
        }
        else row.setBackgroundColor(context.getResources().getColor(R.color.second_row));

        std_nameId.setText(item.getStudentName());
        std_IDId.setText(String.format("Student ID: %s", item.getStudentId()));
        std_rollId.setText(String.format("Roll No: %s", item.getRoll()));
        totalDaysId.setText(String.valueOf(item.getTotalDays()));
        presentDayId.setText(String.valueOf(item.getTotalPresent()));
        absentDayId.setText(String.valueOf(item.getTotalAbsent()));
    }
}
