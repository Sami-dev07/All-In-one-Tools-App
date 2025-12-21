package com.alltools.toolbox.utility.calculator.TimeAndDateActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.alltools.toolbox.utility.calculator.R;
import com.alltools.toolbox.utility.calculator.TimeAndDateActivity.CalenderCodes.activities.Calender_Main;
import com.alltools.toolbox.utility.calculator.TimeAndDateActivity.StopWatch.StopWatchActivity;
import com.alltools.toolbox.utility.calculator.TimeAndDateActivity.TapCounter.TapCounterActivity;
import com.alltools.toolbox.utility.calculator.databinding.ActivityTimeAndDateBinding;

public class TimeAndDateActivity extends AppCompatActivity {

    ActivityTimeAndDateBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTimeAndDateBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_animation);

        binding.backICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                onBackPressed();
            }
        });
        binding.tapCounterIcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), TapCounterActivity.class));
            }
        });
        binding.stopWatchIcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), StopWatchActivity.class));
            }
        });
        binding.calenderIcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), Calender_Main.class));
            }
        });


    }
}