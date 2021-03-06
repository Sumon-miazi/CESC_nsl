package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chivorn.smartmaterialspinner.SmartMaterialSpinner;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.ResultBoardAdapter;
import com.itbeebd.cesc_nsl.api.studentApi.ResultApi;
import com.itbeebd.cesc_nsl.dao.CustomSharedPref;
import com.itbeebd.cesc_nsl.sugarClass.ResultObj;
import com.itbeebd.cesc_nsl.utils.dummy.TermExam;

import java.util.ArrayList;
import java.util.List;


public class ResultBoardFragment extends Fragment {

    private RecyclerView resultBoardRecyclerViewId;
    private ConstraintLayout resultRecordHeaderId;
    private TextView semesterName;
    private SmartMaterialSpinner powerSpinnerView;
    private int examId = 0;
    private int oldExamId = 0;
    private TermExam termExam;
    private List<String> examList;
    private ArrayList<ResultObj> resultObjArrayList;
    private ResultBoardAdapter resultBoardAdapter;
    private ConstraintLayout resultViewLayoutID;
    private LinearLayout no_result_foundId;

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
        semesterName = view.findViewById(R.id.semesterNameViewId);

        resultViewLayoutID = view.findViewById(R.id.resultViewLayoutID);
        no_result_foundId = view.findViewById(R.id.no_result_foundId);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getResultByExamId();

    }

    @Override
    public void onStart() {
        super.onStart();

        if(this.examList != null) {
            initSpinner();
        }
    }

    private void initSpinner(){
        powerSpinnerView.setFloatingLabelText("");
        powerSpinnerView.setItem(examList);

        powerSpinnerView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
              //  Toast.makeText(getContext(), examList.get(position), Toast.LENGTH_SHORT).show();
              //  powerSpinnerView.setFloatingLabelText(examList.get(position));
                examId = termExam.getExamId(examList.get(position));
                getResultByExamId();

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });
    }

    private void getResultByExamId(){
        if(resultObjArrayList == null || examId != oldExamId){
            new ResultApi(getContext()).getResult(
                    examId,
                    CustomSharedPref.getInstance(getContext()).getAuthToken(),
                    (resultObjArrayList, termExam, message) -> {
                        oldExamId = examId;
                        if(resultObjArrayList != null){
                            this.resultObjArrayList = resultObjArrayList;

                            if(resultObjArrayList.size() != 0){
                                viewWidget();
                                semesterName.setText(resultObjArrayList.get(0).getExamName());
                            }
                            else viewWidget();
                            setAdapter();
                        }
                        else viewWidget();

                        if(termExam != null){
                            this.termExam = termExam;

                            if(this.examList == null) {
                                this.examList = termExam.getExamList();
                                initSpinner();
                            }
                        }
                    });
        }
        else {
            viewWidget();
            setAdapter();
        }

        if(examList != null){ initSpinner(); }

    }

    private void setAdapter(){
        resultBoardAdapter = new ResultBoardAdapter(getContext());
        resultBoardAdapter.setItems(resultObjArrayList);
        resultBoardRecyclerViewId.setLayoutManager(new LinearLayoutManager(getContext()));
        resultBoardRecyclerViewId.setAdapter(resultBoardAdapter);
    }

    private void viewWidget(){

            if(resultObjArrayList.size() != 0){

                semesterName.setText(resultObjArrayList.get(0).getExamName());
            }

            resultViewLayoutID.setVisibility(resultObjArrayList.size() != 0? View.VISIBLE: View.GONE);
            no_result_foundId.setVisibility(resultObjArrayList.size() != 0? View.GONE: View.VISIBLE);
    }
}