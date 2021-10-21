package com.itbeebd.cesc_nsl.activities.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.student.adapters.QuizListAdapter;
import com.itbeebd.cesc_nsl.api.studentApi.QuizApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.dummy.LiveQuiz;
import com.itbeebd.cesc_nsl.utils.dummy.Quiz;

import java.util.ArrayList;

public class QuizListActivity extends AppCompatActivity  implements OnRecyclerObjectClickListener<LiveQuiz> {
    private RecyclerView quizListRecyclerViewId;
    private ArrayList<LiveQuiz> liveQuizzes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_list);

        Toolbar mToolBar = findViewById(R.id.quizListToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("LIVE QUIZ");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quizListRecyclerViewId = findViewById(R.id.quizListRecyclerViewId);

//        liveQuizzes = new ArrayList<>();
//        liveQuizzes.add(new LiveQuiz("Bangla", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("English", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Math", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Higher Math", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Physics", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Botany", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Zoology", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Chemistry", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Chemistry 2", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Biology", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Sociology", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Computer", "Test quiz", "2021-07-03T10:09:06.000000Z"));
//        liveQuizzes.add(new LiveQuiz("Ict", "Test quiz", "2021-07-03T10:09:06.000000Z"));

      //  setUpAdapter();
    }

    @Override
    protected void onStart() {
        super.onStart();
        callLiveQuizApi();
    }

    private void callLiveQuizApi() {
        new QuizApi(this, "Loading...").getLiveQuizList(
                CustomSharedPref.getInstance(this).getAuthToken(),
                (object, message) -> {
                    if(object != null){
                        liveQuizzes = (ArrayList<LiveQuiz>) object;
                        if(liveQuizzes.isEmpty()){
                            Toast.makeText(this, "No Data Found!", Toast.LENGTH_SHORT).show();
                        }
                        setUpAdapter();
                    }
                    else {
                        try {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception ignore){}
                    }
                }
        );
    }

    private void setUpAdapter(){
        if(liveQuizzes == null){
            return;
        }
        QuizListAdapter quizListAdapter = new QuizListAdapter(this);
        quizListAdapter.setItems(liveQuizzes);
        quizListAdapter.setListener(this);
        quizListRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        quizListRecyclerViewId.setAdapter(quizListAdapter);
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

    @Override
    public void onItemClicked(LiveQuiz item, View view) {

        new QuizApi(this,"Loading...").getLiveQuizzes(
                CustomSharedPref.getInstance(this).getAuthToken(),
                item.getId(),
                (object, message) -> {
                    if(object != null){
                        item.setQuizArrayList((ArrayList< Quiz >) object);
                        Intent intent = new Intent(this, QuizActivity.class);
                        intent.putExtra("liveQuiz", item);
                        startActivity(intent);
                    }
                    else {
                        try {
                            Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        }
                        catch (Exception ignore){}
                    }
                }
        );
    }
}