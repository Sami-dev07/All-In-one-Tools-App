package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.RandomNumbersAndPassword;

import android.content.Context;
import android.content.SharedPreferences;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

/* loaded from: classes.dex */
public class RandomViewPagerAdapter extends FragmentPagerAdapter {
    SharedPreferences appDisplayPrefs;
    Context context;
    boolean displayCategoryUnits;
    String[] tabs;

    @Override // androidx.viewpager.widget.PagerAdapter
    public int getCount() {
        return 2;
    }

    public RandomViewPagerAdapter(FragmentManager fragmentManager, String[] strArr, Context context) {
        super(fragmentManager);
        this.displayCategoryUnits = false;
        this.tabs = strArr;
        this.context = context;
    }

    @Override // androidx.fragment.app.FragmentPagerAdapter
    public Fragment getItem(int i) {
        return i == 1 ? new PasswordFragment() : i == 0 ? new RandomNumberFragment() : null;
    }
}
