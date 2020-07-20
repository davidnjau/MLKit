package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.bottomNavigation

import android.content.Context
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapIngredientsRecyclerAdapter
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelperNew
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.ShowCustomToast
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo
import java.util.ArrayList

class FragmentAddFragrance : Fragment() {

    private lateinit var totalWeight: TextView
    private lateinit var tvTotalOilWeight: TextView
    private lateinit var tvSuperFat: TextView
    private lateinit var tvWaterWeight: TextView
    private lateinit var tvLyeWeight: TextView

    lateinit var databaseHelper1: DatabaseHelperNew
    private lateinit var preferences: SharedPreferences
    private lateinit var soap_id: String
    private lateinit var FragranceOils: Switch
    private lateinit var linearEssentialOil: LinearLayout
    private lateinit var tvEssentialOil: TextView
    private var calculator: Calculator = Calculator()
    private var isGood = true
    private lateinit var recyclerViewFragnance: RecyclerView
    lateinit var layoutManager2: RecyclerView.LayoutManager
    var soapTrackerPojoArrayList: List<IngredientsPojo>? = null
    var soapDetailsRecyclerAdapter: SoapIngredientsRecyclerAdapter? = null
    private lateinit var btnSaveEssentialOils: Button
    private lateinit var btnSave: Button
    private lateinit var etEssentialOil: EditText

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add_fragrance, container, false)

        totalWeight = view.findViewById(R.id.totalWeight)
        tvTotalOilWeight = view.findViewById(R.id.tvTotalOilWeight)
        tvSuperFat = view.findViewById(R.id.tvSuperFat)

        tvWaterWeight = view.findViewById(R.id.tvWaterWeight)
        tvLyeWeight = view.findViewById(R.id.tvLyeWeight)
        etEssentialOil = view.findViewById(R.id.etEssentialOil)

        databaseHelper1 = DatabaseHelperNew(activity)
        preferences = requireActivity().getSharedPreferences("Soap", Context.MODE_PRIVATE)
        linearEssentialOil = view.findViewById(R.id.linearEssentialOil)
        tvEssentialOil = view.findViewById(R.id.tvEssentialOil)
        recyclerViewFragnance = view.findViewById(R.id.recyclerViewFragnance)
        layoutManager2 = LinearLayoutManager(activity)
        btnSaveEssentialOils = view.findViewById(R.id.btnSaveEssentialOils)
        btnSave = view.findViewById(R.id.btnSave)

        btnSaveEssentialOils.setOnClickListener {

            val txtEssentialTotal = etEssentialOil.text.toString()
            if (!TextUtils.isEmpty(txtEssentialTotal)){

                val LyeConc = txtEssentialTotal.toDouble()

                if (LyeConc < 100){

                    val soap_id = preferences.getString("recipe_id", null).toString()
                    val oils_exists = checkOils(soap_id.toString())
                    if (oils_exists){

                        databaseHelper1.updateEssentialRatio(soap_id, (LyeConc/100))

                        getEssentialOilWeight()

                        Toast.makeText(activity, "Essential oils updated successfully.", Toast.LENGTH_SHORT).show()

                    }else
                        Toast.makeText(activity, "Please add some oils before proceeding", Toast.LENGTH_SHORT).show()

                    StartEssentialOilRecyclerView(soap_id)
                    getValues()


                }else{

                    etEssentialOil.error = "Essential oils% cannot be more than 100 %"
                    Toast.makeText(activity, "Essential oils% cannot be more than 100 %", Toast.LENGTH_SHORT).show()
                }




            }else etEssentialOil.error = "Essential oils cannot be empty."
        }

        FragranceOils = view.findViewById(R.id.FragranceOils)
        FragranceOils.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){

                val soap_id = preferences.getString("recipe_id", null).toString()
                val oils_exists = checkOils(soap_id.toString())
                if (oils_exists){

                    //Add Fragrance \ Essential oils
                    databaseHelper1.updateEssentialRatio(soap_id, 0.02)

                    linearEssentialOil.visibility = View.VISIBLE

                    getEssentialOilWeight()

                }else{

                    Toast.makeText(activity, "You must add Oils to be used in soap making Process", Toast.LENGTH_LONG).show()
                    linearEssentialOil.visibility = View.GONE


                    FragranceOils.isChecked = !FragranceOils.isChecked
                }

            }else{

                etEssentialOil.setText("")
                databaseHelper1.updateEssentialRatio(soap_id, 0.0)
                getEssentialOilWeight()

                databaseHelper1.deleteEssentialOils(soap_id)

                linearEssentialOil.visibility = View.GONE
            }

            StartEssentialOilRecyclerView(soap_id)
            getValues()

        }

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {

            val soap_id = preferences.getString("recipe_id", null).toString()
            val oils_exists = checkOils(soap_id)

            if (oils_exists){

                InputDialog(soap_id)

            }else{

                Toast.makeText(activity, "You first need to select some oils", Toast.LENGTH_SHORT).show()

            }


        }

        btnSave.setOnClickListener {

            //Display all information for preview
            Toast.makeText(activity, "Save work", Toast.LENGTH_SHORT).show()



        }

        return view
    }

    private fun InputDialog(soapId: String) {

        val li = LayoutInflater.from(activity)
        val promptsView = li.inflate(R.layout.alert_dialog_fragnance, null)
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setView(promptsView)

        val EssentialOil = databaseHelper1.getTotalEssentials(soapId)

        val etIngredients = promptsView.findViewById<EditText>(R.id.etIngredients)
        val etPercentage = promptsView.findViewById<EditText>(R.id.etPercentage)
        val tvTitleWeight = promptsView.findViewById<TextView>(R.id.tvTitleWeight)

        tvTitleWeight.text = EssentialOil.toString()

        val RemainingPercentage = calculator.getRemainingEssentialPercentage1(soapId, activity)
        if (RemainingPercentage >= 0) etPercentage.hint = "The remaining percentage is $RemainingPercentage %"

        etPercentage.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (!TextUtils.isEmpty(s.toString())) {
                    val text = s.toString().toDouble()
                    if (100 - text < 0) {
                        isGood = false
                        ShowCustomToast(activity, "Your percentage is not right.")
                    } else {
                        isGood = true
                    }
                }
            }

            override fun afterTextChanged(s: Editable) {
                if (!TextUtils.isEmpty(s.toString())) {
                    val text = s.toString().toDouble()
                    if (100 - text < 0) {
                        isGood = false
                        etPercentage.setText("")
                        etPercentage.error = "Your percentage cannot exceed 100%"
                    } else {
                        isGood = true
                    }
                }
            }
        })

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save") { dialog, id -> }
                .setNegativeButton("Cancel"
                ) { dialog, id ->
                    //                                dialog.cancel();
                }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {
            val txtIngredients = etIngredients.text.toString()
            val txtPercentage = etPercentage.text.toString()

            if (!TextUtils.isEmpty(txtIngredients) && !TextUtils.isEmpty(txtPercentage)){

                val txtIngredients = etIngredients.text.toString()
                val txtPercentage = etPercentage.text.toString()

                if (isGood) {

                    if (!TextUtils.isEmpty(txtIngredients) && !TextUtils.isEmpty(txtPercentage) ) {

                        val RemainingPercentage = calculator.getRemainingEssentialPercentage1(soapId, activity)
                        val GivenPercentage = java.lang.Double.valueOf(txtPercentage)

                        if (RemainingPercentage >= 0) {

                            if (GivenPercentage <= RemainingPercentage) {

                                val txtSoapWeight = calculator.getRemainingEssentialWeight1(soapId,
                                        txtPercentage, activity).toString()

                                databaseHelper1.AddEssentialOils(txtIngredients, soapId, txtPercentage,
                                        txtSoapWeight)

                                ShowCustomToast(activity, "Successfully added an Essential oil")
                                alertDialog.dismiss()

                                StartEssentialOilRecyclerView(soapId)

                            } else {

                                ShowCustomToast(activity, "Your Percentage is too high..")
                                etPercentage.setText("")
                                etPercentage.hint = "The remaining percentage is $RemainingPercentage %"

                            }
                        }

                    } else {

                        if (TextUtils.isEmpty(txtIngredients)) etIngredients.error = "Ingredients cannot be empty.."
                        if (TextUtils.isEmpty(txtPercentage)) etPercentage.error = "Percentage cannot be empty.."
                    }



                } else {

                    etPercentage.setText("")
                    etPercentage.error = "Your Percentage is not correct!"
                }


            }else{

                if (TextUtils.isEmpty(txtIngredients))etIngredients.error = "The fragrance cannot be empty"
                if (TextUtils.isEmpty(txtPercentage))etPercentage.error = "The percentage cannot be empty"

            }

        }
    }

    private fun getEssentialOilWeight(){

        //Get The weight of Essential oils. By Default We will Use 2% of the total Oils
        val soap_id = preferences.getString("recipe_id", null).toString()
        val oils_exists = checkOils(soap_id)

        if (oils_exists){

            val TotalWeight =  databaseHelper1.getTotalOils(soap_id)
            tvEssentialOil.text = TotalWeight.toString()

        }

    }

    override fun onStart() {
        super.onStart()

        getValues()

    }

    private fun getValues() {

        soap_id = preferences.getString("recipe_id", null).toString()

        val OilAmount = databaseHelper1.getOilsWeight(soap_id).oilWeight
        val superFat = databaseHelper1.getOilsWeight(soap_id).superFat
        val TotaWeight = databaseHelper1.getOilsWeight(soap_id).totalWeight

        val LiquidWeight = databaseHelper1.getOilsWeight(soap_id).liquidWeight
        val LyeWeight = databaseHelper1.getOilsWeight(soap_id).lyeWeight

        totalWeight.text = TotaWeight
        tvTotalOilWeight.text = OilAmount
        tvSuperFat.text = superFat

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

    private fun StartEssentialOilRecyclerView(soapId: String) {

        recyclerViewFragnance.layoutManager = layoutManager2
        recyclerViewFragnance.setHasFixedSize(true)

        soapTrackerPojoArrayList = databaseHelper1.getSoapEssentialOils(soapId)
        soapDetailsRecyclerAdapter = SoapIngredientsRecyclerAdapter(activity, soapTrackerPojoArrayList as ArrayList<IngredientsPojo?>?)

        recyclerViewFragnance.adapter = soapDetailsRecyclerAdapter

        if (soapDetailsRecyclerAdapter!!.getItemCount() == 0) {
            recyclerViewFragnance.visibility = View.GONE
        } else {
            recyclerViewFragnance.visibility = View.VISIBLE
        }

    }
}