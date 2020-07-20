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
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.OilsData;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.OilsLiquidData;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.RecipeDetailsPojo;

import java.util.ArrayList;

public class DatabaseHelperNew extends SQLiteOpenHelper {

    public static String DATABASE_NAME = "soap_moulding";
    public static final int DATABASE_VERSION = 1;

    public static final String TABLE_RECIPE_TABLE = "recipe_table";
    public static final String TABLE_SOAP_MY_OILS = "soap_my_oils";
    public static final String TABLE_SOAP_OILS = "soap_oils";
    public static final String TABLE_ESSENTIAL_OILS = "essential_oils_table";


    public static final String KEY_ID = "id";
    public static final String KEY_RECIPE_NAME = "recipe_name";
    public static final String KEY_DATE_IN = "date_in";
    public static final String KEY_TOTAL_WEIGHT = "total_weight";
    public static final String KEY_NAOH_WEIGHT = "total_lye_weight";
    public static final String KEY_LIQUID_WEIGHT = "total_liquid_weight";
    public static final String KEY_NAOH_RATIO = "lye_ratio";
    public static final String KEY_LIQUID_RATIO = "liquid_ratio";
    public static final String KEY_ESSENTIAL_OIL_WEIGHT = "essential_oil";
    public static final String KEY_ESSENTIAL_OIL_RATIO = "essential_ratio";
    public static final String KEY_ESSENTIAL_OIL_NAME = "essential_name";
    public static final String KEY_SUPER_FAT = "supper_fat";
    public static final String KEY_TOTAL_OIL_AMOUNT = "oil_amount";

    public static final String KEY_SOAP_ID = "soap_id";
    public static final String KEY_OIL_NAME = "oil_name";
    public static final String KEY_NAOH = "naoh_weight";
    public static final String KEY_WEIGHT = "weight";
    public static final String KEY_PERCENTAGE = "percentage";

    public static final String KEY_INGREDIENTS = "ingredients";

    private double OilWeight = 0.0;
    private double LyeWeight = 0.0;
    private double txtNaohWeight = 0.0;
    private double TotalSap = 0.0;
    private double SoapWeight = 0.0;
    private double NaohRatio = 0.0;
    private double LiquidRatio = 0.0;

    ContentResolver mContentResolver;


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

    public static final String CREATE_TABLE_SOAP_MY_OILS = "CREATE TABLE "
            + TABLE_SOAP_MY_OILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_OIL_NAME + " TEXT, " +
            KEY_NAOH + " TEXT, " +
            KEY_WEIGHT + " TEXT, " +
            KEY_PERCENTAGE + " TEXT, " +
            KEY_NAOH_WEIGHT + " TEXT " + " );";

    public static final String CREATE_TABLE_SOAP_OILS = "CREATE TABLE "
            + TABLE_SOAP_OILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_OIL_NAME + " TEXT, " +
            KEY_NAOH + " TEXT " + " );";

    public static final String CREATE_TABLE_ESSENTIAL_OILS = "CREATE TABLE "
            + TABLE_ESSENTIAL_OILS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"+
            KEY_SOAP_ID + " TEXT, " +
            KEY_ESSENTIAL_OIL_NAME + " TEXT, " +
            KEY_PERCENTAGE + " TEXT, " +
            KEY_WEIGHT + " TEXT " + " );";

    public DatabaseHelperNew(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

        mContentResolver = context.getContentResolver();

    }
    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(CREATE_TABLE_RECIPE_TABLE);
        db.execSQL(CREATE_TABLE_SOAP_MY_OILS);
        db.execSQL(CREATE_TABLE_SOAP_OILS);
        db.execSQL(CREATE_TABLE_ESSENTIAL_OILS);


    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_RECIPE_TABLE +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_MY_OILS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_SOAP_OILS +"'");
        db.execSQL(" DROP TABLE IF EXISTS '" + TABLE_ESSENTIAL_OILS +"'");


        onCreate(db);

    }
    public void AddSoapOils(String OilName, Double Naoh){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_OIL_NAME, OilName);
        contentValues.put(KEY_NAOH, Naoh);
        db.insert(TABLE_SOAP_OILS, null, contentValues);

    }
    public long AddRecipe(String DateIn, String recipeName){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(KEY_RECIPE_NAME, recipeName);
        contentValues.put(KEY_DATE_IN, DateIn);

        contentValues.put(KEY_NAOH_WEIGHT, 0.0);
        contentValues.put(KEY_LIQUID_WEIGHT, 0.0);
        contentValues.put(KEY_TOTAL_WEIGHT, 0.0);

        contentValues.put(KEY_LIQUID_RATIO, 0.667);
        contentValues.put(KEY_NAOH_RATIO, 0.333);

        contentValues.put(KEY_ESSENTIAL_OIL_WEIGHT, 0.0);
        contentValues.put(KEY_ESSENTIAL_OIL_RATIO, 0.0);

        contentValues.put(KEY_SUPER_FAT, 0.0);

        contentValues.put(KEY_TOTAL_OIL_AMOUNT, 0.0);

        long id = db.insert(TABLE_RECIPE_TABLE, null, contentValues);
        return id;
    }

    public RecipeDetailsPojo getOilsWeight(String recipeId){

        SQLiteDatabase db = this.getWritableDatabase();
        RecipeDetailsPojo recipeDetailsPojo = new RecipeDetailsPojo();

        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE  +" WHERE " + KEY_ID + " = '"+recipeId+"'";
        Cursor c = db.rawQuery(selectQuery, null);

        if (c != null && c.moveToFirst()) {

            do {

                String recipeName = c.getString(c.getColumnIndex(KEY_RECIPE_NAME));
                String dateCreated = c.getString(c.getColumnIndex(KEY_DATE_IN));

                String OilWeight = c.getString(c.getColumnIndex(KEY_TOTAL_OIL_AMOUNT));
                String superFat = c.getString(c.getColumnIndex(KEY_SUPER_FAT));

                String TotalWeight = c.getString(c.getColumnIndex(KEY_TOTAL_WEIGHT));

                String WaterWeight = c.getString(c.getColumnIndex(KEY_LIQUID_WEIGHT));
                String LyeWeight = c.getString(c.getColumnIndex(KEY_NAOH_WEIGHT));

                String LiquidRatio = c.getString(c.getColumnIndex(KEY_LIQUID_RATIO));
                String NaoHRatio = c.getString(c.getColumnIndex(KEY_NAOH_RATIO));

                String essentialWeight = c.getString(c.getColumnIndex(KEY_ESSENTIAL_OIL_WEIGHT));
                String essentialPercentage = c.getString(c.getColumnIndex(KEY_ESSENTIAL_OIL_RATIO));

                recipeDetailsPojo.setRecipeName(recipeName);
                recipeDetailsPojo.setDate_in(dateCreated);

                recipeDetailsPojo.setOilWeight(OilWeight);
                recipeDetailsPojo.setSuperFat(superFat);
                recipeDetailsPojo.setTotalWeight(TotalWeight);

                recipeDetailsPojo.setLiquidWeight(WaterWeight);
                recipeDetailsPojo.setLyeWeight(LyeWeight);

                recipeDetailsPojo.setLiquid(LiquidRatio);
                recipeDetailsPojo.setLyeRatio(NaoHRatio);

                recipeDetailsPojo.setEssentialRatio(essentialPercentage);
                recipeDetailsPojo.setEssentialOil(essentialWeight);

            } while (c.moveToNext());

            c.close();

        }
        return recipeDetailsPojo;
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
    public void updateOilAmount(String id, double TotalWeight){

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
                double TotalSapo =  getSapofinication(oilId,NewOilWeight);
                updateMySap(id,oilId, TotalSapo);

            } while (c.moveToNext());

            c.close();

        }

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(id)});

    }
    public void updateMyOilWeight(String id, String weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_WEIGHT, weight);

        db.update(TABLE_SOAP_MY_OILS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});


    }
    public double getSapofinication(String OilId, String oilWeight){

        Double OilWeight = Double.parseDouble(oilWeight);

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_MY_OILS+" WHERE " + KEY_ID + " = '"+OilId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                String DbNaohName = c.getString(c.getColumnIndex(KEY_OIL_NAME));
                Double SapNaoh = getOilSaponification(DbNaohName);

                TotalSap = SapNaoh * OilWeight;

            } while (c.moveToNext());

            c.close();


        }

        return TotalSap;


    }
    public void updateMySap(String txtSoapId, String id, double weight){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();
        cv.put(KEY_NAOH_WEIGHT, weight);

        db.update(TABLE_SOAP_MY_OILS, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});

        double TotalWeight = getOilWeight(txtSoapId);

        updateMySoapWeight(txtSoapId, TotalWeight);

    }
    public double getOilWeight(String SoapId){

        SoapWeight=0.0;

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_MY_OILS+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
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
    public void updateMySoapWeight(String id, double LYeweight){

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        double Liquidratio = getLiquidLyeWeights(id).getLiquid_weight();
        double Lyeratio = getLiquidLyeWeights(id).getLye_weight();

        double LiquidWeight = (LYeweight * Liquidratio)/ Lyeratio;
        double Weight = LYeweight + LiquidWeight;

        cv.put(KEY_NAOH_WEIGHT, LYeweight);
        cv.put(KEY_LIQUID_WEIGHT, LiquidWeight);
        cv.put(KEY_TOTAL_WEIGHT, Weight);

        db.update(TABLE_RECIPE_TABLE, cv, KEY_ID + " = ?", new String[]{String.valueOf(id)});

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
    private Double getOilSaponification(String OilName){

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_OILS+" WHERE " + KEY_OIL_NAME + " = '"+OilName+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);
        if (c != null){

            if (c.moveToFirst()) {

                do {

                    txtNaohWeight = c.getDouble(c.getColumnIndex(KEY_NAOH));

                } while (c.moveToNext());


            }

            c.close();
        }

        return txtNaohWeight;
    }
    public void updateSuperFatRatio(String SoapId, double Weight){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(KEY_SUPER_FAT, Weight);

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(SoapId)});

        getTotalOils_SuperFat(SoapId);



    }
    public void getTotalOils_SuperFat(String SoapId){

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

        updateMySoapWeight(SoapId, OilWeight);

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

                    SoapWeight = DbNaohName + SoapWeight;

                } while (c.moveToNext());


            }

            c.close();

        }
        return SoapWeight;

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

                double TotalSapo =  getSapofinication(id,NewOilWeight);
                updateMySap(recipeId,id, TotalSapo);

            } while (c.moveToNext());

            c.close();

        }



    }
    public void updateRatiosPercentages(String txtSoapId, String lyeRatio, String liquidRatio){

        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues cv = new ContentValues();

        double TotalRatio = Double.parseDouble(lyeRatio) + Double.parseDouble(liquidRatio);

        //Ratios are saved as decimals
        double Lyeratio = Double.parseDouble(lyeRatio) / TotalRatio;
        double Liquidratio = Double.parseDouble(liquidRatio) / TotalRatio;

        cv.put(KEY_NAOH_RATIO, Lyeratio);
        cv.put(KEY_LIQUID_RATIO, Liquidratio);
        db.update(TABLE_RECIPE_TABLE, cv, KEY_ID + " = ?", new String[]{String.valueOf(txtSoapId)});

        double TotalWeight = getTotalOilWeight(txtSoapId);

        double LYeweight = TotalWeight * Lyeratio;
        double LiquidWeight = TotalWeight *  Liquidratio;

        cv.put(KEY_NAOH_WEIGHT, LYeweight);
        cv.put(KEY_LIQUID_WEIGHT, LiquidWeight);

        db.update(TABLE_RECIPE_TABLE, cv, KEY_ID + " = ?", new String[]{String.valueOf(txtSoapId)});


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
    public void updateEssentialRatio(String SoapId, double Weight){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(KEY_ESSENTIAL_OIL_RATIO, Weight);

        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?", new String[]{String.valueOf(SoapId)});

        //Update Essential Oil weights

        UpdateEssentialWeights(SoapId);

    }
    public void UpdateEssentialWeights(String SoapId) {

        double EssentialOilWeight = getTotalEssentialOilWeight(SoapId);

        String selectQuery = "SELECT * FROM " + TABLE_ESSENTIAL_OILS+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                String essential_oil_id = c.getString(c.getColumnIndex(KEY_ID));
                double essential_oil_percentage = c.getDouble(c.getColumnIndex(KEY_PERCENTAGE));

                double newWeight = (essential_oil_percentage/100) * EssentialOilWeight;

                updateEssentialWeights(essential_oil_id, newWeight);


            } while (c.moveToNext());

            c.close();

        }

    }
    public void updateEssentialWeights(String essentialId, double Weight){

        ContentValues contentValues = new ContentValues();
        SQLiteDatabase db = this.getWritableDatabase();

        contentValues.put(KEY_WEIGHT, Weight);

        db.update(TABLE_ESSENTIAL_OILS, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(essentialId)});

    }
    public double getTotalEssentialOilWeight(String SoapId) {

        OilWeight = 0.0;

        ArrayList<IngredientsPojo> IngredientsPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_RECIPE_TABLE+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                OilWeight = c.getDouble(c.getColumnIndex(KEY_ESSENTIAL_OIL_WEIGHT));

            } while (c.moveToNext());

            c.close();

        }

        return OilWeight;

    }
    public double getTotalOils(String SoapId){

        OilWeight = 0.0;
        ContentValues contentValues = new ContentValues();

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

                    OilWeight = totalWeight * EssentialOil;

                } while (c.moveToNext());

            }

            c.close();

        }


        contentValues.put(KEY_ESSENTIAL_OIL_WEIGHT, OilWeight);
        db.update(TABLE_RECIPE_TABLE, contentValues, KEY_ID + " = ?",
                new String[]{String.valueOf(SoapId)});

        UpdateEssentialWeights(SoapId);

        return OilWeight;

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

    //Get Data
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
    public ArrayList<IngredientsPojo> getMyOils(String SoapId) {

        ArrayList<IngredientsPojo> soapTrackerPojoArrayList = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_SOAP_MY_OILS +" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                IngredientsPojo soapOilsPojo = new IngredientsPojo();
                soapOilsPojo.setId(c.getString(c.getColumnIndex(KEY_ID)));
                soapOilsPojo.setIngredient_name(c.getString(c.getColumnIndex(KEY_OIL_NAME)));
                soapOilsPojo.setGrams(c.getString(c.getColumnIndex(KEY_WEIGHT)));
                soapOilsPojo.setPercentage(c.getString(c.getColumnIndex(KEY_PERCENTAGE)));
                soapTrackerPojoArrayList.add(soapOilsPojo);


            } while (c.moveToNext());

            c.close();

        }

        return soapTrackerPojoArrayList;
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
    //Delete
    public void deleteEssentialOils(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ESSENTIAL_OILS, KEY_SOAP_ID + " = ?",new String[]{String.valueOf(id)});

    }
    public void deleteOneEssentialOil(String id) {

        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_ESSENTIAL_OILS, KEY_ID + " = ?",new String[]{String.valueOf(id)});

    }
    public void deleteMyOil(String oil_id, Context context) {

        SQLiteDatabase db = this.getWritableDatabase();

        //Get the Soap id from the deleted Oil id
        String SoapId = getSoapId(oil_id).getSoapId();
        double OilWeight = getSoapId(oil_id).getOilWeight();

        Calculator calculator = new Calculator();
        double WeightUpdate = calculator.getRemainingOilWeight1(SoapId, OilWeight, context);

        updateMySoapWeight(SoapId, WeightUpdate);

        db.delete(TABLE_SOAP_MY_OILS, KEY_ID + " = ?",new String[]{String.valueOf(oil_id)});

    }

}
