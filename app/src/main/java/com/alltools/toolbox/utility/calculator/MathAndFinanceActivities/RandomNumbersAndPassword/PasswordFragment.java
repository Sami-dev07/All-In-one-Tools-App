package com.alltools.toolbox.utility.calculator.MathAndFinanceActivities.RandomNumbersAndPassword;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.AppCompatCheckBox;
import androidx.fragment.app.Fragment;

import com.alltools.toolbox.utility.calculator.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.security.SecureRandom;
import java.util.Random;

/* loaded from: classes.dex */
public class PasswordFragment extends Fragment implements View.OnClickListener {
    private static final Random RANDOM = new SecureRandom();
    Button btCopy;
    Button btGenerate;
    Button btReset;
    Button btShare;
    AppCompatCheckBox cbIncludeNumber;
    TextInputEditText etNumber;
    int iNumber;
    LinearLayout llCopyShare;
    TextInputLayout tipNumber;
    TextView tvResult;
    String Capital_chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
    String Small_chars = "abcdefghijklmnopqrstuvwxyz";
    String numbers = "0123456789";
    String values = " ";
    Random rndm_method = new Random();

    @Override // androidx.fragment.app.Fragment
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(R.layout.form_random_password, viewGroup, false);
    }

    @Override // androidx.fragment.app.Fragment
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        findAllViewByIds();
        setAllOnClickListeners();
    }

    private void findAllViewByIds() {
        this.etNumber = (TextInputEditText) getActivity().findViewById(R.id.et_password);
        this.tipNumber = (TextInputLayout) getActivity().findViewById(R.id.tip_password);
        this.btGenerate = (Button) getActivity().findViewById(R.id.bt_generate);
        this.btReset = (Button) getActivity().findViewById(R.id.bt_reset);
        this.btShare = (Button) getActivity().findViewById(R.id.bt_share);
        this.btCopy = (Button) getActivity().findViewById(R.id.bt_copy);
        this.tvResult = (TextView) getActivity().findViewById(R.id.tv_result_password);
        this.llCopyShare = (LinearLayout) getActivity().findViewById(R.id.ll_copy_share);
        this.cbIncludeNumber = (AppCompatCheckBox) getActivity().findViewById(R.id.cb_include_number);
    }

    private void setAllOnClickListeners() {
        this.btCopy.setOnClickListener(this);
        this.btShare.setOnClickListener(this);
        this.btGenerate.setOnClickListener(this);
        this.btReset.setOnClickListener(this);
    }

    private void shareApp() {
        Intent intent = new Intent("android.intent.action.SEND");
        intent.setType("text/plain");
        intent.putExtra("android.intent.extra.SUBJECT", getResources().getString(R.string.app_name));
        intent.putExtra("android.intent.extra.TEXT", this.tvResult.getText().toString() + "\n\n -- Source -- \n\nhttps://play.google.com/store/apps/details?id=com.droidfoundry.tools");
        startActivity(Intent.createChooser(intent, "Share"));
    }

    private void copyResult() {
//        try {
//            AppUtils.copyToClipboard(getActivity().getApplicationContext(), this.tvResult.getText().toString(), "Copy");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    public String generateRandomPassword(int i) {
        if (this.cbIncludeNumber.isChecked()) {
            this.values = this.Capital_chars + this.Small_chars + this.numbers;
        } else {
            this.values = this.Capital_chars + this.Small_chars;
        }
        String str = "";
        if (i <= 15) {
            for (int i2 = 0; i2 < i; i2++) {
                double nextDouble = RANDOM.nextDouble();
                double length = this.values.length();
                Double.isNaN(length);
                int i3 = (int) (nextDouble * length);
                str = str + this.values.substring(i3, i3 + 1);
            }
        } else {
            Toast.makeText(getActivity(), "Password hint", Toast.LENGTH_SHORT).show();
        }
        return str;
    }

    @Override // android.view.View.OnClickListener
    public void onClick(View view) {
        if (view.getId() == R.id.bt_copy) {
            copyResult();
        } else if (view.getId() == R.id.bt_share) {
            shareApp();
        } else if (view.getId() == R.id.bt_generate) {
            this.iNumber = CommonCalculationUtils.getIntValue(this.etNumber);
            TextView textView = this.tvResult;
            textView.setText(generateRandomPassword(this.iNumber) + "");
            this.llCopyShare.setVisibility(0);
        } else if (view.getId() == R.id.bt_reset) {
            this.etNumber.setText("");
            this.tvResult.setText("");
        }
    }
}
