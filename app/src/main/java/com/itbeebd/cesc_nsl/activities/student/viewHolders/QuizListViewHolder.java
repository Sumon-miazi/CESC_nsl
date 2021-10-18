package com.itbeebd.cesc_nsl.activities.student.viewHolders;

import android.content.Context;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.BaseViewHolder;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.LiveQuiz;
import com.parassidhu.simpledate.SimpleDateKt;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class QuizListViewHolder  extends BaseViewHolder<LiveQuiz, OnRecyclerObjectClickListener<LiveQuiz>> {
    private Context context;
    private CardView rootItem;
    private TextView subjectNameViewId;
    private TextView examTitleId;
    private TextView timeRemainningId;
    private TextView examDateId;
    private Button participateBtnId;
    private CountDownTimer countDownTimer;

    public QuizListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        rootItem = itemView.findViewById(R.id.quizArchiveCardView);
        subjectNameViewId = itemView.findViewById(R.id.subjectNameViewId);
        examTitleId = itemView.findViewById(R.id.examTitleId);
        timeRemainningId = itemView.findViewById(R.id.timeRemainingId);
        examDateId = itemView.findViewById(R.id.examDateId);
        participateBtnId = itemView.findViewById(R.id.participateBtnId);

    }


    @Override
    public void onBind(LiveQuiz item, @Nullable OnRecyclerObjectClickListener<LiveQuiz> listener) {
        subjectNameViewId.setText(item.getSubjectName());
        examTitleId.setText(item.getExamTitle());
      //  timeRemainningId.setText(timeClockId.getText());
        examDateId.setText(SimpleDateKt.toDateTimeStandardIn12Hours(getDateFromString(item.getExamStartDateTime())));
        System.out.println("Exam time >>>>>>> " + item.getExamStartDateTime());

        participateBtnId.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

        countDown(100000, 1000);
    }

    private void countDown(int timeInMillis, int interval){
        if(countDownTimer == null)
        countDownTimer =  new CountDownTimer(timeInMillis, interval) {

            public void onTick(long millisUntilFinished) {
                Long totalSecRemaining = millisUntilFinished / interval;
                Long hour = totalSecRemaining / 3600;

                totalSecRemaining = totalSecRemaining % 3600;
                Long min = totalSecRemaining / 60;

                Long sec = totalSecRemaining % 60;


                String timeRemaining = hour + "h : " + min + "m : " + sec +"s";
                timeRemainningId.setText(timeRemaining);
            }

            public void onFinish() {
                timeRemainningId.setText("Quiz is started");
                participateBtnId.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    private Date getDateFromString(String dateString){
        System.out.println("????????? " + dateString);
        Date date;
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("GMT"));
        try {
            date = format.parse(dateString);
            System.out.println("????????? " + date);
        } catch (Exception ignore) {
            ignore.printStackTrace();
            date = new Date();
        }
        return date;
    }

//    private Date getDateFromString(String dateString){
//        System.out.println("????????? " + dateString);
//
//        try {
//            Instant timeStamp = Instant.parse(dateString);
//            // To get Time or Date," with Instant you must provide time-zone too"
//            ZonedDateTime dateTimeZone = ZonedDateTime.ofInstant(timeStamp, ZoneOffset.UTC);
//
//            System.out.println(dateTimeZone);
//            System.out.println(dateTimeZone.toLocalDate());// can also be tolocalTime
//            return java.sql.Timestamp.valueOf(String.valueOf(dateTimeZone.toLocalDateTime()));
//        } catch (Exception ignore) {
//            ignore.printStackTrace();
//        }
//
//        return new Date();
//    }

}
