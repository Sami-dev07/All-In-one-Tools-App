package com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.widget.Chronometer;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.alltools.toolbox.utility.calculator.R;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class VoiceRecorder extends AppCompatActivity {
    private static final String ARG_POSITION = "position";
    private static final String LOG_TAG = "RecordFragment";
    public static final int MY_PERMISSIONS_AUDIO_RECORDING = 202;
    boolean isRecordStoragePermissionGranted;
    private TextView mRecordingPrompt;
    private int position;
    ProgressBar progressBar;
    private FloatingActionButton mRecordButton = null;
    private int mRecordPromptCount = 0;
    private boolean mStartRecording = true;
    private boolean mPauseRecording = true;
    private Chronometer mChronometer = null;
    long timeWhenPaused = 0;

    void eee(VoiceRecorder recordFragment) {
        int i = recordFragment.mRecordPromptCount;
        recordFragment.mRecordPromptCount = i + 1;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recorder);

        this.mChronometer = findViewById(R.id.chronometer);
        this.mRecordingPrompt = findViewById(R.id.recording_status_text);
        this.progressBar = findViewById(R.id.recordProgressBar);
        FloatingActionButton floatingActionButton = findViewById(R.id.btnRecord);
        this.mRecordButton = floatingActionButton;
        floatingActionButton.setOnClickListener(view -> requestAudioRecordPermission());

    }


    public void requestAudioRecordPermission() {
        final String[] strArr = {"android.permission.RECORD_AUDIO"};
        if (ContextCompat.checkSelfPermission(VoiceRecorder.this, "android.permission.RECORD_AUDIO") != 0) {
            Activity activity = VoiceRecorder.this;
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.RECORD_AUDIO")) {
                MaterialAlertDialogBuilder materialAlertDialogBuilder = new MaterialAlertDialogBuilder(VoiceRecorder.this);
                materialAlertDialogBuilder.setMessage(getResources().getString(R.string.audio_storage_permission_hint));
                materialAlertDialogBuilder.setTitle(getResources().getString(R.string.permission_text));
                materialAlertDialogBuilder.setPositiveButton(getResources().getText(R.string.common_proceed_text), (dialogInterface, i) -> ActivityCompat.requestPermissions(VoiceRecorder.this, strArr, 202));
                materialAlertDialogBuilder.show();
                return;
            }
            ActivityCompat.requestPermissions(activity, strArr, 202);
            return;
        }
        this.isRecordStoragePermissionGranted = true;
        onRecord(this.mStartRecording);
        this.mStartRecording = !this.mStartRecording;
    }

    @Override
    public void onRequestPermissionsResult(int i, String[] strArr, int[] iArr) {
        super.onRequestPermissionsResult(i, strArr, iArr);
        if (i == 202) {
            if (iArr.length > 0 && iArr[0] == 0) {
                this.isRecordStoragePermissionGranted = true;
                onRecord(this.mStartRecording);
                this.mStartRecording = !this.mStartRecording;
                return;
            }
            Toast.makeText(VoiceRecorder.this, getResources().getString(R.string.audio_storage_permission_hint), Toast.LENGTH_SHORT).show();
        }
    }

    private void onRecord(boolean z) {
        Intent intent = new Intent(VoiceRecorder.this, RecordingService.class);
        if (z) {
            this.mRecordButton.setImageResource(R.drawable.ic_media_stop);
            Toast.makeText(this, R.string.toast_recording_start, Toast.LENGTH_SHORT).show();
            this.progressBar.setProgress((int) SystemClock.elapsedRealtime());
            this.mChronometer.setBase(SystemClock.elapsedRealtime());
            this.mChronometer.start();
            this.mChronometer.setOnChronometerTickListener(chronometer -> {
                if (mRecordPromptCount == 0) {
                    TextView textView = mRecordingPrompt;
                    textView.setText(getString(R.string.record_in_progress) + ".");
                } else if (mRecordPromptCount == 1) {
                    TextView textView2 = mRecordingPrompt;
                    textView2.setText(getString(R.string.record_in_progress) + "..");
                } else if (mRecordPromptCount == 2) {
                    TextView textView3 = mRecordingPrompt;
                    textView3.setText(getString(R.string.record_in_progress) + "...");
                    mRecordPromptCount = -1;
                }
                eee(VoiceRecorder.this);
            });
            VoiceRecorder.this.startService(intent);
            VoiceRecorder.this.getWindow().addFlags(128);
            TextView textView = this.mRecordingPrompt;
            textView.setText(getString(R.string.record_in_progress) + ".");
            this.mRecordPromptCount = this.mRecordPromptCount + 1;
            return;
        }
        this.mRecordButton.setImageResource(R.drawable.ic_mic_white_36dp);
        this.mChronometer.stop();
        this.mChronometer.setBase(SystemClock.elapsedRealtime());
        this.timeWhenPaused = 0L;
        this.mRecordingPrompt.setText(getString(R.string.record_prompt));
        VoiceRecorder.this.stopService(intent);
        VoiceRecorder.this.getWindow().clearFlags(128);
    }

}