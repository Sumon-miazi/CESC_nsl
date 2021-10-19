package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.adapters.QuizAdapter;
import com.itbeebd.cesc_nsl.api.studentApi.QuizApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.dummy.LiveQuiz;
import com.itbeebd.cesc_nsl.utils.dummy.Quiz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class QuizActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<Quiz> {

    private RecyclerView quizRecyclerViewId;
    private ArrayList<Quiz> quizArrayList;
    private LiveQuiz liveQuiz;
    private Toolbar mToolBar;
    private Button quizSubmitBtnId;
    private LinearLayout liveQuizTimerLayoutId;
    private TextView quizTimerViewId;
    private TextView timeRId;
    private CountDownTimer countDownTimer;
    private String type;
    private QuizAdapter quizAdapter;
    private boolean quizAlreadySubmitted = false;
    private int rightAnswer = 0;
    private int wrongAnswer = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);

        mToolBar =  findViewById(R.id.quizToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("QUIZ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quizRecyclerViewId = findViewById(R.id.quizRecyclerViewId);
        liveQuizTimerLayoutId = findViewById(R.id.liveQuizTimerLayoutId);
        quizTimerViewId = findViewById(R.id.quizTimerViewId);
        timeRId = findViewById(R.id.timeRId);
        quizSubmitBtnId = findViewById(R.id.quizSubmitBtnId);

        quizSubmitBtnId.setOnClickListener(view -> submitQuizResult());

        if(getIntent().hasExtra("quiz")){
            quizArrayList = (ArrayList<Quiz>) getIntent().getSerializableExtra("quiz");
            type = "oldQuiz";
            setupAdapter();
        }
        if(getIntent().hasExtra("liveQuiz")){
            liveQuiz = (LiveQuiz) getIntent().getSerializableExtra("liveQuiz");
            quizArrayList = liveQuiz.getQuizArrayList();
            type = "liveQuiz";
            setupForLiveQuiz();
        }
    }

    private void setupForLiveQuiz(){
        mToolBar.setVisibility(View.GONE);
        setupAdapter();
        liveQuizTimerLayoutId.setVisibility(View.VISIBLE);
        quizSubmitBtnId.setVisibility(View.VISIBLE);
        countDown((int) getDateDiff(getDateFromString(
                liveQuiz.getExamEndDateTime()),
                Calendar.getInstance().getTime())
        );
    }

    private void setupAdapter() {
        if(quizArrayList != null){
            quizAdapter = new QuizAdapter(this, type);
            quizAdapter.setItems(quizArrayList);
            quizRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
            quizRecyclerViewId.setAdapter(quizAdapter);
        }
    }

    private void submitQuizResult(){
        rightAnswer = 0;
        wrongAnswer = 0;
        calculate();

        new QuizApi(this, "Submitting...").submitLiveExam(
                CustomSharedPref.getInstance(this).getAuthToken(),
                liveQuiz.getId(),
                rightAnswer,
                wrongAnswer,
                (isSuccess, message) -> {
                    quizAlreadySubmitted = isSuccess;
                    if(isSuccess || message.equals("Already submitted")){
                        quizTimerViewId.setText("Quiz Submitted");
                        countDownTimer.cancel();
                        quizSubmitBtnId.setVisibility(View.GONE);
                        timeRId.setVisibility(View.INVISIBLE);
                    }
                    else {
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        quizSubmitBtnId.setVisibility(View.VISIBLE);
                        timeRId.setVisibility(View.VISIBLE);
                    }
                }
        );
    }

    private void countDown(int timeInMillis){
            countDownTimer =  new CountDownTimer(timeInMillis, 1000) {

                public void onTick(long millisUntilFinished) {
                    Long totalSecRemaining = millisUntilFinished / 1000;
                    Long hour = totalSecRemaining / 3600;

                    totalSecRemaining = totalSecRemaining % 3600;
                    Long min = totalSecRemaining / 60;

                    Long sec = totalSecRemaining % 60;

                    String timeRemaining = hour + "h : " + min + "m : " + sec +"s";
                    quizTimerViewId.setText(timeRemaining);
                }

                public void onFinish() {
                    quizTimerViewId.setText("Quiz has finished");
                    timeRId.setVisibility(View.INVISIBLE);
                    quizSubmitBtnId.setVisibility(View.GONE);
                    if(!quizAlreadySubmitted) submitQuizResult();
                }
            }.start();
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

    @Override
    public void onItemClicked(Quiz item, View view) {
        for(int i = 0; i < quizArrayList.size(); i++){
            if(item.getQuestion().equals(quizArrayList.get(i).getQuestion())){
                quizArrayList.set(i,quizArrayList.get(i));

                refreshAdapter(i);

                break;
            }
        }
    }

    private void refreshAdapter(int i){
        quizRecyclerViewId.post(() -> {
            quizAdapter.notifyItemChanged(i);
        });
    }

    private void calculate(){
        for(int i = 0; i < quizArrayList.size(); i++){
            if(quizArrayList.get(i).getCheckedAnswer() != 0){
                if(quizArrayList.get(i).getCheckedAnswer() == quizArrayList.get(i).getAnswer()) {
                    rightAnswer += 1;
                }
                else {
                    wrongAnswer += 1;
                }
            }
        }
    }







    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}