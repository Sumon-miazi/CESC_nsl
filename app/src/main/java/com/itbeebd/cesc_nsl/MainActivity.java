package com.itbeebd.cesc_nsl;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.PopupMenu;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import com.androidstudy.networkmanager.Tovuti;
import com.bumptech.glide.Glide;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.itbeebd.cesc_nsl.activities.LoginActivity;
import com.itbeebd.cesc_nsl.activities.VideoPlayerActivity;
import com.itbeebd.cesc_nsl.activities.home.NoticeGeneraFragment;
import com.itbeebd.cesc_nsl.activities.student.StudentDashboardActivity;
import com.itbeebd.cesc_nsl.activities.teacher.TeacherDashboardActivity;
import com.itbeebd.cesc_nsl.activities.teacher.TeacherLoginActivity;
import com.itbeebd.cesc_nsl.api.ApiUrls;
import com.itbeebd.cesc_nsl.api.StarterApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.dao.StudentDao;
import com.itbeebd.cesc_nsl.dao.TeacherDao;
import com.itbeebd.cesc_nsl.utils.CheckNetworkConnection;
import com.itbeebd.cesc_nsl.utils.dummy.Notice;

import org.imaginativeworld.whynotimagecarousel.ImageCarousel;
import org.imaginativeworld.whynotimagecarousel.model.CarouselItem;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private CheckNetworkConnection networkConnection;
    private Button tryAgainBtn;
    private boolean flag;
    private long time;

    private ConstraintLayout videoGalleryLayoutId;
    private ImageView userProfileImageHintId;
    private ImageView loginBtnHintId;
    
    private ImageView videoThumbId;
    private TextView videoTitleId;
    private TextView videoCaptionId;

    private TextView bottomAddressId;
    private TextView bottomEmailId;
    private TextView bottomPhoneId;

    private CardView sliderCardId;
    private ImageCarousel carousel;

    private ViewPager2 viewPager;
    private TabLayout tabLayout;

    private ViewPager2 news_event_pager;
    private TabLayout news_event_tab_layout;
    private Map<String, Object> allData;
    private NoticeGeneraFragment generalNoticeFragment;
    private NoticeGeneraFragment academicNoticeFragment;
    private NoticeGeneraFragment admissionNoticeFragment;
    
    private NoticeGeneraFragment newsFragment;
    private NoticeGeneraFragment eventFragment;

    private Map<String, String> videoData;

    // tab titles
    private String[] titles = new String[]{"GENERAL", "ACADEMIC", "ADMISSION"};
    private String[] newsEvent = new String[]{"NEWS", "EVENT"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();

        networkConnection = new CheckNetworkConnection(this);
        flag = CustomSharedPref.getInstance(this).getUserLoggedInOrNot();

        loginBtnHintId.setOnClickListener(this::showLoginMenu);
        userProfileImageHintId.setOnClickListener(view -> checkInternet(CustomSharedPref.getInstance(this).getUserType()));

        generalNoticeFragment = new NoticeGeneraFragment();
        academicNoticeFragment = new NoticeGeneraFragment();
        admissionNoticeFragment = new NoticeGeneraFragment();

        newsFragment = new NoticeGeneraFragment();
        eventFragment = new NoticeGeneraFragment();

        setLoginOrUserProfileLink();

        getDataFromApi();

        viewPager.setAdapter(new ViewPagerFragmentAdapter(this, "notice"));

        news_event_pager.setAdapter(new ViewPagerFragmentAdapter(this, "newsEvent"));

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            tab.setText(titles[position]);
        }).attach();

        new TabLayoutMediator(news_event_tab_layout, news_event_pager, (tab, position) -> {
            tab.setText(newsEvent[position]);
        }).attach();
    }

    private void init() {
        videoGalleryLayoutId = findViewById(R.id.videoGalleryLayoutId);
        videoThumbId = findViewById(R.id.videoThumbId);
        videoTitleId = findViewById(R.id.videoTitleId);
        videoCaptionId = findViewById(R.id.videoCaptionId);

        userProfileImageHintId = findViewById(R.id.userProfileImageHintId);
        loginBtnHintId = findViewById(R.id.loginBtnHintId);
        sliderCardId = findViewById(R.id.sliderCardId);
        carousel = findViewById(R.id.carousel);

        bottomAddressId = findViewById(R.id.bottomAddressId);
        bottomEmailId = findViewById(R.id.bottomEmailId);
        bottomPhoneId = findViewById(R.id.bottomPhoneId);

        viewPager = findViewById(R.id.pager);
        tabLayout = findViewById(R.id.tab_layout);

        news_event_pager = findViewById(R.id.news_event_pager);
        news_event_tab_layout = findViewById(R.id.news_event_tab_layout);

        videoGalleryLayoutId.setOnClickListener(view -> {
            if(videoData != null){
                Intent intent = new Intent(this, VideoPlayerActivity.class);
                intent.putExtra("web_url", videoData.get("url"));
                startActivity(intent);
            }
        });
    }
    
    private void showLoginMenu(View view){
        //Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(MainActivity.this, loginBtnHintId);
        //Inflating the Popup using xml file
        popup.getMenuInflater().inflate(R.menu.login_menu, popup.getMenu());

        //registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {
                //Toast.makeText(MainActivity.this,"You Clicked : " + item.getTitle(), Toast.LENGTH_SHORT).show();
                if(item.getItemId() == R.id.teacher){
                    startActivity(new Intent(MainActivity.this, TeacherLoginActivity.class));
                //    finish();
                }
                else if(item.getItemId() == R.id.student){
                    startActivity(new Intent(MainActivity.this, LoginActivity.class));
                //    finish();
                }
                return true;
            }
        });

        popup.show();//showing popup menu
    }

    private void setLoginOrUserProfileLink() {
        if (CustomSharedPref.getInstance(this).getUserLoggedInOrNot()) {
            if (CustomSharedPref.getInstance(this).getUserType().equals("teacher"))
                setImageView(userProfileImageHintId, ApiUrls.BASE_IMAGE_URL +  new TeacherDao().getTeacher(this).getImage());
            else setImageView(userProfileImageHintId, ApiUrls.BASE_IMAGE_URL +  new StudentDao().getStudent(this).getImage());
        } else {
            loginBtnHintId.setVisibility(View.VISIBLE);
        }
    }


    private void getDataFromApi() {
        new StarterApi(this, "Loading...").getHomeData((object, message) -> {
            if (object != null) {
                allData = (Map<String, Object>) object;
                setSlider((ArrayList<Map<String, String>>) allData.get("sliderImage"));

                System.out.println("general >>>>> " + ((ArrayList<Notice>) allData.get("general_notice")).size());
                generalNoticeFragment = new NoticeGeneraFragment((ArrayList<Notice>) allData.get("general_notice"));
                academicNoticeFragment = new NoticeGeneraFragment((ArrayList<Notice>) allData.get("academic_notice"));
                admissionNoticeFragment = new NoticeGeneraFragment((ArrayList<Notice>) allData.get("admission_notice"));
                viewPager.setAdapter(new ViewPagerFragmentAdapter(this, "notice"));

                setVideoGallery((Map<String, String>) allData.get("videoData"));

                newsFragment = new NoticeGeneraFragment((ArrayList<Notice>) allData.get("news"));
                eventFragment = new NoticeGeneraFragment((ArrayList<Notice>) allData.get("events"));
                news_event_pager.setAdapter(new ViewPagerFragmentAdapter(this, "newsEvent"));

                setSideData((Map<String, String>) allData.get("siteData"));
            }
        });
    }

    private void setSideData(Map<String, String> siteData) {
        bottomAddressId.setText(siteData.get("address"));
        bottomEmailId.setText(siteData.get("email"));
        bottomPhoneId.setText(siteData.get("phone"));
    }


    private void setSlider(ArrayList<Map<String, String>> images) {
// Register lifecycle. For activity this will be lifecycle/getLifecycle() and for fragments it will be viewLifecycleOwner/getViewLifecycleOwner().
        carousel.registerLifecycle(getLifecycle());

        List<CarouselItem> list = new ArrayList<>();

        for (int i = 0; i < images.size(); i++) {
            System.out.println("title >>>>>> " + images.get(i).get("title"));
            if (images.get(i).get("title") != null && !Objects.requireNonNull(images.get(i).get("title")).equalsIgnoreCase("null"))
                list.add(new CarouselItem(images.get(i).get("url"), images.get(i).get("title")));
            else list.add(new CarouselItem(images.get(i).get("url")));
        }

        carousel.setData(list);

        sliderCardId.setVisibility(View.VISIBLE);

    }

    private void setVideoGallery(Map<String, String> data){
        videoData = data;
        videoTitleId.setText(data.get("title"));
        videoCaptionId.setText(data.get("caption"));
        setImageView(videoThumbId, new VideoPlayerActivity().getYoutubeThumbnailUrlFromVideoUrl(data.get("url")));
        videoGalleryLayoutId.setVisibility(View.VISIBLE);
        System.out.println("thumb >>>>>> " + new VideoPlayerActivity().getYoutubeThumbnailUrlFromVideoUrl(data.get("url")));
    }

    private void setImageView(ImageView imageView, String imageUrl) {
        Glide.with(this)
                .load(imageUrl)
                .placeholder(R.drawable.default_male)
                .error(R.drawable.default_male)
                .fallback(R.drawable.default_male)
                .into(imageView);

        imageView.setVisibility(View.VISIBLE);
    }

    private void checkInternet(String userType) {
        Intent intent;
        if (userType.equals("teacher")) intent = new Intent(this, TeacherDashboardActivity.class);
        else intent = new Intent(this, StudentDashboardActivity.class);

        takeActionOnInternetStatus(networkConnection.haveNetworkConnection(), intent);
    }

    private void takeActionOnInternetStatus(boolean isConnected, Intent intent) {
        if (!isConnected) {
            Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCancelable(true);
            dialog.setContentView(R.layout.no_internet);

            tryAgainBtn = dialog.findViewById(R.id.tryAgainId);

            tryAgainBtn.setOnClickListener(view -> {
                checkInternet(CustomSharedPref.getInstance(this).getUserType());
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
    protected void onStop() {
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
        //    startActivity(new Intent(this, MainActivity.class));
            finish();
        } else {
            time = System.currentTimeMillis();
            Toast.makeText(this, "press again to exit", Toast.LENGTH_SHORT).show();
        }
    }

    private class ViewPagerFragmentAdapter extends FragmentStateAdapter {
        private String type;
        public ViewPagerFragmentAdapter(@NonNull FragmentActivity fragmentActivity, String type) {
            super(fragmentActivity);
            this.type = type;
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            System.out.println(">>>>>>>>>>>. position " + position);
            if(type.equals("notice")){
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
            else {
                switch (position) {
                    case 0:
                        return newsFragment;
                    case 1:
                        return eventFragment;
                }
                return newsFragment;
            }

        }

        @Override
        public int getItemCount() {
            if(type.equals("notice")) return titles.length;
            else return newsEvent.length;
        }
    }
}