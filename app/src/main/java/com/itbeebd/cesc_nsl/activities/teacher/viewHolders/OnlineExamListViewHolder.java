package com.itbeebd.cesc_nsl.activities.teacher.viewHolders;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.OnlineExam;
import com.parassidhu.simpledate.SimpleDateKt;

import java.text.SimpleDateFormat;
import java.util.Date;

public class OnlineExamListViewHolder  extends BaseViewHolder<OnlineExam, OnRecyclerObjectClickListener<OnlineExam>> {
    private Context context;
    private CardView rootItem;
    private TextView subjectNameViewId;
    private TextView examTitleId;
    private TextView totalMarkId;
    private TextView examDateId;
    private TextView classNameId;
    private TextView resultStatusId;


    public OnlineExamListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        rootItem = itemView.findViewById(R.id.onlineExamCardId);
        subjectNameViewId = itemView.findViewById(R.id.subjectNameViewId);
        examTitleId = itemView.findViewById(R.id.examTitleId);
        totalMarkId = itemView.findViewById(R.id.totalMarkId);
        examDateId = itemView.findViewById(R.id.examDateId);
        classNameId = itemView.findViewById(R.id.classNameId);
        resultStatusId = itemView.findViewById(R.id.resultStatusId);
    }


    @Override
    public void onBind(OnlineExam item, @Nullable OnRecyclerObjectClickListener<OnlineExam> listener) {
        subjectNameViewId.setText(item.getSubjectName());
        examTitleId.setText(item.getExamTitle());

        String examStartStr = "Quiz will start at: " + SimpleDateKt.toDateTimeStandardIn12Hours(getDateFromString(item.getExamStartDateTime()));

        examDateId.setText(examStartStr);
        classNameId.setText("Class: " + item.getClassName());
        resultStatusId.setText("Exam Status: " + item.getPublishStatus());
        totalMarkId.setText("Total Mark: " + item.getTotalMark());

        rootItem.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });
    }

    private Date getDateFromString(String dateString){
        System.out.println("????????? " + dateString);
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        //   format.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            date = format.parse(dateString);
            System.out.println("????????? " + date);
            return date;
        } catch (Exception ignore) {
            ignore.printStackTrace();
        }
        return null;
    }
}
