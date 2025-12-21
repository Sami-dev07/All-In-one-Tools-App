package com.alltools.toolbox.utility.calculator.EssentialTools;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

import com.alltools.toolbox.utility.calculator.EssentialTools.Compass.CompassActivity;
import com.alltools.toolbox.utility.calculator.EssentialTools.FlashLight.FlashlightActivity;
import com.alltools.toolbox.utility.calculator.EssentialTools.Location.LocationActivity;
import com.alltools.toolbox.utility.calculator.EssentialTools.Magnifier.MagnifierActivity;
import com.alltools.toolbox.utility.calculator.EssentialTools.MetalDetector.MetalDetectorActivity;
import com.alltools.toolbox.utility.calculator.EssentialTools.QRCode.QRCodeActivity;
import com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.VoiceRecorderMain;
import com.alltools.toolbox.utility.calculator.R;
import com.alltools.toolbox.utility.calculator.databinding.ActivityEssentialToolsBinding;

public class EssentialToolsActivity extends AppCompatActivity {

    ActivityEssentialToolsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEssentialToolsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.press_animation);

        binding.backICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                onBackPressed();
            }
        });

        binding.compassICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), CompassActivity.class));
            }
        });
        binding.flashlightICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), FlashlightActivity.class));
            }
        });
        binding.qrICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), QRCodeActivity.class));
            }
        });
        binding.magnifierICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), MagnifierActivity.class));

            }
        });
        binding.locationICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), LocationActivity.class));

            }
        });
        binding.easyNotesICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
//                startActivity(new Intent(getApplicationContext(), NotesListActivity.class));
            }
        });
        binding.metalICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), MetalDetectorActivity.class));

            }
        });
        binding.recorderICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                startActivity(new Intent(getApplicationContext(), VoiceRecorderMain.class));

            }
        });
        binding.interSpeedICC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
//                startActivity(new Intent(getApplicationContext(), MetronomeActivity.class));

            }
        });



    }
}