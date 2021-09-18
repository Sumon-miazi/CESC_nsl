package com.itbeebd.cesc_nsl.activities.student;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.View;

import com.gauravk.bubblenavigation.BubbleNavigationConstraintView;
import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.itbeebd.cesc_nsl.MainActivity;
import com.itbeebd.cesc_nsl.R;

public class StudentDashboardActivity extends AppCompatActivity  implements BubbleNavigationChangeListener {

    private BubbleNavigationLinearView navigationLinearView;
    private FragmentManager fragmentManager;
    private StudentDashboardFragment dashboardFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        dashboardFragment = new StudentDashboardFragment();

        navigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
        navigationLinearView.setCurrentActiveItem(1);
        navigationLinearView.setNavigationChangeListener(this);

        // get fragment manager
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainerId, dashboardFragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onNavigationChanged(View view, int position) {

    }
}