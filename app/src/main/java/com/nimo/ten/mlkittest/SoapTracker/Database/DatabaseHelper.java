package com.nimo.ten.mlkittest.SoapTracker.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "soap_tracking";
    private static final int DATABASE_VERSION = 1;

    private static final String TABLE_SOAP_DETALS = "soap_details";
    private static final String TABLE_SOAP_INGREDIENTS = "soap_ingredients";
    private static final String TABLE_SOAP_NOTES = "soap_notes";
    private static final String TABLE_SOAP_LYE = "soap_lye";

    private static final String KEY_ID = "id";

    private static final String KEY_SOAP_NAME = "soap_name";
    private static final String KEY_DATE_IN = "date_in";
    private static final String KEY_DATE_OUT = "date_out";
    private static final String KEY_CONDITION = "condition";
    private static final String KEY_PHOTO1_Id = "photo1_id";

    private static final String KEY_PHOTO_PATH = "pic_path";

    private static final String KEY_SOAP_ID = "soap_id";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String KEY_SUBSTANCE = "substance";
    private static final String KEY_PERCENTAGE = "percentage";
    private static final String KEY_WEIGHT = "weight";

    private static final String KEY_NOTES = "notes";
    private static final String KEY_PRIORITY = "priority";

    private static final String KEY_NOTIFICATION_STATUS = "notification_status";

    private static final String KEY_TOTAL_WEIGHT = "total_weight";
    private static final String KEY_NAOH_WEIGHT = "total_lye_weight";
    private static final String KEY_LIQUID_WEIGHT = "total_liquid_weight";

    ContentResolver mContentResolver;

    private static final String CREATE_TABLE_SOAP_INGREDIENTS = "CREATE TABLE "
            + TABLE_SOAP_INGREDIENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_INGREDIENTS + " TEXT, "+
            KEY_PERCENTAGE + " TEXT, "+
            KEY_WEIGHT + " TEXT " + " );";


    private static final String CREATE_TABLE_SOAP_LYE = "CREATE TABLE "
            + TABLE_SOAP_LYE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_SUBSTANCE + " TEXT, "+
            KEY_PERCENTAGE + " TEXT, "+
            KEY_WEIGHT + " TEXT " + " );";


    private static final String CREATE_TABLE_SOAP_NOTES = "CREATE TABLE "
            + TABLE_SOAP_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_NOTES + " TEXT, "+
            KEY_PRIORITY + " TEXT " + " );";

    private static final String CREATE_TABLE_SOAP_DETALS = "CREATE TABLE "
            + TABLE_SOAP_DETALS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_NAME + " TEXT, " +
            KEY_PHOTO1_Id + " BLOB, " +
            KEY_DATE_IN + " TEXT, "+
            KEY_CONDITION + " TEXT, "+
            KEY_DATE_OUT + " TEXT, "+
            KEY_NOTIFICATION_STATUS + " TEXT, "+
            KEY_TOTAL_WEIGHT + " TEXT, "+
            KEY_NAOH_WEIGHT + " TEXT, "+
            KEY_LIQUID_WEIGHT + " TEXT, "+
            KEY_PHOTO_PATH + " TEXT " + " );";

    private boolean isUpdated = false;

    private int updater;

    private String WeightStatus;

    private Double txtRemWeight;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContentResolver = context.getContentResolver();

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_SOAP_DETALS);
        db.execSQL(CREATE_TABLE_SOAP_INGREDIENTS);
        db.execSQL(CREATE_TABLE_SOAP_NOTES);
        db.execSQL(CREATE_TABLE_SOAP_LYE);

        Log.d("++++", CREATE_TABLE_SOAP_DETALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_DETALS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_INGREDIENTS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_NOTES +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_LYE +"'");

        onCreate(db);

    }

    public long AddSoapTracker(String soap_name, String date_in, String date_out, byte[] image, String txtFilePath){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues soap_tracker = new ContentValues();
        soap_tracker.put(KEY_SOAP_NAME, soap_name);
        soap_tracker.put(KEY_DATE_IN, date_in);
        soap_tracker.put(KEY_DATE_OUT, date_out);
        soap_tracker.put(KEY_CONDITION, "Healing");
        soap_tracker.put(KEY_PHOTO1_Id, image);
        soap_tracker.put(KEY_PHOTO_PATH, txtFilePath);
        soap_tracker.put(KEY_NOTIFICATION_STATUS, "false");
        soap_tracker.put(KEY_TOTAL_WEIGHT, "0.0");
        soap_tracker.put(KEY_NAOH_WEIGHT, "0.0");
        soap_tracker.put(KEY_LIQUID_WEIGHT, "0.0");

        return db.insert( TABLE_SOAP_DETALS, null, soap_tracker );
    }

    public void AddIngredients(String soap_ingredients, String soap_id, String percentage, String weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues soap_tracker = new ContentValues();
        soap_tracker.put(KEY_INGREDIENTS, soap_ingredients);
        soap_tracker.put(KEY_SOAP_ID, soap_id);
        soap_tracker.put(KEY_PERCENTAGE, percentage);
        soap_tracker.put(KEY_WEIGHT, weight);

        db.insert( TABLE_SOAP_INGREDIENTS, null, soap_tracker );
    }
    public void AddLye(String soap_substance, String soap_id, String percentage, String weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues soap_tracker = new ContentValues();
        soap_tracker.put(KEY_SUBSTANCE, soap_substance);
        soap_tracker.put(KEY_SOAP_ID, soap_id);
        soap_tracker.put(KEY_PERCENTAGE, percentage);
        soap_tracker.put(KEY_WEIGHT, weight);

        db.insert( TABLE_SOAP_LYE, null, soap_tracker );
    }




    public void AddNotes(String soap_id, String notes, String priority){


        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues soap_tracker = new ContentValues();
        soap_tracker.put(KEY_SOAP_ID, soap_id);
        soap_tracker.put(KEY_NOTES, notes);
        soap_tracker.put(KEY_PRIORITY, priority);

        db.insert( TABLE_SOAP_NOTES, null, soap_tracker );
    }

    public ArrayList<SoapTrackerPojo> getSoapTrack(String SoapCondtion) {

        ArrayList<SoapTrackerPojo> soapTrackerPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                String txtCondition = c.getString(c.getColumnIndex(KEY_CONDITION));

                if (SoapCondtion.equals(txtCondition)){

                    SoapTrackerPojo soapTrackerPojo = new SoapTrackerPojo();

                    soapTrackerPojo.setName(c.getString(c.getColumnIndex(KEY_SOAP_NAME)));
                    soapTrackerPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    soapTrackerPojo.setDateIn(c.getString(c.getColumnIndex(KEY_DATE_IN)));
                    soapTrackerPojo.setDateOut(c.getString(c.getColumnIndex(KEY_DATE_OUT)));

                    byte[] imgByte = c.getBlob(2);
                    soapTrackerPojo.setPhoto(imgByte);

                    soapTrackerPojoArrayList.add(soapTrackerPojo);

                }


            } while (c.moveToNext());

            c.close();

        }

        return soapTrackerPojoArrayList;
    }

    public ArrayList<SoapTrackerPojo> getSoapCuringDates() {

        ArrayList<SoapTrackerPojo> soapTrackerPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS +" WHERE " + KEY_DATE_OUT ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                SoapTrackerPojo soapTrackerPojo = new SoapTrackerPojo();

                soapTrackerPojo.setName(c.getString(c.getColumnIndex(KEY_SOAP_NAME)));
                soapTrackerPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));
                soapTrackerPojo.setDateIn(c.getString(c.getColumnIndex(KEY_DATE_IN)));
                soapTrackerPojo.setDateOut(c.getString(c.getColumnIndex(KEY_DATE_OUT)));
                soapTrackerPojo.setCondition(c.getString(c.getColumnIndex(KEY_CONDITION)));

                byte[] imgByte = c.getBlob(2);
                soapTrackerPojo.setPhoto(imgByte);
                soapTrackerPojoArrayList.add(soapTrackerPojo);

            } while (c.moveToNext());

            c.close();

        }

        return soapTrackerPojoArrayList;
    }

    public void deleteSoap(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SOAP_DETALS, KEY_ID + " = ?",new String[]{String.valueOf(id)});
        db.delete(TABLE_SOAP_NOTES, KEY_SOAP_ID + " = ?",new String[]{String.valueOf(id)});
        db.delete(TABLE_SOAP_INGREDIENTS, KEY_SOAP_ID + " = ?",new String[]{String.valueOf(id)});

    }

    public void deleteIngredients(String  id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SOAP_INGREDIENTS, KEY_ID + " = ?",new String[]{String.valueOf(id)});

    }

    public void deleteNotes(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SOAP_NOTES, KEY_ID + " = ?",new String[]{String.valueOf(id)});

    }

    public void updateSoapCondition(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_CONDITION, "Healed");

        db.update(TABLE_SOAP_DETALS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }
    public String updateSoapTotalWeight(String id, String Weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        cv.put(KEY_TOTAL_WEIGHT, Weight);

        updater = db.update(TABLE_SOAP_DETALS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        if (updater > 0){

            WeightStatus = "updated";

        }else {

            WeightStatus = "added";

        }

        return WeightStatus;

    }

    public void updateDate(String dateType ,String date, String id){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        if (dateType.equals("DateIn")){

            cv.put(KEY_DATE_IN, date);

        }
        if (dateType.equals("DateOut")){

            cv.put(KEY_DATE_OUT, date);

        }

        db.update(TABLE_SOAP_DETALS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});

    }

    public void updateSoapNotifcation(String id){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTIFICATION_STATUS, "true");

        db.update(TABLE_SOAP_DETALS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }

    public void updateWeights(String id, String Weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_WEIGHT, Weight);

        db.update(TABLE_SOAP_INGREDIENTS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }

    public void updateLyeWeights(String id, String Weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_WEIGHT, Weight);

        db.update(TABLE_SOAP_LYE, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }




    public void updateSoapIngredients(String id, String txtIngredients, String percentage, String Weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_INGREDIENTS, txtIngredients);
        cv.put(KEY_PERCENTAGE, percentage);
        cv.put(KEY_WEIGHT, Weight);

        db.update(TABLE_SOAP_INGREDIENTS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }

    public void updateSoapNotes(String id, String Notes){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NOTES, Notes);

        db.update(TABLE_SOAP_NOTES, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }
    public void updateLyeWeight(String id, String Weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAOH_WEIGHT, Weight);

        cv.put(KEY_LIQUID_WEIGHT, getRemainingLiquid(id, Weight));

        db.update(TABLE_SOAP_DETALS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});

    }

    public Double getRemainingLiquid(String SoapId, String Weight) {

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS +" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                String txtTotalWeight = c.getString(c.getColumnIndex(KEY_TOTAL_WEIGHT));

                txtRemWeight = Double.parseDouble(txtTotalWeight) - Double.parseDouble(Weight);


            } while (c.moveToNext());

            c.close();

        }

        return txtRemWeight;
    }


    public ArrayList<IngredientsPojo> getSoapIngredients(String SoapId) {

        ArrayList<IngredientsPojo> IngredientsPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SOAP_INGREDIENTS+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                IngredientsPojo IngredientsPojo = new IngredientsPojo();

                IngredientsPojo.setIngredient_name(c.getString(c.getColumnIndex(KEY_INGREDIENTS)));
                IngredientsPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));

                IngredientsPojo.setSoap_id(SoapId);

                IngredientsPojo.setGrams(c.getString(c.getColumnIndex(KEY_WEIGHT)));
                IngredientsPojo.setPercentage(c.getString(c.getColumnIndex(KEY_PERCENTAGE)));


                IngredientsPojoArrayList.add(IngredientsPojo);


            } while (c.moveToNext());

            c.close();

        }

        return IngredientsPojoArrayList;
    }

    public ArrayList<IngredientsPojo> getSoapLye(String SoapId) {

        ArrayList<IngredientsPojo> IngredientsPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SOAP_LYE + " WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                IngredientsPojo IngredientsPojo = new IngredientsPojo();

                IngredientsPojo.setIngredient_name(c.getString(c.getColumnIndex(KEY_SUBSTANCE)));
                IngredientsPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));

                IngredientsPojo.setSoap_id(SoapId);

                IngredientsPojo.setGrams(c.getString(c.getColumnIndex(KEY_WEIGHT)));
                IngredientsPojo.setPercentage(c.getString(c.getColumnIndex(KEY_PERCENTAGE)));


                IngredientsPojoArrayList.add(IngredientsPojo);


            } while (c.moveToNext());

            c.close();

        }

        return IngredientsPojoArrayList;
    }

    public ArrayList<IngredientsPojo> getSoapNotes(String SoapId) {

        ArrayList<IngredientsPojo> IngredientsPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SOAP_NOTES+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {
                IngredientsPojo IngredientsPojo = new IngredientsPojo();

                IngredientsPojo.setNotes(c.getString(c.getColumnIndex(KEY_NOTES)));
                IngredientsPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));
                IngredientsPojo.setPriority(c.getString(c.getColumnIndex(KEY_PRIORITY)));

                IngredientsPojoArrayList.add(IngredientsPojo);


            } while (c.moveToNext());

            c.close();

        }

        return IngredientsPojoArrayList;
    }


    public SoapTrackerPojo getSoapInformation(String SoapId) {

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        SoapTrackerPojo soapTrackerPojo = new SoapTrackerPojo();

        if (c.moveToFirst()) {

            do {

                soapTrackerPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));

                soapTrackerPojo.setName(c.getString(c.getColumnIndex(KEY_SOAP_NAME)));
                soapTrackerPojo.setDateIn(c.getString(c.getColumnIndex(KEY_DATE_IN)));
                soapTrackerPojo.setDateOut(c.getString(c.getColumnIndex(KEY_DATE_OUT)));
                soapTrackerPojo.setCondition(c.getString(c.getColumnIndex(KEY_CONDITION)));

                soapTrackerPojo.setPicPath(c.getString(c.getColumnIndex(KEY_PHOTO_PATH)));

                byte[] imgByte = c.getBlob(2);
                soapTrackerPojo.setPhoto(imgByte);

            } while (c.moveToNext());

            c.close();

        }

        return soapTrackerPojo;
    }


}
