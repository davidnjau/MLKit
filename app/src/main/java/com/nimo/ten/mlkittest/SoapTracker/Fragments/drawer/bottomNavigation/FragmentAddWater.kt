package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.bottomNavigation

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelperNew
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator

class FragmentAddWater : Fragment() {

    private lateinit var totalWeight: TextView
    private lateinit var tvTotalOilWeight: TextView
    private lateinit var tvSuperFat: TextView
    private lateinit var tvWaterWeight: TextView
    private lateinit var tvLyeWeight: TextView

    lateinit var databaseHelper: DatabaseHelper
    lateinit var databaseHelper1: DatabaseHelperNew
    private lateinit var preferences: SharedPreferences
    private lateinit var soap_id: String

    private lateinit var btnSaveRatio: Button
    private lateinit var btnSaveConcentration: Button
    private lateinit var btnSave: Button
    private lateinit var etWater: EditText
    private lateinit var etLiquid: EditText
    private lateinit var etLyeConcentration: EditText
    private lateinit var calculator: Calculator
    private lateinit var linear1: LinearLayout
    private lateinit var linear2: LinearLayout
    private lateinit var radioGroup: RadioGroup
    private lateinit var radioRatio: RadioButton
    private lateinit var radioLye: RadioButton


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add_water, container, false)

        totalWeight = view.findViewById(R.id.totalWeight)
        tvTotalOilWeight = view.findViewById(R.id.tvTotalOilWeight)
        tvSuperFat = view.findViewById(R.id.tvSuperFat)
        tvWaterWeight = view.findViewById(R.id.tvWaterWeight)
        tvLyeWeight = view.findViewById(R.id.tvLyeWeight)

        databaseHelper = DatabaseHelper(activity)
        databaseHelper1 = DatabaseHelperNew(activity)

        preferences = requireActivity().getSharedPreferences("Soap", Context.MODE_PRIVATE)

        btnSaveRatio = view.findViewById(R.id.btnSaveRatio)
        btnSaveConcentration = view.findViewById(R.id.btnSaveConcentration)
        etWater = view.findViewById(R.id.etWater)
        etLiquid = view.findViewById(R.id.etLiquid)
        etLyeConcentration = view.findViewById(R.id.etLyeConcentration)
        btnSave = view.findViewById(R.id.btnSave)

        calculator = Calculator()

        linear1 = view.findViewById(R.id.linear1)
        linear2 = view.findViewById(R.id.linear2)

        radioRatio = view.findViewById(R.id.radioRatio)
        radioLye = view.findViewById(R.id.radioLye)

        radioGroup = view.findViewById(R.id.radioGroup)

        radioRatio.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                linear1.visibility = View.VISIBLE
            }else
                linear1.visibility = View.GONE
        }

        radioLye.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){
                linear2.visibility = View.VISIBLE
            }else
                linear2.visibility = View.GONE
        }

        btnSaveRatio.setOnClickListener {

            val txtWater = etWater.text.toString()
            val txtLye = etLiquid.text.toString()

            if (!TextUtils.isEmpty(txtWater) && !TextUtils.isEmpty(txtLye)) {

                val oils_exists = checkOils(soap_id.toString())

                if (oils_exists){

                    databaseHelper1.updateRatiosPercentages(soap_id, txtLye, txtWater)

//                    val txtLiquidWeight = databaseHelper.getWaterLyeAmount(soap_id).liquid_weight.toString()
//                    val txtLyeWeight = databaseHelper.getWaterLyeAmount(soap_id).lye_weight.toString()
//
//                    tvWaterWeight.text = txtLiquidWeight
//                    tvLyeWeight.text = txtLyeWeight

                    Toast.makeText(activity, "Ratios updated successfully.", Toast.LENGTH_SHORT).show()

                    GetValues()


                }else
                    Toast.makeText(activity, "You first need to select some oils", Toast.LENGTH_SHORT).show()


            }else{


                if (TextUtils.isEmpty(txtWater))etWater.error = "The Water ratio cannot be empty"
                if (TextUtils.isEmpty(txtLye))etLiquid.error = "The Lye ratio cannot be empty"

            }

        }
        btnSaveConcentration.setOnClickListener {

            val txtLyeConc = etLyeConcentration.text.toString()

            if (!TextUtils.isEmpty(txtLyeConc)) {

                //Check if the value is more than 100%
                val LyeConc = txtLyeConc.toDouble()

                if (LyeConc < 100){

                    val soap_id = preferences.getString("recipe_id", null).toString()
                    val oils_exists = checkOils(soap_id)

                    val WaterConc = 100 - LyeConc
                    val  txtWater = WaterConc.toString()

                    if (oils_exists){

                        databaseHelper1.updateRatiosPercentages(soap_id, txtLyeConc, txtWater)

//                        calculator.getTotalOilWeight(soap_id, activity)
//                        val txtLiquidWeight = databaseHelper.getWaterLyeAmount(soap_id).liquid_weight.toString()
//                        val txtLyeWeight = databaseHelper.getWaterLyeAmount(soap_id).lye_weight.toString()
//
//                        tvWaterWeight.text = txtLiquidWeight
//                        tvLyeWeight.text = txtLyeWeight

                        GetValues()


                    }else
                        Toast.makeText(activity, "You first need to select some oils", Toast.LENGTH_SHORT).show()

                }else{

                    etLyeConcentration.error = "Lye Concentration cannot be more than 100 %"
                    Toast.makeText(activity, "Lye Concentration cannot be more than 100 %", Toast.LENGTH_SHORT).show()
                }


            }else{


                if (TextUtils.isEmpty(txtLyeConc))etLyeConcentration.error = "The Lye concentration cannot be empty"

            }


        }

        btnSave.setOnClickListener {

            //Check if Ratios have been added. If not use the default of 33%
            var fragment = FragmentAddFragrance()
            loadFragment(fragment)

        }

        return view
    }

    override fun onStart() {
        super.onStart()

        soap_id = preferences.getString("recipe_id", null).toString()

        val OilAmount = databaseHelper1.getOilsWeight(soap_id).oilWeight
        val superFat = databaseHelper1.getOilsWeight(soap_id).superFat
        val TotaWeight = databaseHelper1.getOilsWeight(soap_id).totalWeight

        totalWeight.text = TotaWeight
        tvTotalOilWeight.text = OilAmount
        tvSuperFat.text = superFat

    }

    private fun GetValues() {

        val LiquidWeight = databaseHelper1.getOilsWeight(soap_id).liquidWeight
        val LyeWeight = databaseHelper1.getOilsWeight(soap_id).lyeWeight

        tvWaterWeight.text = LiquidWeight
        tvLyeWeight.text = LyeWeight
    }

    private fun checkOils(id: String): Boolean{

        val selectQuery = "SELECT * FROM " + DatabaseHelperNew.TABLE_SOAP_MY_OILS+ " WHERE " + DatabaseHelperNew.KEY_SOAP_ID + " = '" + id + "'"
        val db: SQLiteDatabase = databaseHelper1.readableDatabase

        val cursor1: Cursor = db.rawQuery(selectQuery, null)

        if (cursor1.count > 0)
            return true
        else
            return false


    }

    private fun loadFragment(fragment: Fragment) {
        // load fragment
        val transaction = requireActivity().supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}