package com.alltools.toolbox.utility.calculator.EssentialTools.SpeedMeter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;

import com.alltools.toolbox.utility.calculator.R;

public class GpsServices extends Service implements LocationListener {

    private LocationManager mLocationManager;
    private Location lastlocation = new Location("last");
    private Data data;

    private double currentLon = 0;
    private double currentLat = 0;
    private double lastLon = 0;
    private double lastLat = 0;

    private PendingIntent contentIntent;

    private final GnssStatus.Callback gnssStatusCallback = new GnssStatus.Callback() {
        @Override
        public void onSatelliteStatusChanged(GnssStatus status) {
            int satsInView = status.getSatelliteCount();
            int satsUsed = 0;

            for (int i = 0; i < satsInView; i++) {
                if (status.usedInFix(i)) {
                    satsUsed++;
                }
            }
            Log.d("GpsServices", "Satellites in view: " + satsInView + ", used: " + satsUsed);
        }
    };

    @Override
    public void onCreate() {
        Intent notificationIntent = new Intent(this, SpeedMeterActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        contentIntent = PendingIntent.getActivity(
                this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE);

        updateNotification(false);

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Request necessary permissions
            return;
        }
        mLocationManager.registerGnssStatusCallback(gnssStatusCallback, null);
        mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
    }

    @Override
    public void onLocationChanged(@NonNull Location location) {
        data = SpeedMeterActivity.getData();
        if (data.isRunning()) {
            currentLat = location.getLatitude();
            currentLon = location.getLongitude();

            if (data.isFirstTime()) {
                lastLat = currentLat;
                lastLon = currentLon;
                data.setFirstTime(false);
            }

            lastlocation.setLatitude(lastLat);
            lastlocation.setLongitude(lastLon);
            double distance = lastlocation.distanceTo(location);

            if (location.getAccuracy() < distance) {
                data.addDistance(distance);

                lastLat = currentLat;
                lastLon = currentLon;
            }

            if (location.hasSpeed()) {
                data.setCurSpeed(location.getSpeed() * 3.6);
                if (location.getSpeed() == 0) {
                    new isStillStopped().execute();
                }
            }
            data.update();
            updateNotification(true);
        }
    }

    @SuppressLint("StringFormatMatches")
    public void updateNotification(boolean asData) {
        String channelId = "gps_service_channel";
        Notification.Builder builder;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    "GPS Service Channel",
                    NotificationManager.IMPORTANCE_LOW
            );
            channel.setDescription("Channel for GPS service");
            NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
            builder = new Notification.Builder(getBaseContext(), channelId);
        } else {
            builder = new Notification.Builder(getBaseContext());
        }

        builder.setContentTitle(getString(R.string.running))
                .setSmallIcon(R.drawable.ic_notification)
                .setContentIntent(contentIntent);

        if (asData) {
            builder.setContentText(String.format(getString(R.string.notification), data.getMaxSpeed(), data.getDistance()));
        } else {
            builder.setContentText(String.format(getString(R.string.notification), '-', '-'));
        }

        Notification notification = builder.build();
        startForeground(R.string.noti_id, notification);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return START_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onDestroy() {
        mLocationManager.removeUpdates(this);
        mLocationManager.unregisterGnssStatusCallback(gnssStatusCallback);
        stopForeground(true);
    }

    @Override
    public void onProviderDisabled(String provider) {
    }

    @Override
    public void onProviderEnabled(String provider) {
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }

    class isStillStopped extends AsyncTask<Void, Integer, String> {
        int timer = 0;

        @Override
        protected String doInBackground(Void... unused) {
            try {
                while (data.getCurSpeed() == 0) {
                    Thread.sleep(1000);
                    timer++;
                }
            } catch (InterruptedException t) {
                return ("The sleep operation failed");
            }
            return ("return object when task is finished");
        }

        @Override
        protected void onPostExecute(String message) {
            data.setTimeStopped(timer);
        }
    }
}
