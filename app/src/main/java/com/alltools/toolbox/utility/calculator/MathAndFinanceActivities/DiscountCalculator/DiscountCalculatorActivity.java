package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.DiscountCalculator;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.alltools.toolbox.utility.calculator.R;

public class DiscountCalculatorActivity extends AppCompatActivity {
    private EditText etOriginalPrice;
    private EditText etDiscountPercentage;
    private Button btnCalculate;
    private TextView tvResult;    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discount_calculator);

        etOriginalPrice = findViewById(R.id.etOriginalPrice);
        etDiscountPercentage = findViewById(R.id.etDiscountPercentage);
        btnCalculate = findViewById(R.id.btnCalculate);
        tvResult = findViewById(R.id.tvResult);

        // Set up button click listener
        btnCalculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDiscount();
            }
        });
    }

    private void calculateDiscount() {
        // Get user input
        String originalPriceStr = etOriginalPrice.getText().toString();
        String discountPercentageStr = etDiscountPercentage.getText().toString();

        // Check for empty input
        if (originalPriceStr.isEmpty() || discountPercentageStr.isEmpty()) {
            tvResult.setText("Please enter both fields.");
            return;
        }

        // Parse input to double
        double originalPrice = Double.parseDouble(originalPriceStr);
        double discountPercentage = Double.parseDouble(discountPercentageStr);

        // Calculate discounted price
        double discountAmount = originalPrice * (discountPercentage / 100);
        double discountedPrice = originalPrice - discountAmount;

        // Display result
        tvResult.setText(String.format("Discounted Price: %.2f", discountedPrice));
    }
}