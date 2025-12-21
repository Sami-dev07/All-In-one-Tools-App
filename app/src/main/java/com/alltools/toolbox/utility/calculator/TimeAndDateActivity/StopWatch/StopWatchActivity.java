package com.alltools.toolbox.utility.calculator.TimeAndDateActivity.StopWatch;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.TextView;

import com.alltools.toolbox.utility.calculator.R;

public class StopWatchActivity extends AppCompatActivity implements View.OnClickListener {
    private static final long REFRESH_DELAY = 100;
    private static final String STATE_LAST_TICK = "lastTick";
    private static final String STATE_RUNNING = "running";
    private static final String STATE_SETS_COUNT = "setsCount";
    private static final String STATE_TOTAL_TIME = "totalTime";

    private Handler mHandler;
    private long mLastTick;
    private AppCompatButton mReset;
    private int mSetsCount;
    private AppCompatButton mStart;
    private TextView mTime;
    private long mTotalTime;
    private boolean mRunning = false;

    private final Runnable mUpdater = new Runnable() {
        @Override
        public void run() {
            if (mRunning) {
                long elapsedRealtime = SystemClock.elapsedRealtime();
                mTotalTime += elapsedRealtime - mLastTick;
                refreshTimer();
                mLastTick = elapsedRealtime;
                mHandler.postDelayed(this, REFRESH_DELAY);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stop_watch);
        getWindow().addFlags(128);

        findAllViewsById();
        initParams();

        if (savedInstanceState != null) {
            mRunning = savedInstanceState.getBoolean(STATE_RUNNING);
            mTotalTime = savedInstanceState.getLong(STATE_TOTAL_TIME);
            mLastTick = savedInstanceState.getLong(STATE_LAST_TICK);
            mSetsCount = savedInstanceState.getInt(STATE_SETS_COUNT);
        }

        setAllOnClickListener();
    }

    private void setAllOnClickListener() {
        mStart.setOnClickListener(this);
        mReset.setOnClickListener(this);
    }

    private void initParams() {
        mHandler = new Handler();
        refreshButtons();
        refreshTimer();
    }

    private void findAllViewsById() {
        mTime = findViewById(R.id.time);
        mStart = findViewById(R.id.start);
        mReset = findViewById(R.id.reset);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.start) {
            toggleStartPause();
        } else if (view.getId() == R.id.reset) {
            reset();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_RUNNING, mRunning);
        outState.putLong(STATE_TOTAL_TIME, mTotalTime);
        outState.putInt(STATE_SETS_COUNT, mSetsCount);
        outState.putLong(STATE_LAST_TICK, mLastTick);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (mRunning) {
            mHandler.post(mUpdater);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        mHandler.removeCallbacks(mUpdater);
    }

    private void toggleStartPause() {
        mRunning = !mRunning;
        if (mRunning) {
            mSetsCount++;
            mLastTick = SystemClock.elapsedRealtime();
            mHandler.post(mUpdater);
        }
        refreshButtons();
    }

    private void reset() {
        if (!mRunning) {
            mTotalTime = 0L;
            refreshTimer();
            mReset.setEnabled(false);
        }
    }

    private void refreshButtons() {
        mReset.setEnabled(!mRunning);
        mStart.setText(getString(mRunning ? R.string.pause : R.string.start));
    }

    private void refreshTimer() {
        long seconds = mTotalTime / 1000;
        mTime.setText(String.format(getString(R.string.time_format), seconds / 60, seconds % 60, (mTotalTime % 1000) / REFRESH_DELAY));
    }


}
