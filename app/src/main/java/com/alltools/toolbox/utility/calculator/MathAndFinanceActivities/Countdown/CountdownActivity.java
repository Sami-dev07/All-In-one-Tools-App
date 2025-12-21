package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.Countdown;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.R;

public class CountdownActivity extends AppCompatActivity {
    private TextView tvHour, tvMinute, tvSecond;
    private ImageView btnStart, btnStop, btnPlus15;
    private Button btnChangeTime;
    private CountDownTimer countDownTimer;
    private long timeInMillis, timeLeftInMillis;
    private boolean timerRunning;
    String TAG = "timer";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_countdown);

        tvHour = findViewById(R.id.tvHour);
        tvMinute = findViewById(R.id.tvMinute);
        tvSecond = findViewById(R.id.tvSecond);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        btnPlus15 = findViewById(R.id.btnPlus15);
        btnChangeTime = findViewById(R.id.btnChangeTime);

        Intent intent = getIntent();
        int hour = intent.getIntExtra("hour", 0);
        Log.d(TAG, "onCreate: Hours: " + hour);
        int minute = intent.getIntExtra("minute", 0);
        Log.d(TAG, "onCreate: Minutes: " + minute);
        timeInMillis = (hour * 3600 + minute * 60) * 1000;
        timeLeftInMillis = timeInMillis;

        updateTimerText();

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!timerRunning) {
                    startTimer();
                }else{
                    pauseTimer();
                }
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (timerRunning) {
                    resetTimer();
                }
            }
        });

        btnPlus15.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add15Seconds();
            }
        });

        btnChangeTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent changeTimeIntent = new Intent(CountdownActivity.this, SetTimeActivity.class);
                startActivity(changeTimeIntent);
            }
        });
    }

    private void startTimer() {
        countDownTimer = new CountDownTimer(timeLeftInMillis, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeLeftInMillis = millisUntilFinished;
                updateTimerText();
            }

            @Override
            public void onFinish() {
                timerRunning = false;
                btnStart.setImageResource(R.drawable.ic_play);
            }
        }.start();

        timerRunning = true;
        btnStart.setImageResource(R.drawable.ic_pause);
    }

    private void pauseTimer() {
        countDownTimer.cancel();
        timerRunning = false;
        btnStart.setImageResource(R.drawable.ic_play);
    }


    private void add15Seconds() {
        timeLeftInMillis += 15000;
        if (timerRunning) {
            countDownTimer.cancel();
            startTimer();
        }
        updateTimerText();
    }
    private void resetTimer() {
        timeLeftInMillis = timeInMillis;
        updateTimerText();
        btnStart.setImageResource(R.drawable.ic_play);
    }

    private void updateTimerText() {
        int hours = (int) (timeLeftInMillis / 1000) / 3600;
        int minutes = (int) ((timeLeftInMillis / 1000) % 3600) / 60;
        int seconds = (int) (timeLeftInMillis / 1000) % 60;

        tvHour.setText(String.format("%02d\nHrs", hours));
        tvMinute.setText(String.format("%02d\nMins", minutes));
        tvSecond.setText(String.format("%02d\nSec", seconds));
    }
}