package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.StorageConverter;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import com.alltools.toolbox.utility.calculator.R;

public class StorageConverterActivity extends AppCompatActivity {

    private static final String[] ITEMS_STORAGE = {"bit", "B", "KB", "KiB", "MB", "MiB", "GB", "GiB", "TB", "TiB"};
    private Spinner inputSpinner, outputSpinner;
    private EditText inputValue;
    private TextView resultTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_storage_converter);
        inputSpinner = findViewById(R.id.input_unit_spinner);
        outputSpinner = findViewById(R.id.output_unit_spinner);
        inputValue = findViewById(R.id.input_value);
        resultTextView = findViewById(R.id.result_textview);
        Button convertButton = findViewById(R.id.convert_button);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ITEMS_STORAGE);
        inputSpinner.setAdapter(adapter);
        outputSpinner.setAdapter(adapter);

       
        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertStorage();
            }
        });
    }

    
    private void convertStorage() {
        String input = inputValue.getText().toString();
        if (input.isEmpty()) {
            Toast.makeText(StorageConverterActivity.this, "Please enter a value", Toast.LENGTH_SHORT).show();
            return;
        }
        double inputStorage = Double.parseDouble(input);
        String fromUnit = inputSpinner.getSelectedItem().toString();
        String toUnit = outputSpinner.getSelectedItem().toString();
        double result = convertUnits(inputStorage, fromUnit, toUnit);
        resultTextView.setText("Result: " + result + " " + toUnit);
    }

   
    private double convertUnits(double value, String fromUnit, String toUnit) {
        double valueInBits = toBits(value, fromUnit);
        return fromBits(valueInBits, toUnit);
    }

    private double toBits(double value, String unit) {
        return switch (unit) {
            case "bit" -> value;
            case "B" -> value * 8;
            case "KB" -> value * 8000;
            case "KiB" -> value * 8192;
            case "MB" -> value * 8000000;
            case "MiB" -> value * 8388608;
            case "GB" -> value * 8000000000L;
            case "GiB" -> value * 8589934592L;
            case "TB" -> value * 8000000000000L;
            case "TiB" -> value * 8796093022208L;
            default -> value;
        };
    }


    private double fromBits(double value, String unit) {
        return switch (unit) {
            case "B" -> value / 8;
            case "KB" -> value / 8000;
            case "KiB" -> value / 8192;
            case "MB" -> value / 8000000;
            case "MiB" -> value / 8388608;
            case "GB" -> value / 8000000000L;
            case "GiB" -> value / 8589934592L;
            case "TB" -> value / 8000000000000L;
            case "TiB" -> value / 8796093022208L;
            default -> value;
        };
    }
}