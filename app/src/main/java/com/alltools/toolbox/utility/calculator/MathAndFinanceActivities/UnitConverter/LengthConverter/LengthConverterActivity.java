package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.LengthConverter;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.alltools.toolbox.utility.calculator.R;

public class LengthConverterActivity extends AppCompatActivity {
    private static final String[] ITEMS_DISTANCE = {"km", "m", "dm", "cm", "mm", "ft", "in", "yd", "mi", "NM"};
    private Spinner inputSpinner, outputSpinner;
    private EditText inputValue;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_length_converter);

        inputSpinner = findViewById(R.id.input_unit_spinner);
        outputSpinner = findViewById(R.id.output_unit_spinner);
        inputValue = findViewById(R.id.input_value);
        resultTextView = findViewById(R.id.result_textview);
        Button convertButton = findViewById(R.id.convert_button);

        // Set up Spinners with the distance units
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ITEMS_DISTANCE);
        inputSpinner.setAdapter(adapter);
        outputSpinner.setAdapter(adapter);

       
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertLength();
            }
        });
    }

    
    private void convertLength() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(LengthConverterActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputLength = Double.parseDouble(input);
        String fromUnit = inputSpinner.getSelectedItem().toString();
        String toUnit = outputSpinner.getSelectedItem().toString();

        double result = convertUnits(inputLength, fromUnit, toUnit);
        resultTextView.setText("Result: " + result + " " + toUnit);
    }

   
    private double convertUnits(double value, String fromUnit, String toUnit) {
        // Convert the input value to meters first (base unit)
        double valueInMeters = toMeters(value, fromUnit);

        // Then convert from meters to the target unit
        return fromMeters(valueInMeters, toUnit);
    }

    // Convert from any unit to meters
    private double toMeters(double value, String unit) {
        switch (unit) {
            case "km": return value * 1000;
            case "m": return value;
            case "dm": return value / 10;
            case "cm": return value / 100;
            case "mm": return value / 1000;
            case "ft": return value * 0.3048;
            case "in": return value * 0.0254;
            case "yd": return value * 0.9144;
            case "mi": return value * 1609.34;
            case "NM": return value * 1852;
            default: return value;
        }
    }

    // Convert from meters to the target unit
    private double fromMeters(double value, String unit) {
        switch (unit) {
            case "km": return value / 1000;
            case "m": return value;
            case "dm": return value * 10;
            case "cm": return value * 100;
            case "mm": return value * 1000;
            case "ft": return value / 0.3048;
            case "in": return value / 0.0254;
            case "yd": return value / 0.9144;
            case "mi": return value / 1609.34;
            case "NM": return value / 1852;
            default: return value;
        }
    }
}