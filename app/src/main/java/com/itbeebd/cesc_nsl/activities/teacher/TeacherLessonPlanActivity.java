package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.dao.TeacherDao;

import java.util.ArrayList;
import java.util.Arrays;

import abhishekti7.unicorn.filepicker.UnicornFilePicker;
import abhishekti7.unicorn.filepicker.utils.Constants;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;

public class TeacherLessonPlanActivity extends AppCompatActivity {

    private CardView a_classCardId;
    private CardView a_sectionCardId;
    private CardView a_subjectCardId;

    private TextView a_subjectViewId;
    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private TextView fileNamesId;

    private LinearLayout addMoreLessonFileBtnId;
    private Button  lessonPlanSubmitBtnId;

    private String[] classes;
    private String[] sections;
    private String[] subjects;

    private String[] selectedSection;
    private String selectedClass;
    private String selectedSubject;
    private TeacherDao teacherDao;
    private boolean[] allSectionSelected;
    private static final int REQUEST_WRITE_PERMISSION = 786;

    private ArrayList<String> mSelected_files;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lesson_plan2);

        Toolbar mToolBar =  findViewById(R.id.guideStudentListToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("LESSON PLAN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        a_subjectCardId = findViewById(R.id.a_subjectCardId);
        a_subjectViewId = findViewById(R.id.a_subjectViewId);
        a_classCardId = findViewById(R.id.a_classCardId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);
        fileNamesId = findViewById(R.id.fileNamesId);
        addMoreLessonFileBtnId = findViewById(R.id.addMoreLessonFileBtnId);
        lessonPlanSubmitBtnId = findViewById(R.id.lessonPlanSubmitBtnId);

        teacherDao = new TeacherDao();
        classes = teacherDao.getAllClasses();

        lessonPlanSubmitBtnId.setVisibility(View.GONE);

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

        a_subjectCardId.setOnClickListener(view -> {
            if(subjects == null){
                Toast.makeText(this, "No subject found", Toast.LENGTH_SHORT).show();
                return;
            }
            else if(subjects.length == 0){
                Toast.makeText(this, "Subject list is empty", Toast.LENGTH_SHORT).show();
                return;
            }
            else {
                initSubjectSpinner();
            }
        });

        addMoreLessonFileBtnId.setOnClickListener(view -> {
            System.out.println("addMoreLessonFileBtnId");
            requestPermission();
        });

        setToolTip(a_classCardId, "Select a class");

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
      //  int REQUEST_CODE_CHOOSE = 100;
        if (resultCode == RESULT_OK) {
            mSelected_files = data.getStringArrayListExtra("filePaths");

            String fileNames = "";
            if(mSelected_files != null)
            for(int i = 0; i < mSelected_files.size(); i++){
                System.out.println(">>>>>>> " + mSelected_files.get(i));
                fileNames += mSelected_files.get(i) + "\n";
            }
            fileNamesId.setText(fileNames);

        }
    }

    private void initClassSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a class");
        b.setItems(classes, (dialog, which) -> {
            selectedClass = classes[which];
            a_classViewId.setText(selectedClass);

            sections = teacherDao.getAllSectionByClassName(selectedClass);
            subjects = teacherDao.getAllSubjectByClassName(selectedClass);
            a_subjectViewId.setText("Select");

            selectedSection = sections.clone();
            allSectionSelected = new boolean[sections.length];
            Arrays.fill(allSectionSelected, true);

            a_sectionViewId.setText(getSelectedSection(true, 0));

            System.out.println(">>>>>> class " + which);

            setToolTip(a_subjectCardId, "Select a subject");
        });

        b.show();
    }

    private void initSubjectSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select a subject");
        b.setItems(subjects, (dialog, which) -> {
            selectedSubject = subjects[which];
            a_subjectViewId.setText(selectedSubject);
        });

        b.show();
    }

    private String getSelectedSection(boolean addItem, int index){

        System.out.println("index >>>>>. " + index);
        System.out.println("sections item >>>>>. " + sections[index]);
        System.out.println("selectedSection item >>>>>. " + selectedSection[index]);

        if(addItem) selectedSection[index] = sections[index];
        else selectedSection[index] = null;

        String selectedItems = "";

        for(int i = 0; i < selectedSection.length; i++){
            String data = selectedSection[i];
            if(data != null){
                String[] str = data.split("-");
                selectedItems += str[1] + ", ";
            }
        }

        return selectedItems.substring(0, selectedItems.length() >= 2? selectedItems.length()-2 : 0);
    }

    private void initSectionSpinner(){

        AlertDialog.Builder b = new AlertDialog.Builder(this);
        b.setTitle("Select section");

        b.setMultiChoiceItems(sections, allSectionSelected, (dialogInterface, i, b1) -> {
            System.out.println("b1 >>>>>>>> " + b1);
            allSectionSelected[i] = b1;
            a_sectionViewId.setText(getSelectedSection(b1, i));
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

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            //  openFilePicker();
            openFileChooser();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //   openFilePicker();
            openFileChooser();
        }
        else {
            Toast.makeText(this, "You will not be able to upload or download any files without giving access permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void openFileChooser(){
        UnicornFilePicker.from(this)
                .addConfigBuilder()
                .selectMultipleFiles(true)
                .showOnlyDirectory(false)
                .setRootDirectory(Environment.getExternalStorageDirectory().getAbsolutePath())
                .showHiddenFiles(false)
                .setFilters(new String[]{"pdf", "png", "jpg", "jpeg"})
                .addItemDivider(true)
                .theme(R.style.UnicornFilePicker_Dracula)
                .build()
                .forResult(Constants.REQ_UNICORN_FILE);
    }
}