package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.RandomNumbersAndPassword;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.MenuItem;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import com.alltools.toolbox.utility.calculator.R;
import com.google.android.material.tabs.TabLayout;

public class RandomHomeActivity extends AppCompatActivity {
    SharedPreferences adPrefs;
    TabLayout tabLayout;
    String[] tabs;
    Toolbar toolbar;
    ViewPager viewPager;

    @Override // androidx.fragment.app.FragmentActivity, androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        try {
            setContentView(R.layout.form_random_home);
            findAllViewByIds();
            setTabParams();
//            setToolBarProperties();
//            changeStatusBarColors();
//            loadBannerAds();
        } catch (Exception e) {
            e.printStackTrace();
            finish();
        }
    }

//    private void loadBannerAds() {
//        SharedPreferences sharedPreferences = getSharedPreferences(AdUtils.AD_PREFS_FILE_NAME, 0);
//        this.adPrefs = sharedPreferences;
//        if (sharedPreferences.getBoolean(AdUtils.PREMIUM_KEY, false)) {
//            return;
//        }
//        AdUtils.loadGoogleAdaptiveBannerAds(getApplicationContext(), (LinearLayout) findViewById(R.id.ll_banner_ad), getAdSize());
//    }
//
//    private AdSize getAdSize() {
//        try {
//            Display defaultDisplay = getWindowManager().getDefaultDisplay();
//            DisplayMetrics displayMetrics = new DisplayMetrics();
//            defaultDisplay.getMetrics(displayMetrics);
//            return AdSize.getCurrentOrientationAnchoredAdaptiveBannerAdSize(this, (int) (displayMetrics.widthPixels / displayMetrics.density));
//        } catch (Exception unused) {
//            return AdSize.SMART_BANNER;
//        }
//    }

//    @Override // androidx.activity.ComponentActivity, android.app.Activity
//    public void onBackPressed() {
//        setResult(-1, new Intent());
//        finish();
//    }

    private void findAllViewByIds() {
        this.viewPager = (ViewPager) findViewById(R.id.vp_home);
        this.tabLayout = (TabLayout) findViewById(R.id.tab_home);
//        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
    }

    private void setTabParams() {
        this.viewPager.setAdapter(new RandomViewPagerAdapter(getSupportFragmentManager(), this.tabs, this));
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.tools_color_random));
        this.tabLayout.setTabTextColors(ColorStateList.valueOf(ContextCompat.getColor(this, R.color.tools_edit_text_primary_color)));
        this.tabLayout.getTabAt(0).setText("Numbers");
        this.tabLayout.getTabAt(1).setText("Password");
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() { // from class: com.droidfoundry.tools.maths.random.RandomHomeActivity.1
            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrollStateChanged(int i) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageScrolled(int i, float f, int i2) {
            }

            @Override // androidx.viewpager.widget.ViewPager.OnPageChangeListener
            public void onPageSelected(int i) {
            }
        });
    }

    @Override // android.app.Activity
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            setResult(-1, new Intent());
            finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }

//    private void setToolBarProperties() {
//        setSupportActionBar(this.toolbar);
//        setTitle("");
//        getSupportActionBar().setHomeButtonEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_action_back);
//        this.toolbar.setTitleTextColor(-1);
//    }

//    private void changeStatusBarColors() {
//        if (Build.VERSION.SDK_INT >= 21) {
//            if (Build.VERSION.SDK_INT >= 23) {
//                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.status_bar_color_m));
//            } else {
//                getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.black));
//            }
//        }
//    }
}
