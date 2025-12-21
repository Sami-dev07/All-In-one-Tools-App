package com.alltools.toolbox.utility.calculator.EssentialTools.SpeedMeter;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.GnssStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.SystemClock;
import android.provider.Settings;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.util.Log;
import android.view.View;
import android.widget.Chronometer;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.preference.PreferenceManager;

import com.alltools.toolbox.utility.calculator.R;
import com.google.gson.Gson;
import com.melnykov.fab.FloatingActionButton;

import java.util.Locale;

public class SpeedMeterActivity extends AppCompatActivity implements LocationListener {

    private SharedPreferences sharedPreferences;
    private LocationManager mLocationManager;
    private static Data data;

    private FloatingActionButton fab;
    private FloatingActionButton refresh;
    private TextView satellite;
    private TextView status;
    private TextView accuracy;
    private TextView currentSpeed;
    private TextView maxSpeed;
    private TextView averageSpeed;
    private TextView distance;
    private Chronometer time;
    private Data.OnGpsServiceUpdate onGpsServiceUpdate;

    private boolean firstfix;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_speed_meter);

        data = new Data(onGpsServiceUpdate);

        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        fab = findViewById(R.id.fab);
        fab.setVisibility(View.INVISIBLE);

        refresh = findViewById(R.id.refresh);
        refresh.setVisibility(View.INVISIBLE);

        onGpsServiceUpdate = new Data.OnGpsServiceUpdate() {
            @Override
            public void update() {
                double maxSpeedTemp = data.getMaxSpeed();
                double distanceTemp = data.getDistance();
                double averageTemp;
                if (sharedPreferences.getBoolean("auto_average", false)) {
                    averageTemp = data.getAverageSpeedMotion();
                } else {
                    averageTemp = data.getAverageSpeed();
                }

                String speedUnits;
                String distanceUnits;
                if (sharedPreferences.getBoolean("miles_per_hour", false)) {
                    maxSpeedTemp *= 0.62137119;
                    distanceTemp = distanceTemp / 1000.0 * 0.62137119;
                    averageTemp *= 0.62137119;
                    speedUnits = "mi/h";
                    distanceUnits = "mi";
                } else {
                    speedUnits = "km/h";
                    if (distanceTemp <= 1000.0) {
                        distanceUnits = "m";
                    } else {
                        distanceTemp /= 1000.0;
                        distanceUnits = "km";
                    }
                }

                SpannableString s = new SpannableString(String.format("%.0f %s", maxSpeedTemp, speedUnits));
                s.setSpan(new RelativeSizeSpan(0.5f), s.length() - speedUnits.length() - 1, s.length(), 0);
                maxSpeed.setText(s);

                s = new SpannableString(String.format("%.0f %s", averageTemp, speedUnits));
                s.setSpan(new RelativeSizeSpan(0.5f), s.length() - speedUnits.length() - 1, s.length(), 0);
                averageSpeed.setText(s);

                s = new SpannableString(String.format("%.3f %s", distanceTemp, distanceUnits));
                s.setSpan(new RelativeSizeSpan(0.5f), s.length() - distanceUnits.length() - 1, s.length(), 0);
                distance.setText(s);
            }
        };

        mLocationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        satellite = findViewById(R.id.satellite);
        status = findViewById(R.id.status);
        accuracy = findViewById(R.id.accuracy);
        maxSpeed = findViewById(R.id.maxSpeed);
        averageSpeed = findViewById(R.id.averageSpeed);
        distance = findViewById(R.id.distance);
        time = findViewById(R.id.time);
        currentSpeed = findViewById(R.id.currentSpeed);

        time.setText("00:00:00");
        time.setOnChronometerTickListener(new Chronometer.OnChronometerTickListener() {
            boolean isPair = true;

            @Override
            public void onChronometerTick(Chronometer chrono) {
                long time;
                if (data.isRunning()) {
                    time = SystemClock.elapsedRealtime() - chrono.getBase();
                    data.setTime(time);
                } else {
                    time = data.getTime();
                }

                int h = (int) (time / 3600000);
                int m = (int) (time - h * 3600000) / 60000;
                int s = (int) (time - h * 3600000 - m * 60000) / 1000;
                String hh = h < 10 ? "0" + h : h + "";
                String mm = m < 10 ? "0" + m : m + "";
                String ss = s < 10 ? "0" + s : s + "";
                chrono.setText(hh + ":" + mm + ":" + ss);

                if (data.isRunning()) {
                    chrono.setText(hh + ":" + mm + ":" + ss);
                } else {
                    if (isPair) {
                        isPair = false;
                        chrono.setText(hh + ":" + mm + ":" + ss);
                    } else {
                        isPair = true;
                        chrono.setText("");
                    }
                }
            }
        });
    }

    public void onFabClick(View v) {
        if (!data.isRunning()) {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_pause));
            data.setRunning(true);
            time.setBase(SystemClock.elapsedRealtime() - data.getTime());
            time.start();
            data.setFirstTime(true);
            startService(new Intent(getBaseContext(), GpsServices.class));
            refresh.setVisibility(View.INVISIBLE);
        } else {
            fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
            data.setRunning(false);
            status.setText("");
            stopService(new Intent(getBaseContext(), GpsServices.class));
            refresh.setVisibility(View.VISIBLE);
        }
    }

    public void onRefreshClick(View v) {
        resetData();
        stopService(new Intent(getBaseContext(), GpsServices.class));
    }

    @Override
    protected void onResume() {
        super.onResume();
        firstfix = true;
        if (!data.isRunning()) {
            Gson gson = new Gson();
            String json = sharedPreferences.getString("data", "");
            data = gson.fromJson(json, Data.class);
        }
        if (data == null) {
            data = new Data(onGpsServiceUpdate);
        } else {
            data.setOnGpsServiceUpdate(onGpsServiceUpdate);
        }

        if (mLocationManager.getAllProviders().contains(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
            mLocationManager.registerGnssStatusCallback(gnssStatusCallback, null);
        } else {
            Log.w("MainActivity", "No GPS location provider found. GPS data display will not be available.");
        }

        if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            showGpsDisabledDialog();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocationManager.removeUpdates(this);
        mLocationManager.unregisterGnssStatusCallback(gnssStatusCallback);

        SharedPreferences.Editor prefsEditor = sharedPreferences.edit();
        Gson gson = new Gson();
        String json = gson.toJson(data);
        prefsEditor.putString("data", json);
        prefsEditor.commit();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        stopService(new Intent(getBaseContext(), GpsServices.class));
    }

    @Override
    public void onLocationChanged(Location location) {
        if (location.hasAccuracy()) {
            double acc = location.getAccuracy();
            String units;
            if (sharedPreferences.getBoolean("miles_per_hour", false)) {
                units = "ft";
                acc *= 3.28084;
            } else {
                units = "m";
            }
            SpannableString s = new SpannableString(String.format("%.0f %s", acc, units));
            s.setSpan(new RelativeSizeSpan(0.75f), s.length() - units.length() - 1, s.length(), 0);
            accuracy.setText(s);

            if (firstfix) {
                status.setText("");
                fab.setVisibility(View.VISIBLE);
                if (!data.isRunning() && !TextUtils.isEmpty(maxSpeed.getText())) {
                    refresh.setVisibility(View.VISIBLE);
                }
                firstfix = false;
            }
        }
        if (location.hasSpeed()) {
            double speed = location.getSpeed();
            String units;
            if (sharedPreferences.getBoolean("miles_per_hour", false)) {
                speed *= 2.23693629;
                units = "mi/h";
            } else {
                speed *= 3.6;
                units = "km/h";
            }
            SpannableString s = new SpannableString(String.format(Locale.ENGLISH, "%.0f %s", speed, units));
            s.setSpan(new RelativeSizeSpan(0.25f), s.length() - units.length() - 1, s.length(), 0);
            currentSpeed.setText(s);
        }
    }

    @Override
    public void onProviderDisabled(@NonNull String provider) {
        if (LocationManager.GPS_PROVIDER.equals(provider)) {
            showGpsDisabledDialog();
        }
    }

    @Override
    public void onProviderEnabled(@NonNull String provider) {
        if (LocationManager.GPS_PROVIDER.equals(provider)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            mLocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 500, 0, this);
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
    }
    public static Data getData() {
        return data;
    }
    private void resetData() {
        fab.setVisibility(View.INVISIBLE);
        refresh.setVisibility(View.INVISIBLE);
        status.setText(getResources().getString(R.string.waiting_for_fix));
        firstfix = true;
        data = new Data(onGpsServiceUpdate);
        time.stop();
        time.setText("00:00:00");
        currentSpeed.setText("0");
        accuracy.setText("");
        maxSpeed.setText("0.0");
        averageSpeed.setText("0.0");
        distance.setText("0.000");
        satellite.setText("");
    }

    private void showGpsDisabledDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("R.string.enable_gps")
                .setMessage("R.string.enable_gps_message")
                .setPositiveButton("R.string.enable_gps", (dialog, id) -> startActivity(new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)))
                .setNegativeButton("R.string.cancel", (dialog, id) -> dialog.cancel());
        builder.create().show();
    }

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

            satellite.setText(satsUsed + "/" + satsInView);
            if (satsUsed == 0) {
                fab.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_play));
                data.setRunning(false);
                stopService(new Intent(getBaseContext(), GpsServices.class));
                fab.setVisibility(View.INVISIBLE);
                refresh.setVisibility(View.INVISIBLE);
                accuracy.setText("");
                firstfix = true;
            }
        }

        @Override
        public void onStarted() {
        }

        @Override
        public void onStopped() {
            if (!mLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
                showGpsDisabledDialog();
            }
        }

        @Override
        public void onFirstFix(int ttffMillis) {
        }
    };
}
