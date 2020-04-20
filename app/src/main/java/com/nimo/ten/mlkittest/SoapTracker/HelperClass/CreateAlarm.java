package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

public class CreateAlarm {

    AlarmManager alarmManager;
    PendingIntent pendingIntent;

    public void StartAlarmWork(Context context) {

        alarmManager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);

        Intent alarmIntent = new Intent(context, MyBroadCastReceiver.class);
        pendingIntent = PendingIntent.getBroadcast(context, 0, alarmIntent, 0);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){

            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, 0 , pendingIntent);

        }else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){

            alarmManager.setExact(AlarmManager.RTC_WAKEUP, 0, pendingIntent);
        }else {

            alarmManager.set(AlarmManager.RTC_WAKEUP, 0 , pendingIntent);
        }

    }
}
