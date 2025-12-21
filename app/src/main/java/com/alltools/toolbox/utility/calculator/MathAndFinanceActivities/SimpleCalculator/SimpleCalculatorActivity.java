package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.SimpleCalculator;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.alltools.toolbox.utility.calculator.R;

public class SimpleCalculatorActivity extends AppCompatActivity {
    private AppCompatButton[] numberButtons;
    private AppCompatButton bEqual, bMultiply, bDivide, bAdd, bSubtract, bClear, bDot, bPara1, bPara2;
    private TextView tInput, tOutput;

    private final char ADDITION = '+';
    private final char SUBTRACTION = '-';
    private final char MULTIPLICATION = '*';
    private final char DIVISION = '/';
    private final char EQUALS = '=';
    private final char MODULUS = '%';

    private char action;
    private double value1 = Double.NaN;
    private double value2;
    Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_calculator);


        animation = AnimationUtils.loadAnimation(this, R.anim.press_animation);


        initializeViews();
        setNumberButtonListeners();
        setOperatorButtonListeners();


        findViewById(R.id.buttonBack).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                v.startAnimation(animation);
                String currentInput = tInput.getText().toString();
                if (!currentInput.isEmpty()) {
                    tInput.setText(currentInput.substring(0, currentInput.length() - 1));
                }
            }
        });

    }

    private void initializeViews() {
        tInput = findViewById(R.id.input);
        tOutput = findViewById(R.id.output);

        numberButtons = new AppCompatButton[]{
                findViewById(R.id.button0), findViewById(R.id.button1), findViewById(R.id.button2),
                findViewById(R.id.button3), findViewById(R.id.button4), findViewById(R.id.button5),
                findViewById(R.id.button6), findViewById(R.id.button7), findViewById(R.id.button8),
                findViewById(R.id.button9)
        };

        bEqual = findViewById(R.id.button_equal);
        bMultiply = findViewById(R.id.button_multi);
        bDivide = findViewById(R.id.button_divide);
        bAdd = findViewById(R.id.button_add);
        bSubtract = findViewById(R.id.button_sub);
        bClear = findViewById(R.id.button_clear);
        bDot = findViewById(R.id.button_dot);
        bPara1 = findViewById(R.id.button_para1);
        bPara2 = findViewById(R.id.button_para2);
    }

    private void setNumberButtonListeners() {

        View.OnClickListener numberClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                handleNumberButtonClick(((AppCompatButton) view).getText().toString());
            }
        };

        for (AppCompatButton button : numberButtons) {
            button.setOnClickListener(numberClickListener);
        }

        bDot.setOnClickListener(numberClickListener);
    }

    private void handleNumberButtonClick(String number) {
        ifErrorOnOutput();
        exceedLength();
        tInput.setText(tInput.getText().toString() + number);
    }

    private void setOperatorButtonListeners() {
        bAdd.setOnClickListener(new OperatorClickListener(ADDITION));
        bSubtract.setOnClickListener(new OperatorClickListener(SUBTRACTION));
        bMultiply.setOnClickListener(new OperatorClickListener(MULTIPLICATION));
        bDivide.setOnClickListener(new OperatorClickListener(DIVISION));
        bPara1.setOnClickListener(new OperatorClickListener(MODULUS));

        bEqual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tInput.getText().length() > 0) {
                    performCalculation();
                    action = EQUALS;
                    displayResult();
                } else {
                    tOutput.setText("Error");
                }
            }
        });

        bClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                clearInput();
            }
        });

        bClear.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                view.startAnimation(animation);
                clearAll();
                return true;
            }
        });

        bPara2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.startAnimation(animation);
                handlePara2Click();
            }
        });
    }

    private void handlePara2Click() {
        if (!tOutput.getText().toString().isEmpty() || !tInput.getText().toString().isEmpty()) {
            try {
                value1 = Double.parseDouble(tInput.getText().toString());
                action = MODULUS;
                tOutput.setText("-" + tInput.getText().toString());
                tInput.setText("");
            } catch (NumberFormatException e) {
                tOutput.setText("Error");
            }
        } else {
            tOutput.setText("Error");
        }
    }

    private void performCalculation() {
        try {
            if (!Double.isNaN(value1)) {
                if (tOutput.getText().toString().startsWith("-")) {
                    value1 = -value1;
                }
                value2 = Double.parseDouble(tInput.getText().toString());
                switch (action) {
                    case ADDITION:
                        value1 += value2;
                        break;
                    case SUBTRACTION:
                        value1 -= value2;
                        break;
                    case MULTIPLICATION:
                        value1 *= value2;
                        break;
                    case DIVISION:
                        value1 /= value2;
                        break;
                    case MODULUS:
                        value1 %= value2;
                        break;
                }
            } else {
                value1 = Double.parseDouble(tInput.getText().toString());
            }
        } catch (NumberFormatException | ArithmeticException e) {
            tOutput.setText("Error");
        }
    }

    private void displayResult() {
        if (isInteger(value1)) {
            tOutput.setText(String.valueOf((int) value1));
        } else {
            tOutput.setText(String.valueOf(value1));
        }
        tInput.setText(null);
    }

    private boolean isInteger(double value) {
        return value == (int) value;
    }

    private void clearInput() {
        if (tInput.getText().length() > 0) {
            CharSequence name = tInput.getText().toString();
            tInput.setText(name.subSequence(0, name.length() - 1));
        } else {
            clearAll();
        }
    }

    private void clearAll() {
        value1 = Double.NaN;
        value2 = Double.NaN;
        tInput.setText("");
        tOutput.setText("");
    }

    private void ifErrorOnOutput() {
        if (tOutput.getText().toString().equals("Error")) {
            tOutput.setText("");
        }
    }

    private void exceedLength() {
        if (tInput.getText().toString().length() > 10) {
            tInput.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20);
        }
    }

    private class OperatorClickListener implements View.OnClickListener {
        private final char operator;

        OperatorClickListener(char operator) {
            this.operator = operator;
        }

        @Override
        public void onClick(View view) {
            view.startAnimation(animation);
            if (tInput.getText().length() > 0) {
                action = operator;
                performCalculation();
                displayOperator();
                tInput.setText(null);
            } else {
                tOutput.setText("Error");
            }
        }

        private void displayOperator() {
            if (isInteger(value1)) {
                tOutput.setText(String.valueOf((int) value1) + operator);
            } else {
                tOutput.setText(String.valueOf(value1) + operator);
            }
        }
    }
}