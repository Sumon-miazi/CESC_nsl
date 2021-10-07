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
import com.itbeebd.cesc_nsl.utils.dummy.TeacherLessonPlan;

public class TeacherLessonPlanListViewHolder  extends BaseViewHolder<TeacherLessonPlan, OnRecyclerObjectClickListener<TeacherLessonPlan>> {

    private final Context context;
    private final ConstraintLayout resultRowId;
    private final TextView subjectNoId;
    private final TextView lessonTitleViewId;
    private final TextView classNameViewId;
    private final TextView sectionNameId;
    private final TextView subjectNameId;

    public TeacherLessonPlanListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;
        this.resultRowId = itemView.findViewById(R.id.resultRowId);
        this.subjectNoId = itemView.findViewById(R.id.subjectNoId);
        this.lessonTitleViewId = itemView.findViewById(R.id.lessonTitleViewId);
        this.classNameViewId = itemView.findViewById(R.id.classNameViewId);
        this.sectionNameId = itemView.findViewById(R.id.sectionNameId);
        this.subjectNameId = itemView.findViewById(R.id.subjectNameId);

    }

    @Override
    public void onBind(TeacherLessonPlan item, @Nullable OnRecyclerObjectClickListener<TeacherLessonPlan> listener) {
        if(this.getPosition() % 2 == 0){
            resultRowId.setBackgroundColor(context.getResources().getColor(R.color.first_row));
        }
        else resultRowId.setBackgroundColor(context.getResources().getColor(R.color.second_row));

        subjectNoId.setText(String.valueOf(this.getPosition() + 1));
        lessonTitleViewId.setText(item.getTitle());
        classNameViewId.setText(item.getClassName());
        subjectNameId.setText(item.getSubjectName());

        sectionNameId.setText(item.getSectionsName());

        resultRowId.setOnClickListener(view -> {
          //  assert listener != null;
         //   listener.onItemClicked(item, view);
        });
    }

    private String[] getFileTypeAndFileName(String file){
        String type = file.substring(file.lastIndexOf(".") + 1);
        String name = file.substring(file.lastIndexOf("/") + 1);

        return new String[]{type, name};
    }
}
