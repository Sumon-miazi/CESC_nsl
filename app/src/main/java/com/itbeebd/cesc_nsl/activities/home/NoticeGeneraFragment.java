package com.itbeebd.cesc_nsl.activities.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.genericClasses.OnRecyclerObjectClickListener;
import com.itbeebd.cesc_nsl.utils.dummy.Notice;

import java.util.ArrayList;


public class NoticeGeneraFragment extends Fragment implements OnRecyclerObjectClickListener<Notice> {

    private ArrayList<Notice> notices;
    private RecyclerView noticeBoardRecyclerViewId;

    public NoticeGeneraFragment() {
        // Required empty public constructor
    }

    public NoticeGeneraFragment(ArrayList<Notice> notices) {
       this.notices = notices;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notice_genera, container, false);
        noticeBoardRecyclerViewId = view.findViewById(R.id.noticeBoardRecyclerViewId);

        setAdapter();
        return view;
    }

    private void setAdapter() {
        if(notices == null){
            return;
        }

        NoticeAdapter noticeAdapter = new NoticeAdapter(getContext());
    //    noticeAdapter.setListener(getContext());
        noticeAdapter.setItems(notices);
      //  noticeAdapter.setItems(notices.size() > 3? notices.subList(0,2) : notices);
        noticeBoardRecyclerViewId.setLayoutManager(new LinearLayoutManager(getContext()));
      //  noticeBoardRecyclerViewId.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        noticeBoardRecyclerViewId.setAdapter(noticeAdapter);
    }

    @Override
    public void onItemClicked(Notice item, View view) {

    }
}