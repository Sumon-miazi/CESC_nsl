package com.itbeebd.cesc_nsl.activities.teacher;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.interfaces.FragmentToActivity;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;

public class GuidedStudentProfileActivity extends AppCompatActivity {

    private Student student;
    private Guardian mother;
    private Guardian father;

    private TextView studentNameViewId;
    private ImageView studentProfileViewId;
    private TextView classNameViewId;
    private TextView rollViewId;
    private TextView studentIdViewId;
    private TextView studentGroupViewId;
    private TextView categoryViewId;
    private TextView classViewId;
    private TextView sectionViewId;
    private TextView academicYearViewId;
    private TextView genderViewId;
    private TextView religionViewId;
    private TextView bloodGroupViewId;
    private TextView birthDayViewId;
    private TextView presentAddressViewId;
    private TextView permanentAddressViewId;
    private TextView phoneNumberViewId;
    private TextView emailViewId;
    private TextView nationalityViewId;


    private LinearLayout guardianInfoLayout;
    private LinearLayout transportInfoLayoutId;
    private FragmentToActivity fragmentToActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guided_student_profile);

        studentNameViewId = findViewById(R.id.studentNameViewId);
        studentProfileViewId = findViewById(R.id.studentProfileViewId);
        classNameViewId = findViewById(R.id.classNameViewId);
        rollViewId = findViewById(R.id.rollViewId);
        studentIdViewId = findViewById(R.id.studentIdViewId);
        studentGroupViewId = findViewById(R.id.studentGroupViewId);
        categoryViewId = findViewById(R.id.categoryViewId);
        classViewId = findViewById(R.id.classViewId);
        sectionViewId = findViewById(R.id.sectionViewId);
        academicYearViewId = findViewById(R.id.academicYearViewId);
        genderViewId = findViewById(R.id.genderViewId);
        religionViewId = findViewById(R.id.religionViewId);
        bloodGroupViewId = findViewById(R.id.bloodGroupViewId);
        birthDayViewId = findViewById(R.id.birthDayViewId);
        presentAddressViewId = findViewById(R.id.presentAddressViewId);
        permanentAddressViewId = findViewById(R.id.permanentAddressViewId);
        phoneNumberViewId = findViewById(R.id.phoneNumberViewId);
        emailViewId = findViewById(R.id.emailViewId);
        nationalityViewId = findViewById(R.id.nationalityViewId);


        transportInfoLayoutId = findViewById(R.id.transportInfoLayoutId);
        guardianInfoLayout = findViewById(R.id.guardianInfoSectionLayoutId);

        if(getIntent().hasExtra("student")){
            this.student = (Student) getIntent().getSerializableExtra("student");
            this.mother = this.student.getMother();
            this.father = this.student.getFather();

            System.out.println("mother name >>> " + mother.getName());
            System.out.println("father name >>> " + father.getName());
            setStudentProfileData();
        }

    }


    private void setStudentProfileData() {
        setImageInImageView(studentProfileViewId, student.getImage());
        //    System.out.println("++++++ " + String.format("Class: %s", student.getClassName()));
        studentNameViewId.setText(student.getName());
        classNameViewId.setText(String.format("Class: %s", student.getClassName()));
        rollViewId.setText(String.format("Roll no: %s", student.getRoll()));
        studentIdViewId.setText(String.format("ID: %s", student.getStudentId()));

        studentGroupViewId.setText(student.getStudentGroup());
        categoryViewId.setText(student.getCategory());
        classViewId.setText(student.getClassName());
        sectionViewId.setText(student.getSectionName());
        academicYearViewId.setText(String.format("%s", student.getAcademic_year()));
        genderViewId.setText(student.getGender());
        religionViewId.setText(student.getReligion());
        bloodGroupViewId.setText(student.getBlood());
        birthDayViewId.setText(student.getDate_of_birth());
        presentAddressViewId.setText(student.getPresent_address());
        permanentAddressViewId.setText(student.getPermanent_address());
        phoneNumberViewId.setText(student.getMobile());
        emailViewId.setText(student.getEmail());
        nationalityViewId.setText(student.getNationality());

        // Guardian details
        if (mother != null) setGuardianInfo(mother);
        if (father != null) setGuardianInfo(father);
    }

    private void setGuardianInfo(Guardian guardian) {

        View guardianView = LayoutInflater.from(this).inflate(R.layout.single_guardian_view, guardianInfoLayout, false);

        ImageView guardianProfileViewId = guardianView.findViewById(R.id.guardianProfileViewId);
        TextView parentNameViewId = guardianView.findViewById(R.id.parentNameViewId);
        TextView studentGuardianRelationViewId = guardianView.findViewById(R.id.studentGuardianRelationViewId);
        TextView parentOccupationViewId = guardianView.findViewById(R.id.parentOccupationViewId);
        TextView parentPhoneNoViewId = guardianView.findViewById(R.id.parentPhoneNoViewId);
        TextView parentAddressViewId = guardianView.findViewById(R.id.parentAddressViewId);
        TextView parentBloodGroupViewId = guardianView.findViewById(R.id.parentBloodGroupViewId);
        TextView parentDesignationViewId = guardianView.findViewById(R.id.parentDesignationViewId);
        TextView parentEmailViewId = guardianView.findViewById(R.id.parentEmailViewId);

        setImageInImageView(guardianProfileViewId, guardian.getProfileImage());
        parentNameViewId.setText(guardian.getName());
        studentGuardianRelationViewId.setText(guardian.getRelation());
        parentOccupationViewId.setText(guardian.getOccupation());
        parentPhoneNoViewId.setText(guardian.getMobile());
        parentAddressViewId.setText(guardian.getLocation());
        parentBloodGroupViewId.setText(guardian.getBlood_group());
        parentDesignationViewId.setText(guardian.getDesignation());
        parentEmailViewId.setText(guardian.getEmail());

        guardianInfoLayout.addView(guardianView);
        guardianInfoLayout.setVisibility(View.VISIBLE);

    }

    private void setImageInImageView(ImageView imageView, String url) {
        if (url != null) {
            System.out.println(">>>>>> " + ApiUrls.BASE_IMAGE_URL + student.getImage());
            Glide.with(this)
                    .load(ApiUrls.BASE_IMAGE_URL + url)
                    .placeholder(R.drawable.default_male)
                    .error(R.drawable.default_male)
                    .fallback(R.drawable.default_male)
                    .into(imageView);
        }
    }
}