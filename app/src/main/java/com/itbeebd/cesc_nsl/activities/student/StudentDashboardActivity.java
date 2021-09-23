package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.fragments.PaymentFragment;
import com.itbeebd.cesc_nsl.activities.student.fragments.ResultBoardFragment;
import com.itbeebd.cesc_nsl.activities.student.fragments.StudentDashboardFragment;
import com.itbeebd.cesc_nsl.activities.student.fragments.StudentProfileFragment;


public class StudentDashboardActivity extends AppCompatActivity  implements BubbleNavigationChangeListener {

    private BubbleNavigationLinearView navigationLinearView;
    private FragmentManager fragmentManager;
    private StudentDashboardFragment dashboardFragment;
    private StudentProfileFragment profileFragment;
    private PaymentFragment paymentFragment;
    private ResultBoardFragment resultBoardFragment;
    private long time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        dashboardFragment = new StudentDashboardFragment();
        profileFragment = new StudentProfileFragment();
        paymentFragment = new PaymentFragment();
        resultBoardFragment = new ResultBoardFragment();

        navigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
        navigationLinearView.setCurrentActiveItem(0);
        navigationLinearView.setNavigationChangeListener(this);

        // get fragment manager
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.getFragments().size() == 0) {
            fragmentTransaction.add(R.id.fragmentContainerId, dashboardFragment);
        } else {
            fragmentTransaction.replace(R.id.fragmentContainerId, dashboardFragment);
        }
        fragmentTransaction.commit();

    }

    @Override
    public void onNavigationChanged(View view, int position) {
        System.out.println(">>>>>.. position " + position);
        if(position == 0){
            changeFragmentInDashBoard(dashboardFragment);
        }
        else if (position == 1){
            changeFragmentInDashBoard(resultBoardFragment);
        }
        else if (position == 3){
            changeFragmentInDashBoard(paymentFragment);
        }
        else if (position == 4){
            changeFragmentInDashBoard(profileFragment);
        }
        else changeFragmentInDashBoard(dashboardFragment);
    }

    @Override
    public void onBackPressed() {
        if (time + 2000 > System.currentTimeMillis()) {
            finish();
        } else {
            time = System.currentTimeMillis();
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    private void changeFragmentInDashBoard(Fragment fragment ){
        // get fragment manager
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragmentContainerId, fragment);
        fragmentTransaction.commit();
    }
}


        /*
        Flashbar flashbar = new FlashBar().flashBar(this,
                "this is title",
                "This is message",
                5000
        );
        flashbar.show();

         */
