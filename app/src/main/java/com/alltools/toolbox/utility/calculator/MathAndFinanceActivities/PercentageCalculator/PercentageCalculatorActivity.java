package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.PercentageCalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.R;

public class PercentageCalculatorActivity extends AppCompatActivity {

    EditText priceEditText, discountEditText;
    CardView buttonDiscount;
    TextView youSaved, priceAfterDiscount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_percentage_calculator);

        priceEditText = findViewById(R.id.priceEditText);
        discountEditText = findViewById(R.id.discountEditText);
        buttonDiscount = findViewById(R.id.buttonDiscount);
        youSaved = findViewById(R.id.youSaved);
        priceAfterDiscount = findViewById(R.id.priceAfterDiscount);

        buttonDiscount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calculateDiscount();
            }
        });
    }

    private void calculateDiscount() {
        String priceStr = priceEditText.getText().toString();
        String discountStr = discountEditText.getText().toString();

        if (priceStr.isEmpty() || discountStr.isEmpty()) {
            Toast.makeText(this, "Please enter both values", Toast.LENGTH_SHORT).show();
            return;
        }

        double price = Double.parseDouble(priceStr);
        double discount = Double.parseDouble(discountStr);

        double savedAmount = price * discount / 100;
        double finalPrice = price - savedAmount;

        youSaved.setText(String.valueOf(savedAmount));
        priceAfterDiscount.setText(String.valueOf(finalPrice));
    }
}