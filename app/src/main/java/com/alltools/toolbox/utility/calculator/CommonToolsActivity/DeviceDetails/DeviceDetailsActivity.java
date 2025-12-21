package com.alltools.toolbox.utility.calculator.CommonToolsActivity.DeviceDetails;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;

import com.alltools.toolbox.utility.calculator.R;
import com.alltools.toolbox.utility.calculator.databinding.ActivityDeviceDetailsBinding;

import java.util.Locale;

public class DeviceDetailsActivity extends AppCompatActivity {

    ActivityDeviceDetailsBinding binding;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDeviceDetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

//        DeviceDetailsUtils.getDeviceDetails();


        String androidVersion = Build.VERSION.RELEASE;
        String deviceName = Build.MODEL;
        String manufacturer = Build.MANUFACTURER;
        String version = Build.DISPLAY;
        String product = Build.PRODUCT;
        String board = Build.BOARD;
        String hardware = Build.HARDWARE;
        int apiLevel = Build.VERSION.SDK_INT;
        String language = Locale.getDefault().getLanguage();


        binding.deviceAndroidVersion.setText(androidVersion);
        binding.deviceName.setText(deviceName);
        binding.deviceManufacture.setText(manufacturer);
        binding.deviceVersion.setText(androidVersion);
        binding.deviceProduct.setText(product);
        binding.deviceBoard.setText(board);
        binding.deviceHardware.setText(hardware);
        binding.deviceApiLevel.setText(""+Build.VERSION.SDK_INT);
        binding.deviceLanguage.setText(language);




    }
}