package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.utils.Notification;

import java.util.ArrayList;

public class StudentAllNotificationActivity extends AppCompatActivity {

    private ArrayList<Notification> notifications;
    private RecyclerView studentNotificationRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_all_notification);

        Toolbar mToolBar = (Toolbar) findViewById(R.id.notificationToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("NOTIFICATION");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        studentNotificationRecyclerView = findViewById(R.id.studentNotificationRecyclerViewId);

        if(getIntent().hasExtra("notifications")){
            notifications = (ArrayList<Notification>) getIntent().getSerializableExtra("notifications");

            if(notifications != null) setNotificationAdapter();
        }

        System.out.println(">>>>>>> notice " + notifications.size() );
    }

    private void setNotificationAdapter(){
        StudentNotificationAdapter notificationAdapter = new StudentNotificationAdapter(this);
        notificationAdapter.setItems(notifications);
        studentNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        //    studentNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        studentNotificationRecyclerView.setAdapter(notificationAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}