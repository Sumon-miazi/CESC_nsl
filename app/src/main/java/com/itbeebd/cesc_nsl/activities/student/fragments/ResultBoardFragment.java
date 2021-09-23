package com.itbeebd.cesc_nsl.activities.student.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.evrencoskun.tableview.TableView;
import com.itbeebd.cesc_nsl.R;
import com.itbeebd.cesc_nsl.activities.student.adapters.MyTableViewAdapter;
import com.itbeebd.cesc_nsl.utils.tableDataObj.Cell;
import com.itbeebd.cesc_nsl.utils.tableDataObj.ColumnHeader;
import com.itbeebd.cesc_nsl.utils.tableDataObj.RowHeader;

import java.util.ArrayList;
import java.util.List;


public class ResultBoardFragment extends Fragment {

    private List<RowHeader> mRowHeaderList;
    private List<ColumnHeader> mColumnHeaderList;
    private List<List<Cell>> mCellList;

    private TableView tableView;

    public ResultBoardFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mRowHeaderList = new ArrayList<>();
        mColumnHeaderList = new ArrayList<>();
        mCellList = new ArrayList<>();

        mRowHeaderList.add(new RowHeader("test"));

        mColumnHeaderList.add(new ColumnHeader("Full Mark"));
        mColumnHeaderList.add(new ColumnHeader("Sub Mark"));
        mColumnHeaderList.add(new ColumnHeader("Obj Mark"));
        mColumnHeaderList.add(new ColumnHeader("Prac Mark"));
        mColumnHeaderList.add(new ColumnHeader("CT Mark"));
        mColumnHeaderList.add(new ColumnHeader("Total"));
        mColumnHeaderList.add(new ColumnHeader("CGP"));
        mColumnHeaderList.add(new ColumnHeader("Grade"));

        List<Cell> cells = new ArrayList<>();
        cells.add(new Cell("123"));
        cells.add(new Cell("433"));
        cells.add(new Cell("433"));
        cells.add(new Cell("433"));
        cells.add(new Cell("433"));
        cells.add(new Cell("433"));
        cells.add(new Cell("433"));
        cells.add(new Cell("433"));
        mCellList.add(cells);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_result_board, container, false);
        tableView = view.findViewById(R.id.table_view_id);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        // Create our custom TableView Adapter
        MyTableViewAdapter adapter = new MyTableViewAdapter();

        // Set this adapter to the our TableView
        tableView.setAdapter(adapter);

        // Let's set datas of the TableView on the Adapter
        adapter.setAllItems(mColumnHeaderList, mRowHeaderList, mCellList);

    }
}