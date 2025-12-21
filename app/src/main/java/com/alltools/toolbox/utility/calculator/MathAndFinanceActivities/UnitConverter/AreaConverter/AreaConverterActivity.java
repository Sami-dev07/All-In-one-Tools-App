package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.UnitConverter.AreaConverter;

import android.annotation.SuppressLint;
import android.icu.math.BigDecimal;
import android.icu.number.NumberFormatter;
import android.icu.number.Precision;
import android.icu.text.NumberFormat;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListPopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.alltools.toolbox.utility.calculator.R;
import com.google.android.material.button.MaterialButton;

import java.util.HashMap;
import java.util.Locale;

public class AreaConverterActivity extends AppCompatActivity {

    private Spinner spinnerFrom, spinnerTo;
    private EditText inputArea;
    private TextView result;
    private Button convertButton;

    private HashMap<String, Double> conversionRates;

    private static final String[] AREA_UNITS = {"km²", "m²", "dm²", "cm²", "mm²", "ft²", "yd²", "in²", "mi²", "acre"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_converter);
        spinnerFrom = findViewById(R.id.spinnerFrom);
        spinnerTo = findViewById(R.id.spinnerTo);
        inputArea = findViewById(R.id.inputArea);
        result = findViewById(R.id.result);
        convertButton = findViewById(R.id.convertButton);

        conversionRates = new HashMap<>();
        conversionRates.put("km²", 1e6);
        conversionRates.put("m²", 1.0);
        conversionRates.put("dm²", 0.01);
        conversionRates.put("cm²", 0.0001);
        conversionRates.put("mm²", 1e-6);
        conversionRates.put("ft²", 0.092903);
        conversionRates.put("yd²", 0.836127);
        conversionRates.put("in²", 0.00064516);
        conversionRates.put("mi²", 2.59e6);
        conversionRates.put("acre", 4046.86);

        convertButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertArea();
            }
        });
    }

    private void convertArea() {
        String fromUnit = spinnerFrom.getSelectedItem().toString();
        String toUnit = spinnerTo.getSelectedItem().toString();
        String input = inputArea.getText().toString();

        if (!input.isEmpty()) {
            double inputValue = Double.parseDouble(input);

            // Convert the input to m²
            double valueInSquareMeters = inputValue * conversionRates.get(fromUnit);

            // Convert from m² to the target unit
            double convertedValue = valueInSquareMeters / conversionRates.get(toUnit);

            result.setText(String.format("%.6f %s", convertedValue, toUnit));
        } else {
            result.setText("Please enter an area value.");
        }
    }
}
