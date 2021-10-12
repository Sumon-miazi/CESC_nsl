package com.itbeebd.cesc_nsl;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.androidstudy.networkmanager.Tovuti;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.FirebaseApp;
import com.google.firebase.appcheck.FirebaseAppCheck;
import com.google.firebase.appcheck.debug.DebugAppCheckProviderFactory;
import com.itbeebd.cesc_nsl.activities.LoginActivity;
import com.itbeebd.cesc_nsl.activities.home.NoticeGeneraFragment;
import com.itbeebd.cesc_nsl.activities.teacher.TeacherLoginActivity;
import com.itbeebd.cesc_nsl.api.StarterApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.CheckNetworkConnection;
import com.itbeebd.cesc_nsl.utils.dummy.Notice;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private CheckNetworkConnection networkConnection;
    private Button tryAgainBtn;
    private boolean flag;
    private long time;

    private Button studentLoginBtnId;
    private Button teacherLoginBtnId;
    private CardView sliderCardId;
    private ImageCarousel carousel;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private Map<String, Object> allData;
    private NoticeGeneraFragment generalNoticeFragment;
    private NoticeGeneraFragment academicNoticeFragment;
    private NoticeGeneraFragment admissionNoticeFragment;

    // tab titles
    private String[] titles = new String[]{"GENERAL", "ACADEMIC", "ADMISSION"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        networkConnection = new CheckNetworkConnection(this);
        flag = CustomSharedPref.getInstance(this).getStudentLoggedInOrNot();

        sliderCardId = findViewById(R.id.sliderCardId);
        carousel = findViewById(R.id.carousel);
        studentLoginBtnId = findViewById(R.id.studentLoginBtnId);
        teacherLoginBtnId = findViewById(R.id.teacherLoginBtnId);

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);

        viewPager.setAdapter(new ViewPagerFragmentAdapter(this));
      //  pagerFragmentAdapter =
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();

        FirebaseApp.initializeApp(MainActivity.this);
        FirebaseAppCheck firebaseAppCheck = FirebaseAppCheck.getInstance();
        firebaseAppCheck.installAppCheckProviderFactory(
                DebugAppCheckProviderFactory.getInstance());

        studentLoginBtnId.setOnClickListener(view -> checkInternet(new Intent(this, LoginActivity.class )));

        teacherLoginBtnId.setOnClickListener(view -> checkInternet(new Intent(this, TeacherLoginActivity.class )));

        generalNoticeFragment = new NoticeGeneraFragment();
        academicNoticeFragment = new NoticeGeneraFragment();
        admissionNoticeFragment = new NoticeGeneraFragment();
    //    setSlider();
        getDataFromApi();
    }


    private void getDataFromApi(){
        new StarterApi(this, "Loading...").getHomeData((object, message) -> {
            if(object != null){
                allData = (Map<String, Object>) object;
                setSlider((ArrayList<Map<String, String>>) allData.get("sliderImage"));
                System.out.println("general >>>>> " + ((ArrayList<Notice>) allData.get("general_notice")).size());
                generalNoticeFragment = new NoticeGeneraFragment((ArrayList<Notice>) allData.get("general_notice"));
                academicNoticeFragment = new NoticeGeneraFragment((ArrayList<Notice>) allData.get("academic_notice"));
                admissionNoticeFragment = new NoticeGeneraFragment((ArrayList<Notice>) allData.get("admission_notice"));
                viewPager.setAdapter(new ViewPagerFragmentAdapter(this));
            }
        });
    }


    private void setSlider(ArrayList<Map<String, String>> images){
// Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragments it will be viewLifecycleOwner/getViewLifecycleOwner().
        carousel.registerLifecycle(getLifecycle());

        List<CarouselItem> list = new ArrayList<>();

        for(int i = 0; i < images.size(); i++){
            if(images.get(i).get("title") != null) list.add(new CarouselItem(images.get(i).get("url"), images.get(i).get("title")));
            else list.add(new CarouselItem(images.get(i).get("url")));
        }

        carousel.setData(list);

        sliderCardId.setVisibility(View.VISIBLE);

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

    @Override
    public void onBackPressed() {
        //  super.onBackPressed();
        if (time + 2000 > System.currentTimeMillis()) {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            time = System.currentTimeMillis();
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {

        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity) {
            super(fragmentActivity);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            System.out.println(">>>>>>>>>>>. position " + position);
            switch (position) {
                case 0:
                    return generalNoticeFragment;
                case 1:
                    return academicNoticeFragment;
                case 2:
                    return admissionNoticeFragment;
            }
            return generalNoticeFragment;
        }

        @Override
        public int getItemCount() {
            return titles.length;
        }
    }
}