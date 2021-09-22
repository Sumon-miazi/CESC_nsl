package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.ProfileEditApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.interfaces.BooleanResponse;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.utils.dummy.GuardianDummy;
import com.itbeebd.cesc_nsl.utils.dummy.StudentDummy;
import com.nguyenhoanglam.imagepicker.model.Config;
import com.nguyenhoanglam.imagepicker.model.Image;
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EditStudentProfileActivity extends AppCompatActivity implements View.OnClickListener {

    private Student student;
    private List<Guardian> guardians;
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

    private LinearLayout guardianInfoEditLayout;
    private Button submitChangesBtnId;

    private View changedButton;
    private String imageUrl;

    private boolean isUserImage = false;
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

        guardianInfoEditLayout = findViewById(R.id.guardianInfoEditLayout);

        userImageChangeBtnId.setOnClickListener(this);

        submitChangesBtnId = findViewById(R.id.submitChangesBtnId);
        submitChangesBtnId.setOnClickListener(view -> {
            getAllEditedData();
        });

        student = new StudentDao().getStudent(this);

        if(student != null){
            guardians = new StudentDao().getGuardian(student);
            setUserProfileInfo();
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

        if (guardians != null) setStudentGuardianEditInfo();
        else System.out.println(">>>>>>>> guardian is null ");
    }

    private void setStudentGuardianEditInfo() {

        // Guardian details
        System.out.println("$$$$$$ " + guardians.size());
        for (int i = 0; i < guardians.size(); i++) {
            View guardianView = LayoutInflater.from(this).inflate(R.layout.single_guardian_info_edit_view,
                    guardianInfoEditLayout, false);

            ImageView guardianProfileViewId = guardianView.findViewById(R.id.guardianProfileViewId);
            TextView studentGuardianRelationViewId = guardianView.findViewById(R.id.studentGuardianRelationViewId);

            Button parentImageChangeBtnId = guardianView.findViewById(R.id.parentImageChangeBtnId);
            parentImageChangeBtnId.setId(i+1);
            parentImageChangeBtnId.setOnClickListener(this);

            TextInputLayout parentNameTextFieldId = guardianView.findViewById(R.id.parentNameTextFieldId);
            TextInputLayout occupationTextFieldId = guardianView.findViewById(R.id.occupationTextFieldId);
            TextInputLayout phoneTextFieldId = guardianView.findViewById(R.id.phoneTextFieldId);
            TextInputLayout addressTextFieldId = guardianView.findViewById(R.id.addressTextFieldId);
            TextInputLayout parentBloodGroupTextFieldId = guardianView.findViewById(R.id.parentBloodGroupTextFieldId);
            TextInputLayout designationTextFieldId = guardianView.findViewById(R.id.designationTextFieldId);
            TextInputLayout parentOrganizationFieldId = guardianView.findViewById(R.id.parentOrganizationFieldId);
            TextInputLayout parentEmailTextFieldId = guardianView.findViewById(R.id.parentEmailTextFieldId);

            setImageInImageView(guardianProfileViewId, ApiUrls.BASE_IMAGE_URL + guardians.get(i).getProfileImage());
            studentGuardianRelationViewId.setText(guardians.get(i).getRelation());
            parentNameTextFieldId.getEditText().setText(guardians.get(i).getName());
            occupationTextFieldId.getEditText().setText(guardians.get(i).getOccupation());
            parentOrganizationFieldId.getEditText().setText(guardians.get(i).getOrganization());
            phoneTextFieldId.getEditText().setText(guardians.get(i).getMobile());
            addressTextFieldId.getEditText().setText(guardians.get(i).getLocation());
            parentBloodGroupTextFieldId.getEditText().setText(guardians.get(i).getBlood_group());
            designationTextFieldId.getEditText().setText(guardians.get(i).getDesignation());
            parentEmailTextFieldId.getEditText().setText(guardians.get(i).getEmail());

            guardianInfoEditLayout.addView(guardianView);

            Map<String, Object> guardianInfo = new HashMap<>();
            guardianInfo.put("id", guardians.get(i).getId());
            guardianInfo.put("student_id", student.getId());
            guardianInfo.put("imageView", guardianProfileViewId);
            guardianInfo.put("imageUrl", guardians.get(i).getProfileImage());
            guardianInfo.put("imageBtn", parentImageChangeBtnId);
            guardianInfo.put("name", parentNameTextFieldId);
            guardianInfo.put("relation", guardians.get(i).getRelation());
            guardianInfo.put("occupation", occupationTextFieldId);
            guardianInfo.put("phone", phoneTextFieldId);
            guardianInfo.put("address", addressTextFieldId);
            guardianInfo.put("bloodGroup", parentBloodGroupTextFieldId);
            guardianInfo.put("designation", designationTextFieldId);
            guardianInfo.put("organization", parentOrganizationFieldId);
            guardianInfo.put("email", parentEmailTextFieldId);

            guardianInfoList.add(guardianInfo);
        }
    }


    @Override
    public void onClick(View view) {

        isUserImage = view.getId() == R.id.userImageChangeBtnId;

        changedButton = view;

        System.out.println(">>>>>>>> imageBtnId " + view.getId());

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

            if(isUserImage){
                student.setImage(imageUrl);
                setProfileImage(userProfileViewId, image);
            }
            else {
                for(int i = 0; i < guardianInfoList.size(); i++){
                    if(((Button)guardianInfoList.get(i).get("imageBtn")).getId() == changedButton.getId()){

                        ImageView imageView = (ImageView)guardianInfoList.get(i).get("imageView");
                        guardianInfoList.get(i).put("imageUrl", imageUrl);

                        setProfileImage(imageView, image);
                    }

                }
            }
    }

    private void setProfileImage(ImageView imageView, Image image) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Uri uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, String.valueOf(image.getId()));
            Glide.with(this)
                    .load(uri)
                  //  .apply(RequestOptions.skipMemoryCacheOf(true))
                   // .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
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
                    .dontAnimate()
                    .centerCrop()
                    .into(imageView);
        }
    }

/*
    private void getAllEditedData(){
        Map<String, Map<String, String>> data = new HashMap<>();

        Map<String, String> studentDetails = new HashMap<>();
        studentDetails.put("image", student.getImage());
        studentDetails.put("religion", religionTextFieldId.getEditText().getText().toString());
        studentDetails.put("blood", bloodGroupTextFieldId.getEditText().getText().toString());
        studentDetails.put("date_of_birth", birthDayTextFieldId.getEditText().getText().toString());
        studentDetails.put("gender", genderTextFieldId.getEditText().getText().toString());
        studentDetails.put("present_address", presentAddressTextFieldId.getEditText().getText().toString());
        studentDetails.put("permanent_address", permanentAddressTextFieldId.getEditText().getText().toString());
        studentDetails.put("email", emailTextFieldId.getEditText().getText().toString());
        studentDetails.put("mobile", studentPhoneTextFieldId.getEditText().getText().toString());
        studentDetails.put("nationality", nationalityTextFieldId.getEditText().getText().toString());
        studentDetails.put("previous_school", previousSchoolTextFieldId.getEditText().getText().toString());
        studentDetails.put("helth_problem", healthProblemTextFieldId.getEditText().getText().toString());
        studentDetails.put("identification_mark", idMarkTextFieldId.getEditText().getText().toString());

        data.put("student", studentDetails);
        System.out.println("????????? " + studentDetails.get("identification_mark").toString());
     //   Map<String, Map<String, Object>> guardianDetailsList = new HashMap<>();

        for(int i = 0; i < guardianInfoList.size(); i++){

            Map<String, String> guardianDetail = new HashMap<>();

            guardianDetail.put("relation", guardianInfoList.get(i).get("relation").toString());
            guardianDetail.put("name",((TextInputLayout)guardianInfoList.get(i).get("name")).getEditText().getText().toString());
            guardianDetail.put("occupation",((TextInputLayout)guardianInfoList.get(i).get("occupation")).getEditText().getText().toString());
            guardianDetail.put("phone",((TextInputLayout)guardianInfoList.get(i).get("phone")).getEditText().getText().toString());
            guardianDetail.put("address",((TextInputLayout)guardianInfoList.get(i).get("address")).getEditText().getText().toString());
            guardianDetail.put("blood_group",((TextInputLayout)guardianInfoList.get(i).get("bloodGroup")).getEditText().getText().toString());
            guardianDetail.put("designation",((TextInputLayout)guardianInfoList.get(i).get("designation")).getEditText().getText().toString());
            guardianDetail.put("organization",((TextInputLayout)guardianInfoList.get(i).get("organization")).getEditText().getText().toString());
            guardianDetail.put("email",((TextInputLayout)guardianInfoList.get(i).get("email")).getEditText().getText().toString());
            guardianDetail.put("imageUrl", guardianInfoList.get(i).get("imageUrl").toString());

          //  guardianDetailsList.put(guardianInfoList.get(i).get("relation").toString(), guardianDetail);
            data.put(guardianInfoList.get(i).get("relation").toString(), guardianDetail);
//            System.out.println("&&&&& " + ((TextInputLayout)guardianInfoList.get(i).get("name")).getEditText().getText());
//            System.out.println("&&&&& " + ((TextInputLayout)guardianInfoList.get(i).get("occupation")).getEditText().getText());
        }

        callEditProfileApi(data);
    }

 */

    private void getAllEditedData(){

        Map<String, Object> data = new HashMap<>();

        StudentDummy studentDummy = new StudentDummy(
                student.getImage(),
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

        data.put("student", studentDummy);


        for(int i = 0; i < guardianInfoList.size(); i++){

            GuardianDummy guardianDummy = new GuardianDummy(
                    Integer.parseInt(guardianInfoList.get(i).get("student_id").toString()),
                    Integer.parseInt(guardianInfoList.get(i).get("id").toString()),
                    guardianInfoList.get(i).get("relation").toString(),
                    ((TextInputLayout)guardianInfoList.get(i).get("name")).getEditText().getText().toString(),
                    ((TextInputLayout)guardianInfoList.get(i).get("occupation")).getEditText().getText().toString(),
                    ((TextInputLayout)guardianInfoList.get(i).get("phone")).getEditText().getText().toString(),
                    ((TextInputLayout)guardianInfoList.get(i).get("address")).getEditText().getText().toString(),
                    ((TextInputLayout)guardianInfoList.get(i).get("bloodGroup")).getEditText().getText().toString(),
                    ((TextInputLayout)guardianInfoList.get(i).get("designation")).getEditText().getText().toString(),
                    ((TextInputLayout)guardianInfoList.get(i).get("organization")).getEditText().getText().toString(),
                    ((TextInputLayout)guardianInfoList.get(i).get("email")).getEditText().getText().toString(),
                    guardianInfoList.get(i).get("imageUrl").toString()
            );

            data.put(guardianInfoList.get(i).get("relation").toString(), guardianDummy);
        }

        callEditProfileApi(data);
    }

    //   private void callEditProfileApi(Map<String, Object> studentDetails, Map<String, Map<String, Object>> guardianDetailsList) {
    private void callEditProfileApi(Map<String, Object> data) {
        new ProfileEditApi(this).updatedData(data,
                CustomSharedPref.getInstance(this).getAuthToken(),
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
}