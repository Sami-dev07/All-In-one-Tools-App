package com.alltools.toolbox.utility.calculator.EssentialTools.FlashLight;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.camera2.CameraAccessException;
import android.hardware.camera2.CameraManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.alltools.toolbox.utility.calculator.R;

public class FlashlightActivity extends AppCompatActivity {

    ImageView flashLightOn;
    private boolean isFlashlightOn = false;
    private CameraManager cameraManager;
    private String cameraId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flashlight);

        flashLightOn = findViewById(R.id.flashLightOn);

        cameraManager = (CameraManager) getSystemService(Context.CAMERA_SERVICE);

        try {
            cameraId = cameraManager.getCameraIdList()[0];
        } catch (CameraAccessException e) {
            e.printStackTrace();
        }

        flashLightOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggleFlashlight();
            }
        });
    }

    private void toggleFlashlight() {
        if (getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH)) {
            try {
                if (isFlashlightOn) {
                    cameraManager.setTorchMode(cameraId, false);
                    flashLightOn.setImageResource(R.drawable.flash_off);
                    isFlashlightOn = false;
                } else {
                    cameraManager.setTorchMode(cameraId, true);
                    flashLightOn.setImageResource(R.drawable.flash_on);
                    isFlashlightOn = true;
                }
            } catch (CameraAccessException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this, "No flashlight available on this device", Toast.LENGTH_SHORT).show();
        }
    }
}