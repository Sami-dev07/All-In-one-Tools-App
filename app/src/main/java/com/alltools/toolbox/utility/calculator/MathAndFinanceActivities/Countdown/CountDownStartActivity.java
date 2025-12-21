package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.Countdown;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.R;
import com.alltools.toolbox.utility.calculator.databinding.ActivityCountDownStartBinding;

public class CountDownStartActivity extends AppCompatActivity {


    ActivityCountDownStartBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCountDownStartBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


        binding.countDownBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SetTimeActivity.class));
            }
        });
    }
}