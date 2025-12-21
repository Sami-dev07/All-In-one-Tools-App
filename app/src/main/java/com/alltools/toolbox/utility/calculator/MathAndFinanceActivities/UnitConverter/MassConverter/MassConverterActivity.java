package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.MassConverter;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.alltools.toolbox.utility.calculator.R;

public class MassConverterActivity extends AppCompatActivity {
    private static final String[] ITEMS_MASS = {"mg", "g", "kg", "oz", "lb"};
    private Spinner inputSpinner, outputSpinner;
    private EditText inputValue;
    private TextView resultTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mass_converter);

        inputSpinner = findViewById(R.id.input_unit_spinner);
        outputSpinner = findViewById(R.id.output_unit_spinner);
        inputValue = findViewById(R.id.input_value);
        resultTextView = findViewById(R.id.result_textview);
        Button convertButton = findViewById(R.id.convert_button);

        // Set up Spinners with the mass units
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ITEMS_MASS);
        inputSpinner.setAdapter(adapter);
        outputSpinner.setAdapter(adapter);

       
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertMass();
            }
        });
    }

    
    private void convertMass() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(MassConverterActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }

        double inputMass = Double.parseDouble(input);
        String fromUnit = inputSpinner.getSelectedItem().toString();
        String toUnit = outputSpinner.getSelectedItem().toString();

        double result = convertUnits(inputMass, fromUnit, toUnit);
        resultTextView.setText("Result: " + result + " " + toUnit);
    }

   
    private double convertUnits(double value, String fromUnit, String toUnit) {
        // Convert the input value to grams first (base unit)
        double valueInGrams = toGrams(value, fromUnit);

        // Then convert from grams to the target unit
        return fromGrams(valueInGrams, toUnit);
    }

    // Convert from any unit to grams
    private double toGrams(double value, String unit) {
        switch (unit) {
            case "mg": return value / 1000;
            case "g": return value;
            case "kg": return value * 1000;
            case "oz": return value * 28.3495;
            case "lb": return value * 453.592;
            default: return value;
        }
    }

    // Convert from grams to the target unit
    private double fromGrams(double value, String unit) {
        switch (unit) {
            case "mg": return value * 1000;
            case "g": return value;
            case "kg": return value / 1000;
            case "oz": return value / 28.3495;
            case "lb": return value / 453.592;
            default: return value;
        }
    }
}