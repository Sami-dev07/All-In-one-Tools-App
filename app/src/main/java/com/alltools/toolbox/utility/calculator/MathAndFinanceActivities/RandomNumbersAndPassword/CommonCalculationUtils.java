package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.RandomNumbersAndPassword;

import android.widget.EditText;

public class CommonCalculationUtils {

    public static boolean isValueZero(double d) {
        return d == 0.0d;
    }

    public static boolean isValueZero(float f) {
        return f == 0.0f;
    }

    public static boolean isValueZero(int i) {
        return i == 0;
    }

    public static boolean isValueZero(long j) {
        return j == 0;
    }

    public static boolean isEditTextInputEmpty(EditText editText) {
        return editText.getText() == null || editText.getText().toString() == null || editText.getText().toString().trim().isEmpty();
    }

    public static boolean isStringEmpty(String str) {
        return str == null || str.equalsIgnoreCase("");
    }

    public static int getIntValue(EditText editText) {
        try {
            return Integer.parseInt(editText.getText().toString().trim());
        } catch (Exception unused) {
            return 0;
        }
    }

    public static int getIntValue(String str) {
        try {
            return Integer.parseInt(str.trim());
        } catch (Exception unused) {
            return 0;
        }
    }

    public static long getLongValue(EditText editText) {
        try {
            return Long.parseLong(editText.getText().toString().trim());
        } catch (Exception unused) {
            return 0L;
        }
    }

    public static long getLongValue(String str) {
        try {
            return Long.parseLong(str.trim());
        } catch (Exception unused) {
            return 0L;
        }
    }

    public static double getDoubleValue(EditText editText) {
        try {
            return Double.parseDouble(editText.getText().toString().trim());
        } catch (Exception unused) {
            return 0.0d;
        }
    }

    public static double getDoubleValue(String str) {
        try {
            return Double.parseDouble(str.trim());
        } catch (Exception unused) {
            return 0.0d;
        }
    }

    public static String getStringValue(EditText editText) {
        try {
            return editText.getText().toString().trim();
        } catch (Exception unused) {
            return "";
        }
    }

    public static float getFloatValue(EditText editText) {
        try {
            return Float.parseFloat(editText.getText().toString().trim());
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    public static float getFloatValue(String str) {
        try {
            return Float.parseFloat(str.trim());
        } catch (Exception unused) {
            return 0.0f;
        }
    }

    public static double kgsToPounds(Double d) {
        return d.doubleValue() * 2.20462d;
    }

    public static double kgsToPounds(EditText editText) {
        return getDoubleValue(editText) * 2.20462d;
    }

    public static double poundsToKgs(Double d) {
        return d.doubleValue() * 0.453592d;
    }

    public static double poundsToKgs(EditText editText) {
        return getDoubleValue(editText) * 0.453592d;
    }

    public static double cmToInches(Double d) {
        return d.doubleValue() * 0.393701d;
    }

    public static double cmToInches(EditText editText) {
        return getDoubleValue(editText) * 0.393701d;
    }

    public static double inchesToCentimeters(Double d) {
        return d.doubleValue() * 2.546d;
    }

    public static double inchesToCentimeters(EditText editText) {
        return getDoubleValue(editText) * 2.546d;
    }

    public static double feetAndInchToCm(Double d, Double d2) {
        return (d.doubleValue() * 30.48d) + (d2.doubleValue() * 2.54d);
    }

    public static double feetAndInchToCm(EditText editText, EditText editText2) {
        return (getDoubleValue(editText) * 30.48d) + (getDoubleValue(editText2) * 2.54d);
    }

    public static double milesToKm(Double d) {
        return d.doubleValue() * 1.60934d;
    }

    public static double kmToMiles(Double d) {
        return d.doubleValue() * 0.621371d;
    }

}
