package com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.media.CamcorderProfile;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.Helper.Utils;
import com.alltools.toolbox.utility.calculator.R;

public class HiddenCameraActivity extends AppCompatActivity {

    private static final String TAG = "RecorderActivity";
    private int quality;

    private Handler handler;

    private final ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className,
                                       IBinder service) {
            MainService.LocalBinder binder = (MainService.LocalBinder) service;
            MainService mService = binder.getService();
            Utils.mBound = true;
            mService.startRecord(quality);
            Toast.makeText(getApplicationContext(), "Binded", Toast.LENGTH_SHORT).show();

        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            Log.e(TAG, "MainService Disconnected");
            Utils.mBound = false;
        }
    };

    Button startRecording;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hidden_camera);
        quality = CamcorderProfile.QUALITY_720P;
        startRecording = findViewById(R.id.startRecording);
        handler = new Handler();
        startRecording.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.mBound) {
                    unbindService(mConnection);
                    Utils.mBound = false;
                    stopService(new Intent(HiddenCameraActivity.this, MainService.class));
                    startRecording.setText("Start Recording");

                } else {
                    Utils.mBound = true;
                    startRecording.setVisibility(View.INVISIBLE);
                    startRecording.setText("Stop Recording");
                    createAndStartRecording();
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            startRecording.setVisibility(View.VISIBLE);

                        }
                    },1500);
                }
            }
        });
    }


    @Override
    protected void onDestroy() {
        Log.e(TAG, "Destroy");
        super.onDestroy();
        if (Utils.mBound) {
            unbindService(mConnection);
            Utils.mBound = false;
        }
    }

    @Override
    protected void onPause() {
        super.onPause();

    }


    private void createAndStartRecording() {

        if (!Settings.canDrawOverlays(HiddenCameraActivity.this)) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                    Uri.parse("package:" + getPackageName()));
            startActivityForResult(intent, 1234);

            return;
        }
        Utils.isForeground = false;
        Intent bgVideoServiceIntent = new Intent(this, MainService.class);
        startService(bgVideoServiceIntent);
        bindService(bgVideoServiceIntent, mConnection, Context.BIND_AUTO_CREATE);



    }




}