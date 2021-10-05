package com.itbeebd.cesc_nsl.activities.teacher.viewHolders;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.GuidedStudentProfileActivity;
import com.itbeebd.cesc_nsl.sugarClass.Student;

import net.cachapa.expandablelayout.ExpandableLayout;

public class GuideStudentListViewHolder extends BaseViewHolder<Student, OnRecyclerObjectClickListener<Student>>{

    private final Context context;
    private final TextView studentID;
    private final TextView slId;
    private final TextView studentRollViewId;
    private final TextView studentNameViewId;
    private final ConstraintLayout attendanceRow;
    private final ExpandableLayout expandableLayout;
    private final TextView motherNameId;
    private final TextView motherPhoneId;
    private final TextView addressViewId;
    private final TextView viewStudentProfile;
    public static ExpandableLayout previousNode;

    public GuideStudentListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.studentID = itemView.findViewById(R.id.studentIDid);
        this.attendanceRow = itemView.findViewById(R.id.first_row);
        this.slId = itemView.findViewById(R.id.slId);
        this.studentRollViewId = itemView.findViewById(R.id.studentRollViewId);
        this.studentNameViewId = itemView.findViewById(R.id.studentNameViewId);

        this.expandableLayout = itemView.findViewById(R.id.expandable_layout);
        this.motherNameId = itemView.findViewById(R.id.motherNameId);
        this.motherPhoneId = itemView.findViewById(R.id.motherPhoneId);
        this.addressViewId = itemView.findViewById(R.id.addressViewId);
        this.viewStudentProfile = itemView.findViewById(R.id.viewStudentProfile);
    }

    @Override
    public void onBind(Student item, @Nullable OnRecyclerObjectClickListener<Student> listener) {

        if(this.getPosition() % 2 == 0){
            attendanceRow.setBackgroundColor(context.getResources().getColor(R.color.first_row));
        }
        else attendanceRow.setBackgroundColor(context.getResources().getColor(R.color.second_row));

        slId.setText(String.valueOf(this.getPosition() + 1));
        studentRollViewId.setText(String.valueOf(item.getRoll()));
        studentNameViewId.setText(item.getName());
        studentID.setText(String.valueOf(item.getStudentId()));

        motherNameId.setText(String.format("Mother name: %s", item.getMother().getName()));
        motherPhoneId.setText(String.format("Mother phone: %s", item.getMother().getMobile()));
        addressViewId.setText(String.format("Present address: %s", item.getPresent_address()));

        item.getFather();

        attendanceRow.setOnClickListener(view -> {
            if(previousNode != null && previousNode != expandableLayout){
                previousNode.collapse();
            }
            expandableLayout.toggle();

            previousNode = this.expandableLayout;
        });

        viewStudentProfile.setOnClickListener(view -> {
            System.out.println(">>>>>>> student name " + item.getName());
            Intent intent = new Intent(context, GuidedStudentProfileActivity.class);
            intent.putExtra("student", item);
            context.startActivity(intent);
//            assert listener != null;
//            listener.onItemClicked(item, view);
        });

    }
}
