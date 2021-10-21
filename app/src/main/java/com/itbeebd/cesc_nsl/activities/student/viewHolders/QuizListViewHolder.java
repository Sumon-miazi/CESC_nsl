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
import java.util.Calendar;
import java.util.Date;

public class QuizListViewHolder  extends BaseViewHolder<LiveQuiz, OnRecyclerObjectClickListener<LiveQuiz>> {
    private final Context context;
    private final CardView rootItem;
    private final TextView subjectNameViewId;
    private final TextView examTitleId;
    private final TextView timeRemainningId;
    private final TextView examDateId;
    private final TextView examEndDateId;
    private final TextView timerHintId;
    private final Button participateBtnId;
    private CountDownTimer startCountDownTimer;
    private CountDownTimer endCountDownTimer;
    private boolean examTimeEnd;

    public QuizListViewHolder(@NonNull View itemView, Context context) {
        super(itemView);
        this.context = context;

        rootItem = itemView.findViewById(R.id.quizArchiveCardView);
        subjectNameViewId = itemView.findViewById(R.id.subjectNameViewId);
        examTitleId = itemView.findViewById(R.id.examTitleId);
        timeRemainningId = itemView.findViewById(R.id.timeRemainingId);
        examDateId = itemView.findViewById(R.id.examDateId);
        examEndDateId = itemView.findViewById(R.id.examEndDateId);
        timerHintId = itemView.findViewById(R.id.timerHintId);
        participateBtnId = itemView.findViewById(R.id.participateBtnId);
        startCountDownTimer = null;
        endCountDownTimer = null;
    }


    @Override
    public void onBind(LiveQuiz item, @Nullable OnRecyclerObjectClickListener<LiveQuiz> listener) {
        subjectNameViewId.setText(item.getSubjectName());
        examTitleId.setText(item.getExamTitle());

        String examStartStr = "Quiz start time: " + SimpleDateKt.toDateTimeStandardIn12Hours(getDateFromString(item.getExamStartDateTime()));
        String examEndStr = "Quiz end time: " + SimpleDateKt.toDateTimeStandardIn12Hours(getDateFromString(item.getExamEndDateTime()));

        examDateId.setText(examStartStr);
        examEndDateId.setText(examEndStr);

        participateBtnId.setOnClickListener(view -> {
            assert listener != null;
            listener.onItemClicked(item, view);
        });

        if(item.getFinal_submit().equals("yes")){
            timeRemainningId.setText("Already perticipate");
            timerHintId.setText("You can't participate twice");
            participateBtnId.setVisibility(View.GONE);
        }
        else {
            // exam start timer
            timeRemainningId.setTextColor(context.getResources().getColor(R.color.white_inactive_5));
            timerHintId.setText(R.string.start_hint);
            countDown((int) getDateDiff(getDateFromString(
                    item.getExamStartDateTime()),
                    Calendar.getInstance().getTime()),
                    item
            );
        }
    }

    private void countDown(int timeInMillis, LiveQuiz item){
        this.examTimeEnd = false;
        if(startCountDownTimer == null)
            startCountDownTimer =  new CountDownTimer(timeInMillis, 1000) {

            public void onTick(long millisUntilFinished) {
                Long totalSecRemaining = millisUntilFinished / 1000;
                Long hour = totalSecRemaining / 3600;

                totalSecRemaining = totalSecRemaining % 3600;
                Long min = totalSecRemaining / 60;

                Long sec = totalSecRemaining % 60;

                String timeRemaining = hour + "h : " + min + "m : " + sec +"s";
                timeRemainningId.setText(timeRemaining);
            }

            public void onFinish() {
                callTimerFinish(item);
            }
        }.start();
    }

    private void countDown2(int timeInMillis, LiveQuiz item){
        this.examTimeEnd = true;
        if(endCountDownTimer == null)
            endCountDownTimer =  new CountDownTimer(timeInMillis, 1000) {

                public void onTick(long millisUntilFinished) {
                    Long totalSecRemaining = millisUntilFinished / 1000;
                    Long hour = totalSecRemaining / 3600;

                    totalSecRemaining = totalSecRemaining % 3600;
                    Long min = totalSecRemaining / 60;

                    Long sec = totalSecRemaining % 60;

                    String timeRemaining = hour + "h : " + min + "m : " + sec +"s";
                    timeRemainningId.setText(timeRemaining);
                }

                public void onFinish() {
                    callTimerFinish(item);
                }
            }.start();
    }

    private void callTimerFinish(LiveQuiz item){
        if(examTimeEnd){
            timeRemainningId.setText("Quiz Ended");
            timerHintId.setText("Result will publish soon");
            participateBtnId.setVisibility(View.GONE);
        }
        else {
            countDown2((int) getDateDiff(getDateFromString(
                    item.getExamEndDateTime()),
                    Calendar.getInstance().getTime()),
                    item
            );
            timeRemainningId.setTextColor(context.getResources().getColor(R.color.red_inactive));
            timerHintId.setText("Quiz End Time Remaining");
            participateBtnId.setVisibility(View.VISIBLE);
        }
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



    /**
     * Get a diff between two dates
     * @return the diff value, in the days
     */
    private long getDateDiff(Date startDate, Date endDate) {

       // SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            long time = startDate.getTime() - endDate.getTime()  ;
            if(time < 0) return 1;
            else return time;
           // return TimeUnit.DAYS.convert(format.parse(newDate).getTime() - Calendar.getInstance().getTime(), TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
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
