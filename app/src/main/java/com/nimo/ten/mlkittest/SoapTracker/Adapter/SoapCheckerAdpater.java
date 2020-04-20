package com.nimo.ten.mlkittest.SoapTracker.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.OfflineNotification;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;


public class SoapCheckerAdpater {

    DatabaseHelper databaseHelper;
    private static final String TABLE_SOAP_DETALS = "soap_details";
    private static final String KEY_ID = "id";
    private static final String KEY_DATE_IN = "date_in";
    private static final String KEY_DATE_OUT = "date_out";
    private static final String KEY_CONDITION = "condition";

    public void getSoapCondition(Context context){

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

                    String date_in = c.getString(c.getColumnIndex(KEY_DATE_IN));
                    String date_out = c.getString(c.getColumnIndex(KEY_DATE_OUT));

                    long dateIn = offlineNotification.getTimeInMillis(date_in);
                    long dateOut = offlineNotification.getTimeInMillis(date_out);

                    long remainingDays = TimeUnit.DAYS.convert(dateOut - dateIn, TimeUnit.MILLISECONDS);

                    if (remainingDays <= 0){

                        databaseHelper.updateSoapCondition(soap_id);

                    }



                } while (c.moveToNext());

                c.close();

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

    }

}