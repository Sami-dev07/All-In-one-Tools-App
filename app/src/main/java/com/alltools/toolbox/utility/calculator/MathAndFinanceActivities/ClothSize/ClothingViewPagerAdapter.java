package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ClothSize;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ClothingSize.ClothingWoMenFragment;

public class ClothingViewPagerAdapter extends FragmentPagerAdapter {
    private Context context;
    private String[] tabs;

    public ClothingViewPagerAdapter(FragmentManager fragmentManager, String[] tabs, Context context) {
        super(fragmentManager, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        this.tabs = tabs != null ? tabs : new String[0];  // Initialize tabs to an empty array if null
        this.context = context;
    }

    @Override
    public int getCount() {
        return tabs.length;  // Return the size of the tabs array
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return position == 1 ? new ClothingWoMenFragment() : position == 0 ? new ClothingMenFragment() : null;
    }
}
