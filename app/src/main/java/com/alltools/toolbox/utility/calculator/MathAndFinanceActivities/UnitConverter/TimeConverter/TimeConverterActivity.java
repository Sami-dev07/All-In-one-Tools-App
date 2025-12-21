package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.TimeConverter;

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

public class TimeConverterActivity extends AppCompatActivity {
    private static final String[] ITEMS_TIME = {"ms", "s", "min", "h", "d", "wk"};
    private Spinner inputSpinner, outputSpinner;
    private EditText inputValue;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_time_converter);
        inputSpinner = findViewById(R.id.input_unit_spinner);
        outputSpinner = findViewById(R.id.output_unit_spinner);
        inputValue = findViewById(R.id.input_value);
        resultTextView = findViewById(R.id.result_textview);
        Button convertButton = findViewById(R.id.convert_button);

        // Set up Spinners with the time units
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ITEMS_TIME);
        inputSpinner.setAdapter(adapter);
        outputSpinner.setAdapter(adapter);

       
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertTime();
            }
        });
    }

    
    private void convertTime() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(TimeConverterActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputTime = Double.parseDouble(input);
        String fromUnit = inputSpinner.getSelectedItem().toString();
        String toUnit = outputSpinner.getSelectedItem().toString();

        double result = convertUnits(inputTime, fromUnit, toUnit);
        resultTextView.setText("Result: " + result + " " + toUnit);
    }

   
    private double convertUnits(double value, String fromUnit, String toUnit) {
        // Convert the input value to seconds first (base unit)
        double valueInSeconds = toSeconds(value, fromUnit);

        // Then convert from seconds to the target unit
        return fromSeconds(valueInSeconds, toUnit);
    }

    // Convert from any unit to seconds
    private double toSeconds(double value, String unit) {
        switch (unit) {
            case "ms": return value / 1000;
            case "s": return value;
            case "min": return value * 60;
            case "h": return value * 3600;
            case "d": return value * 86400;
            case "wk": return value * 604800;
            default: return value;
        }
    }

    // Convert from seconds to the target unit
    private double fromSeconds(double value, String unit) {
        switch (unit) {
            case "ms": return value * 1000;
            case "s": return value;
            case "min": return value / 60;
            case "h": return value / 3600;
            case "d": return value / 86400;
            case "wk": return value / 604800;
            default: return value;
        }
    }

}