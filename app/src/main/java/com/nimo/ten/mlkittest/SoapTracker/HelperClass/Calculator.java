package com.nimo.ten.mlkittest.SoapTracker.HelperClass;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo;
import com.nimo.ten.mlkittest.SoapTracker.Pojo.LyeCalculatorPojo;

import java.util.ArrayList;
import java.util.List;


public class Calculator {

    private static final String TABLE_SOAP_INGREDIENTS = "soap_ingredients";
    private static final String KEY_SOAP_ID = "soap_id";
    private static final String KEY_PERCENTAGE = "percentage";

    private static final String TABLE_SOAP_DETALS = "soap_details";
    private static final String KEY_TOTAL_WEIGHT = "total_weight";
    private static final String KEY_ID = "id";

    private static final String TABLE_SOAP_LYE = "soap_lye";
    private static final String KEY_NAOH_WEIGHT = "total_lye_weight";
    private static final String KEY_LIQUID_WEIGHT = "total_liquid_weight";

    DatabaseHelper databaseHelper;

    Double TotalPercerntage = 0.0;
    private Double i = 0.0;
    private double RemPercentage = 0.0;
    private double TotalWeight = 0.0;
    private double TotalLiquidWeight = 0.0;

    private double SoapWeight = 0.0;
    private double RemainingLyeWeight = 0.0;
    private double SoapPerc = 0.0;

    private boolean isUpdated = false;

    private List<Integer> myPercentage = new ArrayList<>();
    private String txtDbNaohWeight;

    private String txtNaoh;
    private String txtSubstance;

    public Double getRemainingPercentage(String SoapId, Context context){

        Clear();

        databaseHelper = new DatabaseHelper(context);

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_INGREDIENTS+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {


            do {

                int default_percentage = c.getInt(c.getColumnIndex(KEY_PERCENTAGE));

                myPercentage.add(default_percentage);


            } while (c.moveToNext());

            c.close();

        }

        for (int i = 0; i < myPercentage.size(); i++){

            SoapPerc = myPercentage.get(i) + SoapPerc;

        }

        RemPercentage = 100 - SoapPerc;



        return RemPercentage;

    }

    public Double getLiquidRemainingPercentage(String SoapId, Context context){

        Clear();

        databaseHelper = new DatabaseHelper(context);

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_LYE+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {


            do {

                int default_percentage = c.getInt(c.getColumnIndex(KEY_PERCENTAGE));

                myPercentage.add(default_percentage);


            } while (c.moveToNext());

            c.close();

        }

        for (int i = 0; i < myPercentage.size(); i++){

            SoapPerc = myPercentage.get(i) + SoapPerc;

        }

        RemPercentage = 100 - SoapPerc;



        return RemPercentage;

    }

    private void Clear() {

        myPercentage.clear();
        SoapPerc = 0.0;
        RemPercentage = 0.0;
    }

    public Double getTotalWeight(String SoapId, Context context){

        databaseHelper = new DatabaseHelper(context);

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                TotalWeight = c.getInt(c.getColumnIndex(KEY_TOTAL_WEIGHT));


            } while (c.moveToNext());

            c.close();

        }

        return TotalWeight;

    }

    public Double getTotalLiquidWeight(String SoapId, Context context){

        databaseHelper = new DatabaseHelper(context);

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                TotalLiquidWeight = c.getInt(c.getColumnIndex(KEY_LIQUID_WEIGHT));


            } while (c.moveToNext());

            c.close();

        }

        return TotalLiquidWeight;

    }

    public boolean UpdateAllGrams(String SoapId, String txtWeight, Context context){

        databaseHelper = new DatabaseHelper(context);

        String isContinueWithUpdate = databaseHelper.updateSoapTotalWeight(SoapId, txtWeight);

        if (isContinueWithUpdate.equals("updated")){

            String txtLye = String.valueOf(getRemainingLiquidWeight(SoapId, "0.3333", context).getTxtNaOH());

            databaseHelper.updateLyeWeight(SoapId, txtLye);

            //The update is done and update the weights for ingredients
            String selectQuery = "SELECT * FROM " + TABLE_SOAP_INGREDIENTS+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
            SQLiteDatabase db = databaseHelper.getReadableDatabase();
            Cursor c = db.rawQuery(selectQuery, null);

            if (c.moveToFirst()) {

                do {

                    double percentages = c.getInt(c.getColumnIndex(KEY_PERCENTAGE));
                    String txtId = c.getString(c.getColumnIndex(KEY_ID));

                    String txtNewWeight = String.valueOf(getRemainingWeight(SoapId, String.valueOf(percentages) , context));

                    //Get New Calculations
                    databaseHelper.updateWeights(txtId, txtNewWeight);

                } while (c.moveToNext());

                c.close();

                isUpdated = true;



            }

            //The update is done and update the weights for Lye
            String selectQueryLye = "SELECT * FROM " + TABLE_SOAP_LYE+" WHERE " + KEY_SOAP_ID + " = '"+SoapId+"'";
            Cursor cC = db.rawQuery(selectQueryLye, null);

            if (cC.moveToFirst()) {

                do {

                    double percentages = cC.getInt(cC.getColumnIndex(KEY_PERCENTAGE));
                    String txtId = cC.getString(cC.getColumnIndex(KEY_ID));

                    String txtNewWeight = String.valueOf(getRemainingLiquidPercentageWeight(SoapId, String.valueOf(percentages) , context));

                    //Get New Calculations
                    databaseHelper.updateLyeWeights(txtId, txtNewWeight);

                } while (cC.moveToNext());

                cC.close();

                isUpdated = true;


            }

        }

        return isUpdated;

    }

    public Double getRemainingWeight(String SoapId, String givenPercentage, Context context){

        Double txtTotalWeight = getTotalWeight(SoapId, context);

        Double txtPercentage = Double.valueOf(givenPercentage);

        databaseHelper = new DatabaseHelper(context);

        SoapWeight = txtTotalWeight * ( txtPercentage / 100 );

        return SoapWeight;

    }

    public Double getRemainingLiquidPercentageWeight(String SoapId, String givenPercentage, Context context){

        Double txtTotalWeight = getTotalLiquidWeight(SoapId, context);

        Double txtPercentage = Double.valueOf(givenPercentage);

        databaseHelper = new DatabaseHelper(context);

        SoapWeight = txtTotalWeight * ( txtPercentage / 100 );

        return SoapWeight;

    }

    public LyeCalculatorPojo getRemainingLiquidWeight(String SoapId, String givenPercentage, Context context){

        Double txtPercentage = Double.valueOf(givenPercentage);
        double txtNaOhWeight = txtPercentage *  getTotalWeight(SoapId, context);

        //Get the Lye Percentage
        databaseHelper = new DatabaseHelper(context);

        String selectQuery = "SELECT * FROM " + TABLE_SOAP_DETALS+" WHERE " + KEY_ID + " = '"+SoapId+"'";
        SQLiteDatabase db = databaseHelper.getReadableDatabase();
        Cursor c = db.rawQuery(selectQuery, null);

        if (c.moveToFirst()) {

            do {

                txtDbNaohWeight = c.getString(c.getColumnIndex(KEY_NAOH_WEIGHT));


            } while (c.moveToNext());

            c.close();

        }


        double remWeight = 100 - txtPercentage;

        return new LyeCalculatorPojo(txtDbNaohWeight, txtNaOhWeight, remWeight);

    }

    public LyeCalculatorPojo getNaohSubstance(String SoapId, Context context){

        databaseHelper = new DatabaseHelper(context);

        String query = "SELECT * FROM " + TABLE_SOAP_DETALS + " WHERE " + KEY_ID + " = '" + SoapId + "'";
        SQLiteDatabase database = databaseHelper.getReadableDatabase();
        Cursor cursor = database.rawQuery(query, null);

        if (cursor.moveToFirst()){

            do{

                txtSubstance = cursor.getString(cursor.getColumnIndex(KEY_LIQUID_WEIGHT));
                txtNaoh = cursor.getString(cursor.getColumnIndex(KEY_NAOH_WEIGHT));

            }while (cursor.moveToNext());

            cursor.close();

        }

        return new LyeCalculatorPojo(null, Double.valueOf(txtNaoh), Double.valueOf(txtSubstance));

    }



}
