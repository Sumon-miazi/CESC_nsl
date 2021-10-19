package com.itbeebd.cesc_nsl.activities.teacher;

import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.teacherApi.NotificationApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;

import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class SendNotificationActivity extends AppCompatActivity {
    private CardView a_classCardId;
    private CardView a_sectionCardId;
    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private String[] classes;
    private String[] sections;
    private String selectedSection;
    private String selectedClass;
    private TeacherDao teacherDao;
    
    private TextInputLayout notiTitleViewId;
    private TextInputLayout notiBodyViewId;
    private Button sendNotiBtnId;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_notification);

        Toolbar mToolBar =  findViewById(R.id.sendNotiToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("SEND NOTIFICATION");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a_classCardId = findViewById(R.id.a_classCardId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);

        notiTitleViewId = findViewById(R.id.notiTitleViewId);
        notiBodyViewId = findViewById(R.id.notiBodyViewId);
        sendNotiBtnId = findViewById(R.id.sendNotiBtnId);

        setToolTip(a_classCardId, "Select a class");

        teacherDao = new TeacherDao();
        classes = teacherDao.getAllClasses();

        a_classCardId.setOnClickListener(view -> {
            if(classes == null){
                Toast.makeText(this, "No class found", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(classes.length == 0){
                Toast.makeText(this, "Class list is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                initClassSpinner();
            }
        });

        a_sectionCardId.setOnClickListener(view -> {
            if(sections == null){
                Toast.makeText(this, "Select a class first", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(sections.length == 0){
                Toast.makeText(this, "No section found", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                initSectionSpinner();
            }
        });

        sendNotiBtnId.setOnClickListener(this::sendNotification);
    }

    private void sendNotification(View view) {
        String title = notiTitleViewId.getEditText().getText().toString().trim();
        String message = notiBodyViewId.getEditText().getText().toString().trim();
        if(selectedClass == null){
            Toast.makeText(this, "Select a class first", Toast.LENGTH_SHORT).show();
            return;
        }
        if(title.isEmpty()){
            Toast.makeText(this, "Enter a title please", Toast.LENGTH_SHORT).show();
            return;
        }

        if(message.isEmpty()){
            Toast.makeText(this, "Enter a message please", Toast.LENGTH_SHORT).show();
            return;
        }
        int selectedClassId = teacherDao.getClassIdByName(selectedClass);
        int selectedSectionId = 0;
        if(selectedSection != null){
            selectedSectionId = teacherDao.getSectionIdByName(selectedSection);
        }
        
        callSentNotification(title, message, selectedClassId, selectedSectionId);
    }

    private void callSentNotification(String title, String message, int selectedClassId, int selectedSectionId){
        new NotificationApi(this, "Sending...").sendNotification(
                CustomSharedPref.getInstance(this).getAuthToken(),
                title,
                message,
                selectedClassId,
                selectedSectionId,
                (isSuccess, message1) -> {
                    Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    if(isSuccess){
                        finish();
                    }
                }
        );
    }

    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");

        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);
            a_sectionViewId.setText("Skip or Select");

            sections = teacherDao.getAllSectionByClassName(selectedClass);
            selectedSection = null;
            
            System.out.println(">>>>>> class " + which);

          //  setToolTip(a_sectionCardId, "Select a section");
        });

        b.show();
    }

    private void initSectionSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a section");

        b.setItems(sections, (dialog, which) -> {
            System.out.println(">>>>>> section " + which);
            selectedSection = sections[which];
            a_sectionViewId.setText(selectedSection.substring(selectedSection.lastIndexOf("-") + 1));

        });

        b.show();
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

    private void setToolTip(CardView view, String tip){
        new SimpleTooltip.Builder(this)
                .anchorView(view)
                .text(tip)
                .gravity(Gravity.BOTTOM)
                .animated(true)
                .transparentOverlay(false)
                .build()
                .show();
    }

}