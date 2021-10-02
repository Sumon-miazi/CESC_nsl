package com.itbeebd.cesc_nsl.interfaces;

import androidx.drawerlayout.widget.DrawerLayout;

public interface FragmentToActivity {
    void call(DrawerLayout drawerLayout, String data);
    void changeActivity(String message);
}
