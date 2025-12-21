package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.AreaConverter.AreaConverterActivity;
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.LengthConverter.LengthConverterActivity;
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.MassConverter.MassConverterActivity;
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.TimeConverter.TimeConverterActivity;
import com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.VolumeConverter.VolumeConverterActivity;
import com.alltools.toolbox.utility.calculator.R;

public class UnitsActivityMain extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_units_main);



        findViewById(R.id.areaButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AreaConverterActivity.class));
            }
        });

        findViewById(R.id.lengthButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), LengthConverterActivity.class));

            }
        });
        findViewById(R.id.timeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), TimeConverterActivity.class));

            }
        });
        findViewById(R.id.massButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), MassConverterActivity.class));

            }
        });
        findViewById(R.id.volumeButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), VolumeConverterActivity.class));

            }
        });
        findViewById(R.id.storageButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), VolumeConverterActivity.class));

            }
        });


    }
}