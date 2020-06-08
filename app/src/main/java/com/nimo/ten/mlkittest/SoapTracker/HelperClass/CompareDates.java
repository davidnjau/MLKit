package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.SoapHeal_Healing;
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapCheckerAdpater;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.concurrent.TimeUnit;

public class CompareDates {

    DatabaseHelper databaseHelper;
    private static final String TABLE_SOAP_DETALS = "soap_details";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE_IN = "date_in";
    private static final String KEY_DATE_OUT = "date_out";
    private static final String KEY_CONDITION = "condition";
    private static final String KEY_SOAP_NAME = "soap_name";
    private static final String KEY_NOTIFICATION_STATUS = "notification_status";

    public void getDates(Context context){

        try {

            OfflineNotification offlineNotification = new OfflineNotification();
            databaseHelper = new DatabaseHelper(context);

            SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");

            String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS ;
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {

                do {

                    String txtCondition = c.getString(c.getColumnIndex(KEY_CONDITION));
                    String soap_id = c.getString(c.getColumnIndex(KEY_ID));
                    String soap_name = c.getString(c.getColumnIndex(KEY_SOAP_NAME));

                    String date_in = c.getString(c.getColumnIndex(KEY_DATE_IN));
                    String date_out = c.getString(c.getColumnIndex(KEY_DATE_OUT));

                    String notification_status = c.getString(c.getColumnIndex(KEY_NOTIFICATION_STATUS));

                    long dateIn = offlineNotification.getTimeInMillis(date_in);
                    long dateOut = offlineNotification.getTimeInMillis(date_out);

                    long remainingDays = TimeUnit.DAYS.convert(dateOut - dateIn, TimeUnit.MILLISECONDS);

                    if (remainingDays == 0){

                        CreateNotification(soap_id, soap_name, remainingDays, context, notification_status);

                    }



                } while (c.moveToNext());

                c.close();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }


    }

    private void CreateNotification(String id, String soap_name, long time, Context context, String isNotified) {

        //
        databaseHelper.updateSoapCondition(id,"Healed");

        System.out.println("-*-*-* " + isNotified);

        if (isNotified.equals("false")) {

            databaseHelper.updateSoapNotifcation(id);

            int NOTIFICATION_ID = Integer.parseInt(id);
            String CHANNEL_ID = "my_channel_01";
            CharSequence name = "my_channel";
            String description = "This is my channel";

            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

                int importance = NotificationManager.IMPORTANCE_HIGH;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
                channel.setDescription(description);
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                channel.setShowBadge(false);
                assert notificationManager != null;
                notificationManager.createNotificationChannel(channel);

            }

            NotificationCompat.Builder builder = new NotificationCompat.Builder(context, CHANNEL_ID);

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

                builder.setSmallIcon(R.mipmap.ic_pic_logo);

            } else {

                builder.setSmallIcon(R.mipmap.ic_pic_logo);
            }

            builder.setContentTitle("Soap healed notification.");
            builder.setContentText(soap_name + " has healed and is ready for usage.");

            Intent intent = new Intent(context, SoapHeal_Healing.class);

            TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
            stackBuilder.addParentStack(SoapHeal_Healing.class);
            stackBuilder.addNextIntent(intent);

            PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
            builder.setContentIntent(pendingIntent);
            assert notificationManager != null;
            notificationManager.notify(NOTIFICATION_ID, builder.build());

        }

    }

}
