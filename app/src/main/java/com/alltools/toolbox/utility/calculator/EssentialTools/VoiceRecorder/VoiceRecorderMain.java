package com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.CommonToolsActivity.DeviceDetails.DeviceDetailsActivity;
import com.alltools.toolbox.utility.calculator.EssentialTools.VoiceRecorder.SavedRecords.SavedRecordingsActivity;
import com.alltools.toolbox.utility.calculator.R;

public class VoiceRecorderMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice_recorder_main);


        findViewById(R.id.voiceRecorder).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), VoiceRecorder.class));

            }
        });
        findViewById(R.id.savedRecordings).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), SavedRecordingsActivity.class));

            }
        });
    }
}