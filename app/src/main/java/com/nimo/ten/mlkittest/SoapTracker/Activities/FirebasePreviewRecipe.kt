package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.Manifest
import android.content.Context
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.graphics.*
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.*
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Adapter.PreviewRecyclerAdapter
import com.nimo.ten.mlkittest.SoapTracker.Pojo.IngredientsPojo
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

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
    private lateinit var logo: Bitmap
    private lateinit var scaleBitmap:Bitmap
    var pageWidth = 1200

    var pageSize = 860

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

        logo = BitmapFactory.decodeResource(resources, R.drawable.nimo_logo)
        scaleBitmap = Bitmap.createScaledBitmap(logo, 1200, 518, false)

        btnSave.setOnClickListener {

            GeneratePdf()

        }
    }

    private fun GeneratePdf() {

        val txtSoapName = tvRecipeName.text.toString()

        val myPdfDocument = PdfDocument()
        val myPaint = Paint()
        val titlePaint = Paint()
        val contentPaint = Paint()

        val myPageInfo = PageInfo.Builder(1200, 2010, 1).create()
        val myPage = myPdfDocument.startPage(myPageInfo)
        val canvas = myPage.canvas

        canvas.drawBitmap(scaleBitmap, 0f, 0f, myPaint)

        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.typeface = Typeface.create(Typeface.DEFAULT, Typeface.BOLD)
        titlePaint.textSize = 70f

        canvas.drawText("Nimonaturals Recipes", pageWidth / 2.toFloat(), 600f, titlePaint)

        myPaint.color = Color.rgb(0, 113, 180)
        myPaint.textSize = 30f
        myPaint.textAlign = Paint.Align.RIGHT

        contentPaint.color = Color.GRAY
        contentPaint.textSize = 30f
        contentPaint.textAlign = Paint.Align.RIGHT

        canvas.drawText("Email: info@nimonaturals.com", 1160f, 450f, contentPaint)
        canvas.drawText("Phone: +254791529944", 1160f, 500f, contentPaint)

        titlePaint.textAlign = Paint.Align.CENTER
        titlePaint.textSize = 70f
        canvas.drawText(txtSoapName, pageWidth / 2.toFloat(), 670f, titlePaint)

        myPaint.style = Paint.Style.STROKE
        myPaint.strokeWidth = 2f
        canvas.drawRect(20f, 780f, pageWidth - 20.toFloat(), 860f, myPaint)

        myPaint.textAlign = Paint.Align.LEFT
        myPaint.style = Paint.Style.FILL

        canvas.drawText("S.No", 40f, 830f, myPaint)
        canvas.drawText("Oil name", 120f, 830f, myPaint)
        canvas.drawText("Grams", 480f, 830f, myPaint)

        canvas.drawLine(110f, 790f, 110f, 840f, myPaint)
        canvas.drawLine(460f, 790f, 460f, 840f, myPaint)
        canvas.drawLine(680f, 790f, 680f, 840f, myPaint)


        for (i in oilsPojoArrayList.indices) {

            pageSize += 40
            val name: String = oilsPojoArrayList[i].ingredient_name
            val maritalStatus: String = oilsPojoArrayList[i].grams

            canvas.drawText((i + 1).toString() + ".", 40f, pageSize.toFloat(), myPaint)
            canvas.drawText(name, 120f, pageSize.toFloat(), myPaint)
            canvas.drawText(maritalStatus, 480f, pageSize.toFloat(), myPaint)

        }


        myPdfDocument.finishPage(myPage)

        val file = File(Environment.getExternalStorageDirectory(), "/$txtSoapName.pdf")
        try {
            myPdfDocument.writeTo(FileOutputStream(file))
        } catch (e: IOException) {
            e.printStackTrace()
        }

        myPdfDocument.close()

        Toast.makeText(this, "Pdf has been generated and saved as $txtSoapName.pdf in your phone.", Toast.LENGTH_LONG).show()
    }

    override fun onStart() {
        super.onStart()

        if (permissionAlreadyGranted()) {
        } else {
            RequestPermissions()
        }

        RecipeKey = sharedPreferences.getString("recipe_key", null).toString()

        getData()
    }

    private fun permissionAlreadyGranted(): Boolean {
        val result = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
        return result == PackageManager.PERMISSION_GRANTED
    }

    private fun RequestPermissions() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
        }
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), 1)
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String?>, grantResults: IntArray) {
        if (requestCode == 1) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted successfully", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Permission is denied!", Toast.LENGTH_SHORT).show()
            }
        }
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
