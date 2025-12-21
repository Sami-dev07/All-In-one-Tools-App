package com.alltools.toolbox.utility.calculator.TimeAndDateActivity.TapCounter;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.R;
import com.alltools.toolbox.utility.calculator.databinding.ActivityTapCounterBinding;

public class TapCounterActivity extends AppCompatActivity {

    ActivityTapCounterBinding binding;
    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTapCounterBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        TextView tapCounterTextView = findViewById(R.id.tapCounter);
//        binding.tapCounterTextView.setText(0);
        tapCounterTextView.setText("0");

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_animation);
        binding.taper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                count++;
                tapCounterTextView.setText(String.valueOf(count));

            }
        });

        binding.resetCounter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                count = 0;
                 tapCounterTextView.setText(String.valueOf(count));

            }
        });

    }
}