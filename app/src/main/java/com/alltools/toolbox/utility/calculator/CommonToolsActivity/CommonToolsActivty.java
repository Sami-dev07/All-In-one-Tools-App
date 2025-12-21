package com.alltools.toolbox.utility.calculator.CommonToolsActivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.alltools.toolbox.utility.calculator.CommonToolsActivity.DeviceDetails.DeviceDetailsActivity;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.HiddenCameraActivity;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.ImageCompressor.ImageResizerActivity;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.ImageToPDF.ImageToPdfActivity;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.PianoActivity.PianoActivity;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.TextToSpeech.TextToSpeechActivity;
import com.alltools.toolbox.utility.calculator.CommonToolsActivity.TodoList.TodoListMainActivity;
import com.alltools.toolbox.utility.calculator.R;
import com.alltools.toolbox.utility.calculator.databinding.ActivityCommonToolsActivtyBinding;

public class CommonToolsActivty extends AppCompatActivity {

    ActivityCommonToolsActivtyBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCommonToolsActivtyBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_animation);

        binding.backICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                onBackPressed();
            }
        });
        binding.speechTextICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick( View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), TextToSpeechActivity.class));

            }
        });
        binding.pianoICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), PianoActivity.class));
            }
        });
        binding.deviceInfoIcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), DeviceDetailsActivity.class));
            }
        });
        binding.imagePDFIcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), ImageToPdfActivity.class));

            }
        });
        binding.hiddenCameraIcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), HiddenCameraActivity.class));
            }
        });
        binding.compressImageICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                Intent intent = new Intent(CommonToolsActivty.this, ImageResizerActivity.class);
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                CommonToolsActivty.this.startActivity(intent);
            }
        });
        binding.todoICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                Intent intent = new Intent(CommonToolsActivty.this, TodoListMainActivity.class);
                startActivity(intent);
            }
        });
        binding.groceryIcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
            }
        });

    }
}