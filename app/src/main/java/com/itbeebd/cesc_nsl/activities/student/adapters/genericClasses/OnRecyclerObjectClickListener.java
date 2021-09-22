package com.itbeebd.cesc_nsl.activities.student.adapters.genericClasses;

import android.view.View;

public interface OnRecyclerObjectClickListener<T> extends BaseRecyclerListener {
    void onItemClicked(T item, View view);
}
