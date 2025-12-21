package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.ScientificCalculator;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.alltools.toolbox.utility.calculator.R;

public class ScientificCalculatorActivity extends AppCompatActivity {
    AppCompatButton button1, button2, button3, button4, button5, button6, button7, button8, button9, button0, buttonDot, buttonPi, buttonEqual, buttonPlus, buttonMinus, buttonDivide, buttonMultiply, buttonSqrt, buttonSquare, buttonFactorial, buttonLn, buttonLog, buttonSin, buttonCos, buttonTan, buttonInverse, buttonClear, buttonBackspace, buttonLeftBracket, buttonRightBracket;
    TextView textViewSecondary, textViewMain;
    String piValue = "3.14159265";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scientific_calculator);

        initializeButtons();
        initializeTextViews();

        setOnClickListeners();
    }

    private void initializeButtons() {
        button1 = findViewById(R.id.button1);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        button4 = findViewById(R.id.button4);
        button5 = findViewById(R.id.button5);
        button6 = findViewById(R.id.button6);
        button7 = findViewById(R.id.button7);
        button8 = findViewById(R.id.button8);
        button9 = findViewById(R.id.button9);
        button0 = findViewById(R.id.button0);
        buttonClear = findViewById(R.id.buttonClear);
        buttonBackspace = findViewById(R.id.buttonBackspace);
        buttonLeftBracket = findViewById(R.id.buttonLeftBracket);
        buttonRightBracket = findViewById(R.id.buttonRightBracket);
        buttonPi = findViewById(R.id.buttonPi);
        buttonDot = findViewById(R.id.buttonDot);
        buttonEqual = findViewById(R.id.buttonEqual);
        buttonPlus = findViewById(R.id.buttonPlus);
        buttonMinus = findViewById(R.id.buttonMinus);
        buttonMultiply = findViewById(R.id.buttonMultiply);
        buttonDivide = findViewById(R.id.buttonDivide);
        buttonInverse = findViewById(R.id.buttonInverse);
        buttonSqrt = findViewById(R.id.buttonSqrt);
        buttonSquare = findViewById(R.id.buttonSquare);
        buttonFactorial = findViewById(R.id.buttonFactorial);
        buttonLn = findViewById(R.id.buttonLn);
        buttonLog = findViewById(R.id.buttonLog);
        buttonSin = findViewById(R.id.buttonSin);
        buttonCos = findViewById(R.id.buttonCos);
        buttonTan = findViewById(R.id.buttonTan);
    }

    private void initializeTextViews() {
        textViewMain = findViewById(R.id.textViewMain);
        textViewSecondary = findViewById(R.id.textViewSecondary);
    }

    private void setOnClickListeners() {
        button1.setOnClickListener(v -> textViewMain.append("1"));
        button2.setOnClickListener(v -> textViewMain.append("2"));
        button3.setOnClickListener(v -> textViewMain.append("3"));
        button4.setOnClickListener(v -> textViewMain.append("4"));
        button5.setOnClickListener(v -> textViewMain.append("5"));
        button6.setOnClickListener(v -> textViewMain.append("6"));
        button7.setOnClickListener(v -> textViewMain.append("7"));
        button8.setOnClickListener(v -> textViewMain.append("8"));
        button9.setOnClickListener(v -> textViewMain.append("9"));
        button0.setOnClickListener(v -> textViewMain.append("0"));
        buttonDot.setOnClickListener(v -> textViewMain.append("."));
        buttonClear.setOnClickListener(v -> {
            textViewMain.setText("");
            textViewSecondary.setText("");
        });
        buttonBackspace.setOnClickListener(v -> {
            String value = textViewMain.getText().toString();
            if (!value.isEmpty()) {
                value = value.substring(0, value.length() - 1);
                textViewMain.setText(value);
            }
        });
        buttonPlus.setOnClickListener(v -> textViewMain.append("+"));
        buttonMinus.setOnClickListener(v -> textViewMain.append("-"));
        buttonDivide.setOnClickListener(v -> textViewMain.append("÷"));
        buttonMultiply.setOnClickListener(v -> textViewMain.append("×"));
        buttonSqrt.setOnClickListener(v -> {
            try {
                double value = Math.sqrt(Double.parseDouble(textViewMain.getText().toString()));
                textViewMain.setText(String.valueOf(value));
            } catch (NumberFormatException | NullPointerException e) {
                textViewMain.setText("Error");
            }
        });
        buttonLeftBracket.setOnClickListener(v -> textViewMain.append("("));
        buttonRightBracket.setOnClickListener(v -> textViewMain.append(")"));
        buttonPi.setOnClickListener(v -> {
            textViewSecondary.setText(buttonPi.getText());
            textViewMain.append(piValue);
        });
        buttonSin.setOnClickListener(v -> textViewMain.append("sin"));
        buttonCos.setOnClickListener(v -> textViewMain.append("cos"));
        buttonTan.setOnClickListener(v -> textViewMain.append("tan"));
        buttonInverse.setOnClickListener(v -> textViewMain.append("^(-1)"));
        buttonFactorial.setOnClickListener(v -> {
            try {
                int value = Integer.parseInt(textViewMain.getText().toString());
                int result = factorial(value);
                textViewMain.setText(String.valueOf(result));
                textViewSecondary.setText(value + "!");
            } catch (NumberFormatException e) {
                textViewMain.setText("Error");
            }
        });
        buttonSquare.setOnClickListener(v -> {
            try {
                double value = Double.parseDouble(textViewMain.getText().toString());
                double square = value * value;
                textViewMain.setText(String.valueOf(square));
                textViewSecondary.setText(value + "²");
            } catch (NumberFormatException e) {
                textViewMain.setText("Error");
            }
        });
        buttonLn.setOnClickListener(v -> textViewMain.append("ln"));
        buttonLog.setOnClickListener(v -> textViewMain.append("log"));
        buttonEqual.setOnClickListener(v -> {
            try {
                String value = textViewMain.getText().toString();
                String replacedValue = value.replace('÷', '/').replace('×', '*');
                double result = evaluateExpression(replacedValue);
                textViewMain.setText(String.valueOf(result));
                textViewSecondary.setText(value);
            } catch (Exception e) {
                textViewMain.setText("Error");
            }
        });
    }

    private int factorial(int n) {
        return (n == 1 || n == 0) ? 1 : n * factorial(n - 1);
    }

    private double evaluateExpression(final String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char) ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm();
                    else if (eat('-')) x -= parseTerm();
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseTerm();
                    else if (eat('/')) x /= parseTerm();
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor();
                if (eat('-')) return -parseFactor();

                double x;
                int startPos = this.pos;
                if (eat('(')) {
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') {
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else if ((ch >= 'a' && ch <= 'z') || ch == '.') {
                    while ((ch >= 'a' && ch <= 'z') || ch == '.') nextChar();
                    String func = expression.substring(startPos, this.pos);
                    x = parseFactor();
                    if (func.equals("sqrt")) x = Math.sqrt(x);
                    else if (func.equals("sin")) x = Math.sin(Math.toRadians(x));
                    else if (func.equals("cos")) x = Math.cos(Math.toRadians(x));
                    else if (func.equals("tan")) x = Math.tan(Math.toRadians(x));
                    else if (func.equals("log")) x = Math.log10(x);
                    else if (func.equals("ln")) x = Math.log(x);
                    else throw new RuntimeException("Unknown function: " + func);
                } else {
                    throw new RuntimeException("Unexpected: " + (char) ch);
                }
                if (eat('^')) x = Math.pow(x, parseFactor());
                return x;
            }
        }.parse();
        }
}