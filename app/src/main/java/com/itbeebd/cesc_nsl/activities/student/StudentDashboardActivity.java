package com.itbeebd.cesc_nsl.activities.student;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.gauravk.bubblenavigation.BubbleNavigationLinearView;
import com.gauravk.bubblenavigation.listener.BubbleNavigationChangeListener;
import com.itbeebd.cesc_nsl.MainActivity;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.fragments.PaymentFragment;
import com.itbeebd.cesc_nsl.activities.student.fragments.ResultBoardFragment;
import com.itbeebd.cesc_nsl.activities.student.fragments.StudentDashboardFragment;
import com.itbeebd.cesc_nsl.activities.student.fragments.StudentProfileFragment;


public class StudentDashboardActivity extends AppCompatActivity  implements BubbleNavigationChangeListener, ActivityCompat.OnRequestPermissionsResultCallback {

    private BubbleNavigationLinearView navigationLinearView;
    private FragmentManager fragmentManager;
    private StudentDashboardFragment dashboardFragment;
    private StudentProfileFragment profileFragment;
    private PaymentFragment paymentFragment;
    private ResultBoardFragment resultBoardFragment;
    private long time;
    private static final int REQUEST_WRITE_PERMISSION = 786;
    private String currentFragment = "dashboardFragment";
    private DrawerLayout studentDrawerId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_dashboard);

        navigationLinearView = findViewById(R.id.bottom_navigation_view_linear);
        navigationLinearView.setCurrentActiveItem(0);
        navigationLinearView.setNavigationChangeListener(this);


        dashboardFragment = new StudentDashboardFragment((drawerLayout, data) -> {
            this.studentDrawerId = drawerLayout;

            if(data.equals("Successfully logged out.")){
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }
            else {
                navigationLinearView.setCurrentActiveItem(Integer.parseInt(data));
                onNavigationChanged(null, Integer.parseInt(data));
            }
        });

        profileFragment = new StudentProfileFragment();
        paymentFragment = new PaymentFragment();
        resultBoardFragment = new ResultBoardFragment();

        // get fragment manager
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if (fragmentManager.getFragments().size() == 0) {
            fragmentTransaction.add(R.id.fragmentContainerId, dashboardFragment, "Dashboard");
        } else {
            fragmentTransaction.replace(R.id.fragmentContainerId, dashboardFragment, "Dashboard");
        }
        fragmentTransaction.commit();

        requestPermission();

    }

    @Override
    public void onNavigationChanged(View view, int position) {
        System.out.println(">>>>>.. position " + position);
        if(position == 0){
            currentFragment = "dashboardFragment";
            changeFragmentInDashBoard(dashboardFragment);
        }
        else if (position == 1){
            currentFragment = "resultBoardFragment";
            changeFragmentInDashBoard(resultBoardFragment);
        }
        else if (position == 3){
            currentFragment = "paymentFragment";
            changeFragmentInDashBoard(paymentFragment);
        }
        else if (position == 4){
            currentFragment = "profileFragment";
            changeFragmentInDashBoard(profileFragment);
        }
        else {
            currentFragment = "dashboardFragment";
            changeFragmentInDashBoard(dashboardFragment);
        }
    }

    @Override
    public void onBackPressed() {
        if(!currentFragment.equals("dashboardFragment")){
            navigationLinearView.setCurrentActiveItem(0);
            onNavigationChanged(null, 0);
        }
        else{
            if(this.studentDrawerId != null){
                try {
                    if(studentDrawerId.isDrawerOpen(GravityCompat.START))
                        studentDrawerId.closeDrawer(GravityCompat.START);
                    else closeApp();
                }
                catch (Exception ignore){
                    closeApp();
                }
            }
            else closeApp();

        }
    }

    private void closeApp(){
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




    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_WRITE_PERMISSION && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            //   openFilePicker();
        }
        else {
            Toast.makeText(this, "You will not able to download any files without giving access permission", Toast.LENGTH_SHORT).show();
        }
    }

    private void requestPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_WRITE_PERMISSION);
        } else {
            //  openFilePicker();
        }
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
