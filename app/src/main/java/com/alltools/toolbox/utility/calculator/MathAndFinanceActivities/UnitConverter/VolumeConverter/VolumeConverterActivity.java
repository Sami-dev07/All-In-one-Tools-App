package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.VolumeConverter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;


import com.alltools.toolbox.utility.calculator.R;

public class VolumeConverterActivity extends AppCompatActivity {
    private static final String[] ITEMS_VOLUME = {"L", "m³", "dm³", "cm³", "mL"};
    private Spinner inputSpinner, outputSpinner;
    private EditText inputValue;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_volume_converter);

        inputSpinner = findViewById(R.id.input_unit_spinner);
        outputSpinner = findViewById(R.id.output_unit_spinner);
        inputValue = findViewById(R.id.input_value);
        resultTextView = findViewById(R.id.result_textview);
        Button convertButton = findViewById(R.id.convert_button);

        // Set up Spinners with the volume units
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ITEMS_VOLUME);
        inputSpinner.setAdapter(adapter);
        outputSpinner.setAdapter(adapter);

       
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertVolume();
            }
        });
    }

    
    private void convertVolume() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(VolumeConverterActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputVolume = Double.parseDouble(input);
        String fromUnit = inputSpinner.getSelectedItem().toString();
        String toUnit = outputSpinner.getSelectedItem().toString();

        double result = convertUnits(inputVolume, fromUnit, toUnit);
        resultTextView.setText("Result: " + result + " " + toUnit);
    }

   
    private double convertUnits(double value, String fromUnit, String toUnit) {
        // Convert the input value to liters first (base unit)
        double valueInLiters = toLiters(value, fromUnit);

        // Then convert from liters to the target unit
        return fromLiters(valueInLiters, toUnit);
    }

    // Convert from any unit to liters
    private double toLiters(double value, String unit) {
        switch (unit) {
            case "m³": return value * 1000;
            case "dm³": return value;
            case "cm³": return value / 1000;
            case "mL": return value / 1000;
            case "L": return value;
            default: return value;
        }
    }

    // Convert from liters to the target unit
    private double fromLiters(double value, String unit) {
        switch (unit) {
            case "m³": return value / 1000;
            case "dm³": return value;
            case "cm³": return value * 1000;
            case "mL": return value * 1000;
            case "L": return value;
            default: return value;
        }
    }
}