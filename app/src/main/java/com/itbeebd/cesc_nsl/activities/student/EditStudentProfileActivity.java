package com.itbeebd.cesc_nsl.activities.student;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.ProfileEditApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.utils.dummy.GuardianDummy;
import com.itbeebd.cesc_nsl.utils.dummy.StudentDummy;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class EditStudentProfileActivity extends AppCompatActivity {

    private Student student;
    private Guardian mother;
    private Guardian father;
    private List<Map<String, Object>> guardianInfoList;

    private ImageView userProfileViewId;
    private Button userImageChangeBtnId;
    private TextInputLayout religionTextFieldId;
    private TextInputLayout birthDayTextFieldId;
    private TextInputLayout presentAddressTextFieldId;
    private TextInputLayout permanentAddressTextFieldId;
    private TextInputLayout studentPhoneTextFieldId;
    private TextInputLayout nationalityTextFieldId;
    private TextInputLayout healthProblemTextFieldId;
    private TextInputLayout genderTextFieldId;
    private TextInputLayout bloodGroupTextFieldId;
    private TextInputLayout emailTextFieldId;
    private TextInputLayout previousSchoolTextFieldId;
    private TextInputLayout idMarkTextFieldId;

    private ImageView motherProfileViewId;
    private TextView motherRelationViewId;
    private Button motherImageChangeBtnId;
    private TextInputLayout motherNameTextFieldId;
    private TextInputLayout m_occupationTextFieldId;
    private TextInputLayout m_phoneTextFieldId;
    private TextInputLayout m_addressTextFieldId;
    private TextInputLayout m_parentBloodGroupTextFieldId;
    private TextInputLayout m_designationTextFieldId;
    private TextInputLayout m_parentOrganizationFieldId;
    private TextInputLayout m_parentEmailTextFieldId;

    private ImageView fatherProfileViewId;
    private TextView fatherRelationViewId;
    private Button fatherImageChangeBtnId;
    private TextInputLayout fatherNameTextFieldId;
    private TextInputLayout f_occupationTextFieldId;
    private TextInputLayout f_phoneTextFieldId;
    private TextInputLayout f_addressTextFieldId;
    private TextInputLayout f_parentBloodGroupTextFieldId;
    private TextInputLayout f_designationTextFieldId;
    private TextInputLayout f_parentOrganizationFieldId;
    private TextInputLayout f_parentEmailTextFieldId;

    private LinearLayout guardianInfoEditLayout;
    private Button submitChangesBtnId;

    private View changedButton;
    private String imageUrl;
    private String studentImageUrl;
    private String motherImageUrl;
    private String fatherImageUrl;

    private String imageOwner;
    public static final int PICK_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);

        Toolbar mToolBar = findViewById(R.id.editProfileToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("EDIT PROFILE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        guardianInfoList = new ArrayList<>();

        userProfileViewId = findViewById(R.id.userProfileViewId);
        userImageChangeBtnId = findViewById(R.id.userImageChangeBtnId);
        religionTextFieldId = findViewById(R.id.religionTextFieldId);
        birthDayTextFieldId = findViewById(R.id.birthDayTextFieldId);
        presentAddressTextFieldId = findViewById(R.id.presentAddressTextFieldId);
        permanentAddressTextFieldId = findViewById(R.id.permanentAddressTextFieldId);
        studentPhoneTextFieldId = findViewById(R.id.studentPhoneTextFieldId);
        nationalityTextFieldId = findViewById(R.id.nationalityTextFieldId);
        healthProblemTextFieldId = findViewById(R.id.healthProblemTextFieldId);
        genderTextFieldId = findViewById(R.id.genderTextFieldId);
        bloodGroupTextFieldId = findViewById(R.id.bloodGroupTextFieldId);
        emailTextFieldId = findViewById(R.id.emailTextFieldId);
        previousSchoolTextFieldId = findViewById(R.id.previousSchoolTextFieldId);
        idMarkTextFieldId = findViewById(R.id.idMarkTextFieldId);

      //  guardianInfoEditLayout = findViewById(R.id.guardianInfoEditLayout);

        motherProfileViewId = findViewById(R.id.motherBlockId).findViewById(R.id.guardianProfileViewId);
        motherRelationViewId = findViewById(R.id.motherBlockId).findViewById(R.id.studentGuardianRelationViewId);

        motherImageChangeBtnId = findViewById(R.id.motherBlockId).findViewById(R.id.parentImageChangeBtnId);
        motherNameTextFieldId = findViewById(R.id.motherBlockId).findViewById(R.id.parentNameTextFieldId);
        m_occupationTextFieldId = findViewById(R.id.motherBlockId).findViewById(R.id.occupationTextFieldId);
        m_phoneTextFieldId = findViewById(R.id.motherBlockId).findViewById(R.id.phoneTextFieldId);
        m_addressTextFieldId = findViewById(R.id.motherBlockId).findViewById(R.id.addressTextFieldId);
        m_parentBloodGroupTextFieldId = findViewById(R.id.motherBlockId).findViewById(R.id.parentBloodGroupTextFieldId);
        m_designationTextFieldId = findViewById(R.id.motherBlockId).findViewById(R.id.designationTextFieldId);
        m_parentOrganizationFieldId = findViewById(R.id.motherBlockId).findViewById(R.id.parentOrganizationFieldId);
        m_parentEmailTextFieldId = findViewById(R.id.motherBlockId).findViewById(R.id.parentEmailTextFieldId);



        fatherProfileViewId = findViewById(R.id.fatherBlockId).findViewById(R.id.guardianProfileViewId);
        fatherRelationViewId = findViewById(R.id.fatherBlockId).findViewById(R.id.studentGuardianRelationViewId);

        fatherImageChangeBtnId = findViewById(R.id.fatherBlockId).findViewById(R.id.parentImageChangeBtnId);
        fatherNameTextFieldId = findViewById(R.id.fatherBlockId).findViewById(R.id.parentNameTextFieldId);
        f_occupationTextFieldId = findViewById(R.id.fatherBlockId).findViewById(R.id.occupationTextFieldId);
        f_phoneTextFieldId = findViewById(R.id.fatherBlockId).findViewById(R.id.phoneTextFieldId);
        f_addressTextFieldId = findViewById(R.id.fatherBlockId).findViewById(R.id.addressTextFieldId);
        f_parentBloodGroupTextFieldId = findViewById(R.id.fatherBlockId).findViewById(R.id.parentBloodGroupTextFieldId);
        f_designationTextFieldId = findViewById(R.id.fatherBlockId).findViewById(R.id.designationTextFieldId);
        f_parentOrganizationFieldId = findViewById(R.id.fatherBlockId).findViewById(R.id.parentOrganizationFieldId);
        f_parentEmailTextFieldId = findViewById(R.id.fatherBlockId).findViewById(R.id.parentEmailTextFieldId);


        userImageChangeBtnId.setOnClickListener(view -> {changeImage("student");});
        motherImageChangeBtnId.setOnClickListener(view -> {changeImage("mother");});
        fatherImageChangeBtnId.setOnClickListener(view -> {changeImage("father");});

        submitChangesBtnId = findViewById(R.id.submitChangesBtnId);
        submitChangesBtnId.setOnClickListener(view -> {
            getAllEditedData();
        });

        student = new StudentDao().getStudent(this);

        if(student != null){
          //  guardians = new StudentDao().getGuardian(student);
            setUserProfileInfo();

            mother = new StudentDao().getGuardian(student, "Mother");
            father = new StudentDao().getGuardian(student, "Father");

            setStudentGuardianEditInfo(mother, father);

        }
    }

    private void setUserProfileInfo() {
        setImageInImageView(userProfileViewId, ApiUrls.BASE_IMAGE_URL + student.getImage());
        religionTextFieldId.getEditText().setText(student.getReligion());
        birthDayTextFieldId.getEditText().setText(student.getDate_of_birth());
        presentAddressTextFieldId.getEditText().setText(student.getPresent_address());
        permanentAddressTextFieldId.getEditText().setText(student.getPermanent_address());
        studentPhoneTextFieldId.getEditText().setText(student.getMobile());
        nationalityTextFieldId.getEditText().setText(student.getNationality());
        healthProblemTextFieldId.getEditText().setText(student.getHelth_problem());
        genderTextFieldId.getEditText().setText(student.getGender());
        bloodGroupTextFieldId.getEditText().setText(student.getBlood());
        emailTextFieldId.getEditText().setText(student.getEmail());
        previousSchoolTextFieldId.getEditText().setText(student.getPrevious_school());
        idMarkTextFieldId.getEditText().setText(student.getIdentification_mark());
    }

    private void setStudentGuardianEditInfo(Guardian mother, Guardian father) {

        // mother details
        if(mother != null){
            setImageInImageView(motherProfileViewId, ApiUrls.BASE_IMAGE_URL + mother.getProfileImage());
            motherRelationViewId.setText(mother.getRelation());
            motherNameTextFieldId.getEditText().setText(mother.getName());
            m_occupationTextFieldId.getEditText().setText(mother.getOccupation());
            m_parentOrganizationFieldId.getEditText().setText(mother.getOrganization());
            m_phoneTextFieldId.getEditText().setText(mother.getMobile());
            m_addressTextFieldId.getEditText().setText(mother.getLocation());
            m_parentBloodGroupTextFieldId.getEditText().setText(mother.getBlood_group());
            m_designationTextFieldId.getEditText().setText(mother.getDesignation());
            m_parentEmailTextFieldId.getEditText().setText(mother.getEmail());
        }


        // father details
        if(father != null){
            setImageInImageView(fatherProfileViewId, ApiUrls.BASE_IMAGE_URL + father.getProfileImage());
            fatherRelationViewId.setText(father.getRelation());
            fatherNameTextFieldId.getEditText().setText(father.getName());
            f_occupationTextFieldId.getEditText().setText(father.getOccupation());
            f_parentOrganizationFieldId.getEditText().setText(father.getOrganization());
            f_phoneTextFieldId.getEditText().setText(father.getMobile());
            f_addressTextFieldId.getEditText().setText(father.getLocation());
            f_parentBloodGroupTextFieldId.getEditText().setText(father.getBlood_group());
            f_designationTextFieldId.getEditText().setText(father.getDesignation());
            f_parentEmailTextFieldId.getEditText().setText(father.getEmail());
        }

    }

    public void changeImage(String name) {
        imageOwner = name;
        ImagePicker.with(this)
                .setFolderMode(true)
                .setFolderTitle("Album")
                .setDirectoryName("Image Picker")
                .setMultipleMode(false)
                .setShowNumberIndicator(true)
                .setMaxSize(1)
                .setLimitMessage("You can select up to 1 images")
                .setRequestCode(PICK_IMAGE)
                .start();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data != null) {
            ArrayList<Image> images = data.getParcelableArrayListExtra(Config.EXTRA_IMAGES);
            // Do stuff with image's path or id. For example:
            //      imageOfTheContent.setVisibility(View.VISIBLE);
            //      chooseBtn.setVisibility(View.GONE);
            //      cancel_image_selection.setVisibility(View.VISIBLE);
            setUpImageInView(images.get(0));
        }
    }

    private void setUpImageInView(Image image) {
        assert image != null;
          //  Uri filePath = Uri.fromFile(new File(image.getPath()));
            imageUrl = image.getPath();

            if(imageOwner.equals("student")) {
                studentImageUrl = image.getPath();
                setProfileImage(userProfileViewId, image);
            }
            else if(imageOwner.equals("mother")){
                motherImageUrl = image.getPath();
                setProfileImage(motherProfileViewId, image);
            }
            else if(imageOwner.equals("father")){
                fatherImageUrl = image.getPath();
                setProfileImage(fatherProfileViewId, image);
            }
    }

    private void setProfileImage(ImageView imageView, Image image) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(image.getId()));
            Glide.with(this)
                    .load(uri)
                  //  .apply(RequestOptions.skipMemoryCacheOf(true))
                   // .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .error(R.drawable.default_male)
                    .dontAnimate()
                    .centerCrop()
                    .into(imageView);
        } else {
            setImageInImageView(imageView, imageUrl);
        }
    }


    private void setImageInImageView(ImageView imageView, String url) {
        if (url != null) {
            //  System.out.println(">>>>>> " + ApiUrls.BASE_IMAGE_URL + student.getImage());
            Glide.with(this)
                    .load(url)
                   // .apply(RequestOptions.skipMemoryCacheOf(true))
                   // .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .error(R.drawable.default_male)
                    .dontAnimate()
                    .centerCrop()
                    .into(imageView);
        }
    }

    private void getAllEditedData(){

        StudentDummy studentDummy = new StudentDummy(
                religionTextFieldId.getEditText().getText().toString(),
                bloodGroupTextFieldId.getEditText().getText().toString(),
                birthDayTextFieldId.getEditText().getText().toString(),
                genderTextFieldId.getEditText().getText().toString(),
                presentAddressTextFieldId.getEditText().getText().toString(),
                permanentAddressTextFieldId.getEditText().getText().toString(),
                emailTextFieldId.getEditText().getText().toString(),
                studentPhoneTextFieldId.getEditText().getText().toString(),
                nationalityTextFieldId.getEditText().getText().toString(),
                previousSchoolTextFieldId.getEditText().getText().toString(),
                healthProblemTextFieldId.getEditText().getText().toString(),
                idMarkTextFieldId.getEditText().getText().toString()
        );
        GuardianDummy motherDummy = null;
if(mother != null){
    motherDummy = new GuardianDummy(
            student.getStudentId(),
            Integer.parseInt(String.valueOf(mother.getId())),
            mother.getRelation(),
            motherNameTextFieldId.getEditText().getText().toString(),
            m_occupationTextFieldId.getEditText().getText().toString(),
            m_phoneTextFieldId.getEditText().getText().toString(),
            m_addressTextFieldId.getEditText().getText().toString(),
            m_parentBloodGroupTextFieldId.getEditText().getText().toString(),
            m_designationTextFieldId.getEditText().getText().toString(),
            m_parentOrganizationFieldId.getEditText().getText().toString(),
            m_parentEmailTextFieldId.getEditText().getText().toString()
    );
}

        GuardianDummy fatherDummy = null;
if(father != null){
    fatherDummy = new GuardianDummy(
            student.getStudentId(),
            Integer.parseInt(String.valueOf(father.getId())),
            father.getRelation(),
            fatherNameTextFieldId.getEditText().getText().toString(),
            f_occupationTextFieldId.getEditText().getText().toString(),
            f_phoneTextFieldId.getEditText().getText().toString(),
            f_addressTextFieldId.getEditText().getText().toString(),
            f_parentBloodGroupTextFieldId.getEditText().getText().toString(),
            f_designationTextFieldId.getEditText().getText().toString(),
            f_parentOrganizationFieldId.getEditText().getText().toString(),
            f_parentEmailTextFieldId.getEditText().getText().toString()
    );
}


        new ProfileEditApi(this).updatedData(
                CustomSharedPref.getInstance(this).getAuthToken(),
                studentDummy,
                motherDummy,
                fatherDummy,
                studentImageUrl,
                motherImageUrl,
                fatherImageUrl,
                (isSuccess, message) -> {
                    //  System.out.println(">>>>>>>.. " + isSuccess + " " + message);
                });
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


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
      //  System.out.println("ev.toString() " + ev.toString());
        if (getCurrentFocus() != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }
        return super.dispatchTouchEvent(ev);
    }
}