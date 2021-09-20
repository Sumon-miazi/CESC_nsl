package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;

import com.google.android.material.textfield.TextInputLayout;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.sugarClass.Student;

public class EditStudentProfileActivity extends AppCompatActivity {

    private Student student;
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

    private TextInputLayout parentNameTextFieldId;
    private TextInputLayout occupationTextFieldId;
    private TextInputLayout phoneTextFieldId;
    private TextInputLayout addressTextFieldId;
    private TextInputLayout parentBloodGroupTextFieldId;
    private TextInputLayout designationTextFieldId;
    private TextInputLayout parentEmailTextFieldId;

    private Button submitChangesBtnId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_student_profile);

        Toolbar mToolBar = findViewById(R.id.editProfileToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("EDIT PROFILE");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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

        parentNameTextFieldId = findViewById(R.id.parentNameTextFieldId);
        occupationTextFieldId = findViewById(R.id.occupationTextFieldId);
        phoneTextFieldId = findViewById(R.id.phoneTextFieldId);
        addressTextFieldId = findViewById(R.id.addressTextFieldId);
        parentBloodGroupTextFieldId = findViewById(R.id.parentBloodGroupTextFieldId);
        designationTextFieldId = findViewById(R.id.designationTextFieldId);
        parentEmailTextFieldId = findViewById(R.id.parentEmailTextFieldId);

        submitChangesBtnId = findViewById(R.id.submitChangesBtnId);

        if(getIntent().hasExtra("student")){
            student = (Student) getIntent().getSerializableExtra("student");
            setUserProfileInfo();
        }
    }

    private void setUserProfileInfo() {
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