package com.itbeebd.cesc_nsl.utils.tableDataObj;

import androidx.annotation.Nullable;

public class Cell {
    @Nullable
    private String mData;

    public Cell(@Nullable String data) {
        this.mData = data;
    }

    @Nullable
    public String getData() {
        return mData;
    }
}