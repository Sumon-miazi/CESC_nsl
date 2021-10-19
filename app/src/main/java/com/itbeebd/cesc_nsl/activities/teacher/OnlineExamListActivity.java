package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.adapters.OnlineExamListAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.OnlineExamApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.dummy.OnlineExam;
import com.itbeebd.cesc_nsl.utils.dummy.Quiz;

import java.util.ArrayList;

public class OnlineExamListActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<OnlineExam> {

    private RecyclerView onlineExamListRecyclerViewId;
    private ArrayList<OnlineExam> onlineExams;
    private Button addOnlineExamBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_exam_list);


        Toolbar mToolBar = findViewById(R.id.onlineExamListToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("ONLINE EXAM");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onlineExamListRecyclerViewId = findViewById(R.id.onlineExamListRecyclerViewId);
        addOnlineExamBtnId = findViewById(R.id.addOnlineExamBtnId);

        addOnlineExamBtnId.setOnClickListener(view -> {
            startActivity(new Intent(this, OnlineExamActivity.class));
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        callLiveQuizApi();
    }

    private void callLiveQuizApi() {
        new OnlineExamApi(this, "Loading...").getOnlineExamList(
                CustomSharedPref.getInstance(this).getAuthToken(),
                (object, message) -> {
                    if(object != null){
                       onlineExams = (ArrayList<OnlineExam>) object;
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
        if(onlineExams == null){
            return;
        }
        OnlineExamListAdapter onlineExamListAdapter = new OnlineExamListAdapter(this);
        onlineExamListAdapter.setItems(onlineExams);
        onlineExamListAdapter.setListener(this);
        onlineExamListRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        onlineExamListRecyclerViewId.setAdapter(onlineExamListAdapter);
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
    public void onItemClicked(OnlineExam item, View view) {

        new OnlineExamApi(this,"Loading...").getOnlineExamQuizzes(
                CustomSharedPref.getInstance(this).getAuthToken(),
                item.getId(),
                (object, message) -> {
                    if(object != null){
                        item.setQuizArrayList((ArrayList<Quiz>) object);
                        Intent intent = new Intent(this, OnlineExamActivity.class);
                        intent.putExtra("onlineExam", item);
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