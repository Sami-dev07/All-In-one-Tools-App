package com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.Helper;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.alltools.toolbox.utility.calculator.CommonToolsActivity.HiddenCamera.MainService;


public class PauseActionBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if ("NO_ACTION".equals(action)) {
            Toast.makeText(context, "You clicked No", Toast.LENGTH_SHORT).show();
            Toast.makeText(context, "Received", Toast.LENGTH_SHORT).show();
            context.unbindService(Utils.connection);
            context.stopService(new Intent(context, MainService.class));

        }

    }

}
