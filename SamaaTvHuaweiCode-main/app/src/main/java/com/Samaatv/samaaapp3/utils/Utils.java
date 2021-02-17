package com.Samaatv.samaaapp3.utils;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.Samaatv.samaaapp3.R;
import com.google.android.material.tabs.TabLayout;

public class Utils {

    public static void setCustomColorsToTabLayout(Context context, TabLayout tabLayout, boolean isEnglish) {
        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            View view = prepareTabView(context, i, tab.getText(), isEnglish, (tabLayout.getTabCount()-1));
            tab.setCustomView(view);
        }
    }


    public static View prepareTabView(Context context, int pos, CharSequence text, boolean isEnglish, int lastPosition) {

        View view = LayoutInflater.from(context).inflate(R.layout.layout_custom_tab_layout, null);
        TextView tv_title = view.findViewById(R.id.tv_title);
//        tv_title.setText(tabTitles[pos]);
        if (isEnglish && pos == 0) {
            tv_title.setTextColor(Color.parseColor("#fe0000"));
        } else if (!isEnglish && pos == lastPosition) {
            tv_title.setTextColor(Color.parseColor("#fe0000"));
        } else {
            tv_title.setTextColor(Color.BLACK);
        }
        tv_title.setText(text.toString().toUpperCase());
        return view;
    }
}
