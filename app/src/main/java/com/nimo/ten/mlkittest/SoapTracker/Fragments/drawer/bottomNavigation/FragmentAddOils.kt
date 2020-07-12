package com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.bottomNavigation

import android.content.Context
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
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapMyOilsAdapter
import com.nimo.ten.mlkittest.SoapTracker.Adapter.SoapOilsAdapter
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.Calculator
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.SoapOilsPojo
import com.nimo.ten.mlkittest.SoapTracker.Pojo.CheckboxPojo
import java.util.*

class FragmentAddOils : Fragment() {

    lateinit var bottomSheetText: TextView
    lateinit var btnChooseSoap: Button
    lateinit var recyclerView6: RecyclerView
    lateinit var layoutManager1: RecyclerView.LayoutManager
    private lateinit var myview: LinearLayout
    private lateinit var oilsAdapter: SoapOilsAdapter
    private var soapLyeLiquidsPojoArrayList = ArrayList<CheckboxPojo>()
    lateinit var databaseHelper: DatabaseHelper
    private lateinit var btnConfirm: Button
    private lateinit var preferences: SharedPreferences
    private lateinit var Recipe_id: String
    private lateinit var recyclerView: RecyclerView
    private lateinit var oilsAdapter1: SoapMyOilsAdapter
    private var soapLyeLiquidsPojoArrayList1 = ArrayList<SoapOilsPojo>()
    private lateinit var btnTotalOilAmount: Button
    private lateinit var btnSaveOils: Button
    private lateinit var etOilAmount: EditText
    private lateinit var calculator:Calculator
    private lateinit var SuperFatSwitch: Switch
    private lateinit var linearSuperFat: LinearLayout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val view = inflater.inflate(R.layout.fragment_add_oils, container, false)

        bottomSheetText = view.findViewById(R.id.bottomSheetText)
        btnChooseSoap = view.findViewById(R.id.btnChooseSoap)

        calculator = Calculator()
        layoutManager1 = LinearLayoutManager(activity)

        recyclerView6 = view.findViewById(R.id.recyclerView6)
        myview = view.findViewById<LinearLayout>(R.id.myview)
        databaseHelper = DatabaseHelper(activity)
        btnConfirm = view.findViewById(R.id.btnConfirm)
        btnTotalOilAmount = view.findViewById(R.id.btnTotalOilAmount)
        btnSaveOils = view.findViewById(R.id.btnSaveOils)
        etOilAmount = view.findViewById(R.id.etOilAmount)

        preferences = requireActivity().getSharedPreferences("Soap", Context.MODE_PRIVATE)
        linearSuperFat = view.findViewById(R.id.linearSuperFat)

        recyclerView = view.findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(activity)

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

        btnConfirm.setOnClickListener {

            Recipe_id = preferences.getString("recipe_id", null).toString()

            for (i in SoapOilsAdapter.soapTrackerPojoArrayList.indices) {
                if (SoapOilsAdapter.soapTrackerPojoArrayList[i].selected) {

                    val txtOilName: String = SoapOilsAdapter.soapTrackerPojoArrayList[i].liquids
                    val txtNaoh: String = SoapOilsAdapter.soapTrackerPojoArrayList[i].naOh

                    databaseHelper.AddMySoapOils(Recipe_id, txtOilName, txtNaoh)

                }
            }

            Toast.makeText(activity, "Oils added to your list.", Toast.LENGTH_SHORT).show()
            bottomSheetBehaviour.state = BottomSheetBehavior.STATE_COLLAPSED

            CheckOilsList()


        }

        btnTotalOilAmount.setOnClickListener {

            val txtOilAmount = etOilAmount.text.toString()
            if (!TextUtils.isEmpty(txtOilAmount)){

                //Total oil amount
                databaseHelper.updateOilAmount(Recipe_id, txtOilAmount.toDouble(), activity)
                Toast.makeText(activity, "The oil amount was updated successfully", Toast.LENGTH_LONG).show()

                CheckOilsList()

            }else {
                etOilAmount.error = "You must first enter the total amount of oils"
                Toast.makeText(activity, "You must first enter the total amount of oils", Toast.LENGTH_LONG).show()
            }
        }

        btnSaveOils.setOnClickListener {

            val txtTotalOils = etOilAmount.text

            if (!TextUtils.isEmpty(txtTotalOils)) {
                //Calculate Everything
                val RemainingPercentage: Double = calculator.getRemainingTotalOilsPercentage(Recipe_id, context)

                if (RemainingPercentage != 0.0) {

                    val RemPerc = RemainingPercentage * -1

                    var result = when  {

                        RemainingPercentage == 100.0 -> "Please save some oil percentages."
                        RemainingPercentage < 100.0 && RemainingPercentage > 0.0 -> "Your percentages are less by $RemainingPercentage % \nPlease adjust your percentages."
                        RemainingPercentage < 0.0 -> "You have exceeded the total percentage by $RemPerc % \nPlease adjust your percentages."
                        else -> "Error! Please try again"
                    }

                    OpenDialog(result)

                } else {

                    Toast.makeText(activity, "Good", Toast.LENGTH_LONG).show()

                }
            }else {

                etOilAmount.error = "The total amount of oils cannot be empty"
            }


        }

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

        return view
    }

    private fun OpenDialog(result: String) {

        val builder = activity?.let { AlertDialog.Builder(it) }
        //set title for alert dialog
        builder!!.setTitle("Alert Dialog")
        //set message for alert dialog
        builder.setMessage(result)

        //performing cancel action
        builder.setNeutralButton("Cancel"){dialogInterface , which ->

        }

        // Create the AlertDialog
        val alertDialog: AlertDialog = builder.create()
        // Set other dialog properties
        alertDialog.setCancelable(false)
        alertDialog.show()

    }


    override fun onStart() {
        super.onStart()

        Recipe_id = preferences.getString("recipe_id", null).toString()

        StartRecyclerView()
        CheckOilsList()
    }

    private fun StartRecyclerView() {

        recyclerView6.layoutManager = layoutManager1
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

    private fun CheckOilsList() {


        recyclerView.setHasFixedSize(true)
        soapLyeLiquidsPojoArrayList1 = databaseHelper.getMySoapOils(Recipe_id)
        oilsAdapter1 = SoapMyOilsAdapter(activity, soapLyeLiquidsPojoArrayList1)
        recyclerView.adapter = oilsAdapter1

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

    private fun getSuperFatValues(){

        val soap_id = preferences.getString("recipe_id", null).toString()
        val oils_exists = checkOils(soap_id)

        if (oils_exists){

            databaseHelper.getTotalOils_SuperFat(soap_id)

        }

    }


}