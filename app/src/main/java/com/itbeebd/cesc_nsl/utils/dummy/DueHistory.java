package com.itbeebd.cesc_nsl.utils.dummy;

import java.io.Serializable;
import java.util.ArrayList;

public class DueHistory implements Serializable {
    private final int academic_year;
    private final int account_head_id;
    private final String account_head_name;
    private final int amount;
    private final int due_amount;
    private final int paidAmount;
    private final int weiber;
    private final int collected_month;
    private final String month;
    private final String payment_category;
    private final String classType;

    public DueHistory(int academic_year, int account_head_id, String account_head_name, int amount,int paidAmount, int due_amount, int weiber, int collected_month, String month, String payment_category, String classType) {
        this.academic_year = academic_year;
        this.account_head_id = account_head_id;
        this.account_head_name = account_head_name;
        this.amount = amount;
        this.due_amount = due_amount;
        this.paidAmount = paidAmount;
        this.weiber = weiber;
        this.collected_month = collected_month;
        this.month = month;
        this.payment_category = payment_category;
        this.classType = classType;
    }

    public int getAcademic_year() {
        return academic_year;
    }

    public int getAccount_head_id() {
        return account_head_id;
    }

    public String getAccount_head_name() {
        return account_head_name;
    }

    public int getAmount() {
        return amount;
    }

    public int getDue_amount() {
        return due_amount;
    }

    public int getPaidAmount() {
        return paidAmount;
    }

    public int getWeiber() {
        return weiber;
    }

    public int getCollected_month() {
        return collected_month;
    }

    public String getMonth() {
        return month;
    }

    public String getPayment_category() {
        return payment_category;
    }

    public String getClassType() {
        return classType;
    }
}