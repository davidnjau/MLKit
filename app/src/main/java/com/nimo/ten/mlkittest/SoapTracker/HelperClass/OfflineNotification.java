package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.app.job.JobParameters;
import android.app.job.JobService;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.nimo.ten.mlkittest.R;
import com.nimo.ten.mlkittest.SoapTracker.Activities.SingleSoapDetails;
import com.nimo.ten.mlkittest.SoapTracker.Activities.SoapHeal_Healing;
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


public class OfflineNotification extends JobService {

    private static final String TAG = "ImageUpload";
    private boolean jobCancelled = false;

    private DatabaseHelper databaseHelper;
    private static final String TABLE = "soap_details";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE_OUT = "date_out";
    private static final String KEY_SOAP_NAME = "soap_name";

    List<String> myId = new ArrayList<>();
    List<Long> myDateOut = new ArrayList<>();
    List<String> mySoapName = new ArrayList<>();

    private long CurrentDateInMillis;

    String txtTitle = "Soap Healing Notification";

    @Override
    public boolean onStartJob(JobParameters params) {

        Log.d(TAG, "Job started");
        doBackgroundWork(params);

        return false;
    }

    private void doBackgroundWork(final JobParameters params) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                ScheduleNotification();

                jobFinished(params, false);

            }


        }).start();
    }

    private void ScheduleNotification() {

        databaseHelper = new DatabaseHelper(getApplicationContext());

        String selectQuery = "SELECT * FROM " + TABLE ;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {

            do {

                String id = c.getString(c.getColumnIndex(KEY_ID));
                String date_out = c.getString(c.getColumnIndex(KEY_DATE_OUT));
                String soap_name = c.getString(c.getColumnIndex(KEY_SOAP_NAME));

                myId.add(id);
                mySoapName.add(soap_name);

                try {

                    myDateOut.add(getTimeInMillis(date_out));

                } catch (ParseException e) {
                    Log.e("-*-*-", e.toString());
                }


            } while (c.moveToNext());

            c.close();

            CompareTime();


        }

    }


    private void CompareTime() {

        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        String CurrentDate = df.format(c);

        try {

            CurrentDateInMillis = getTimeInMillis(CurrentDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        for (int i = 0; i<myId.size(); i++){

            String id = myId.get(i);
            String soap_name = mySoapName.get(i);
            long time = myDateOut.get(i);

            if (time == CurrentDateInMillis){

                System.out.println("*-*-*Soap name " + soap_name);
                System.out.println("*-*-*Date out " + time);
                System.out.println("*-*-*Current time " + CurrentDateInMillis);
                Log.e("-*-*-*--*-*-*-", "*-*-*-*-");

                //The Soap has healed; Create Notification
                CreateNotification(id, soap_name, time);

            }

        }

    }

    private void CreateNotification(String id, String soap_name, long time) {

        //
        databaseHelper.updateSoapCondition(id, "Healed");

        int NOTIFICATION_ID = Integer.parseInt(id);
        String CHANNEL_ID = "my_channel_01";
        CharSequence name = "my_channel";
        String description = "This is my channel";

        NotificationManager notificationManager = (NotificationManager)getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){

            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            channel.enableLights(true);
            channel.setLightColor(Color.RED);
            channel.enableVibration(true);
            channel.setVibrationPattern(new long[]{100,200,300,400,500,400,300,200,400});
            channel.setShowBadge(false);
            assert notificationManager != null;
            notificationManager.createNotificationChannel(channel);

        }

        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(), CHANNEL_ID);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){

            builder.setSmallIcon(R.mipmap.ic_pic_logo);

        }else {

            builder.setSmallIcon(R.mipmap.ic_pic_logo);
        }

        builder.setContentTitle(txtTitle);
        builder.setContentText(soap_name + " has healed and is ready for usage.");

        Intent intent = new Intent(getApplicationContext(), SoapHeal_Healing.class);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(getApplicationContext());
        stackBuilder.addParentStack(SoapHeal_Healing.class);
        stackBuilder.addNextIntent(intent);

        PendingIntent pendingIntent = stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        assert notificationManager != null;
        notificationManager.notify(NOTIFICATION_ID, builder.build());

    }

    public long getTimeInMillis(String date) throws ParseException {

        SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
        Date date1 = df.parse(date);

        assert date1 != null;

        return date1.getTime();

    }


    @Override
    public boolean onStopJob(JobParameters params) {

        Log.d(TAG, "Job cancelled before completion");
        jobCancelled = true;
        return true;
    }

}
