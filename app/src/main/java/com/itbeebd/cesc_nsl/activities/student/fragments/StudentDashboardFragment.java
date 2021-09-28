package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Guideline;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.LessonPlanActivity;
import com.itbeebd.cesc_nsl.activities.student.LibraryBookActivity;
import com.itbeebd.cesc_nsl.activities.student.StudentAllNotificationActivity;
import com.itbeebd.cesc_nsl.activities.student.adapters.ClassRoutineAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.LessonPlanAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.StudentNotificationAdapter;
import com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.studentApi.AttendanceApi;
import com.itbeebd.cesc_nsl.api.studentApi.ClassRoutineApi;
import com.itbeebd.cesc_nsl.api.studentApi.DashboardApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.sugarClass.Book;
import com.itbeebd.cesc_nsl.sugarClass.Student;
import com.itbeebd.cesc_nsl.utils.Attendance;
import com.itbeebd.cesc_nsl.utils.ClassRoutine;
import com.itbeebd.cesc_nsl.utils.DashboardHeaderObj;
import com.itbeebd.cesc_nsl.utils.LessonPlan;
import com.itbeebd.cesc_nsl.utils.NotificationObj;
import com.parassidhu.simpledate.SimpleDateKt;

import java.util.ArrayList;
import java.util.Date;


public class StudentDashboardFragment extends Fragment implements OnRecyclerObjectClickListener<NotificationObj>, View.OnClickListener {

    private CardView quizBlock;
    private TextView quizBlockNumber;

    private CardView quizArchiveBlock;
    private TextView quizArchiveBlockNumber;

    private CardView libraryBlock;
    private TextView libraryBlockNumber;

    private CardView onlineClassBlock;
    private TextView onlineClassBlockNumber;

    private RecyclerView studentNotificationRecyclerView;
    private TextView notificationHint;
    private TextView notificationSeeAll;

    private ImageView filterClassRoutineBtnId;
    private RecyclerView classRoutineRecyclerView;

    private RecyclerView lessonPlanRecyclerView;
    private TextView lessonPlanCountHint;
    private TextView lessonPlanSeeAll;
    private LinearLayout lessonPlanNotFoundId;
    private ConstraintLayout lessonPlanMainViewId;

    private ArrayList<NotificationObj> notificationObjs;
    private ArrayList<LessonPlan> lessonPlans;
    private ArrayList<Book> bookArrayList;

    private TextView totalNotificationHindId;
    private ImageView studentProfileViewId;
    private TextView userNameViewId;
    private TextView todayDateViewId;
    private ImageView studentNotificationAlarmViewId;

    private TextView attendancePresentViewId;
    private TextView attendanceAbsentViewId;
    private ImageView filterAttendanceBtnId;
    private Guideline guideline;

    private LinearLayout routingNotFoundId;
    private TextView routineHindId;

    public StudentDashboardFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @SuppressLint("CutPasteId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_student_dashboard, container, false);

        studentProfileViewId = view.findViewById(R.id.studentProfileViewId);
        userNameViewId = view.findViewById(R.id.userNameViewId);
        todayDateViewId = view.findViewById(R.id.todayDateViewId);
        totalNotificationHindId = view.findViewById(R.id.totalNotificationHindId);
        studentNotificationAlarmViewId = view.findViewById(R.id.studentNotificationAlarmViewId);

        quizBlock = view.findViewById(R.id.quizCardId);
        quizBlockNumber = view.findViewById(R.id.quizCardHeaderSectionId);

        quizArchiveBlock = view.findViewById(R.id.quizArchiveCardId);
        quizArchiveBlockNumber = view.findViewById(R.id.quizArchiveHeaderSectionId);

        libraryBlock = view.findViewById(R.id.libraryCardId);
        libraryBlockNumber = view.findViewById(R.id.libraryHeaderSectionId);

        onlineClassBlock = view.findViewById(R.id.onlineClassCardId);
        onlineClassBlockNumber = view.findViewById(R.id.onlineClassHeaderSectionId);

        attendancePresentViewId = view.findViewById(R.id.attendanceBlockId).findViewById(R.id.attendancePresentViewId);
        attendanceAbsentViewId = view.findViewById(R.id.attendanceBlockId).findViewById(R.id.attendanceAbsentViewId);
        filterAttendanceBtnId = view.findViewById(R.id.attendanceBlockId).findViewById(R.id.filterAttendanceBtnId);
        guideline = view.findViewById(R.id.attendanceBlockId).findViewById(R.id.guideline6);


//        studentNotificationRecyclerView = view.findViewById(R.id.notificationBlockId).findViewById(R.id.studentNotificationRecyclerViewId);
//        notificationHint = view.findViewById(R.id.notificationBlockId).findViewById(R.id.notificationHintId);
//        notificationSeeAll = view.findViewById(R.id.notificationBlockId).findViewById(R.id.notificationSeeAllId);
//        notificationSeeAll.setVisibility(View.VISIBLE);


//        libraryHeader = view.findViewById(R.id.libraryBlockId).findViewById(R.id.sectionHeaderViewId);
//        filterSectionDataBtnId = view.findViewById(R.id.libraryBlockId).findViewById(R.id.filterSectionDataBtnId);
//        filterSectionDataBtnId.setVisibility(View.GONE);
//        libraryHeader.setText("Library Books");
//        libraryRecyclerView = view.findViewById(R.id.libraryBlockId).findViewById(R.id.sectionRecyclerViewId);

        routineHindId = view.findViewById(R.id.classRoutineBlockId).findViewById(R.id.sectionHeaderViewId);
        routingNotFoundId = view.findViewById(R.id.classRoutineBlockId).findViewById(R.id.routingNotFoundId);
        filterClassRoutineBtnId = view.findViewById(R.id.classRoutineBlockId).findViewById(R.id.filterSectionDataBtnId);
        classRoutineRecyclerView = view.findViewById(R.id.classRoutineBlockId).findViewById(R.id.sectionRecyclerViewId);

        lessonPlanMainViewId = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanMainViewId);
        lessonPlanNotFoundId = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanNotFoundId);
        lessonPlanRecyclerView = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanRecyclerViewId);
        lessonPlanCountHint = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonCountHintId);
        lessonPlanSeeAll = view.findViewById(R.id.dashboardLessonPlanBlockId).findViewById(R.id.lessonPlanSeeAllId);

        quizBlock.setOnClickListener(this::gotoQuizView);
        quizArchiveBlock.setOnClickListener(this::gotoQuizArchiveView);
        libraryBlock.setOnClickListener(this::gotoLibraryBookView);
        onlineClassBlock.setOnClickListener(this::gotoOnlineView);

        filterClassRoutineBtnId.setOnClickListener(this::filterClassRoutine);
        filterAttendanceBtnId.setOnClickListener(this::filterAttendance);

   //     notificationSeeAll.setOnClickListener(this);
        studentNotificationAlarmViewId.setOnClickListener(this);
        lessonPlanSeeAll.setOnClickListener(this);

        setDashboardComponentValues();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Student stdObj = new StudentDao().getStudent(getContext());
        String imageUrl = stdObj.getImage();

        Date date = new Date();
        todayDateViewId.setText(SimpleDateKt.toDateEMY(date));

        userNameViewId.setText(stdObj.getName().substring(stdObj.getName().lastIndexOf(" ")+1));

        if( imageUrl != null){
            Glide.with(this)
                    .load(ApiUrls.BASE_IMAGE_URL + imageUrl)
                    .placeholder(R.drawable.default_male)
                    .error(R.drawable.default_male)
                    .fallback(R.drawable.default_male)
                    .into(studentProfileViewId);
        }
    }

    private void setDashboardComponentValues() {
        new DashboardApi(getContext()).getDashboardHeaderInfo(
                CustomSharedPref.getInstance(getContext()).getAuthToken(),
                (object, message) -> {
                    if(object != null){
                        setDashboardData((DashboardHeaderObj) object);
                    }
                    else {
                        try { Toast.makeText(getContext(),message, Toast.LENGTH_SHORT).show(); }catch (Exception ignore){}
                    }
                }
        );


    }

    private void setDashboardData(DashboardHeaderObj object){
        quizBlockNumber.setText(object.getTotalQuiz());
        quizArchiveBlockNumber.setText(object.getTotalQuizArchive());
        libraryBlockNumber.setText(object.getLibraryBookTotal());
        onlineClassBlockNumber.setText(object.getTotalOnlineClass());
        System.out.println("<><><><><><><> " + object.getTotalOnlineClass());

        if(object.getTotalNotifications().equals("0")){
            totalNotificationHindId.setVisibility(View.GONE);
        }
        else {
            totalNotificationHindId.setVisibility(View.VISIBLE);
            String total = object.getTotalNotifications();
            if (total.length() == 1) {
                total = " " + total + " ";
            }
            totalNotificationHindId.setText(total);
        }


        if(object.getNotificationObjArrayList().size() != 0){
            notificationObjs = object.getNotificationObjArrayList();
         //   setNotificationAdapter();
        }

        if(object.getLessonPlanArrayList().size() != 0){
            lessonPlans = object.getLessonPlanArrayList();
            setLessonPlanAdapter();
        }

        if(object.getAttendance() != null){
            setAttendanceGraph(object.getAttendance());
        }

        if(object.getClassRoutineArrayList() != null){
            setClassRoutineAdapter(object.getClassRoutineArrayList());
        }

        if(object.getBookArrayList() != null){
            this.bookArrayList = object.getBookArrayList();
        }
    }

    @SuppressLint("DefaultLocale")
    private void setAttendanceGraph(Attendance attendance) {
        int present = attendance.getPresent();
        int absent = attendance.getAbsent();
        int sum = present + absent;

        attendancePresentViewId.setText(String.format("Present: %d Day(s)", present));
        attendanceAbsentViewId.setText(String.format("Absent: %d Day(s)", absent));

        System.out.println("++++++ " + present);
        System.out.println("++++++ " + absent);
        System.out.println("++++++ " + sum);

        if(sum == 0 || present == absent) {
            guideline.setGuidelinePercent(0.5f);
            return;
        }

        if(present == 0) {
            guideline.setGuidelinePercent(0.0f);
            return;
        }
        else if(absent == 0) {
            guideline.setGuidelinePercent(1.0f);
            return;
        }

        float onePercentage = (float) sum / 10;
        float presentPercentage = (float)present * onePercentage;
        float absentPercentage = (float)absent * onePercentage;

        if(presentPercentage > absentPercentage) guideline.setGuidelinePercent(presentPercentage);
        else guideline.setGuidelinePercent(1 - absentPercentage);


    }

    private void setNotificationAdapter(){
        String notificationSize = "See All(" + notificationObjs.size() + ")";
        notificationHint.setVisibility(View.VISIBLE);
        notificationSeeAll.setText(notificationSize);

        StudentNotificationAdapter notificationAdapter = new StudentNotificationAdapter(getContext());
        notificationAdapter.setItems(notificationObjs.subList(0,2));
        studentNotificationRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        //    studentNotificationRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        studentNotificationRecyclerView.setAdapter(notificationAdapter);
    }

    private void setLessonPlanAdapter() {
        String lessonPlansSize = "See All(" + lessonPlans.size() + ")";



        lessonPlanSeeAll.setText(lessonPlansSize);

        LessonPlanAdapter lessonPlanAdapter = new LessonPlanAdapter(getContext());
        lessonPlanAdapter.setItems(lessonPlans.subList(0,2));
        lessonPlanRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        lessonPlanRecyclerView.setAdapter(lessonPlanAdapter);

        if (lessonPlans.size() != 0) {
            lessonPlanMainViewId.setVisibility(View.VISIBLE);
            lessonPlanNotFoundId.setVisibility(View.GONE);

            lessonPlanCountHint.setVisibility(View.VISIBLE);
            lessonPlanSeeAll.setVisibility(View.VISIBLE);
        }
        else {
            lessonPlanMainViewId.setVisibility(View.GONE);
            lessonPlanNotFoundId.setVisibility(View.VISIBLE);
        }
    }

    private void setClassRoutineAdapter(ArrayList<ClassRoutine> classRoutineArrayList) {
        ClassRoutineAdapter classRoutineAdapter = new ClassRoutineAdapter(getContext());
        classRoutineAdapter.setItems(classRoutineArrayList);
        classRoutineRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        classRoutineRecyclerView.setAdapter(classRoutineAdapter);

        if (classRoutineArrayList.size() != 0) {
            routineHindId.setVisibility(View.VISIBLE);
            routingNotFoundId.setVisibility(View.GONE);
        }
        else {
            routineHindId.setVisibility(View.GONE);
            routingNotFoundId.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onItemClicked(NotificationObj item, View view) {

    }

    @Override
    public void onClick(View view) {
        Intent intent = null;
        if(view.getId() == R.id.notificationSeeAllId || view.getId() == R.id.studentNotificationAlarmViewId){
            if(notificationObjs == null || notificationObjs.size() == 0){
                Toast.makeText(getContext(), "No notification found", Toast.LENGTH_SHORT).show();
                return;
            }
            intent = new Intent(getActivity(), StudentAllNotificationActivity.class);
            intent.putExtra("notifications", notificationObjs);
            totalNotificationHindId.setVisibility(View.GONE);
        }
        if(view.getId() == R.id.lessonPlanSeeAllId){
            intent = new Intent(getActivity(), LessonPlanActivity.class);
            intent.putExtra("lessonPlan",  lessonPlans);
        }
        getActivity().startActivity(intent);
    }


    private void gotoQuizView(View view){
        System.out.println(">>>>>. " + view.getId());
    }

    private void gotoOnlineView(View view) {
        System.out.println(">>>>>. " + view.getId());
    }

    private void gotoLibraryBookView(View view) {
        if(bookArrayList == null || bookArrayList.size() == 0) return;
        System.out.println(">>>>>. " + view.getId());
        Intent intent = new Intent(getActivity(), LibraryBookActivity.class);
        intent.putExtra("books",  bookArrayList);
        getActivity().startActivity(intent);
    }

    private void gotoQuizArchiveView(View view) {
        System.out.println(">>>>>. " + view.getId());
    }


    private void filterClassRoutine(View v) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.weekly_days_view);

        TextView saturdayId = bottomSheetDialog.findViewById(R.id.saturdayId);
        TextView sundayId = bottomSheetDialog.findViewById(R.id.sundayId);
        TextView mondayId = bottomSheetDialog.findViewById(R.id.mondayId);
        TextView tuesdayId = bottomSheetDialog.findViewById(R.id.tuesdayId);
        TextView wednesdayId = bottomSheetDialog.findViewById(R.id.wednesdayId);
        TextView thursdayId = bottomSheetDialog.findViewById(R.id.thursdayId);

        assert saturdayId != null;
        saturdayId.setOnClickListener(view -> { getClassRoutineByDayName("Sat");bottomSheetDialog.dismiss(); });

        assert sundayId != null;
        sundayId.setOnClickListener(view -> { getClassRoutineByDayName("Sun");bottomSheetDialog.dismiss(); });

        assert mondayId != null;
        mondayId.setOnClickListener(view -> { getClassRoutineByDayName("Mon");bottomSheetDialog.dismiss(); });

        assert tuesdayId != null;
        tuesdayId.setOnClickListener(view -> { getClassRoutineByDayName("Tue"); bottomSheetDialog.dismiss(); });

        assert wednesdayId != null;
        wednesdayId.setOnClickListener(view -> { getClassRoutineByDayName("Wed"); bottomSheetDialog.dismiss(); });

        assert thursdayId != null;
        thursdayId.setOnClickListener(view -> { getClassRoutineByDayName("Thu"); bottomSheetDialog.dismiss(); });

        bottomSheetDialog.show();
    }

    private void getClassRoutineByDayName(String name){
        new ClassRoutineApi(getContext()).getClassRoutine(
                name,
                CustomSharedPref.getInstance(getContext()).getAuthToken(),
                (object, message) -> {
                    try {
                        if(object != null){
                            ArrayList<ClassRoutine> classRoutines = (ArrayList<ClassRoutine>) object;
                            setClassRoutineAdapter(classRoutines);
                        }
                        else{
                            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                        }
                    }
                    catch (Exception ignore){ }
                }
        );
    }

    private void filterAttendance(View v) {
        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(requireContext());
        bottomSheetDialog.setContentView(R.layout.month_name_view);

        TextView januaryId = bottomSheetDialog.findViewById(R.id.januaryId);
        TextView februaryId = bottomSheetDialog.findViewById(R.id.februaryId);
        TextView marchId = bottomSheetDialog.findViewById(R.id.marchId);
        TextView aprilId = bottomSheetDialog.findViewById(R.id.aprilId);
        TextView mayId = bottomSheetDialog.findViewById(R.id.mayId);
        TextView juneId = bottomSheetDialog.findViewById(R.id.juneId);
        TextView julyId = bottomSheetDialog.findViewById(R.id.julyId);
        TextView augustId = bottomSheetDialog.findViewById(R.id.augustId);
        TextView septemberId = bottomSheetDialog.findViewById(R.id.septemberId);
        TextView octoberId = bottomSheetDialog.findViewById(R.id.octoberId);
        TextView novemberId = bottomSheetDialog.findViewById(R.id.novemberId);
        TextView decemberId = bottomSheetDialog.findViewById(R.id.decemberId);

        assert januaryId != null;
        januaryId.setOnClickListener(view -> { getAttendanceByMonthName("01");bottomSheetDialog.dismiss(); });

        assert februaryId != null;
        februaryId.setOnClickListener(view -> { getAttendanceByMonthName("02");bottomSheetDialog.dismiss(); });

        assert marchId != null;
        marchId.setOnClickListener(view -> { getAttendanceByMonthName("03");bottomSheetDialog.dismiss(); });

        assert aprilId != null;
        aprilId.setOnClickListener(view -> { getAttendanceByMonthName("04"); bottomSheetDialog.dismiss(); });

        assert mayId != null;
        mayId.setOnClickListener(view -> { getAttendanceByMonthName("05"); bottomSheetDialog.dismiss(); });

        assert juneId != null;
        juneId.setOnClickListener(view -> { getAttendanceByMonthName("06"); bottomSheetDialog.dismiss(); });

        assert julyId != null;
        julyId.setOnClickListener(view -> { getAttendanceByMonthName("07"); bottomSheetDialog.dismiss(); });

        assert augustId != null;
        augustId.setOnClickListener(view -> { getAttendanceByMonthName("08"); bottomSheetDialog.dismiss(); });

        assert septemberId != null;
        septemberId.setOnClickListener(view -> { getAttendanceByMonthName("09"); bottomSheetDialog.dismiss(); });

        assert octoberId != null;
        octoberId.setOnClickListener(view -> { getAttendanceByMonthName("10"); bottomSheetDialog.dismiss(); });

        assert novemberId != null;
        novemberId.setOnClickListener(view -> { getAttendanceByMonthName("11"); bottomSheetDialog.dismiss(); });

        assert decemberId != null;
        decemberId.setOnClickListener(view -> { getAttendanceByMonthName("12"); bottomSheetDialog.dismiss(); });

        bottomSheetDialog.show();
    }

    private void getAttendanceByMonthName(String name){
        new AttendanceApi(getContext()).getAttendanceByMonthName(
                name,
                CustomSharedPref.getInstance(getContext()).getAuthToken(),
                (object, message) -> {
                    try {
                        if(object != null){
                            setAttendanceGraph((Attendance) object);
                        }
                        else Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception ignore){}
                }
        );
    }

}