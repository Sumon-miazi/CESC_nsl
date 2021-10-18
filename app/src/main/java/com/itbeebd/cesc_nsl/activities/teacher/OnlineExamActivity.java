package com.itbeebd.cesc_nsl.activities.teacher;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.itbeebd.cesc_nsl.R;

import java.util.ArrayList;

public class OnlineExamActivity extends AppCompatActivity {

    private LinearLayout questionLayoutId;
    private Button addMoreQuestionBtnId;
    private ArrayList<View> views;
    private int questionNo = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_exam);

        views = new ArrayList<>();

        questionLayoutId = findViewById(R.id.questionLayoutId);
        addMoreQuestionBtnId = findViewById(R.id.addMoreQuestionBtnId);


        addMoreQuestionBtnId.setOnClickListener(view -> addQuestionTemp());

        addQuestionTemp();
    }

    @SuppressLint("DefaultLocale")
    private void addQuestionTemp() {
        View view = LayoutInflater.from(this).inflate(R.layout.single_quiz_setup_view, questionLayoutId, false);
        TextView questionNoHintId = view.findViewById(R.id.questionNoHintId);
        ImageView removeQuestionBoxId = view.findViewById(R.id.removeQuestionBoxId);

        questionNo += 1;
        questionNoHintId.setText(String.format("Question No: %d", questionNo));

        removeQuestionBoxId.setOnClickListener(v -> {
            if(questionNo == 1) return;

            questionNo -= 1;
            ((ViewGroup)view.getParent()).removeView(view);
            views.remove(view);

            for(int i = 0; i < views.size(); i++){
                TextView qId = views.get(i).findViewById(R.id.questionNoHintId);
                qId.setText(String.format("Question No: %d", i + 1));
            }

            Toast.makeText(this, "Item removed!", Toast.LENGTH_SHORT).show();
        });

        views.add(view);
        questionLayoutId.addView(view);
    }
}