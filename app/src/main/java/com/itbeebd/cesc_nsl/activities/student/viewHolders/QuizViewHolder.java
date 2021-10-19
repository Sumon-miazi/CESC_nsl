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
    private final String type;

    public QuizViewHolder(@NonNull View itemView, Context context, String type) {
        super(itemView);
        this.context = context;
        this.type = type;

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

        if(type.equals("liveQuiz")){
            option1.setEnabled(true);
            option2.setEnabled(true);
            option3.setEnabled(true);
            option4.setEnabled(true);

            option1.setChecked(item.getCheckedAnswer() == 1);
            option2.setChecked(item.getCheckedAnswer() == 2);
            option3.setChecked(item.getCheckedAnswer() == 3);
            option4.setChecked(item.getCheckedAnswer() == 4);
        }
        else {
            option1.setChecked(item.getAnswer() == 1);
            option2.setChecked(item.getAnswer() == 2);
            option3.setChecked(item.getAnswer() == 3);
            option4.setChecked(item.getAnswer() == 4);
        }


        option1.setOnClickListener(v -> {
            if (option1.isChecked()) {
                item.setCheckedAnswer(1);
            } else {
                item.setCheckedAnswer(0);
            }
            assert listener != null;
            listener.onItemClicked(item, null);
        });

        option2.setOnClickListener(v -> {
            if (option2.isChecked()) {
                item.setCheckedAnswer(2);
            } else {
                item.setCheckedAnswer(0);
            }
            assert listener != null;
            listener.onItemClicked(item, null);
        });

        option3.setOnClickListener(v -> {
            if (option3.isChecked()) {
                item.setCheckedAnswer(3);
            } else {
                item.setCheckedAnswer(0);
            }
            assert listener != null;
            listener.onItemClicked(item, null);
        });

        option4.setOnClickListener(v -> {
            if (option4.isChecked()) {
                item.setCheckedAnswer(4);
            } else {
                item.setCheckedAnswer(0);
            }
            assert listener != null;
            listener.onItemClicked(item, null);
        });

    }

}
