package com.itbeebd.cesc_nsl.activities.student.adapters.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.QuizArchive;
import com.parassidhu.simpledate.SimpleDateKt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class QuizArchiveViewHolder extends BaseViewHolder<QuizArchive, OnRecyclerObjectClickListener<QuizArchive>> {
    private Context context;
    private CardView rootItem;
    private TextView subjectNameViewId;
    private TextView examTitleId;
    private TextView totalMarkId;
    private TextView examDateId;

    public QuizArchiveViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        rootItem = itemView.findViewById(R.id.quizArchiveCardView);
        subjectNameViewId = itemView.findViewById(R.id.subjectNameViewId);
        examTitleId = itemView.findViewById(R.id.examTitleId);
        totalMarkId = itemView.findViewById(R.id.totalMarkId);
        examDateId = itemView.findViewById(R.id.examDateId);
    }

    @Override
    public void onBind(QuizArchive item, @Nullable OnRecyclerObjectClickListener<QuizArchive> listener) {
        subjectNameViewId.setText(item.getSubjectName());
        examTitleId.setText(item.getExamTitle());
        totalMarkId.setText(String.valueOf(item.getQuizArrayList().size()));
        examDateId.setText(SimpleDateKt.toDateStandard(getDateFromString(item.getExamDate())));

        rootItem.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }

    private Date getDateFromString(String dateString){
        System.out.println("????????? " + dateString);
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        try {
            date = format.parse(dateString);
            System.out.println("????????? " + date);
        } catch (Exception ignore) {
            ignore.printStackTrace();
            date = new Date();
        }
        return date;
    }
}
