package com.itbeebd.cesc_nsl.activities.student.viewHolders;

import android.content.Context;
import android.text.Html;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.Quiz;

public class QuizViewHolder extends BaseViewHolder<Quiz, OnRecyclerObjectClickListener<Quiz>> {

    private final TextView question;
    private final CheckBox option1;
    private final CheckBox option2;
    private final CheckBox option3;
    private final CheckBox option4;

    private final Context context;

    public QuizViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        question = itemView.findViewById(R.id.questionId);
        option1 = itemView.findViewById(R.id.op_1_ans_Id);
        option2 = itemView.findViewById(R.id.op_2_ans_Id);
        option3 = itemView.findViewById(R.id.op_3_ans_Id);
        option4 = itemView.findViewById(R.id.op_4_ans_Id);
    }

    @Override
    public void onBind(Quiz item, @Nullable OnRecyclerObjectClickListener<Quiz> listener) {
        String question_format = (this.getPosition() + 1) + ") " + item.getQuestion().trim().substring(3, item.getQuestion().trim().length() -  5);
        question.setText(Html.fromHtml(question_format.trim()));
        option1.setText(Html.fromHtml(item.getOption1()));
        option2.setText(Html.fromHtml(item.getOption2()));
        option3.setText(Html.fromHtml(item.getOption3()));
        option4.setText(Html.fromHtml(item.getOption4()));

        option1.setChecked(item.getAnswer() == 1);
        option2.setChecked(item.getAnswer() == 2);
        option3.setChecked(item.getAnswer() == 3);
        option4.setChecked(item.getAnswer() == 4);

    }
}
