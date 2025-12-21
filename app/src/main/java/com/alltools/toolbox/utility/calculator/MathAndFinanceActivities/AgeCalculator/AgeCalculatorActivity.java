package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.AgeCalculator;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.R;

import java.util.Calendar;

public class AgeCalculatorActivity extends AppCompatActivity {
    private ImageView buttonSelectBirthDate, buttonSelectTillDate;
    private CardView buttonCalculateAge;
    private TextView textViewBirthDate, textViewTillDate;
    private Calendar birthDate, tillDate;
    private TextView mYears, mMonth, mDays;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_age_calculator);

        buttonSelectBirthDate = findViewById(R.id.buttonSelectBirthDate);
        buttonSelectTillDate = findViewById(R.id.buttonSelectTillDate);
        buttonCalculateAge = findViewById(R.id.buttonCalculateAge);
        textViewBirthDate = findViewById(R.id.textViewBirthDate);
        textViewTillDate = findViewById(R.id.textViewTillDate);

        mYears = findViewById(R.id.mYears);
        mMonth = findViewById(R.id.mMonths);
        mDays = findViewById(R.id.mDays);

        buttonSelectBirthDate.setOnClickListener(v -> showDatePickerDialog(true));

        buttonSelectTillDate.setOnClickListener(v -> showDatePickerDialog(false));

        buttonCalculateAge.setOnClickListener(v -> calculateAge());
    }

    private void showDatePickerDialog(final boolean isBirthDate) {
        final Calendar calendar = Calendar.getInstance();
        DatePickerDialog datePickerDialog = new DatePickerDialog(this,
                (view, year, month, dayOfMonth) -> {
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, month);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    if (isBirthDate) {
                        birthDate = calendar;
                        textViewBirthDate.setText("Birth Date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                    } else {
                        tillDate = calendar;
                        textViewTillDate.setText("Till Date: " + dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                },
                calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private void calculateAge() {
        if (birthDate == null || tillDate == null) {
            Toast.makeText(AgeCalculatorActivity.this, "Please Select both Dates.", Toast.LENGTH_SHORT).show();
            return;
        }

        int years = tillDate.get(Calendar.YEAR) - birthDate.get(Calendar.YEAR);
        int months = tillDate.get(Calendar.MONTH) - birthDate.get(Calendar.MONTH);
        int days = tillDate.get(Calendar.DAY_OF_MONTH) - birthDate.get(Calendar.DAY_OF_MONTH);

        if (days < 0) {
            months--;
            days += birthDate.getActualMaximum(Calendar.DAY_OF_MONTH);
        }

        if (months < 0) {
            years--;
            months += 12;
        }

        mYears.setText(String.valueOf(years));
        mMonth.setText(String.valueOf(months));
        mDays.setText(String.valueOf(days));
    }
}