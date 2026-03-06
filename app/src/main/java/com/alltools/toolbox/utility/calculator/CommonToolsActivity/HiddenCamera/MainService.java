package com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.media.CamcorderProfile;
import android.media.MediaRecorder;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Binder;
import android.os.Build;
import android.os.Environment;
import android.os.IBinder;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.TextureView;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;


import com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.Helper.CameraHelper;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.Helper.PauseActionBroadcast;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.Helper.SharePref;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.Helper.Utils;
import com.alltools.toolbox.utility.calculator.R;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

public class MainService extends Service implements TextureView.SurfaceTextureListener {
    private static final int NOTIFICATION_ID = 123;

    SharePref sharePref;

    private WindowManager windowManager;
    private static final String TAG = "RecorderService";

    private Camera mCamera = null;
    private TextureView mPreview;
    private MediaRecorder mMediaRecorder = null;
    private Uri outputFileDescriptor = null;
    private int quality;

    public static final String CHANNEL_ID = "my_channel";
    private boolean isSurfaceCreated = false;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onCreate() {
        Log.e(TAG, "START Creating Background Recorder Service");
        sharePref = new SharePref(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel();
        }
        if (Utils.isForeground){
            Notification notification = createNotification();
            startForeground(NOTIFICATION_ID, notification);
            Utils.isForeground = false;
        }
        windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        mPreview = new TextureView(this);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);

        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(
                1,
                1,
                WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE |
                        WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE |
                        WindowManager.LayoutParams.FLAG_LAYOUT_IN_SCREEN |
                        WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS |
                        WindowManager.LayoutParams.FLAG_FULLSCREEN,
                PixelFormat.TRANSLUCENT
        );
        layoutParams.gravity = Gravity.CENTER;


        windowManager.addView(mPreview, layoutParams);
        mPreview.setSurfaceTextureListener(this);
        Log.e(TAG, "FINISH Creating Background Recorder Service");
    }


    private Notification createNotification() {

        Intent noIntent = new Intent(this, PauseActionBroadcast.class);
        noIntent.setAction("NO_ACTION");
        PendingIntent noPendingIntent = PendingIntent.getBroadcast(this, 0, noIntent, PendingIntent.FLAG_IMMUTABLE);

        NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("All in one Tools")
                .setContentText("Foreground Service Is Running for Recording")
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .addAction(R.drawable.pause_btn, "No", noPendingIntent)
                .setAutoCancel(true);

        return notificationBuilder.build();
    }





    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Custom Notification Channel";
            String description = "Channel for custom notifications";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    @Override
    public void onSurfaceTextureAvailable(@NonNull SurfaceTexture surface, int width, int height) {
        Log.e(TAG, "START surfaceCreated handler: about to prepare MediaRecorder");
        isSurfaceCreated = true;
        if (outputFileDescriptor != null)
            new MediaPrepareTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }

    @Override
    public void onSurfaceTextureSizeChanged(@NonNull SurfaceTexture surface, int width, int height) {
    }

    @Override
    public boolean onSurfaceTextureDestroyed(@NonNull SurfaceTexture surface) {
        // dummy so far
        return true;
    }

    @Override
    public void onSurfaceTextureUpdated(@NonNull SurfaceTexture surface) {
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "About to destroy");
        if (mMediaRecorder != null) {
            mMediaRecorder.stop();
            releaseMediaRecorder();
            outputFileDescriptor = null;
        }

        if (mCamera != null) {
            mCamera.lock();
            releaseCamera();
        }

        windowManager.removeView(mPreview);
        isSurfaceCreated = false;
    }


    private boolean prepareVideoRecorder() {
        Log.e(TAG, "START prepareVideoRecorder");
        try {
//
            if (sharePref.getSharedTextValue("camera", "back").equals("back")){
                mCamera = Camera.open();
            }else{
                mCamera = openFrontFacingCamera();
            }


//            mCamera = CameraHelper.getDefaultCameraInstance();
        } catch (RuntimeException e) {
            Log.e("Get Camera error", "" + e.getMessage());
            return false;
        }
        Camera.Parameters parameters = mCamera.getParameters();
        List<Camera.Size> mSupportedPreviewSizes = parameters.getSupportedPreviewSizes();
        CameraHelper.getOptimalPreviewSize(mSupportedPreviewSizes,
                mPreview.getWidth(), mPreview.getHeight());


        CamcorderProfile profile = CamcorderProfile.get(quality);

        parameters.setPreviewSize(profile.videoFrameWidth, profile.videoFrameHeight);

        parameters.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);

//        mCamera.setParameters(parameters);
        try {
            mCamera.setPreviewTexture(mPreview.getSurfaceTexture());
        } catch (IOException e) {
            Log.e(TAG, "Surface texture is unavailable or unsuitable" + e.getMessage());
            return false;
        }
        mMediaRecorder = new MediaRecorder();

        if (sharePref.getSharedTextValue("camera", "back").equals("back")){
            mMediaRecorder.setOrientationHint(90);
        }else{
            mMediaRecorder.setOrientationHint(270);

        }

        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
//        if (!sharePref.getBooleanValue("mute")){
            mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);
//
//        }

        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);
        mMediaRecorder.setProfile(profile);

        createAndStartRecording(CameraHelper.getOutputMediaFileName());
        try {
            ParcelFileDescriptor pfd = getContentResolver().openFileDescriptor(outputFileDescriptor, "w");
            Log.e("Get Media File", "" + pfd);
            if (pfd != null) {
                FileDescriptor fd = pfd.getFileDescriptor();
                mMediaRecorder.setOutputFile(fd);
            } else {
                Log.e(TAG, "Failed to obtain FileDescriptor from Uri");
                return false;
            }
        } catch (FileNotFoundException e) {
            Log.e(TAG, "File not found: " + e.getMessage());
            return false;
        }
        Log.e(TAG, "MediaRecorder successfully configured, now prepare it");
        try {
            mMediaRecorder.prepare();
        } catch (IllegalStateException e) {
            Log.e(TAG, "IllegalStateException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        } catch (IOException e) {
            Log.e(TAG, "IOException preparing MediaRecorder: " + e.getMessage());
            releaseMediaRecorder();
            return false;
        }
        return true;
    }

    @SuppressLint("StaticFieldLeak")
    class MediaPrepareTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            if (prepareVideoRecorder()) {
                Log.e(TAG, "Starting record");
                mMediaRecorder.start();
            } else {
                releaseMediaRecorder();
                return false;
            }
            return true;
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }
    }

    private void releaseMediaRecorder() {
        if (mMediaRecorder != null) {
            mMediaRecorder.reset();
            mMediaRecorder.release();
            mMediaRecorder = null;
        }
    }

    private void releaseCamera() {
        if (mCamera != null) {
            mCamera.release();
            mCamera = null;
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.e(TAG, "Binding a client");
        return mBinder;
    }

    private final LocalBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        public MainService getService() {
            return MainService.this;
        }


    }


    public void startRecord(int desiredQuality) {
        Log.e("Get Videos File", " location: " + outputFileDescriptor);
        quality = desiredQuality;
        if (isSurfaceCreated)
            new MediaPrepareTask().executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR);
    }


    private void createAndStartRecording(String fileName) {
        // ...

        File moviesDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_MOVIES);

        String folderName = getString(R.string.movie_folder_name);
        File appFolder = new File(moviesDirectory, folderName);

        if (!appFolder.exists()) {
            if (appFolder.mkdirs()) {
                File outputFile = new File(appFolder, fileName);


                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    ContentValues values = new ContentValues();
                    values.put(MediaStore.Video.Media.DISPLAY_NAME, fileName);
                    values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
                    values.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + "/" + folderName);

                    outputFileDescriptor = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
                } else {
                    outputFileDescriptor = Uri.fromFile(outputFile);
                }


            } else {
                Toast.makeText(this, "Failed to create app folder.", Toast.LENGTH_SHORT).show();
            }
        } else {
            File outputFile = new File(appFolder, fileName);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Video.Media.DISPLAY_NAME, fileName);
                values.put(MediaStore.Video.Media.MIME_TYPE, "video/mp4");
                values.put(MediaStore.Video.Media.RELATIVE_PATH, Environment.DIRECTORY_MOVIES + "/" + folderName);

                outputFileDescriptor = getContentResolver().insert(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                outputFileDescriptor = Uri.fromFile(outputFile);
            }


        }
    }


    @SuppressLint("WrongConstant")
    private Camera openFrontFacingCamera() {
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        int numberOfCameras = Camera.getNumberOfCameras();
        for (int i = 0; i < numberOfCameras; i++) {
            Camera.getCameraInfo(i, cameraInfo);
            if (cameraInfo.facing == 1) {
                try {
                    this.mCamera = Camera.open(i);
                } catch (RuntimeException e) {
                    stopSelf();
                }
            }
        }
        return this.mCamera;
    }

}