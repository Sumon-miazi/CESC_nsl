package com.itbeebd.cesc_nsl.activities.genericClasses;

import android.view.View;

public interface OnRecyclerObjectClickListener<T> extends BaseRecyclerListener {
    void onItemClicked(T item, View view);
}
