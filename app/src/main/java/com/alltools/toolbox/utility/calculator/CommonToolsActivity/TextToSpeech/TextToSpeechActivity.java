package com.alltools.toolbox.utility.calculator.CommonToolsActivity.TextToSpeech;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.alltools.toolbox.utility.calculator.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.lang.reflect.Field;
import java.util.Locale;

public class TextToSpeechActivity extends AppCompatActivity implements View.OnClickListener, TextToSpeech.OnInitListener {
    TextInputEditText enteredText;
    TextInputLayout tipEnteredText;
    private int MY_DATA_CHECK_CODE = 0;
    private TextToSpeech myTTS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_text_to_speech);
            FloatingActionButton floatingActionButton = findViewById(R.id.speak);
            this.enteredText = findViewById(R.id.enter);
            this.tipEnteredText = findViewById(R.id.tip_enter);
            try {
                Field declaredField = TextInputLayout.class.getDeclaredField("defaultStrokeColor");
                declaredField.setAccessible(true);
                declaredField.set(this.tipEnteredText, Integer.valueOf(ContextCompat.getColor(this, R.color.tools_edit_text_primary_color)));
            } catch (Exception e) {
                e.printStackTrace();
            }
            floatingActionButton.setOnClickListener(this);
            Intent intent = new Intent();
            intent.setAction("android.speech.tts.engine.CHECK_TTS_DATA");
            try {
                startActivityForResult(intent, this.MY_DATA_CHECK_CODE);
            } catch (Exception unused) {
                Toast.makeText(this, "Opps! Your device doesn't support Speech to Text", Toast.LENGTH_LONG).show();
            }

    }


    @Override
    public void onClick(View view) {
        speakWords(this.enteredText.getText().toString());
    }

    private void speakWords(String str) {
        try {
            this.myTTS.speak(str, 0, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int i, int i2, Intent intent) {
        super.onActivityResult(i, i2, intent);
        try {
            if (i == this.MY_DATA_CHECK_CODE) {
                if (i2 == 1) {
                    this.myTTS = new TextToSpeech(this, this);
                } else {
                    try {
                        Intent intent2 = new Intent();
                        intent2.setAction("android.speech.tts.engine.INSTALL_TTS_DATA");
                        startActivity(intent2);
                    } catch (Exception unused) {
                        finish();
                    }
                }
            }
        } catch (Exception e) {
            finish();
            e.printStackTrace();
        }
    }

    @Override
    public void onInit(int i) {
        if (i == 0) {
            if (this.myTTS.isLanguageAvailable(Locale.US) == 0) {
                this.myTTS.setLanguage(Locale.US);
            }
        } else if (i == -1) {
            Toast.makeText(this, "Sorry! Text To Speech failed...", Toast.LENGTH_LONG).show();
        }
    }

}