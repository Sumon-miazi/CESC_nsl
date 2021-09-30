package com.itbeebd.cesc_nsl.activities.student;

import android.os.Bundle;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.OnlineClassAdapter;
import com.itbeebd.cesc_nsl.api.studentApi.OnlineClassApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.utils.dummy.OnlineClass;

import java.util.ArrayList;

public class OnlineClassActivity extends AppCompatActivity {

    private ArrayList<OnlineClass> onlineClasses;
    private RecyclerView onlineClassRecyclerView;
    private LinearLayout no_data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_online_class);

        Toolbar mToolBar =  findViewById(R.id.o_class_ToolbarId);
        setSupportActionBar(mToolBar);
        getSupportActionBar().setTitle("ONLINE CLASS");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        onlineClassRecyclerView =  findViewById(R.id.onlineClassRecyclerViewId);
        no_data =  findViewById(R.id.no_book_foundId);

        callOnlineClassApi();
    }

    private void callOnlineClassApi() {
        new OnlineClassApi(this).getOnlineClass(
                CustomSharedPref.getInstance(this).getAuthToken(),
                (object, message) -> {
                    try {
                        if(object != null){
                            onlineClasses = (ArrayList<OnlineClass>) object;
                            setUpAdapter();
                        }
                        else Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
                    }
                    catch (Exception ignore){}
                }
        );
    }

    private void setUpAdapter() {
        OnlineClassAdapter onlineClassAdapter  = new OnlineClassAdapter(this);
        onlineClassAdapter.setItems(onlineClasses);
        onlineClassRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        onlineClassRecyclerView.setAdapter(onlineClassAdapter);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Respond to the action bar's Up/Home button
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}