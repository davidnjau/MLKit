package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.DaysPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsNotesPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapTrackerPojo;

import java.util.ArrayList;

public class FetchDataBase {

    private String DateIn;
    private String DateOut;
    private String id;
    private String SoapName;

    private String txtIngredients;
    private String txtWeight;
    private String txtNotes;

    private static final String KEY_DATE_IN = "date_in";
    private static final String KEY_DATE_OUT = "date_out";
    private static final String KEY_ID = "id";
    private static final String TABLE_SOAP_DETALS = "soap_details";

    private static final String TABLE_SOAP_INGREDIENTS = "soap_ingredients";
    private static final String KEY_SOAP_ID = "soap_id";
    private static final String KEY_WEIGHT = "weight";
    private static final String KEY_INGREDIENTS = "ingredients";
    private static final String TABLE_SOAP_NOTES = "soap_notes";
    private static final String KEY_NOTES = "notes";
    private static final String KEY_SOAP_NAME = "soap_name";

    private static final String KEY_TOTAL_WEIGHT = "total_weight";
    private static final String KEY_NAOH_WEIGHT = "total_lye_weight";
    private static final String KEY_LIQUID_WEIGHT = "total_liquid_weight";

    private DatabaseHelper databaseHelper;

    public DaysPojo getDates(String SoapId, Context context){

        databaseHelper = new DatabaseHelper(context);

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS +" WHERE " + KEY_ID + " = '"+SoapId+"'";;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                id = c.getString(c.getColumnIndex(KEY_ID));
                DateIn = c.getString(c.getColumnIndex(KEY_DATE_IN));
                DateOut = c.getString(c.getColumnIndex(KEY_DATE_OUT));

            } while (c.moveToNext());

            c.close();

        }

        return new DaysPojo(id, DateIn, DateOut, null);

    }

    public ArrayList<DaysPojo> getDateOuts(Context context){

        databaseHelper = new DatabaseHelper(context);

        ArrayList<DaysPojo> daysPojoArrayList = new ArrayList<>();

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS ;
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                DaysPojo daysPojo = new DaysPojo();

                id = c.getString(c.getColumnIndex(KEY_ID));
                DateIn = c.getString(c.getColumnIndex(KEY_DATE_IN));
                DateOut = c.getString(c.getColumnIndex(KEY_DATE_OUT));

                SoapName = c.getString(c.getColumnIndex(KEY_SOAP_NAME));

                daysPojo.setDateOut(DateOut);
                daysPojo.setSoapName(SoapName);

                daysPojoArrayList.add(daysPojo);



            } while (c.moveToNext());

            c.close();

        }

        return daysPojoArrayList;

    }


    public ArrayList<IngredientsNotesPojo> getIngredients(String SoapId, Context context){

        ArrayList<IngredientsNotesPojo> ingredientsNotesPojoArrayList = new ArrayList<>();

        databaseHelper = new DatabaseHelper(context);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_INGREDIENTS +" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";

        IngredientsNotesPojo ingredientsNotesPojo = new IngredientsNotesPojo();

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {

            do {

                ingredientsNotesPojo.setTxtIngredient(c.getString(c.getColumnIndex(KEY_INGREDIENTS)));
//                ingredientsNotesPojo.setTxtWeight(c.getString(c.getColumnIndex(KEY_WEIGHT)));

                ingredientsNotesPojoArrayList.add(ingredientsNotesPojo);

            } while (c.moveToNext());

            c.close();

        }

        return ingredientsNotesPojoArrayList;

    }

    public ArrayList<IngredientsNotesPojo> getNotes(String SoapId, Context context){

        ArrayList<IngredientsNotesPojo> ingredientsNotesPojoArrayList = new ArrayList<>();


        databaseHelper = new DatabaseHelper(context);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_INGREDIENTS +" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        String selectQueryNotes = "SELECT * FROM " + TABLE_SOAP_NOTES +" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";

        IngredientsNotesPojo ingredientsNotesPojo = new IngredientsNotesPojo();

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {

            do {

                ingredientsNotesPojo.setTxtIngredient(c.getString(c.getColumnIndex(KEY_INGREDIENTS)));
                ingredientsNotesPojo.setTxtWeight(c.getString(c.getColumnIndex(KEY_WEIGHT)));

                ingredientsNotesPojoArrayList.add(ingredientsNotesPojo);

            } while (c.moveToNext());

            c.close();

        }

        Cursor cNotes = db.rawQuery(selectQueryNotes, null);
        if (cNotes.moveToFirst()) {

            do {

                ingredientsNotesPojo.setTxtNotes(c.getString(c.getColumnIndex(KEY_NOTES)));

                ingredientsNotesPojoArrayList.add(ingredientsNotesPojo);

            } while (cNotes.moveToNext());

            cNotes.close();

        }



        return ingredientsNotesPojoArrayList;

    }

    public ArrayList<SoapLyeLiquidsPojo> getLye_LiquidWeights(String SoapId, Context context) {

        ArrayList<SoapLyeLiquidsPojo> soapLyeLiquidsPojoArrayList = new ArrayList<>();


        databaseHelper = new DatabaseHelper(context);

        SQLiteDatabase db = databaseHelper.getReadableDatabase();

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS + " WHERE " + KEY_ID + " = '" + SoapId + "'";

        SoapLyeLiquidsPojo soapLyeLiquidsPojo = new SoapLyeLiquidsPojo();

        Cursor c = db.rawQuery(selectQuery, null);
        if (c.moveToFirst()) {

            do {

                soapLyeLiquidsPojo.setNaOh(c.getString(c.getColumnIndex(KEY_NAOH_WEIGHT)));
                soapLyeLiquidsPojo.setLiquids(c.getString(c.getColumnIndex(KEY_LIQUID_WEIGHT)));

                soapLyeLiquidsPojoArrayList.add(soapLyeLiquidsPojo);

            } while (c.moveToNext());

            c.close();

        }
        return soapLyeLiquidsPojoArrayList;
    }


}
