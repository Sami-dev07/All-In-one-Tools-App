package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.RandomNumbersAndPassword;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatRadioButton;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.alltools.toolbox.utility.calculator.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.lang.reflect.Field;
import java.util.Random;

/* loaded from: classes.dex */
public class RandomNumberFragment extends Fragment {
    SharedPreferences adPrefs;
    Button btConvert;
    TextInputEditText etMaximum;
    TextInputEditText etMinimum;
    boolean isGenerateOne = true;
    LinearLayout llResult;
    int maximum;
    int minimum;
    AppCompatRadioButton rbOne;
    AppCompatRadioButton rbTen;
    RadioGroup rgGenerate;
    TextInputLayout tipMaximum;
    TextInputLayout tipMinimum;
    TextView tvResultRange;

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.form_maths_random, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        findAllViewByIds();
        setAllOnClickListeners();
        setRadioButtonListeners();
        setEditTextOutlineColor();
    }

    private void setEditTextOutlineColor() {
        try {
            Field declaredField = TextInputLayout.class.getDeclaredField("defaultStrokeColor");
            declaredField.setAccessible(true);
            declaredField.set(this.tipMaximum, Integer.valueOf(ContextCompat.getColor(getActivity(), R.color.tools_edit_text_primary_color)));
            declaredField.set(this.tipMinimum, Integer.valueOf(ContextCompat.getColor(getActivity(), R.color.tools_edit_text_primary_color)));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setRadioButtonListeners() {
        this.rgGenerate.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() { // from class: com.droidfoundry.tools.maths.random.RandomNumberFragment.1
            @Override // android.widget.RadioGroup.OnCheckedChangeListener
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                if (i == R.id.rb_one) {
                    RandomNumberFragment.this.isGenerateOne = true;
                } else if (i == R.id.rb_ten) {
                    RandomNumberFragment.this.isGenerateOne = false;
                }
            }
        });
    }

    private void findAllViewByIds() {
        this.btConvert = (Button) getActivity().findViewById(R.id.bt_convert);
        this.tipMinimum = (TextInputLayout) getActivity().findViewById(R.id.tip_minimum);
        this.tipMaximum = (TextInputLayout) getActivity().findViewById(R.id.tip_maximum);
        this.etMinimum = (TextInputEditText) getActivity().findViewById(R.id.et_minimum);
        this.etMaximum = (TextInputEditText) getActivity().findViewById(R.id.et_maximum);
        this.rgGenerate = (RadioGroup) getActivity().findViewById(R.id.rg_generate);
        this.rbOne = (AppCompatRadioButton) getActivity().findViewById(R.id.rb_one);
        this.rbTen = (AppCompatRadioButton) getActivity().findViewById(R.id.rb_ten);
        this.tvResultRange = (TextView) getActivity().findViewById(R.id.tv_range_result);
        this.llResult = (LinearLayout) getActivity().findViewById(R.id.ll_generate);
    }

    private void setAllOnClickListeners() {
        this.btConvert.setOnClickListener(new View.OnClickListener() { // from class: com.droidfoundry.tools.maths.random.RandomNumberFragment.2
            @Override // android.view.View.OnClickListener
            public void onClick(View view) {
                try {
                    if (!CommonCalculationUtils.isEditTextInputEmpty(RandomNumberFragment.this.etMaximum) && !CommonCalculationUtils.isEditTextInputEmpty(RandomNumberFragment.this.etMinimum)) {
                        RandomNumberFragment randomNumberFragment = RandomNumberFragment.this;
                        randomNumberFragment.minimum = CommonCalculationUtils.getIntValue(randomNumberFragment.etMinimum);
                        RandomNumberFragment randomNumberFragment2 = RandomNumberFragment.this;
                        randomNumberFragment2.maximum = CommonCalculationUtils.getIntValue(randomNumberFragment2.etMaximum);
                        Random random = new Random();
                        if (RandomNumberFragment.this.isGenerateOne) {
                            TextView textView = RandomNumberFragment.this.tvResultRange;
                            RandomNumberFragment randomNumberFragment3 = RandomNumberFragment.this;
                            textView.setText(randomNumberFragment3.generateRandomInteger(randomNumberFragment3.minimum, RandomNumberFragment.this.maximum, random));
                        } else {
                            String str = "";
                            for (int i = 0; i <= 9; i++) {
                                if (i != 9) {
                                    StringBuilder sb = new StringBuilder();
                                    sb.append(str);
                                    RandomNumberFragment randomNumberFragment4 = RandomNumberFragment.this;
                                    sb.append(randomNumberFragment4.generateRandomInteger(randomNumberFragment4.minimum, RandomNumberFragment.this.maximum, random));
                                    sb.append(", ");
                                    str = sb.toString();
                                } else {
                                    StringBuilder sb2 = new StringBuilder();
                                    sb2.append(str);
                                    RandomNumberFragment randomNumberFragment5 = RandomNumberFragment.this;
                                    sb2.append(randomNumberFragment5.generateRandomInteger(randomNumberFragment5.minimum, RandomNumberFragment.this.maximum, random));
                                    str = sb2.toString();
                                }
                            }
                            RandomNumberFragment.this.tvResultRange.setText(str);
                        }
                        RandomNumberFragment.this.llResult.setVisibility(View.VISIBLE);
                        return;
                    }
                    Toast.makeText(RandomNumberFragment.this.getActivity(), RandomNumberFragment.this.getResources().getString(R.string.max_min_empty), Toast.LENGTH_SHORT).show();
                } catch (Exception unused) {
                    RandomNumberFragment.this.tvResultRange.setText(" 0 ");
                    RandomNumberFragment.this.llResult.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public String generateRandomInteger(int i, int i2, Random random) {
        long j = 0;
        double d = 0;
        if (i > i2) {
            try {
                Toast.makeText(getActivity(), getResources().getString(R.string.max_min_validation), Toast.LENGTH_SHORT).show();
                return "";
            } catch (Exception e) {
                e.printStackTrace();
                return "";
            }
        }
        double nextDouble = random.nextDouble();
        Double.isNaN((i2 - i) + 1);
        return ((int) (((long) (d * nextDouble)) + j)) + "";
    }

    protected void hideKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getActivity().getSystemService("input_method");
        View currentFocus = getActivity().getCurrentFocus();
        if (currentFocus != null) {
            inputMethodManager.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0);
        }
    }

    @Override // androidx.fragment.app.Fragment
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 16908332) {
            hideKeyboard();
            getActivity().finish();
        }
        return super.onOptionsItemSelected(menuItem);
    }
}
