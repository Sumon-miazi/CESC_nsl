package com.itbeebd.cesc_nsl;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.androidstudy.networkmanager.Tovuti;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.itbeebd.cesc_nsl.activities.LoginActivity;
import com.itbeebd.cesc_nsl.activities.teacher.TeacherLoginActivity;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.CheckNetworkConnection;

public class MainActivity extends AppCompatActivity {

    private CheckNetworkConnection networkConnection;
    private Button tryAgainBtn;
    private boolean flag;

    private Button studentLoginBtnId;
    private Button teacherLoginBtnId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkConnection = new CheckNetworkConnection(this);
        flag = CustomSharedPref.getInstance(this).getStudentLoggedInOrNot();

        studentLoginBtnId = findViewById(R.id.studentLoginBtnId);
        teacherLoginBtnId = findViewById(R.id.teacherLoginBtnId);

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance());

        studentLoginBtnId.setOnClickListener(view -> checkInternet(new Intent(this, LoginActivity.class )));

        teacherLoginBtnId.setOnClickListener(view -> checkInternet(new Intent(this, TeacherLoginActivity.class )));

    }

    private void checkInternet(Intent intent) {
            takeActionOnInternetStatus(networkConnection.haveNetworkConnection(), intent);
    }

    private void takeActionOnInternetStatus(boolean isConnected, Intent intent){
        if (!isConnected) {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.no_internet);

            tryAgainBtn = dialog.findViewById(R.id.tryAgainId);

            tryAgainBtn.setOnClickListener(view -> {
                checkInternet(intent);
                dialog.dismiss();
            });

            dialog.show();
        } else {
//            if(flag) this.startActivity(new Intent(this, StudentDashboardActivity.class));
//            else this.startActivity(new Intent(this, LoginActivity.class));
            this.startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onStop(){
        try {
            Tovuti.from(this).stop();
        } catch (Exception ignore) {
        }
        super.onStop();
    }
}