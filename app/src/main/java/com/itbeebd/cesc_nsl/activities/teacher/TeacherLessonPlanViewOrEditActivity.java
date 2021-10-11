package com.itbeebd.cesc_nsl.activities.teacher;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.switchmaterial.SwitchMaterial;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.activities.teacher.adapters.TeacherLessonPlanAdapter;
import com.itbeebd.cesc_nsl.api.teacherApi.LessonApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.utils.NotificationReminder;
import com.itbeebd.cesc_nsl.utils.dummy.LessonFile;
import com.itbeebd.cesc_nsl.utils.dummy.TeacherLessonPlan;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import abhishekti7.unicorn.filepicker.UnicornFilePicker;
import abhishekti7.unicorn.filepicker.utils.Constants;
import io.github.douglasjunior.androidSimpleTooltip.SimpleTooltip;
import okhttp3.MediaType;
import okhttp3.MultipartBody;

public class TeacherLessonPlanViewOrEditActivity extends AppCompatActivity implements OnRecyclerObjectClickListener<String> {

    private CardView a_classCardId;
    private CardView a_sectionCardId;
    private CardView a_subjectCardId;

    private TextView a_subjectViewId;
    private TextView a_classViewId;
    private TextView a_sectionViewId;
    private TextInputLayout lessonTitleId;

    private RecyclerView lessonFileRecyclerViewId;

    private LinearLayout addMoreLessonFileBtnId;
    private Button lessonPlanSubmitBtnId;

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
    private TeacherLessonPlanAdapter lessonPlanAdapter;

    private SwitchMaterial lessonPlanEditId;

    private TeacherLessonPlan lessonPlan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_lesson_plan2);

        Toolbar mToolBar =  findViewById(R.id.guideStudentListToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("LESSON PLAN");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mSelected_files = new ArrayList<>();

        lessonPlanEditId = findViewById(R.id.lessonPlanEditId);
        a_subjectCardId = findViewById(R.id.a_subjectCardId);
        a_subjectViewId = findViewById(R.id.a_subjectViewId);
        a_classCardId = findViewById(R.id.a_classCardId);
        a_classViewId = findViewById(R.id.a_classViewId);
        a_sectionCardId = findViewById(R.id.a_sectionCardId);
        a_sectionViewId = findViewById(R.id.a_sectionViewId);
        lessonTitleId = findViewById(R.id.lessonTitleId);
        lessonFileRecyclerViewId = findViewById(R.id.lessonFileRecyclerViewId);
        addMoreLessonFileBtnId = findViewById(R.id.addMoreLessonFileBtnId);
        lessonPlanSubmitBtnId = findViewById(R.id.lessonPlanSubmitBtnId);

        teacherDao = new TeacherDao();
        classes = teacherDao.getAllClasses();

        lessonPlanSubmitBtnId.setOnClickListener(this::submitLessonPlan);

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
            if(selectedClass == null){
                setToolTip(a_classCardId, "Select a class");
                return;
            }
            else if(subjects == null){
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

        lessonPlanEditId.setOnClickListener((view) -> {
            addMoreLessonFileBtnId.setVisibility(lessonPlanEditId.isChecked()? View.VISIBLE : View.GONE);
            a_classCardId.setClickable(lessonPlanEditId.isChecked());
            a_sectionCardId.setClickable(lessonPlanEditId.isChecked());
            a_subjectCardId.setClickable(lessonPlanEditId.isChecked());

            lessonPlanSubmitBtnId.setVisibility(lessonPlanEditId.isChecked()? View.VISIBLE : View.GONE);
            lessonTitleId.setEnabled(lessonPlanEditId.isChecked());

            if(lessonPlanEditId.isChecked()){ CustomSharedPref.getInstance(this).setUserModeNo(2); }
            else CustomSharedPref.getInstance(this).setUserModeNo(1);
            // enableOrDisableCheckbox();
            setAdapter();
        });

        if(getIntent().hasExtra("TeacherLessonPlan")){
            lessonPlan = (TeacherLessonPlan) getIntent().getSerializableExtra("TeacherLessonPlan");
            setup();
        }

        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
    }

    private void setup() {
        lessonTitleId.getEditText().setText(lessonPlan.getTitle());
        a_classViewId.setText(teacherDao.getClassNameById(lessonPlan.getClassId()));

        selectedClass = teacherDao.getClassNameById(lessonPlan.getClassId());
        sections = teacherDao.getAllSectionByClassName(selectedClass);
        subjects = teacherDao.getAllSubjectByClassName(selectedClass);

       // a_sectionViewId.setText(lessonPlan.getSectionsNameHorizontal());

        String[] tempSelectedSection = lessonPlan.getStrSection();
        selectedSection = new String[sections.length];

        for(int i = 0; i < selectedSection.length; i++){

        }

        allSectionSelected = new boolean[sections.length];
        Arrays.fill(allSectionSelected, false);

        for(int i = 0; i < sections.length; i++){
            System.out.println("sections[" + i + "] " + sections[i] );
            boolean flag = true;
            for(int j = 0; j < tempSelectedSection.length; j++){
                if(tempSelectedSection[j].equals(sections[i])){
                    selectedSection[i] = sections[i];
                    allSectionSelected[i] = true;
                    flag = false;
                }
            }
            if(flag) selectedSection[i] = null;
        }

        a_sectionViewId.setText(getSelectedSection(true, -1));

        a_subjectViewId.setText(teacherDao.getSubjectNameById(lessonPlan.getSubjectId()));
        selectedSubject = teacherDao.getSubjectNameById(lessonPlan.getSubjectId());

        a_classCardId.setClickable(lessonPlanEditId.isChecked());
        a_sectionCardId.setClickable(lessonPlanEditId.isChecked());
        a_subjectCardId.setClickable(lessonPlanEditId.isChecked());

        mSelected_files = lessonPlan.getLessonFiles();
        addMoreLessonFileBtnId.setVisibility(View.GONE);
        lessonPlanSubmitBtnId.setText("update");
        lessonPlanEditId.setVisibility(View.VISIBLE);


        lessonTitleId.setEnabled(false);

        setAdapter();
    }


    private void submitLessonPlan(View view) {
        String title = lessonTitleId.getEditText().getText().toString();
        if(title.isEmpty()){
            lessonTitleId.setError("Enter a title");
            return;
        }

        if(selectedClass == null){
            showToast("Select a class please");
            return;
        }

        if(selectedSection.length == 0){
            showToast("Select a section please");
            return;
        }

        if(selectedSubject == null){
            showToast("Select a subject please");
            return;
        }

        new LessonApi(this, "Uploading...").insertLessonPlan(
                CustomSharedPref.getInstance(this).getAuthToken(),
                generateJson(),
                mSelected_files,
                (isSuccess, message) -> {
                    if(isSuccess){
                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                }
        );

//        new LessonApi(this, "Inserting...").insertLessonPlan(
//                CustomSharedPref.getInstance(this).getAuthToken(),
//                generateJson(),
//                (isSuccess, message) -> {
//                    if(isSuccess){
//                        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//                        finish();
//                    }
//                    else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
//                }
//        );
    }

    private void showToast(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //  int REQUEST_CODE_CHOOSE = 100;
        if (resultCode == RESULT_OK) {
            ArrayList<String> files = data.getStringArrayListExtra("filePaths");

            int lastItemPosition = mSelected_files.size();

            for(int i = 0; i < files.size(); i++){
                System.out.println(">>>>>>> " + files.get(i));
                mSelected_files.add(files.get(i));
                //    fileNames += mSelected_files.get(i) + "\n";
            }

            setAdapter();
            //   itemRangeInserted(lastItemPosition,files.size());

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
            a_subjectViewId.setText(selectedSubject.substring(selectedSubject.lastIndexOf("-") + 1));
        });

        b.show();
    }

    private String getSelectedSection(boolean addItem, int index){

        if(index >= 0){
            if(addItem) selectedSection[index] = sections[index];
            else selectedSection[index] = null;
        }

        String selectedItems = "";

        for(int i = 0; i < selectedSection.length; i++){
            String data = selectedSection[i];
            if(data != null){
                String[] str = data.split("-");
                selectedItems += str[1] + ", ";
               // selectedItems += data + ", ";
            }
        }

        String selectedSectionStr = selectedItems.substring(0, selectedItems.length() >= 2? selectedItems.length()-2 : 0);
        if(selectedSectionStr.isEmpty()) return "SELECT";
        else return selectedSectionStr;
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

    private void setAdapter(){
        lessonPlanSubmitBtnId.setVisibility(lessonPlanEditId.isChecked()? View.VISIBLE : View.GONE);
        lessonPlanAdapter = new TeacherLessonPlanAdapter(this);
        lessonPlanAdapter.setListener(this);
        lessonPlanAdapter.setItems(mSelected_files);
        lessonFileRecyclerViewId.setLayoutManager(new LinearLayoutManager(this));
        lessonFileRecyclerViewId.setAdapter(lessonPlanAdapter);
    }

    @Override
    public void onItemClicked(String item, View view) {

        if (view.getId() == R.id.removeFileBtnId) {
            for (int i = 0; i < mSelected_files.size(); i++) {
                if (mSelected_files.get(i).equalsIgnoreCase(item)) {
                    mSelected_files.remove(item);
                    //   lessonPlanAdapter.remove(item);
                    //    itemRemoved(i);
                    setAdapter();
                    break;
                }
            }
        }
        else if(view.getId() == R.id.lessonPlanCardViewId){
            System.out.println("item >>>>>>> " + item);
            try {
                if(item.startsWith("http")){
                    Intent intent = new Intent(Intent.ACTION_VIEW);
                    intent.setData(Uri.parse(item));
                    startActivity(intent);
                }
                else {
                    startActivity(new NotificationReminder(this).openFile(item));
                }
            }
            catch (Exception e){
                e.printStackTrace();
            }

        }
    }

    private JsonObject generateJson() {

        JSONArray sectionArray = new JSONArray();
        for(int i = 0; i < selectedSection.length; i++){
            if(selectedSection[i] == null) continue;
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("section_id", teacherDao.getSectionIdByName(selectedSection[i]));
                jsonObject.put("name",selectedSection[i]);
                sectionArray.put(jsonObject);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        JSONArray preFileArray = new JSONArray();
        ArrayList<LessonFile> lessonFiles = lessonPlan.getFiles();
        for(int i = 0; i < lessonFiles.size(); i++){
            System.out.println("FullUrl >>>>> " + lessonFiles.get(i).getFullUrl());
            boolean flag = true;
            for(int j = 0; j < mSelected_files.size(); j++){
                System.out.println(" mSelected_files FullUrl >>>>> " + mSelected_files.get(j));
                System.out.println(" mSelected_files if >>>>> " + lessonFiles.get(i).getFullUrl().equals(mSelected_files.get(j)));
                if(lessonFiles.get(i).getFullUrl().equals(mSelected_files.get(j))){
                    mSelected_files.remove(j);
                    flag = false;
                    break;
                }
            }

            if(flag){
                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("id", lessonFiles.get(i).getFile_id());
                    preFileArray.put(jsonObject);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        JSONObject temp = new JSONObject();
        try {
            temp.put("id", lessonPlan.getId());
            temp.put("pre_file", preFileArray);
            temp.put("teacher_id", teacherDao.getTeacher(this).getId());
            temp.put("std_class_id", teacherDao.getClassIdByName(selectedClass));
            temp.put("subject_id", teacherDao.getSubjectIdByName(selectedSubject));
            temp.put("title",lessonTitleId.getEditText().getText().toString().trim());
            temp.put("teacher_upload_file_sections", sectionArray);
            //     temp.put("teacher_upload_file_details", fileArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        JsonParser jsonParser = new JsonParser();
        JsonObject gsonObject = (JsonObject) jsonParser.parse(temp.toString());
        return gsonObject;
    }

    private MultipartBody.Part getFile(String imageFilePath){
        if(imageFilePath == null || imageFilePath.isEmpty())
            return null;
        File file = new File(imageFilePath); // initialize file here
        //   System.out.println(">>>>>>>>> file " + file.toString());
        String imageName = imageFilePath.substring(imageFilePath.lastIndexOf("/")+1);
        return MultipartBody.Part.createFormData("file", imageName, okhttp3.RequestBody.create(MediaType.parse("image/*"), file));
    }

    private void itemRangeInserted(int startPosition, int total) {
        lessonFileRecyclerViewId.post(() -> {
            lessonPlanAdapter.notifyItemRangeInserted(startPosition, total);
        });
    }

    private void itemRemoved(int position) {
        lessonFileRecyclerViewId.post(() -> {
            System.out.println("size >>>>>>>>>>. " + mSelected_files.size());
            lessonPlanAdapter.notifyItemRemoved(position);
        });
    }
}
