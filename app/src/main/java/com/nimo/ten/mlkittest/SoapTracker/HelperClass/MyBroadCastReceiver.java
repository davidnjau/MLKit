package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.Objects;

public class MyBroadCastReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        if (Objects.equals(intent.getAction(), "android.intent.action.BOOT_COMPLETED")){

            Intent ServiceIntent = new Intent(context, MyService.class);
            context.startService(ServiceIntent);

        }else {

            CompareDates compareDates = new CompareDates();
            compareDates.getDates(context);

//            Toast.makeText(context, "alarm just ran", Toast.LENGTH_SHORT).show();
        }

    }
}
