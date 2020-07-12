package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.Intent
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper

class SplashPage : AppCompatActivity() {

    private lateinit var db: SQLiteDatabase
    private lateinit var databaseHelper: DatabaseHelper
    private val myOilName: MutableList<String> = ArrayList()
    private val myNaoh: MutableList<Double> = ArrayList()

    private val TABLE_SOAP_OILS = "soap_oils"
    private val KEY_OIL_NAME = "oil_name"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_page)

        databaseHelper = DatabaseHelper(this)

        db = databaseHelper.getReadableDatabase()

        val handler = Handler()
        handler.postDelayed({

            val intent = Intent(this, MainActivityDrawer::class.java)
            startActivity(intent)
            finish()


        }, 3000)

        AddOils()

    }

    private fun AddOils() {

        myOilName.addAll(listOf("Acai Berry","Almond Butter","Almond Oil", "Aloe Vera Butter",
                "Aloe Vera Oil", "Andiroba Oil", "Apricot Kernel butter", "Apricot Kernel Oil",
                "Argan oil", "Avocado Butter", "Avocado oil", "Babassu oil",
                "Baobab oil", "Beeswax pellets", "Black Cummin Seed oil", "Black Raspberry seed oil",
                "Blackcurrant oil", "Borage oil", "Brazil Nut oil", "Broccoli Seed oil",
                "Buriti Oil", "Calendula oil", "Camelina Oil", "Candelilla Wax",
                "Carnauba Wax", "Carrot oil", "Castor oil", "Cherry Kernel oil",
                "Cocoa Butter – deodorized", "Coconut oil", "Coffee Bean Butter", "Cranberry Seed Oil",
                "Cucumber Seed Oil", "Cupuacu Butter", "Emu oil", "Evening Primrose Oil", "Flax Seed oil",
                "Fractionated Coconut oil", "Grape Seed Oil", "Grape Seed Oil, Chardonnay", "Grape Seed Oil, Reisling",
                "Green Coffee Oil", "Hazelnut Oil", "Hemp Seed butter", "Hemp Seed Oil",
                "Illipe Butter", "Jojoba, Clear", "Jojoba, Natural", "Karanja Oil",
                "Kokum Butter", "Kukui Nut Oil", "Lanolin Oil", "Macadamia Nut Butter",
                "Macadamia Nut Oil", "Mango Butter", "Mango Oil", "Manketti Oil",
                "Maracjua Oil", "Marula Oil", "Meadowfoam Oil", "Monoi de Tahiti Oil",
                "Moringa Oil", "Neem Oil", "Olive Butter", "Olive Oil",
                "Palm Kernel Oil", "Palm Oil",
                "Peach Kernel Oil ", "Pecan Oil", "Pequi Oil", "Perila Oil",
                "Pistachio Nut Butter", "Pomace Olive Oil", "Poppy Seed Oil", "Pumpkin Seed Oil",
                "Red Raspberry Seed Oil", "Rice Bran Oil", "Rice Bran Oil, CP", "Roasted Coffee Oil",
                "Rosehip Oil", "Safflower Oil", "Seabuckthorn Oil", "Sesame Oil",
                "Shea Butter", "Shea Oil", "Soybean Oil", "Stearic Oil",
                "Sunflower Oil", "Sunflower Seed oil", "Tamanu (Foraha Oil)", "Virgin Coconut Cream oil",
                "Walnut Oil", "Watermelon Seed Oil", "Wheatgerm oil", "Yangu Oil",
                "Passionfruit seed oil", "Papaya oil"
        ))

        myNaoh.addAll(listOf(
                0.136, 0.098, 0.137, 0.176, 0.135, 0.132, 0.097, 0.134, 0.134, 0.132, 0.132, 0.176,
                0.143, 0.067, 0.137, 0.132, 0.134, 0.13, 0.138, 0.121, 0.149, 0.134, 0.132, 0.038,
                0.061, 0.134, 0.127, 0.135, 0.136, 0.178, 0.132, 0.135, 0.13, 0.136, 0.135, 0.13, 0.135, 0.234, 0.134, 0.13, 0.135,
                0.137, 0.134, 0.132, 0.135, 0.136, 0.064, 0.065, 0.13, 0.134, 0.135, 0.07, 0.132, 0.137,
                0.134, 0.132, 0.139, 0.137, 0.135, 0.119, 0.178, 0.139, 0.134, 0.088, 0.133, 0.155, 0.139,
                0.136, 0.135, 0.137, 0.135, 0.132, 0.133, 0.138, 0.134, 0.132, 0.133, 0.129, 0.13, 0.133,
                0.135, 0.116, 0.135, 0.126, 0.128, 0.134, 0.147, 0.134, 0.134, 0.148, 0.18, 0.136, 0.135,
                0.135, 0.135, 0.137, 0.134

        ))


        AddToSqlite()

    }

    private fun AddToSqlite() {

        for (i in myOilName.indices){

            val txtOilName = myOilName[i]
            val txtOilNameTest = myOilName[0]
            val txtNaoh = myNaoh[i]

            val query = "Select * From $TABLE_SOAP_OILS WHERE $KEY_OIL_NAME = '$txtOilName'";
            val cursor1: Cursor = db.rawQuery(query, null)

            if (cursor1.count > 0){

            }else{

                databaseHelper.AddSoapOils(txtOilName, txtNaoh)

            }


        }

    }
}
