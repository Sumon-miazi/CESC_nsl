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
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
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
            guardianInfo.put("imageView", guardianProfileViewId);
            guardianInfo.put("imageUrl", guardians.get(i).getProfileImage());
            guardianInfo.put("imageBtn", parentImageChangeBtnId);
            guardianInfo.put("name", parentNameTextFieldId);
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
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
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
                    .apply(RequestOptions.skipMemoryCacheOf(true))
                    .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE))
                    .dontAnimate()
                    .centerCrop()
                    .into(imageView);
        }
    }


    private void getAllEditedData(){

        for(int i = 0; i < guardianInfoList.size(); i++){
            System.out.println("&&&&& " + ((TextInputLayout)guardianInfoList.get(i).get("name")).getEditText().getText());
            System.out.println("&&&&& " + ((TextInputLayout)guardianInfoList.get(i).get("occupation")).getEditText().getText());
        }
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