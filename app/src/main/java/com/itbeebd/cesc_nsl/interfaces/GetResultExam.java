package com.itbeebd.cesc_nsl.interfaces;

import com.itbeebd.cesc_nsl.sugarClass.ResultObj;
import com.itbeebd.cesc_nsl.utils.dummy.TermExam;

import java.util.ArrayList;

public interface GetResultExam {
    void data(ArrayList<ResultObj> resultObjArrayList, TermExam termExam, String message);
}
