package com.alltools.toolbox.utility.calculator.EssentialTools.Compass;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.alltools.toolbox.utility.calculator.R;

public class CompassActivity extends AppCompatActivity implements SensorEventListener {
    private ImageView imageViewCompass;
    private SensorManager sensorManager;
    private Sensor rotationVectorSensor;
    private float[] rotationMatrix = new float[9];
    private float[] orientation = new float[3];

    TextView directionTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compass);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        imageViewCompass = findViewById(R.id.imageViewCompass);

        directionTextView = findViewById(R.id.directionTextView);


        sensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        rotationVectorSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (rotationVectorSensor != null) {
            sensorManager.registerListener(this, rotationVectorSensor, SensorManager.SENSOR_DELAY_UI);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ROTATION_VECTOR) {
            SensorManager.getRotationMatrixFromVector(rotationMatrix, event.values);
            SensorManager.getOrientation(rotationMatrix, orientation);

            float azimuth = (float) Math.toDegrees(orientation[0]); // orientation contains: azimuth, pitch, and roll
            azimuth = (azimuth + 360) % 360;

            imageViewCompass.setRotation(-azimuth);
            String direction = getDirectionFromDegree(azimuth);
            directionTextView.setText(String.format("%.0f° %s", azimuth, direction));

        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        // Not used
    }


    private String getDirectionFromDegree(float degree) {
        if (degree >= 337.5 || degree < 22.5) {
            return "North";
        } else if (degree >= 22.5 && degree < 67.5) {
            return "North East";
        } else if (degree >= 67.5 && degree < 112.5) {
            return "East";
        } else if (degree >= 112.5 && degree < 157.5) {
            return "South East";
        } else if (degree >= 157.5 && degree < 202.5) {
            return "South";
        } else if (degree >= 202.5 && degree < 247.5) {
            return "South West";
        } else if (degree >= 247.5 && degree < 292.5) {
            return "West";
        } else if (degree >= 292.5 && degree < 337.5) {
            return "North West";
        }
        return "";
    }
}