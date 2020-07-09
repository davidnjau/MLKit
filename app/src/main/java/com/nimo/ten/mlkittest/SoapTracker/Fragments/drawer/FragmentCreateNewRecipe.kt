package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
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
import com.nimo.ten.mlkittest.SoapTracker.Activities.MainActivityDrawer
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapMyOilsAdapter
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapOilsAdapter
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.SoapOilsPojo
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo
import java.text.SimpleDateFormat
import java.util.*

class FragmentCreateNewRecipe : Fragment() {

    lateinit var btnChooseSoap: Button
    lateinit var bottomSheetText: TextView
    lateinit var recyclerView6: RecyclerView
    lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var myview: LinearLayout
    private lateinit var mySelectedList: List<String>

    private lateinit var btnConfirm: Button
    private lateinit var preferences: SharedPreferences
    private lateinit var Recipe_id: String
    lateinit var databaseHelper: DatabaseHelper
    private lateinit var oilsAdapter: SoapOilsAdapter
    private var soapLyeLiquidsPojoArrayList = ArrayList<SoapLyeLiquidsPojo>()
    val dateFormat = SimpleDateFormat("dd-MMM-yyyy")
    var c = Calendar.getInstance()
    private lateinit var oilsAdapter1: SoapMyOilsAdapter
    private var soapLyeLiquidsPojoArrayList1 = ArrayList<SoapOilsPojo>()

    private lateinit var etSuperFat: EditText

    private lateinit var linearEssentialOil: LinearLayout

    private lateinit var recyclerView: RecyclerView
    private lateinit var SuperFatSwitch: Switch
    private lateinit var FragranceOils: Switch

    private lateinit var linearSuperFat: LinearLayout

    private lateinit var btnSaveSuperFat: Button

    private lateinit var linear1:LinearLayout
    private lateinit var linear2:LinearLayout
    private lateinit var radioGroup:RadioGroup

    private lateinit var radioRatio:RadioButton
    private lateinit var radioLye:RadioButton
    private lateinit var recyclerViewFragnance: RecyclerView

    private lateinit var tvWaterWeight: TextView
    private lateinit var tvLyeWeight: TextView


    private lateinit var btnSaveRatio: Button

    private lateinit var btnSaveConcentration: Button
    private lateinit var btnSaveEssentialOils: Button

    private lateinit var etWater: EditText
    private lateinit var etLiquid: EditText
    private lateinit var etLyeConcentration: EditText

    private lateinit var imageBtn: ImageButton

    private lateinit var etEssentialOil: EditText
    private lateinit var tvEssentialOil: TextView
    private lateinit var db: SQLiteDatabase

    private var SoapId : String = "0"

    private var calculator: Calculator = Calculator()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_new_recipe, container, false)

        databaseHelper = DatabaseHelper(activity)

        db = databaseHelper.getReadableDatabase()
        preferences = requireActivity().getSharedPreferences("Soap", Context.MODE_PRIVATE)

        bottomSheetText = view.findViewById(R.id.bottomSheetText)
        databaseHelper = DatabaseHelper(activity)

        recyclerViewFragnance = view.findViewById(R.id.recyclerViewFragnance)

        etWater = view.findViewById(R.id.etWater)
        etLiquid = view.findViewById(R.id.etLiquid)
        etLyeConcentration = view.findViewById(R.id.etLyeConcentration)
        linearSuperFat = view.findViewById(R.id.linearSuperFat)

        btnSaveRatio = view.findViewById(R.id.btnSaveRatio)
        btnSaveConcentration = view.findViewById(R.id.btnSaveConcentration)
        btnSaveEssentialOils = view.findViewById(R.id.btnSaveEssentialOils)
        tvEssentialOil = view.findViewById(R.id.tvEssentialOil)

        etEssentialOil = view.findViewById(R.id.etEssentialOil)

        tvWaterWeight = view.findViewById(R.id.tvWaterWeight)
        tvLyeWeight = view.findViewById(R.id.tvLyeWeight)

        imageBtn = view.findViewById(R.id.imageBtn)

        val bottomSheet: View = view.findViewById(R.id.bottomSheet)
        val bottomSheetBehaviour : BottomSheetBehavior<View> = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehaviour.setBottomSheetCallback(object : BottomSheetBehavior.BottomSheetCallback(){

            override fun onSlide(p0: View, p1: Float) {

            }

            override fun onStateChanged(p0: View, p1: Int) {

                when(p1) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        bottomSheetText.text = "Drag to Choose oils"
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        bottomSheetText.text = "Drop to release oils"

                    }

                }

            }
        })

        btnChooseSoap = view.findViewById(R.id.btnChooseSoap)
        btnChooseSoap.setOnClickListener {
            if (bottomSheetBehaviour.state == BottomSheetBehavior.STATE_COLLAPSED)
            {
                bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED
                bottomSheetText.text = "Drag to Choose oils"

            }else{

                bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED
                bottomSheetText.text = "Drop to release oils"

            }
        }
        btnSaveEssentialOils.setOnClickListener {

            val txtEssentialTotal = etEssentialOil.text.toString()
            if (!TextUtils.isEmpty(txtEssentialTotal)){

                val LyeConc = txtEssentialTotal.toDouble()

                if (LyeConc < 100){

                    val soap_id = preferences.getString("recipe_id", null).toString()
                    val oils_exists = checkOils(soap_id.toString())
                    if (oils_exists){

                        databaseHelper.updateEssentialRatio(soap_id, (LyeConc/100))

                        getEssentialOilWeight()

                        Toast.makeText(activity, "Essential oils updated successfully.", Toast.LENGTH_SHORT).show()


                    }else
                        Toast.makeText(activity, "Please add some oils before proceeding", Toast.LENGTH_SHORT).show()

                }else{

                    etEssentialOil.error = "Essential oils% cannot be more than 100 %"
                    Toast.makeText(activity, "Essential oils% cannot be more than 100 %", Toast.LENGTH_SHORT).show()
                }


            }else etEssentialOil.error = "Essential oils cannot be empty."
        }

        imageBtn.setOnClickListener {

            Toast.makeText(activity, "The default lye concentration is 33%", Toast.LENGTH_SHORT).show()

        }

        layoutManager = LinearLayoutManager(activity)

        recyclerView6 = view.findViewById(R.id.recyclerView6)
        myview = view.findViewById<LinearLayout>(R.id.myview)

        btnConfirm = view.findViewById(R.id.btnConfirm)

        btnConfirm.setOnClickListener {

            Recipe_id = preferences.getString("recipe_id", null).toString()

            mySelectedList = oilsAdapter.list
            for (i in mySelectedList.indices) {

                val txtOilName: String = mySelectedList.get(i)
                databaseHelper.AddMySoapOils(Recipe_id, txtOilName)
            }

            Toast.makeText(activity, "Oils added to your list.", Toast.LENGTH_SHORT).show()
            bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED

            CheckOilsList()


        }

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        linearEssentialOil = view.findViewById(R.id.linearEssentialOil)

        btnSaveSuperFat = view.findViewById(R.id.btnSaveSuperFat)

        etSuperFat = view.findViewById(R.id.etSuperFat)
        SuperFatSwitch = view.findViewById(R.id.SuperFatSwitch)

        SuperFatSwitch.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){

                val soap_id = preferences.getString("recipe_id", null).toString()
                val oils_exists = checkOils(soap_id.toString())
                if (oils_exists){

                    linearSuperFat.visibility = View.VISIBLE
                    getSuperFatValues()


                }else{

                    Toast.makeText(activity, "You must add Oils to be used in soap making Process", Toast.LENGTH_LONG).show()
                    linearEssentialOil.visibility = View.GONE

                    if (bottomSheetBehaviour.state == BottomSheetBehavior.STATE_COLLAPSED)
                    {
                        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED

                    }

                    SuperFatSwitch.isChecked = !SuperFatSwitch.isChecked
                }


            }else{

                linearSuperFat.visibility = View.GONE
            }

        }

        btnSaveSuperFat.setOnClickListener {

            val txtSuperFatTotal = etSuperFat.text.toString()
            if (!TextUtils.isEmpty(txtSuperFatTotal)){

                val SuperFat = txtSuperFatTotal.toDouble()

                if (SuperFat < 100){

                    val soap_id = preferences.getString("recipe_id", null).toString()
                    val oils_exists = checkOils(soap_id.toString())
                    if (oils_exists){

                        databaseHelper.updateSuperFatRatio(soap_id, SuperFat)
                        Toast.makeText(activity, "Super fat percentage updated", Toast.LENGTH_LONG).show()

                    }

                }else
                    Toast.makeText(activity, "Super fat percentage cannot be  more than 100%", Toast.LENGTH_LONG).show()


            }else
                etSuperFat.error = "Add a super fat percentage"



        }

        FragranceOils = view.findViewById(R.id.FragranceOils)
        FragranceOils.setOnCheckedChangeListener { buttonView, isChecked ->

            if (isChecked){

                val soap_id = preferences.getString("recipe_id", null).toString()
                val oils_exists = checkOils(soap_id.toString())
                if (oils_exists){

                    //Add Fragrance \ Essential oils

                    linearEssentialOil.visibility = View.VISIBLE

                    getEssentialOilWeight()

                }else{

                    Toast.makeText(activity, "You must add Oils to be used in soap making Process", Toast.LENGTH_LONG).show()
                    linearEssentialOil.visibility = View.GONE

                    if (bottomSheetBehaviour.state == BottomSheetBehavior.STATE_COLLAPSED)
                    {
                        bottomSheetBehaviour.state = BottomSheetBehavior.STATE_EXPANDED

                    }

                    FragranceOils.isChecked = !FragranceOils.isChecked
                }

            }else{

                linearEssentialOil.visibility = View.GONE
            }

        }

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

                val soap_id = preferences.getString("recipe_id", null).toString()
                val oils_exists = checkOils(soap_id.toString())

                if (oils_exists){

                    databaseHelper.updateRatiosPercentages(soap_id, txtLye, txtWater)

                    calculator.getTotalOilWeight(soap_id, activity)

                    val txtLiquidWeight = databaseHelper.getWaterLyeAmount(soap_id).liquid_weight.toString()
                    val txtLyeWeight = databaseHelper.getWaterLyeAmount(soap_id).lye_weight.toString()

                    tvWaterWeight.text = txtLiquidWeight
                    tvLyeWeight.text = txtLyeWeight

                    Toast.makeText(activity, "Ratios updated successfully.", Toast.LENGTH_SHORT).show()


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

                        databaseHelper.updateRatiosPercentages(soap_id, txtLyeConc, txtWater)

                        calculator.getTotalOilWeight(soap_id, activity)

                        val txtLiquidWeight = databaseHelper.getWaterLyeAmount(soap_id).liquid_weight.toString()
                        val txtLyeWeight = databaseHelper.getWaterLyeAmount(soap_id).lye_weight.toString()

                        tvWaterWeight.text = txtLiquidWeight
                        tvLyeWeight.text = txtLyeWeight

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

        val fab = view.findViewById<FloatingActionButton>(R.id.fab)
        fab.setOnClickListener {

            InputDialog()

        }

        return view
    }

    private fun getEssentialOilWeight(){

        //Get The weight of Essential oils. By Default We will Use 2% of the total Oils
        val soap_id = preferences.getString("recipe_id", null).toString()
        val oils_exists = checkOils(soap_id)

        if (oils_exists){

            val TotalWeight =  databaseHelper.getTotalOils(soap_id)
            tvEssentialOil.text = TotalWeight.toString()

        }

    }

    private fun getSuperFatValues(){

        //Get The weight of Essential oils. By Default We will Use 2% of the total Oils
        val soap_id = preferences.getString("recipe_id", null).toString()
        val oils_exists = checkOils(soap_id)

        if (oils_exists){

            val TotalWeight =  databaseHelper.getTotalOils_SuperFat(soap_id)

        }

    }

    private fun InputDialog() {
        val li = LayoutInflater.from(activity)
        val promptsView = li.inflate(R.layout.alert_dialog_fragnance, null)
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setView(promptsView)

        val etIngredients = promptsView.findViewById<EditText>(R.id.etIngredients)
        val etPercentage = promptsView.findViewById<EditText>(R.id.etPercentage)

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

                

            }else{

                if (TextUtils.isEmpty(txtIngredients))etIngredients.error = "The fragrance cannot be empty"
                if (TextUtils.isEmpty(txtPercentage))etPercentage.error = "The percentage cannot be empty"

            }

        }
    }


    override fun onStart() {
        super.onStart()

        CreateDialog()

        StartRecyclerView()

    }

    private fun CreateDialog() {

        val li = LayoutInflater.from(activity)
        val promptsView = li.inflate(R.layout.alert_dialog_recipe, null)
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setView(promptsView)

        val etRecipe = promptsView.findViewById<EditText>(R.id.etRecipe)

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save") { dialog, id -> }

                .setNegativeButton("Cancel") { dialog, id ->

                    val intent = Intent(activity, MainActivityDrawer::class.java)
                    startActivity(intent)

                }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

            val txtRecipe = etRecipe.text.toString()

            if (!TextUtils.isEmpty(txtRecipe)){

                val newDate = Date(c.timeInMillis)
                val resultDate = dateFormat.format(newDate)

                val db: SQLiteDatabase = databaseHelper.writableDatabase

                val query = "Select * From " + DatabaseHelper.TABLE_RECIPE_TABLE + " WHERE " +
                        DatabaseHelper.KEY_RECIPE_NAME + " = '" + txtRecipe + "'"

                val cursorCheck: Cursor = db.rawQuery(query, null)

                if (cursorCheck.count > 0){

                    Toast.makeText(requireContext(), "$txtRecipe already exists.. Change the recipe name", Toast.LENGTH_SHORT).show()
                    etRecipe.error = "Recipe name already exists.."

                }else{

                    val id = databaseHelper.AddRecipe(resultDate, txtRecipe)
                    Toast.makeText(requireContext(), "$txtRecipe added successfully..", Toast.LENGTH_SHORT).show()

                    val editor = preferences.edit()
                    editor.putString("recipe_id", id.toString())
                    editor.apply()

                    activity?.title = "Soap name: $txtRecipe"

                    alertDialog.dismiss()

                    CheckOilsList()

                }

            }else{

                etRecipe.error = "You cannot proceed without a recipe name"

            }

        }

    }

    private fun CheckOilsList() {

        Recipe_id = preferences.getString("recipe_id", null).toString()

        recyclerView.setHasFixedSize(true)
        soapLyeLiquidsPojoArrayList1 = databaseHelper.getMySoapOils(Recipe_id)
        oilsAdapter1 = SoapMyOilsAdapter(activity, soapLyeLiquidsPojoArrayList1)
        recyclerView.adapter = oilsAdapter1

    }

    private fun StartRecyclerView() {

        recyclerView6.layoutManager = layoutManager
        recyclerView6.setHasFixedSize(true)
        soapLyeLiquidsPojoArrayList = databaseHelper.soapOils
        oilsAdapter = SoapOilsAdapter(activity, soapLyeLiquidsPojoArrayList)
        recyclerView6.adapter = oilsAdapter
        if (oilsAdapter.getItemCount() == 0) {
            myview.visibility = View.VISIBLE
            recyclerView6.visibility = View.GONE
        } else {
            myview.visibility = View.GONE
            recyclerView6.visibility = View.VISIBLE
        }

    }



    private fun checkOils(id: String): Boolean{

        val selectQuery = "SELECT * FROM " + DatabaseHelper.TABLE_SOAP_MY_OILS+ " WHERE " + DatabaseHelper.KEY_SOAP_ID + " = '" + id + "'"
        val db: SQLiteDatabase = databaseHelper.readableDatabase

        val cursor1: Cursor = db.rawQuery(selectQuery, null)

        if (cursor1.count > 0)
            return true
        else
            return false


    }


}