package com.nimo.ten.mlkittest.SoapTracker.Database;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator;
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.SoapOilsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.CheckboxPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.EssentialsOilsData;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.OilsData;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.OilsLiquidData;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.ProfilesPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.RecipeDetailsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "soap_tracking";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_SOAP_DETALS = "soap_details";
    public static final String TABLE_SOAP_INGREDIENTS = "soap_ingredients";
    public static final String TABLE_SOAP_NOTES = "soap_notes";
    public static final String TABLE_SOAP_LYE = "soap_lye";
    public static final String TABLE_SOAP_OILS = "soap_oils";
    public static final String TABLE_RECIPE_TABLE = "recipe_table";
    public static final String TABLE_ESSENTIAL_OILS = "essential_oils_table";

    public static final String TABLE_SOAP_MY_OILS = "soap_my_oils";

    public static final String TABLE_PROFILES_DETAILS = "profiles_details";

    public static final String KEY_ID = "id";

    public static final String KEY_SOAP_NAME = "soap_name";
    public static final String KEY_DATE_IN = "date_in";
    public static final String KEY_DATE_OUT = "date_out";
    public static final String KEY_CONDITION = "condition";
    public static final String KEY_PHOTO1_Id = "photo1_id";

    public static final String KEY_OIL_NAME = "oil_name";
    public static final String KEY_NAOH = "naoh_weight";

    public static final String KEY_RECIPE_NAME = "recipe_name";

    public static final String KEY_PHOTO_PATH = "pic_path";

    public static final String KEY_SOAP_ID = "soap_id";
    public static final String KEY_INGREDIENTS = "ingredients";
    public static final String KEY_SUBSTANCE = "substance";
    public static final String KEY_PERCENTAGE = "percentage";
    public static final String KEY_WEIGHT = "weight";

    public static final String KEY_NOTES = "notes";
    public static final String KEY_PRIORITY = "priority";

    public static final String KEY_NOTIFICATION_STATUS = "notification_status";

    public static final String KEY_TOTAL_WEIGHT = "total_weight";
    public static final String KEY_NAOH_WEIGHT = "total_lye_weight";
    public static final String KEY_LIQUID_WEIGHT = "total_liquid_weight";

    public static final String KEY_NAOH_RATIO = "lye_ratio";
    public static final String KEY_LIQUID_RATIO = "liquid_ratio";

    public static final String KEY_ESSENTIAL_OIL_WEIGHT = "essential_oil";
    public static final String KEY_ESSENTIAL_OIL_RATIO = "essential_ratio";
    public static final String KEY_ESSENTIAL_OIL_NAME = "essential_name";

    public static final String KEY_SUPER_FAT = "supper_fat";

    public static final String KEY_NAME = "user_name";
    public static final String KEY_EMAIL = "user_email";
    public static final String KEY_PHONE = "user_phone";
    public static final String KEY_IG = "user_ig";

    public static final String KEY_TOTAL_OIL_AMOUNT = "oil_amount";

    private double SoapWeight = 0.0;
    private double OilWeight = 0.0;

    private double NaohRatio = 0.0;
    private double LiquidRatio = 0.0;
    private String SoapId;

    ContentResolver mContentResolver;

    private Calculator calculator = new Calculator();

    public static final String CREATE_TABLE_SOAP_OILS = "CREATE TABLE "
            + TABLE_SOAP_OILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_OIL_NAME + " TEXT, " +
            KEY_NAOH + " TEXT " + " );";

    public static final String CREATE_TABLE_SOAP_MY_OILS = "CREATE TABLE "
            + TABLE_SOAP_MY_OILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_OIL_NAME + " TEXT, " +
            KEY_NAOH + " TEXT, " +
            KEY_WEIGHT + " TEXT, " +
            KEY_PERCENTAGE + " TEXT, " +
            KEY_NAOH_WEIGHT + " TEXT " + " );";

    public static final String CREATE_TABLE_ESSENTIAL_OILS = "CREATE TABLE "
            + TABLE_ESSENTIAL_OILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_ESSENTIAL_OIL_NAME + " TEXT, " +
            KEY_PERCENTAGE + " TEXT, " +
            KEY_WEIGHT + " TEXT " + " );";

    public static final String CREATE_TABLE_SOAP_INGREDIENTS = "CREATE TABLE "
            + TABLE_SOAP_INGREDIENTS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_INGREDIENTS + " TEXT, "+
            KEY_PERCENTAGE + " TEXT, "+
            KEY_WEIGHT + " TEXT " + " );";

    public static final String CREATE_TABLE_RECIPE_TABLE = "CREATE TABLE "
            + TABLE_RECIPE_TABLE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_RECIPE_NAME + " TEXT, " +
            KEY_DATE_IN + " TEXT, " +

            KEY_LIQUID_WEIGHT + " TEXT, " +
            KEY_NAOH_WEIGHT + " TEXT, " +

            KEY_NAOH_RATIO + " TEXT, " +
            KEY_LIQUID_RATIO + " TEXT, " +

            KEY_ESSENTIAL_OIL_WEIGHT + " TEXT, " +
            KEY_ESSENTIAL_OIL_RATIO + " TEXT, " +
            KEY_SUPER_FAT + " TEXT, " +

            KEY_TOTAL_OIL_AMOUNT + " TEXT, " +

            KEY_TOTAL_WEIGHT + " TEXT " + " );";

    public static final String CREATE_TABLE_PROFILES_DETAILS = "CREATE TABLE "
            + TABLE_PROFILES_DETAILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_NAME + " TEXT, " +
            KEY_EMAIL + " TEXT, "+
            KEY_PHONE + " TEXT, "+
            KEY_IG + " TEXT " + " );";


    public static final String CREATE_TABLE_SOAP_LYE = "CREATE TABLE "
            + TABLE_SOAP_LYE + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_SUBSTANCE + " TEXT, "+
            KEY_PERCENTAGE + " TEXT, "+
            KEY_WEIGHT + " TEXT " + " );";


    public static final String CREATE_TABLE_SOAP_NOTES = "CREATE TABLE "
            + TABLE_SOAP_NOTES + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_NOTES + " TEXT, "+
            KEY_PRIORITY + " TEXT " + " );";

    public static final String CREATE_TABLE_SOAP_DETALS = "CREATE TABLE "
            + TABLE_SOAP_DETALS + "(" + KEY_ID + " TEXT,"+
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

    public boolean isUpdated = false;

    public int updater;

    public String WeightStatus;

    public Double txtRemWeight;

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
        db.execSQL(CREATE_TABLE_PROFILES_DETAILS);
        db.execSQL(CREATE_TABLE_SOAP_OILS);
        db.execSQL(CREATE_TABLE_SOAP_MY_OILS);
        db.execSQL(CREATE_TABLE_RECIPE_TABLE);
        db.execSQL(CREATE_TABLE_ESSENTIAL_OILS);

        Log.d("++++", CREATE_TABLE_SOAP_DETALS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_DETALS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_INGREDIENTS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_NOTES +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_LYE +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_PROFILES_DETAILS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_OILS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_MY_OILS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_RECIPE_TABLE +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_ESSENTIAL_OILS +"'");

        onCreate(db);

    }


    public void AddMySoapOils(String SoapId, String OilName, String Naoh){

        SQLiteDatabase db = this.getWritableDatabase();

        Cursor cursorCheck = db.rawQuery("SELECT * FROM "+TABLE_SOAP_MY_OILS+" WHERE "+KEY_OIL_NAME+"=?" + " AND " + KEY_SOAP_ID + "=?", new String[] {OilName,SoapId});
        if (cursorCheck.getCount() > 0){

        }else {

            ContentValues contentValues = new ContentValues();
            contentValues.put(KEY_OIL_NAME, OilName);
            contentValues.put(KEY_SOAP_ID, SoapId);
            contentValues.put(KEY_WEIGHT, "0.0");
            contentValues.put(KEY_NAOH_WEIGHT, "0.0");
            contentValues.put(KEY_PERCENTAGE, "0.0");
            contentValues.put(KEY_NAOH, Naoh);

            db.insert(TABLE_SOAP_MY_OILS, null, contentValues);
        }


    }

    public long AddRecipe(String DateIn, String recipeName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RECIPE_NAME, recipeName);
        contentValues.put(KEY_DATE_IN, DateIn);

        contentValues.put(KEY_NAOH_WEIGHT, 0.0);
        contentValues.put(KEY_LIQUID_WEIGHT, 0.0);
        contentValues.put(KEY_TOTAL_WEIGHT, "0.0");

        contentValues.put(KEY_LIQUID_RATIO, 0.667);
        contentValues.put(KEY_NAOH_RATIO, 0.333);

        contentValues.put(KEY_ESSENTIAL_OIL_WEIGHT, 0.0);
        contentValues.put(KEY_ESSENTIAL_OIL_RATIO, 0.0);

        contentValues.put(KEY_SUPER_FAT, 0.0);

        contentValues.put(KEY_TOTAL_OIL_AMOUNT, 0.0);

        long id = db.insert(TABLE_RECIPE_TABLE, null, contentValues);
        return id;
    }
    public void AddEssentialOils(String essential_oil, String soap_id, String percentage, String weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues soap_tracker = new ContentValues();
        soap_tracker.put(KEY_ESSENTIAL_OIL_NAME, essential_oil);
        soap_tracker.put(KEY_SOAP_ID, soap_id);
        soap_tracker.put(KEY_PERCENTAGE, percentage);
        soap_tracker.put(KEY_WEIGHT, weight);

        db.insert( TABLE_ESSENTIAL_OILS, null, soap_tracker );
    }

    public void updateRecipe(String id, String TotalWeight, String WaterWeight, String Naoh){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TOTAL_WEIGHT, TotalWeight);
        contentValues.put(KEY_LIQUID_WEIGHT, WaterWeight);
        contentValues.put(KEY_NAOH_WEIGHT, Naoh);

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(id)});

    }

    public void updateOilAmount(String id, double TotalWeight, Context context){

        OilWeight = 0.0;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_TOTAL_OIL_AMOUNT, TotalWeight);

        //Update the oils grams too using the provided percentages

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_MY_OILS  +" WHERE " + KEY_SOAP_ID + " = '"+id+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {

            do {

                String oilId = c.getString(c.getColumnIndex(KEY_ID));

                double oilPercentage = c.getDouble(c.getColumnIndex(KEY_PERCENTAGE));
                OilWeight = oilPercentage * TotalWeight;

                String NewOilWeight = String.valueOf(OilWeight);

                updateMyOilWeight(oilId, NewOilWeight);
                calculator.getSapofinication(id, oilId,NewOilWeight,context);

            } while (c.moveToNext());

            c.close();

        }

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(id)});

    }

    public void AddSoapOils(String OilName, Double Naoh){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_OIL_NAME, OilName);
        contentValues.put(KEY_NAOH, Naoh);
        db.insert(TABLE_SOAP_OILS, null, contentValues);

    }

    public ArrayList<CheckboxPojo> getSoapOils() {

        ArrayList<CheckboxPojo> soapTrackerPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SOAP_OILS ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                CheckboxPojo soapTrackerPojo = new CheckboxPojo();
                soapTrackerPojo.setLiquids(c.getString(c.getColumnIndex(KEY_OIL_NAME)));
                soapTrackerPojo.setNaOh(c.getString(c.getColumnIndex(KEY_NAOH)));
                soapTrackerPojoArrayList.add(soapTrackerPojo);


            } while (c.moveToNext());

            c.close();

        }

        return soapTrackerPojoArrayList;
    }

    public ArrayList<RecipeDetailsPojo> getMyRecipes() {

        ArrayList<RecipeDetailsPojo> soapTrackerPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                RecipeDetailsPojo soapTrackerPojo = new RecipeDetailsPojo();

                soapTrackerPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));
                soapTrackerPojo.setRecipeName(c.getString(c.getColumnIndex(KEY_RECIPE_NAME)));
                soapTrackerPojo.setDate_in(c.getString(c.getColumnIndex(KEY_DATE_IN)));
                soapTrackerPojo.setLiquidWeight(c.getString(c.getColumnIndex(KEY_LIQUID_WEIGHT)));
                soapTrackerPojo.setLyeWeight(c.getString(c.getColumnIndex(KEY_NAOH_WEIGHT)));
                soapTrackerPojo.setLyeRatio(c.getString(c.getColumnIndex(KEY_NAOH_RATIO)));
                soapTrackerPojo.setLiquid(c.getString(c.getColumnIndex(KEY_LIQUID_RATIO)));
                soapTrackerPojo.setEssentialOil(c.getString(c.getColumnIndex(KEY_ESSENTIAL_OIL_WEIGHT)));
                soapTrackerPojo.setEssentialRatio(c.getString(c.getColumnIndex(KEY_ESSENTIAL_OIL_RATIO)));
                soapTrackerPojo.setSuperFat(c.getString(c.getColumnIndex(KEY_SUPER_FAT)));
                soapTrackerPojo.setTotalWeight(c.getString(c.getColumnIndex(KEY_TOTAL_WEIGHT)));

                soapTrackerPojoArrayList.add(soapTrackerPojo);


            } while (c.moveToNext());

            c.close();

        }

        return soapTrackerPojoArrayList;
    }


    public ArrayList<SoapOilsPojo> getMySoapOils(String SoapId) {

            ArrayList<SoapOilsPojo> soapTrackerPojoArrayList = new ArrayList<>();
            String selectQuery = "SELECT * FROM " + TABLE_SOAP_MY_OILS +" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {

                do {

                    SoapOilsPojo soapOilsPojo = new SoapOilsPojo();
                    soapOilsPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));
                    soapOilsPojo.setOil_name(c.getString(c.getColumnIndex(KEY_OIL_NAME)));
                    soapOilsPojo.setNaoh_weight(c.getString(c.getColumnIndex(KEY_NAOH)));
                    soapOilsPojo.setOilWeight(c.getString(c.getColumnIndex(KEY_WEIGHT)));
                    soapOilsPojo.setPercentage(c.getDouble(c.getColumnIndex(KEY_PERCENTAGE)));
                    soapTrackerPojoArrayList.add(soapOilsPojo);


                } while (c.moveToNext());

                c.close();

            }

            return soapTrackerPojoArrayList;
        }


    public long AddSoapTracker(String id, String soap_name, String date_in, String date_out, byte[] image, String txtFilePath){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues soap_tracker = new ContentValues();
        soap_tracker.put(KEY_ID, id);
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

    public void deleteMySoap(String oil_id, Context context) {

        SQLiteDatabase db = this.getWritableDatabase();

        //Get the Soap id from the deleted Oil id
        String SoapId = getSoapId(oil_id).getSoapId();
        double OilWeight = getSoapId(oil_id).getOilWeight();

        Calculator calculator = new Calculator();
        double WeightUpdate = calculator.getRemainingOilWeight(SoapId, OilWeight, context);

        updateMySoapWeight(SoapId, WeightUpdate);

        db.delete(TABLE_SOAP_MY_OILS, KEY_ID + " = ?",new String[]{String.valueOf(oil_id)});

    }

    public void deleteNotes(int id) {

        SQLiteDatabase db = this.getWritableDatabase();

        db.delete(TABLE_SOAP_NOTES, KEY_ID + " = ?",new String[]{String.valueOf(id)});

    }

    public void updateSoapCondition(String id, String status){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_CONDITION, status);

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

    public ArrayList<IngredientsPojo> getSoapEssentialOils(String SoapId) {

        ArrayList<IngredientsPojo> IngredientsPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_ESSENTIAL_OILS+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                IngredientsPojo IngredientsPojo = new IngredientsPojo();

                IngredientsPojo.setIngredient_name(c.getString(c.getColumnIndex(KEY_ESSENTIAL_OIL_NAME)));
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

    public void AddProfilesDetails(String txtName, String txtEmail, String txtPhone, String txtIg){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_NAME, txtName);
        contentValues.put(KEY_EMAIL, txtEmail);
        contentValues.put(KEY_PHONE, txtPhone);
        contentValues.put(KEY_IG, txtIg);

        db.insert( TABLE_PROFILES_DETAILS, null, contentValues );

    }

    public ArrayList<ProfilesPojo> getProfilesInformation() {

        ArrayList<ProfilesPojo> soapTrackerPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PROFILES_DETAILS ;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                String txtUsername = c.getString(c.getColumnIndex(KEY_NAME));
                String txtUseremail = c.getString(c.getColumnIndex(KEY_EMAIL));
                String txtUserphone = c.getString(c.getColumnIndex(KEY_PHONE));

                ProfilesPojo profilesPojo = new ProfilesPojo(txtUsername,txtUseremail, txtUserphone);

                soapTrackerPojoArrayList.add(profilesPojo);


            } while (c.moveToNext());

            c.close();

        }

        return soapTrackerPojoArrayList;
    }

    public void updateMyOilWeight(String id, String weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_WEIGHT, weight);

        db.update(TABLE_SOAP_MY_OILS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }

    public void updateMyOilPercentage(String recipeId, String id, String percentage1, Context context){

        double percentage = Double.parseDouble(percentage1) / 100;

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_PERCENTAGE, percentage);

        db.update(TABLE_SOAP_MY_OILS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE  +" WHERE " + KEY_ID + " = '"+recipeId+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {

            do {

                double TotalWeight = c.getDouble(c.getColumnIndex(KEY_TOTAL_OIL_AMOUNT));

                OilWeight = percentage * TotalWeight;

                String NewOilWeight = String.valueOf(OilWeight);

                updateMyOilWeight(id, NewOilWeight);
                calculator.getSapofinication(recipeId, id,NewOilWeight,context);

            } while (c.moveToNext());

            c.close();

        }



    }
    public RecipeDetailsPojo getOilsWeight(String recipeId){

        SQLiteDatabase db = this.getWritableDatabase();
        RecipeDetailsPojo recipeDetailsPojo = new RecipeDetailsPojo();

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE  +" WHERE " + KEY_ID + " = '"+recipeId+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {

            do {

                String OilWeight = c.getString(c.getColumnIndex(KEY_TOTAL_OIL_AMOUNT));
                String superFat = c.getString(c.getColumnIndex(KEY_SUPER_FAT));

                String TotalWeight = c.getString(c.getColumnIndex(KEY_TOTAL_WEIGHT));

                String WaterWeight = c.getString(c.getColumnIndex(KEY_LIQUID_WEIGHT));
                String LyeWeight = c.getString(c.getColumnIndex(KEY_NAOH_WEIGHT));

                String LiquidRatio = c.getString(c.getColumnIndex(KEY_LIQUID_RATIO));
                String NaoHRatio = c.getString(c.getColumnIndex(KEY_NAOH_RATIO));

                recipeDetailsPojo.setOilWeight(OilWeight);
                recipeDetailsPojo.setSuperFat(superFat);
                recipeDetailsPojo.setTotalWeight(TotalWeight);

                recipeDetailsPojo.setLiquidWeight(WaterWeight);
                recipeDetailsPojo.setLyeWeight(LyeWeight);

                recipeDetailsPojo.setLiquid(LiquidRatio);
                recipeDetailsPojo.setLyeRatio(NaoHRatio);

            } while (c.moveToNext());

            c.close();

        }
        return recipeDetailsPojo;
    }

    public void updateMySoapWeight(String id, double weight){
        SQLiteDatabase db = this.getWritableDatabase();
//
//        OilWeight = 0.0;
//
//        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+id+"'";
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null) {
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    double dbOilWeight = c.getDouble(c.getColumnIndex(KEY_TOTAL_OIL_AMOUNT));
//
//                    OilWeight = weight + dbOilWeight;
//
//                } while (c.moveToNext());
//
//            }
//
//            c.close();
//        }

        ContentValues cv = new ContentValues();
        cv.put(KEY_TOTAL_WEIGHT, weight);

        updateMySoapLiquidLyeWeight(id, weight);

        db.update(TABLE_RECIPE_TABLE, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }

    public void updateMySoapLiquidLyeWeight(String id, double TotalWeight){

        //Get the ratios and update the data
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        OilWeight = 0.0;

        double Liquidratio = getLiquidLyeWeights(id).getLiquid_weight();
        double Lyeratio = getLiquidLyeWeights(id).getLye_weight();

        double NaoHWeight = calculator.getLiquidOilWeight(TotalWeight, Liquidratio, Lyeratio).getLye_weight();
        double LiquidWeight = calculator.getLiquidOilWeight(TotalWeight, Liquidratio, Lyeratio).getLiquid_weight();

        updateWeights(id, NaoHWeight, LiquidWeight);

//        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+id+"'";
//        Cursor c = db.rawQuery(selectQuery, null);
//
//        if (c != null) {
//
//            if (c.moveToFirst()) {
//
//                do {
//
//                    double dbOilWeight = c.getDouble(c.getColumnIndex(KEY_TOTAL_OIL_AMOUNT));
//
//                    OilWeight = TotalWeight + dbOilWeight;
//
//                } while (c.moveToNext());
//
//            }
//
//            c.close();
//        }

        cv.put(KEY_TOTAL_WEIGHT, TotalWeight);

        db.update(TABLE_RECIPE_TABLE, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }
    public void updateMySap(String txtSoapId, String id, double weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAOH_WEIGHT, weight);

        db.update(TABLE_SOAP_MY_OILS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        double TotalWeight = getOilWeight(txtSoapId);

        updateMySoapWeight(txtSoapId, TotalWeight);

    }

    public double getOilWeight(String OilId){

        SoapWeight=0.0;

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_MY_OILS+" WHERE " + KEY_SOAP_ID + " = '"+OilId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.moveToFirst()) {

                do {

                    double DbNaohName = c.getDouble(c.getColumnIndex(KEY_NAOH_WEIGHT));

                    SoapWeight = DbNaohName + SoapWeight;

                } while (c.moveToNext());


            }

            c.close();

        }

        return SoapWeight;

    }

    public void updateRatiosPercentages(String txtSoapId, String lyeRatio, String liquidRatio){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        double TotalRatio = Double.parseDouble(lyeRatio) + Double.parseDouble(liquidRatio);

        double Lyeratio = Double.parseDouble(lyeRatio) / TotalRatio;
        double Liquidratio = Double.parseDouble(liquidRatio) / TotalRatio;

        cv.put(KEY_NAOH_RATIO, Lyeratio);
        cv.put(KEY_LIQUID_RATIO, Liquidratio);

        double TotalWeight = getTotalOilWeight(txtSoapId);

        double NaoHWeight = calculator.getLiquidOilWeight(TotalWeight, Liquidratio, Lyeratio).getLye_weight();
        double LiquidWeight = calculator.getLiquidOilWeight(TotalWeight, Liquidratio, Lyeratio).getLiquid_weight();

        updateWeights(txtSoapId, NaoHWeight, LiquidWeight);

        //Ratios are saved as decimals

        db.update(TABLE_RECIPE_TABLE, cv, KEY_ID + " = ?", new String[]{String.valueOf(txtSoapId)});

    }

    public void updateWeights(String txtSoapId, double NaoHWeight, double LiquidWeight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAOH_WEIGHT, NaoHWeight);
        cv.put(KEY_LIQUID_WEIGHT, LiquidWeight);

        db.update(TABLE_RECIPE_TABLE, cv, KEY_ID + " = ?", new String[]{String.valueOf(txtSoapId)});

    }

    public OilsLiquidData getWaterLyeAmount(String SoapId){

        OilsLiquidData oilsLiquidData = new OilsLiquidData();

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.moveToFirst()) {

                do {

                    double txtLiquid = c.getDouble(c.getColumnIndex(KEY_LIQUID_WEIGHT));
                    double txtLye = c.getDouble(c.getColumnIndex(KEY_NAOH_WEIGHT));

                    oilsLiquidData.setLye_weight(txtLye);
                    oilsLiquidData.setLiquid_weight(txtLiquid);


                } while (c.moveToNext());


            }

            c.close();

        }

        return oilsLiquidData;

    }


    public double getTotalOilWeight(String SoapId){

        SoapWeight = 0.0;

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.moveToFirst()) {

                do {


                    SoapWeight = c.getDouble(c.getColumnIndex(KEY_TOTAL_WEIGHT));


                } while (c.moveToNext());


            }

            c.close();

        }

        return SoapWeight;

    }

    public OilsData getSoapId(String OilId){

         OilsData oilsData = new OilsData();

         String selectQuery = "SELECT * FROM " + TABLE_SOAP_MY_OILS+" WHERE " + KEY_ID + " = '"+OilId+"'";
         SQLiteDatabase db = this.getReadableDatabase();
         Cursor c = db.rawQuery(selectQuery, null);

         if (c != null) {

             if (c.moveToFirst()) {

                 do {

                     String SoapId = c.getString(c.getColumnIndex(KEY_SOAP_ID));
                     double OilWeight = c.getDouble(c.getColumnIndex(KEY_NAOH_WEIGHT));

                     oilsData.setOilWeight(OilWeight);
                     oilsData.setSoapId(SoapId);

                 } while (c.moveToNext());

             }

             c.close();

         }

         return oilsData;

    }

    public OilsLiquidData getLiquidLyeWeights(String SoapId){

        OilsLiquidData oilsData = new OilsLiquidData();

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.moveToFirst()) {

                do {

                    NaohRatio = c.getDouble(c.getColumnIndex(KEY_NAOH_RATIO));
                    LiquidRatio = c.getDouble(c.getColumnIndex(KEY_LIQUID_RATIO));

                    oilsData.setLiquid_weight(LiquidRatio);
                    oilsData.setLye_weight(NaohRatio);

                } while (c.moveToNext());

            }

            c.close();

        }

        return oilsData;

    }

    public double getTotalOils(String SoapId){

        OilWeight = 0.0;

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.moveToFirst()) {

                do {

                    double liquidWeight = c.getDouble(c.getColumnIndex(KEY_LIQUID_WEIGHT));
                    double lyeWeight = c.getDouble(c.getColumnIndex(KEY_NAOH_WEIGHT));
                    double totalWeight = c.getDouble(c.getColumnIndex(KEY_TOTAL_WEIGHT));

                    double EssentialOil = c.getDouble(c.getColumnIndex(KEY_ESSENTIAL_OIL_RATIO));

                    OilWeight = (liquidWeight + lyeWeight + totalWeight) * EssentialOil;

                } while (c.moveToNext());

            }

            c.close();

        }

        updateEssentialWeight(SoapId, OilWeight);

        return OilWeight;

    }

    public void updateEssentialWeight(String SoapId, double Weight){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(KEY_ESSENTIAL_OIL_WEIGHT, Weight);

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(SoapId)});
    }

    public void updateEssentialRatio(String SoapId, double Weight){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(KEY_ESSENTIAL_OIL_RATIO, Weight);

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(SoapId)});
    }

    public double getTotalOilsWeight(String OilId){

        SoapWeight=0.0;

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_MY_OILS+" WHERE " + KEY_SOAP_ID + " = '"+OilId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.moveToFirst()) {

                do {

                    double DbNaohName = c.getDouble(c.getColumnIndex(KEY_NAOH_WEIGHT));
                    String DbName = c.getString(c.getColumnIndex(KEY_OIL_NAME));

                    SoapWeight = DbNaohName + SoapWeight;

                } while (c.moveToNext());


            }

            c.close();

        }
        return SoapWeight;

    }

    public double getTotalOils_SuperFat(String SoapId){

        OilWeight = 0.0;

        double totalWeight = getTotalOilsWeight(SoapId);

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.moveToFirst()) {

                do {

                    double SuperFat = c.getDouble(c.getColumnIndex(KEY_SUPER_FAT));

                    OilWeight = ((100 - SuperFat) / 100) * totalWeight;

                } while (c.moveToNext());

            }

            c.close();

        }

        updateTotalWeight_SuperFat(SoapId, OilWeight);

        return OilWeight;

    }

    public void updateTotalWeight_SuperFat(String SoapId, double Weight){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(KEY_TOTAL_WEIGHT, Weight);

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(SoapId)});
    }

    public void updateSuperFatRatio(String SoapId, double Weight){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(KEY_SUPER_FAT, Weight);

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(SoapId)});

        getTotalOils_SuperFat(SoapId);

    }

    public void updateEssentialOil(String SoapId, double Weight){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(KEY_ESSENTIAL_OIL_WEIGHT, Weight);

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(SoapId)});


    }

    public double getTotalEssentials(String SoapId){

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null) {

            if (c.moveToFirst()) {

                do {

                    OilWeight = c.getDouble(c.getColumnIndex(KEY_ESSENTIAL_OIL_WEIGHT));

                } while (c.moveToNext());


            }

            c.close();

        }

        return OilWeight;

    }

}
