package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.interfaces.FragmentToActivity;
import com.itbeebd.cesc_nsl.sugarClass.Guardian;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.sugarClass.Transport;

public class StudentProfileFragment extends Fragment implements View.OnClickListener {

    private ImageView profileEditBtn;
    private Student student;
    private Guardian mother;
    private Guardian father;
    private Transport transport;
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

    private LinearLayout guardianInfoLayout;
    private LinearLayout transportInfoLayoutId;
    private FragmentToActivity fragmentToActivity;

    public StudentProfileFragment(FragmentToActivity fragmentToActivity) {
        this.fragmentToActivity = fragmentToActivity;
    }

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
        StudentDao studentDao = new StudentDao();

        student = studentDao.getStudent(getContext());

        if (student != null) {
            transport = studentDao.getTransport(student);
            mother = studentDao.getGuardian(student, "Mother");
            father = studentDao.getGuardian(student, "Father");
        }
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

        transportInfoLayoutId = view.findViewById(R.id.transportInfoLayoutId);
        guardianInfoLayout = view.findViewById(R.id.guardianInfoSectionLayoutId);

        profileEditBtn.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View view) {
//        Intent intent = new Intent(getActivity(), EditStudentProfileActivity.class);
//        // intent.putExtra("studentId", student.getId());
//        getActivity().startActivityFor(intent);

        fragmentToActivity.changeActivity("EditStudentProfileActivity");
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        if (student != null) setStudentProfileData();
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
        if (transport != null) {
            transportInfoLayoutId.setVisibility(View.VISIBLE);
            busNameViewId.setText(transport.getBusName());
            busModelViewId.setText(transport.getBusModel());
            routeViewId.setText(transport.getBusRoute());
            busStartTimeViewId.setText(transport.getBusStartTime());
        }

        // Guardian details
        if (mother != null) setGuardianInfo(mother);
        if (father != null) setGuardianInfo(father);
    }

    private void setGuardianInfo(Guardian guardian) {

        View guardianView = LayoutInflater.from(getContext()).inflate(R.layout.single_guardian_view, guardianInfoLayout, false);

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