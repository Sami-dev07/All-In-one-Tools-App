package com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.Helper;

import android.content.Context;
import android.content.SharedPreferences;

public class SharePref {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    public SharePref(Context context) {
        this.sharedPreferences = context.getSharedPreferences("spy Cam", Context.MODE_PRIVATE);
    }

    public void setSharedTextValue(String name, String value){
        editor = sharedPreferences.edit();
        editor.putString(name, value);
        editor.apply();
    }

    public void setSharedIntValue(String name, int value){
        editor = sharedPreferences.edit();
        editor.putInt(name, value);
        editor.apply();
    }


    public String getSharedTextValue(String name, String dummyValue){
        return sharedPreferences.getString(name, dummyValue);
    }

}
