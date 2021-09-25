package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.ResultBoardAdapter;
import com.itbeebd.cesc_nsl.sugarClass.ResultObj;
import com.skydoves.powerspinner.PowerSpinnerView;

import java.util.ArrayList;


public class ResultBoardFragment extends Fragment {

    private RecyclerView resultBoardRecyclerViewId;
    private ConstraintLayout resultRecordHeaderId;
    private TextView semesterName;
    private PowerSpinnerView powerSpinnerView;

    private ResultBoardAdapter resultBoardAdapter;

    public ResultBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_board, container, false);

        powerSpinnerView = view.findViewById(R.id.powerSpinnerView);
        resultBoardRecyclerViewId = view.findViewById(R.id.resultBoardRecyclerViewId);
        resultRecordHeaderId = view.findViewById(R.id.resultRecordHeaderId);
        semesterName = view.findViewById(R.id.textView38);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<ResultObj> resultObjs = new ArrayList<>();
        resultObjs.add(new ResultObj("Higher Math first paper", "100", "80", "20", "40", "100", "49", "33", "A+"));
        resultObjs.add(new ResultObj("Math", "100", "80", "20", "40", "100", "49", "33", "A+"));
        resultObjs.add(new ResultObj("Math", "100", "80", "20", "40", "100", "49", "33", "A+"));
        resultObjs.add(new ResultObj("Math", "100", "80", "20", "40", "100", "49", "33", "A+"));
        resultObjs.add(new ResultObj("Math", "100", "80", "20", "40", "100", "49", "33", "A+"));

        resultBoardAdapter = new ResultBoardAdapter(getContext());
        resultBoardAdapter.setItems(resultObjs);
        resultBoardRecyclerViewId.setLayoutManager(new LinearLayoutManager(getContext()));
        resultBoardRecyclerViewId.setAdapter(resultBoardAdapter);
    }

    @Override
    public void onStart() {
        super.onStart();

    }
}