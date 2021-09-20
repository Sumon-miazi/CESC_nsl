package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.EditStudentProfileActivity;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Transport;

public class StudentProfileFragment extends Fragment implements View.OnClickListener {

    private ImageView profileEditBtn;
    private Student student;

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
    private TextView houseViewId;
    private TextView presentAddressViewId;
    private TextView permanentAddressViewId;
    private TextView phoneNumberViewId;
    private TextView emailViewId;
    private TextView nationalityViewId;
    private TextView previousSchoolViewId;
    private TextView healthProblemViewId;
    private TextView idMarkViewId;

    private TextView busNameViewId;
    private TextView busModelViewId;
    private TextView routeViewId;
    private TextView busStartTimeViewId;

    public StudentProfileFragment() {
        // Required empty public constructor
    }

    public static StudentProfileFragment newInstance(String param1, String param2) {
        StudentProfileFragment fragment = new StudentProfileFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        student = new StudentDao().getStudent(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_profile, container, false);

        profileEditBtn = view.findViewById(R.id.profileEditBtnId);
        studentNameViewId = view.findViewById(R.id.studentNameViewId);
        studentProfileViewId = view.findViewById(R.id.studentProfileViewId);
        classNameViewId = view.findViewById(R.id.classNameViewId);
        rollViewId = view.findViewById(R.id.rollViewId);
        studentIdViewId = view.findViewById(R.id.studentIdViewId);
        studentGroupViewId = view.findViewById(R.id.studentGroupViewId);
        categoryViewId = view.findViewById(R.id.categoryViewId);
        classViewId = view.findViewById(R.id.classViewId);
        sectionViewId = view.findViewById(R.id.sectionViewId);
        academicYearViewId = view.findViewById(R.id.academicYearViewId);
        genderViewId = view.findViewById(R.id.genderViewId);
        religionViewId = view.findViewById(R.id.religionViewId);
        bloodGroupViewId = view.findViewById(R.id.bloodGroupViewId);
        birthDayViewId = view.findViewById(R.id.birthDayViewId);
        houseViewId = view.findViewById(R.id.houseViewId);
        presentAddressViewId = view.findViewById(R.id.presentAddressViewId);
        permanentAddressViewId = view.findViewById(R.id.permanentAddressViewId);
        phoneNumberViewId = view.findViewById(R.id.phoneNumberViewId);
        emailViewId = view.findViewById(R.id.emailViewId);
        nationalityViewId = view.findViewById(R.id.nationalityViewId);
        previousSchoolViewId = view.findViewById(R.id.previousSchoolViewId);
        healthProblemViewId = view.findViewById(R.id.healthProblemViewId);
        idMarkViewId = view.findViewById(R.id.idMarklViewId);

        busNameViewId = view.findViewById(R.id.busNameViewId);
        busModelViewId = view.findViewById(R.id.busModelViewId);
        routeViewId = view.findViewById(R.id.routeViewId);
        busStartTimeViewId = view.findViewById(R.id.busStartTimeViewId);

        profileEditBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), EditStudentProfileActivity.class);
      //  intent.putExtra("student", student);
        getActivity().startActivity(intent);
    }

    @Override
    public void onStart() {
        super.onStart();

        if(student != null) setStudentProfileData();
      //  setStudentProfileData();
    }

    private void setStudentProfileData(){
        if(student.getImage() != null){
              System.out.println(">>>>>> " + ApiUrls.BASE_IMAGE_URL + student.getImage());
            Glide.with(this)
                    .load(ApiUrls.BASE_IMAGE_URL + student.getImage())
                    .into(studentProfileViewId);
        }
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
        bloodGroupViewId.setText(student.getBlood_group());
        birthDayViewId.setText(student.getDate_of_birth());
        houseViewId.setText(student.getHouse_id());
        presentAddressViewId.setText(student.getPresent_address());
        permanentAddressViewId.setText(student.getPermanent_address());
        phoneNumberViewId.setText(student.getMobile());
        emailViewId.setText(student.getEmail());
        nationalityViewId.setText(student.getNationality());
        previousSchoolViewId.setText(student.getPrevious_school());
        healthProblemViewId.setText(student.getHelth_problem());
        idMarkViewId.setText(student.getIdentification_mark());


        // Transport details
        Transport transport = new StudentDao().getTransport(student);
        if(transport != null){
            busNameViewId.setText(transport.getBusName());
            busModelViewId.setText(transport.getBusModel());
            routeViewId.setText(transport.getBusRoute());
            busStartTimeViewId.setText(transport.getBusStartTime());
        }


    }
}