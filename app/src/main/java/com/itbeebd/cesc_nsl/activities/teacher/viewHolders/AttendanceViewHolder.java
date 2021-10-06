package com.itbeebd.cesc_nsl.activities.teacher.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.ClassAttendance;

public class AttendanceViewHolder extends BaseViewHolder<ClassAttendance, OnRecyclerObjectClickListener<ClassAttendance>> {

    private final Context context;
    private final CheckBox isPresent;
    private final TextView slId;
    private final TextView studentRollViewId;
    private final TextView studentNameViewId;
    private final ConstraintLayout attendanceRow;

    public AttendanceViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.isPresent = itemView.findViewById(R.id.absentOrPresentCheckId);
        this.attendanceRow = itemView.findViewById(R.id.first_row);
        this.slId = itemView.findViewById(R.id.slId);
        this.studentRollViewId = itemView.findViewById(R.id.studentRollViewId);
        this.studentNameViewId = itemView.findViewById(R.id.studentNameViewId);
    }

    @Override
    public void onBind(ClassAttendance item, @Nullable OnRecyclerObjectClickListener<ClassAttendance> listener) {

        if(this.getPosition() % 2 == 0){
            attendanceRow.setBackgroundColor(context.getResources().getColor(R.color.first_row));
        }
        else attendanceRow.setBackgroundColor(context.getResources().getColor(R.color.second_row));

        slId.setText(String.valueOf(this.getPosition() + 1));
        studentRollViewId.setText(String.valueOf(item.getRoll()));
        studentNameViewId.setText(item.getName());

//        isPresent.setOnCheckedChangeListener((compoundButton, b) -> {
////            System.out.println("check >>>>> " + b);
////            System.out.println("student_id >>>>> " + item.getStudentid());
////
////            isPresent.setChecked(b);
////            isPresent.setText(b? "P" : "A");
////            isPresent.setTextColor(context.getResources().getColor(b? R.color.green_active : R.color.red_active));
////
////            item.setPresent();
//            assert listener != null;
//            listener.onItemClicked(item, compoundButton);
//        });

        isPresent.setChecked(item.isPresent());
        isPresent.setText(item.isPresent()? "P" : "A");
        isPresent.setTextColor(context.getResources().getColor(item.isPresent()? R.color.green_active : R.color.red_active));

        isPresent.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }
}
