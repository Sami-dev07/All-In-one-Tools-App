package com.alltools.toolbox.utility.calculator.CommonToolsActivity.PianoActivity;

import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alltools.toolbox.utility.calculator.R;
import com.chengtao.pianoview.entity.Piano;
import com.chengtao.pianoview.listener.OnLoadAudioListener;
import com.chengtao.pianoview.listener.OnPianoListener;
import com.chengtao.pianoview.view.PianoView;

public class PianoActivity extends AppCompatActivity
        implements OnPianoListener, OnLoadAudioListener, SeekBar.OnSeekBarChangeListener, View.OnClickListener {

    private static final float SEEK_BAR_THUMB_OFFSET = -12;
    private PianoView pianoView;
    private SeekBar pianoSeekBar;
    private int seekBarProgress = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_piano);

        initializeViews();
        configurePianoView();
    }

    private void initializeViews() {
        pianoView = findViewById(R.id.pv);
        pianoSeekBar = findViewById(R.id.sb);
        Button leftButton = findViewById(R.id.iv_left_arrow);
        Button rightButton = findViewById(R.id.iv_right_arrow);
        pianoSeekBar.setThumbOffset((int) convertDpToPixel());
        pianoSeekBar.setOnSeekBarChangeListener(this);
        leftButton.setOnClickListener(this);
        rightButton.setOnClickListener(this);
    }

    private void configurePianoView() {
        pianoView.setSoundPollMaxStream(10);
        pianoView.setPianoListener(this);
        pianoView.setLoadAudioListener(this);
    }

    @Override
    public void onPianoInitFinish() {
    }

    @Override
    public void onPianoClick(Piano.PianoKeyType type, Piano.PianoVoice voice, int group, int positionOfGroup) {
    }

    @Override
    public void loadPianoAudioStart() {
        showToast("Loading Piano Music...");
    }

    @Override
    public void loadPianoAudioFinish() {
        showToast("Piano Music Loaded");
    }

    @Override
    public void loadPianoAudioError(Exception e) {
        showToast("Error Loading Piano Music");
        Log.e("PianoActivity", "Error loading piano music", e);
    }

    @Override
    public void loadPianoAudioProgress(int progress) {
        Log.d("PianoActivity", "Loading Progress: " + progress);
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        pianoView.scroll(progress);
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
    }

    @Override
    public void onClick(View view) {
        if (seekBarProgress == 0) {
            calculateSeekBarProgress();
        }

        int progress = pianoSeekBar.getProgress();
        if (view.getId() == R.id.iv_left_arrow) {
            pianoSeekBar.setProgress(Math.max(progress - seekBarProgress, 0));
        } else if (view.getId() == R.id.iv_right_arrow) {
            pianoSeekBar.setProgress(Math.min(progress + seekBarProgress, 100));
        }
    }

    private void calculateSeekBarProgress() {
        try {
            seekBarProgress = (pianoView.getLayoutWidth() * 100) / pianoView.getPianoWidth();
        } catch (Exception e) {
            Log.e("PianoActivity", "Error calculating seek bar progress", e);
        }
    }

    private float convertDpToPixel() {
        Resources resources = getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        return PianoActivity.SEEK_BAR_THUMB_OFFSET * ((float) metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    private void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pianoView != null) {
            pianoView.releaseAutoPlay();
        }
    }
}
