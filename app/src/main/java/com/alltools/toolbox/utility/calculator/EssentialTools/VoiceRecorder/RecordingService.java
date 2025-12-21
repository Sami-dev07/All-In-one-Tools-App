package com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;
import androidx.core.app.NotificationCompat;

import com.alltools.toolbox.utility.calculator.MainActivity;
import com.alltools.toolbox.utility.calculator.R;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class RecordingService extends Service {
    private static final String LOG_TAG = "RecordingService";
    private static final SimpleDateFormat mTimerFormat = new SimpleDateFormat("mm:ss", Locale.getDefault());
    private DBHelper mDatabase;
    private String mFileName = null;
    private String mFilePath = null;
    private MediaRecorder mRecorder = null;
    private long mStartingTimeMillis = 0;
    private long mElapsedMillis = 0;
    private int mElapsedSeconds = 0;
    private OnTimerChangedListener onTimerChangedListener = null;
    private Timer mTimer = null;
    private TimerTask mIncrementTimerTask = null;

    public interface OnTimerChangedListener {
        void onTimerChanged(int i);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    static int access$008(RecordingService recordingService) {
        int i = recordingService.mElapsedSeconds;
        recordingService.mElapsedSeconds = i + 1;
        return i;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.mDatabase = new DBHelper(getApplicationContext());
    }

    @Override
    public int onStartCommand(Intent intent, int i, int i2) {
        startRecording();
        return Service.START_STICKY;
    }

    @Override
    public void onDestroy() {
        if (this.mRecorder != null) {
            stopRecording();
        }
        super.onDestroy();
    }

    public void startRecording() {
        setFileNameAndPath();
        MediaRecorder mediaRecorder = new MediaRecorder();
        this.mRecorder = mediaRecorder;
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        this.mRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        this.mRecorder.setOutputFile(this.mFilePath);
        this.mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);
        this.mRecorder.setAudioChannels(1);

        try {
            this.mRecorder.prepare();
            this.mRecorder.start();
            this.mStartingTimeMillis = System.currentTimeMillis();
        } catch (IOException unused) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    public void setFileNameAndPath() {
        File file;
        int i = 0;
        do {
            i++;
            this.mFilePath = getAbsoluteFileName() + (this.mDatabase.getCount() + i) + ".mp3";
            file = new File(this.mFilePath);
            this.mFileName = file.getName();
            if (!file.exists()) {
                return;
            }
        } while (!file.isDirectory());
    }

    private String getAbsoluteFileName() {
        return String.valueOf(new File(getFileOutputUri().getPath()));
    }

    private Uri getFileOutputUri() {
        if ("mounted".equals(Environment.getExternalStorageState())) {
            return Uri.fromFile(new File(getExternalFilesDir(Environment.DIRECTORY_MUSIC), "record_file"));
        }
        return Uri.fromFile(new File(getFilesDir() + "/" + Environment.DIRECTORY_MUSIC, "record_file"));
    }

    public void stopRecording() {
        this.mRecorder.stop();
        this.mElapsedMillis = System.currentTimeMillis() - this.mStartingTimeMillis;
        this.mRecorder.release();
        Toast.makeText(this, getString(R.string.toast_recording_finish) + " " + this.mFilePath, Toast.LENGTH_LONG).show();
        TimerTask timerTask = this.mIncrementTimerTask;
        if (timerTask != null) {
            timerTask.cancel();
            this.mIncrementTimerTask = null;
        }
        this.mRecorder = null;
        try {
            this.mDatabase.addRecording(this.mFileName, this.mFilePath, this.mElapsedMillis);
        } catch (Exception e) {
            Log.e(LOG_TAG, "exception", e);
        }
    }

    private void startTimer() {
        this.mTimer = new Timer();
        TimerTask timerTask = new TimerTask() { // from class: com.droidfoundry.tools.sound.audio.RecordingService.1
            @Override // java.util.TimerTask, java.lang.Runnable
            public void run() {
                RecordingService.access$008(RecordingService.this);
                if (RecordingService.this.onTimerChangedListener != null) {
                    RecordingService.this.onTimerChangedListener.onTimerChanged(RecordingService.this.mElapsedSeconds);
                }
                ((NotificationManager) RecordingService.this.getSystemService("notification")).notify(1, RecordingService.this.createNotification());
            }
        };
        this.mIncrementTimerTask = timerTask;
        this.mTimer.scheduleAtFixedRate(timerTask, 1000L, 1000L);
    }

    public Notification createNotification() {
        NotificationCompat.Builder ongoing = new NotificationCompat.Builder(getApplicationContext()).setSmallIcon(R.drawable.ic_mic_white_36dp).setContentTitle(getString(R.string.notification_recording)).setContentText(mTimerFormat.format(Integer.valueOf(this.mElapsedSeconds * 1000))).setOngoing(true);
        ongoing.setContentIntent(PendingIntent.getActivities(getApplicationContext(), 0, new Intent[]{new Intent(getApplicationContext(), MainActivity.class)}, PendingIntent.FLAG_IMMUTABLE));
        return ongoing.build();
    }
}