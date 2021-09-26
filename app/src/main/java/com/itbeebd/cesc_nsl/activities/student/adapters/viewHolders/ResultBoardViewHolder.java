package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.sugarClass.ResultObj;

import net.cachapa.expandablelayout.ExpandableLayout;

public class ResultBoardViewHolder extends BaseViewHolder<ResultObj, OnRecyclerObjectClickListener<ResultObj>> {

    private final ConstraintLayout resultRowId;
    private final TextView subjectNoId;
    private final TextView subjectNameId;
    private final TextView subjectFullMarkViewId;
    private final TextView subjectTotalMarkViewId;
    private final TextView cgpViewId;
    private final TextView gradeViewId;
    private final TextView subjectiveMarkViewId;
    private final TextView objectMarkViewId;
    private final TextView practicalMarkViewId;
    private final TextView classTestMarkViewId;
    private final TextView highestMarkViewId;
    private final ExpandableLayout expandable_layout;


    private Context context;

    public ResultBoardViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        resultRowId = itemView.findViewById(R.id.resultRowId);
        subjectNoId = itemView.findViewById(R.id.subjectNoId);
        subjectNameId = itemView.findViewById(R.id.subjectNameId);
        subjectFullMarkViewId = itemView.findViewById(R.id.subjectTotalMarkViewId2);
        subjectTotalMarkViewId = itemView.findViewById(R.id.subjectTotalMarkViewId);
        cgpViewId = itemView.findViewById(R.id.cgpViewId);
        gradeViewId = itemView.findViewById(R.id.gradeViewId);
        subjectiveMarkViewId = itemView.findViewById(R.id.subjectMarkViewId);
        objectMarkViewId = itemView.findViewById(R.id.objectMarkViewId);
        practicalMarkViewId = itemView.findViewById(R.id.practicalMarkViewId);
        classTestMarkViewId = itemView.findViewById(R.id.classTestMarkViewId);
        expandable_layout = itemView.findViewById(R.id.expandable_layout);
        highestMarkViewId = itemView.findViewById(R.id.highestMarkViewId);

    }

    @Override
    public void onBind(ResultObj item, @Nullable OnRecyclerObjectClickListener<ResultObj> listener) {
        if(this.getPosition() % 2 == 0){
            resultRowId.setBackgroundColor(context.getResources().getColor(R.color.first_row));
        }
        else resultRowId.setBackgroundColor(context.getResources().getColor(R.color.second_row));

        subjectNoId.setText(String.valueOf(this.getPosition() + 1));
        highestMarkViewId.setText(String.format("HIGHEST MARK: %s", item.getHighest_mark()));
        subjectNameId.setText(item.getSubjectName());
        subjectFullMarkViewId.setText(String.valueOf(item.getFullMark()));
        subjectTotalMarkViewId.setText(String.valueOf(item.getTotalMark()));
        cgpViewId.setText(String.valueOf(item.getCgp()));
        gradeViewId.setText(String.valueOf(item.getGrade()));
        subjectiveMarkViewId.setText(String.format("Subjective Mark: %s", item.getSubjectiveMark()));
        objectMarkViewId.setText(String.format("Objective Mark: %s", item.getObjectiveMark()));
        practicalMarkViewId.setText(String.format("Practical Mark: %s", item.getPracticalMark()));
        classTestMarkViewId.setText(String.format("Class test Mark: %s", item.getCtMark()));

        resultRowId.setOnClickListener(view -> {
            expandable_layout.toggle();
        });
    }
}