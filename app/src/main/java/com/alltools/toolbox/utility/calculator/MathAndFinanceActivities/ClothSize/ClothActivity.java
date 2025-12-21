package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ClothSize;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import com.google.android.material.tabs.TabLayout;
import com.alltools.toolbox.utility.calculator.R;
import java.util.Objects;

public class ClothActivity extends AppCompatActivity {
    TabLayout tabLayout;
    String[] tabs = {"Tab 1", "Tab 2"};
    ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cloth);
        findAllViewById();
        setTabParams();
    }

    private void findAllViewById() {
        this.viewPager = findViewById(R.id.vp_home);
        this.tabLayout = findViewById(R.id.tab_home);
    }

    private void setTabParams() {
        if (tabs == null) {
            tabs = new String[]{"Tab 1", "Tab 2"};
        }
        this.viewPager.setAdapter(new ClothingViewPagerAdapter(getSupportFragmentManager(), tabs, this));
        this.tabLayout.setupWithViewPager(this.viewPager);
        this.tabLayout.setSelectedTabIndicatorColor(ContextCompat.getColor(this, R.color.tools_color_cloths_size));
        Objects.requireNonNull(this.tabLayout.getTabAt(0)).setText(getResources().getString(R.string.clothing_men_text));
        Objects.requireNonNull(this.tabLayout.getTabAt(1)).setText(getResources().getString(R.string.clothing_women_text));
        this.viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrollStateChanged(int state) {
            }

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }
        });
    }

    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View currentFocus = getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            hideKeyboard();
            setResult(RESULT_OK, new Intent());
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
