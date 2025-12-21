package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.FuelCost;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alltools.toolbox.utility.calculator.R;

public class FuelCostActivity extends AppCompatActivity {
    private EditText distanceInput;
    private EditText fuelPriceInput;
    private EditText fuelEfficiencyInput;
    private TextView resultText;
    private Button calculateButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fuel_cost);
        distanceInput = findViewById(R.id.distanceInput);
        fuelPriceInput = findViewById(R.id.fuelPriceInput);
        fuelEfficiencyInput = findViewById(R.id.fuelEfficiencyInput);
        resultText = findViewById(R.id.resultText);
        calculateButton = findViewById(R.id.calculateButton);

        calculateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateFuelCost();
            }
        });
    }
    private void calculateFuelCost() {
        String distanceStr = distanceInput.getText().toString();
        String fuelPriceStr = fuelPriceInput.getText().toString();
        String fuelEfficiencyStr = fuelEfficiencyInput.getText().toString();

        if (distanceStr.isEmpty() || fuelPriceStr.isEmpty() || fuelEfficiencyStr.isEmpty()) {
            resultText.setText("Please enter all values.");
            return;
        }

        double distance = Double.parseDouble(distanceStr);
        double fuelPrice = Double.parseDouble(fuelPriceStr);
        double fuelEfficiency = Double.parseDouble(fuelEfficiencyStr);

        // Calculate fuel cost
        double fuelNeeded = distance / fuelEfficiency;
        double totalCost = fuelNeeded * fuelPrice;

        resultText.setText(String.format("Total Fuel Cost: $%.2f", totalCost));
    }
}