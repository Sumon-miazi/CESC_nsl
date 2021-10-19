package com.itbeebd.cesc_nsl.activities.student;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.QuizArchiveAdapter;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.api.studentApi.QuizApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.dummy.QuizArchive;

import java.util.ArrayList;

public class QuizArchiveActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<QuizArchive> {

    private RecyclerView quizArchiveRecyclerViewId;
    private ArrayList<QuizArchive> quizArchives;
    private int numberOfColumns = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_archive);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.quizArchiveToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("QUIZ ARCHIVE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        quizArchiveRecyclerViewId = findViewById(R.id.quizArchiveRecyclerViewId);

        callQuizArchiveApi();
    }

    private void callQuizArchiveApi() {
        new QuizApi(this, "Loading...").getQuizArchive(
                CustomSharedPref.getInstance(this).getAuthToken(),
                (object, message) -> {
                    if(object != null){
                        quizArchives = (ArrayList<QuizArchive>) object;
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
        QuizArchiveAdapter archiveAdapter = new QuizArchiveAdapter(this);
        archiveAdapter.setItems(quizArchives);
        archiveAdapter.setListener(this);
        quizArchiveRecyclerViewId.setLayoutManager(new GridLayoutManager(this, this.numberOfColumns));
        quizArchiveRecyclerViewId.setAdapter(archiveAdapter);
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
    public void onItemClicked(QuizArchive item, View view) {
        if(item.getQuizArrayList() != null){
            if(item.getQuizArrayList().size() != 0){
                Intent intent = new Intent(this, QuizActivity.class);
                intent.putExtra("quiz", item.getQuizArrayList());
                startActivity(intent);
            }
        }
    }
}