package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Adapter.PreviewRecyclerAdapter
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelperNew
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo


class PreviewRecipeDetails : AppCompatActivity() {

    private lateinit var tvTotalOilWeight: TextView
    private lateinit var tvSuperFat: TextView
    private lateinit var tvWaterWeight: TextView
    private lateinit var tvLyeWeight: TextView
    private lateinit var tvEssentialOilPercentage: TextView
    private lateinit var tvEssentialOilWeight: TextView

    private lateinit var btnSave: TextView

    private lateinit var tvRecipeName: TextView
    private lateinit var tvDateCreated: TextView

    private lateinit var recyclerViewOils: RecyclerView
    private lateinit var recyclerViewEssentialOils: RecyclerView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var RecipeId : String
    private lateinit var databaseHelper1:DatabaseHelperNew

    var soapTrackerPojoArrayList: List<IngredientsPojo>? = null
    var previewRecipeDetails: PreviewRecyclerAdapter? = null

    lateinit var layoutManager2: RecyclerView.LayoutManager
    lateinit var layoutManager: RecyclerView.LayoutManager

    private var soapLyeLiquidsPojoArrayList1 = ArrayList<IngredientsPojo>()
    private lateinit var fab: FloatingActionButton

    private var oilsUsed = ArrayList<IngredientsPojo>()
    private var essentialOilsUsed = ArrayList<IngredientsPojo>()

    var database = FirebaseDatabase.getInstance()
    var myRef = database.getReference("Recipes")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preview_recipe)

        tvTotalOilWeight = findViewById(R.id.tvTotalOilWeight)
        tvSuperFat = findViewById(R.id.tvSuperFat)
        tvWaterWeight = findViewById(R.id.tvWaterWeight)
        tvLyeWeight = findViewById(R.id.tvLyeWeight)
        tvEssentialOilPercentage = findViewById(R.id.tvEssentialOilPercentage)
        tvEssentialOilWeight = findViewById(R.id.tvEssentialOilWeight)
        btnSave = findViewById(R.id.btnSave)

        tvRecipeName = findViewById(R.id.tvRecipeName)
        tvDateCreated = findViewById(R.id.tvDateCreated)

        layoutManager2 = LinearLayoutManager(applicationContext)
        layoutManager = LinearLayoutManager(applicationContext)

        recyclerViewOils = findViewById(R.id.recyclerViewOils)
        recyclerViewEssentialOils = findViewById(R.id.recyclerViewEssentialOils)

        databaseHelper1 = DatabaseHelperNew(this)
        sharedPreferences = applicationContext.getSharedPreferences("Soap", Context.MODE_PRIVATE)

        fab = findViewById(R.id.fab)
        fab.setOnClickListener {

            val recipeKey = myRef.push().key.toString()
            val recipeDetails = myRef.child(recipeKey).child("weight_information")

            val oilDetails = myRef.child(recipeKey).child("oils_used")
            val essentialDetails = myRef.child(recipeKey).child("essential_oils_used")

            //Upload To Firebase Database
            val recipeName = databaseHelper1.getOilsWeight(RecipeId).recipeName
            val LiquidWeight = databaseHelper1.getOilsWeight(RecipeId).liquidWeight
            val LyeWeight = databaseHelper1.getOilsWeight(RecipeId).lyeWeight
            val essentilaWeight = databaseHelper1.getOilsWeight(RecipeId).essentialOil
            val essentialPercentage = databaseHelper1.getOilsWeight(RecipeId).essentialRatio.toDouble() * 100
            val superFat = databaseHelper1.getOilsWeight(RecipeId).superFat
            val OilAmount = databaseHelper1.getOilsWeight(RecipeId).oilWeight

            val firebaseKey = databaseHelper1.getOilsWeight(RecipeId).firebaseKey

            Log.e("*-*-* ", firebaseKey)

            oilsUsed = databaseHelper1.getMyOils(RecipeId)
            essentialOilsUsed = databaseHelper1.getSoapEssentialOils(RecipeId)

            if (firebaseKey == "none"){

                recipeDetails.child("recipe_name").setValue(recipeName)
                recipeDetails.child("liquid_weight").setValue(LiquidWeight)
                recipeDetails.child("naoh_weight").setValue(LyeWeight)
                recipeDetails.child("essential_weight").setValue(essentilaWeight)
                recipeDetails.child("essential_percentage").setValue(essentialPercentage.toString())
                recipeDetails.child("super_fat_percentage").setValue(superFat)
                recipeDetails.child("oil_amount").setValue(OilAmount)
                recipeDetails.child("recipe_key").setValue(recipeKey)

                for (i in oilsUsed){

                    val newOils = oilDetails.push()

                    val oilName = i.ingredient_name
                    val oilWeight = i.grams

                    newOils.child("oil_name").setValue(oilName)
                    newOils.child("oil_weight").setValue(oilWeight)

                }
                for (i in essentialOilsUsed){

                    val newOils = essentialDetails.push()

                    val oilName = i.ingredient_name
                    val oilWeight = i.grams

                    newOils.child("oil_name").setValue(oilName)
                    newOils.child("oil_weight").setValue(oilWeight)
                }

                databaseHelper1.updateRecipeKey(RecipeId, recipeKey)

            }else{

                Toast.makeText(applicationContext, "The recipe is already in the database", Toast.LENGTH_SHORT).show()

            }

        }
        btnSave.setOnClickListener {

            startActivity(Intent(applicationContext, SoapHeal_Healing::class.java))

        }
    }

    override fun onStart() {
        super.onStart()

        RecipeId = sharedPreferences.getString("recipe_id", null).toString()

        getWeights()
        getEssentialOils()
        getOilsUsed()

    }

    private fun getWeights(){

        val OilAmount = databaseHelper1.getOilsWeight(RecipeId).oilWeight
        val superFat = databaseHelper1.getOilsWeight(RecipeId).superFat

        val LiquidWeight = databaseHelper1.getOilsWeight(RecipeId).liquidWeight
        val LyeWeight = databaseHelper1.getOilsWeight(RecipeId).lyeWeight

        val essentilaWeight = databaseHelper1.getOilsWeight(RecipeId).essentialOil
        val essentialPercentage = databaseHelper1.getOilsWeight(RecipeId).essentialRatio.toDouble() * 100

        val recipeName = databaseHelper1.getOilsWeight(RecipeId).recipeName
        val dateCreated = databaseHelper1.getOilsWeight(RecipeId).date_in

        tvTotalOilWeight.text = OilAmount
        tvSuperFat.text = superFat

        tvWaterWeight.text = LiquidWeight
        tvLyeWeight.text = LyeWeight

        tvEssentialOilPercentage.text = essentialPercentage.toString()
        tvEssentialOilWeight.text = essentilaWeight

        tvRecipeName.text = recipeName
        tvDateCreated.text = dateCreated

    }

    private fun getOilsUsed(){

        recyclerViewOils.layoutManager = layoutManager
        recyclerViewOils.setHasFixedSize(true)
        soapLyeLiquidsPojoArrayList1 = databaseHelper1.getMyOils(RecipeId)
        previewRecipeDetails = PreviewRecyclerAdapter(applicationContext, soapLyeLiquidsPojoArrayList1)
        recyclerViewOils.adapter = previewRecipeDetails

    }

    private fun getEssentialOils(){

        recyclerViewEssentialOils.layoutManager = layoutManager2
        recyclerViewEssentialOils.setHasFixedSize(true)
        soapTrackerPojoArrayList = databaseHelper1.getSoapEssentialOils(RecipeId)
        previewRecipeDetails = PreviewRecyclerAdapter(applicationContext,
                soapTrackerPojoArrayList as ArrayList<IngredientsPojo?>?)

        recyclerViewEssentialOils.adapter = previewRecipeDetails

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.preview_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.ic_share) {

        } else if (id == R.id.ic_generate_pdf) {

            GeneratePdf()

        } else if (id == R.id.icon_Sign_Out) {

            Toast.makeText(this, "Under Development", Toast.LENGTH_SHORT).show()

        } else if (id == R.id.icon_exit_app) {
            ExitDialog()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun GeneratePdf() {


    }

    private fun ExitDialog() {
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setMessage("Are you sure You want to exit the app")
        alertDialogBuilder.setPositiveButton("yes"
        ) { arg0, arg1 ->
            val intent = Intent(Intent.ACTION_MAIN)
            intent.addCategory(Intent.CATEGORY_HOME)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
        alertDialogBuilder.setNegativeButton("No") { dialog, which -> }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }
}
