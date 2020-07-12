package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.res.Configuration
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.navigation.NavigationView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Fragments.drawer.*
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.replaceFragmenty

class MainActivityDrawer : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    private lateinit var drawer: DrawerLayout
    private lateinit var toggle: ActionBarDrawerToggle

    private val fragmentManager = supportFragmentManager

    private val fragmentRawMaterials = FragmentRawMaterials()
    private val fragmentSampleRecipe = FragmentSampleRecipe()
    private val fragmentMyRecipe = FragmentMyRecipe()
    private val fragmentNewRecipe = FragmentNewRecipe()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_drawer)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

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

                replaceFragmenty(
                        fragment = FragmentCreateNewRecipe(),
                        allowStateLoss = true,
                        containerViewId = R.id.mainContent
                )
                setTitle("Create new recipe")


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


        }

        drawer.closeDrawer(GravityCompat.START)
        return true


    }

    override fun onStart() {
        super.onStart()

        replaceFragmenty(
                fragment = FragmentMyRecipe(),
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
}
