package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.res.Configuration
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelperNew
import com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.*
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.replaceFragmenty
import java.text.SimpleDateFormat
import java.util.*

class MainActivityDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private val fragmentManager = supportFragmentManager

    private val fragmentRawMaterials = FragmentRawMaterials()
    private val fragmentSampleRecipe = FragmentSampleRecipe()
    private val fragmentMyRecipe = FragmentMyRecipe()
    private val fragmentNewRecipe = FragmentNewRecipe()

    val dateFormat = SimpleDateFormat("dd-MMM-yyyy")
    var c = Calendar.getInstance()
    lateinit var databaseHelper1: DatabaseHelperNew
    private lateinit var preferences : SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        databaseHelper1 = DatabaseHelperNew(this)

        preferences = applicationContext.getSharedPreferences("Soap", Context.MODE_PRIVATE)

        drawer = findViewById(R.id.drawer_layout)

        val navigationView: NavigationView = findViewById(R.id.nav_view)
        navigationView.setNavigationItemSelectedListener(this)

        toggle = ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer.addDrawerListener(toggle)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)


    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        toggle.syncState()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        toggle.onConfigurationChanged(newConfig)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (toggle.onOptionsItemSelected(item)) {
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onNavigationItemSelected(p0: MenuItem): Boolean {

        when(p0.itemId){

            R.id.nav_new_recipe -> {

                CreateDialog()

            }
            R.id.nav_my_recipe -> {

                replaceFragmenty(
                        fragment = FragmentMyRecipe(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                )
                setTitle("My recipes")
            }
            R.id.nav_raw_ingredients -> {

                replaceFragmenty(
                        fragment = FragmentRawMaterials(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                )
                setTitle("Buy Raw Ingredients")

            }
            R.id.nav_sample_recipes -> {

                replaceFragmenty(
                        fragment = FragmentSampleRecipe(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                )
                setTitle("View Sample Recipes")
            }
            R.id.nav_about_us -> {

                replaceFragmenty(
                        fragment = FragmentAboutUs(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                )
                setTitle("Contact us")
            }
            R.id.nav_raw_ingredients -> {

                replaceFragmenty(
                        fragment = FragmentBuyRawMaterials(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                )
                setTitle("View Sample Recipes")
            }

            R.id.nav_profile -> {

                startActivity(Intent(applicationContext, Profile::class.java))
            }

            R.id.nav_exit_app ->{

                ExitDialog()
            }


        }

        drawer.closeDrawer(GravityCompat.START)
        return true


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

    override fun onStart() {
        super.onStart()

        replaceFragmenty(
                fragment = FragmentSampleRecipe(),
                allowStateLoss = true,
                containerViewId = R.id.mainContent
        )
    }

    override fun onBackPressed() {

        if (drawer.isDrawerOpen(GravityCompat.START)){
            drawer.closeDrawer(GravityCompat.START)
        }else
            super.onBackPressed()
    }

    private fun CreateDialog() {

        val li = LayoutInflater.from(this)
        val promptsView = li.inflate(R.layout.alert_dialog_recipe, null)
        val alertDialogBuilder = AlertDialog.Builder(this)
        alertDialogBuilder.setView(promptsView)

        val etRecipe = promptsView.findViewById<EditText>(R.id.etRecipe)

        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Save") { dialog, id -> }

                .setNegativeButton("Cancel") { dialog, id ->

                    val intent = Intent(this, MainActivityDrawer::class.java)
                    startActivity(intent)

                }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()

        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setOnClickListener {

            val txtRecipe = etRecipe.text.toString()

            if (!TextUtils.isEmpty(txtRecipe)){

                val newDate = Date(c.timeInMillis)
                val resultDate = dateFormat.format(newDate)

                val db: SQLiteDatabase = databaseHelper1.writableDatabase

                val query = "Select * From " + DatabaseHelper.TABLE_RECIPE_TABLE + " WHERE " +
                        DatabaseHelper.KEY_RECIPE_NAME + " = '" + txtRecipe + "'"

                val cursorCheck: Cursor = db.rawQuery(query, null)

                if (cursorCheck.count > 0){

                    Toast.makeText(this, "$txtRecipe already exists.. Change the recipe name", Toast.LENGTH_SHORT).show()
                    etRecipe.error = "Recipe name already exists.."

                }else{

                    val id = databaseHelper1.AddRecipe(resultDate, txtRecipe)
                    Toast.makeText(this, "$txtRecipe added successfully..", Toast.LENGTH_SHORT).show()

                    val editor = preferences.edit()
                    editor.putString("recipe_id", id.toString())
                    editor.apply()

                    this.title = "Soap name: $txtRecipe"

                    replaceFragmenty(
                            fragment = FragmentStartRecipe(),
                            allowStateLoss = true,
                            containerViewId = R.id.mainContent
                    )
                    setTitle("Create new recipe")

                    alertDialog.dismiss()

                }

            }else{

                etRecipe.error = "You cannot proceed without a recipe name"

            }

        }

    }

}
