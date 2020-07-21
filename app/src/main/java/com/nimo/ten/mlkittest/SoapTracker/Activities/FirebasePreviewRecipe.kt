package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Adapter.PreviewRecyclerAdapter
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.FirebaseData
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo

class FirebasePreviewRecipe : AppCompatActivity() {

    private lateinit var tvTotalOilWeight: TextView
    private lateinit var tvSuperFat: TextView
    private lateinit var tvWaterWeight: TextView
    private lateinit var tvLyeWeight: TextView
    private lateinit var tvEssentialOilPercentage: TextView
    private lateinit var tvEssentialOilWeight: TextView

    private lateinit var btnSave: TextView

    private lateinit var tvRecipeName: TextView

    private lateinit var recyclerViewOils: RecyclerView
    private lateinit var recyclerViewEssentialOils: RecyclerView

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var RecipeKey : String

    lateinit var layoutManager2: RecyclerView.LayoutManager
    lateinit var layoutManager: RecyclerView.LayoutManager
    private lateinit var mDatabase: DatabaseReference

    private var oilsPojoArrayList = ArrayList<IngredientsPojo>()
    private var essentialPojoArrayList1 = ArrayList<IngredientsPojo>()
    var previewRecipeDetails: PreviewRecyclerAdapter? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_firebase_preview_recipe)

        tvTotalOilWeight = findViewById(R.id.tvTotalOilWeight)
        tvSuperFat = findViewById(R.id.tvSuperFat)
        tvWaterWeight = findViewById(R.id.tvWaterWeight)
        tvLyeWeight = findViewById(R.id.tvLyeWeight)
        tvEssentialOilPercentage = findViewById(R.id.tvEssentialOilPercentage)
        tvEssentialOilWeight = findViewById(R.id.tvEssentialOilWeight)
        btnSave = findViewById(R.id.btnSave)

        tvRecipeName = findViewById(R.id.tvRecipeName)
        mDatabase = FirebaseDatabase.getInstance().reference.child("Recipes")

        layoutManager2 = LinearLayoutManager(applicationContext)
        layoutManager = LinearLayoutManager(applicationContext)

        recyclerViewOils = findViewById(R.id.recyclerViewOils)
        recyclerViewEssentialOils = findViewById(R.id.recyclerViewEssentialOils)

        sharedPreferences = applicationContext.getSharedPreferences("Soap", Context.MODE_PRIVATE)
    }

    override fun onStart() {
        super.onStart()

        RecipeKey = sharedPreferences.getString("recipe_key", null).toString()

        getData()
    }

    private fun getData() {

        //Fetch Data from Firebase Database
        mDatabase.child(RecipeKey).child("weight_information")
                .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(childSnapshot: DataSnapshot) {

                val recipe_name = childSnapshot.child("recipe_name").value
                val liquid_weight = childSnapshot.child("liquid_weight").value.toString()
                val naoh_weight = childSnapshot.child("naoh_weight").value.toString()
                val essential_weight = childSnapshot.child("essential_weight").value.toString()
                val essential_percentage = childSnapshot.child("essential_percentage").value.toString()
                val super_fat_percentage = childSnapshot.child("super_fat_percentage").value.toString()
                val oil_amount = childSnapshot.child("oil_amount").value.toString()

                tvTotalOilWeight.text = oil_amount
                tvSuperFat.text = super_fat_percentage

                tvWaterWeight.text = liquid_weight
                tvLyeWeight.text = naoh_weight

                tvEssentialOilPercentage.text = essential_percentage
                tvEssentialOilWeight.text = essential_weight

                tvRecipeName.text = recipe_name.toString()
            }

            override fun onCancelled(error: DatabaseError) {}
        })


        mDatabase.child(RecipeKey).child("oils_used").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {

                    val firebase_data = IngredientsPojo()

                    val oil_name = childSnapshot.child("oil_name").value.toString()
                    val oil_weight = childSnapshot.child("oil_weight").value.toString()

                    firebase_data.ingredient_name = oil_name
                    firebase_data.grams = oil_weight

                    oilsPojoArrayList.add(firebase_data)

                    getOilsUsed()


                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })


        mDatabase.child(RecipeKey).child("essential_oils_used").addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (childSnapshot in dataSnapshot.children) {

                    val firebase_data = IngredientsPojo()

                    val oil_name = childSnapshot.child("oil_name").value.toString()
                    val oil_weight = childSnapshot.child("oil_weight").value.toString()

                    firebase_data.ingredient_name = oil_name
                    firebase_data.grams = oil_weight

                    essentialPojoArrayList1.add(firebase_data)

                    getEssentialOils()

                }
            }

            override fun onCancelled(error: DatabaseError) {}
        })
    }

    private fun getOilsUsed(){

        recyclerViewOils.layoutManager = layoutManager
        recyclerViewOils.setHasFixedSize(true)

        previewRecipeDetails = PreviewRecyclerAdapter(applicationContext, oilsPojoArrayList)
        recyclerViewOils.adapter = previewRecipeDetails

    }

    private fun getEssentialOils(){

        recyclerViewEssentialOils.layoutManager = layoutManager2
        recyclerViewEssentialOils.setHasFixedSize(true)

        previewRecipeDetails = PreviewRecyclerAdapter(applicationContext, essentialPojoArrayList1)
        recyclerViewEssentialOils.adapter = previewRecipeDetails

    }

}
