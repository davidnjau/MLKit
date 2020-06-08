package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Adapter.OilsAdapter
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentOilList
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentOilMain
import com.nimo.ten.mlkittest.SoapTracker.Pojo.SoapLyeLiquidsPojo

class SoapRecipe : AppCompatActivity() {

    private lateinit var btnChooseSoap: Button

    private val fragmenManager = supportFragmentManager
    private val fragmentOilList = FragmentOilList()
    private val fragmentOilMain = FragmentOilMain()

    private var isOpened: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_soap_recipe)

        btnChooseSoap = findViewById(R.id.btnChooseSoap)
        btnChooseSoap.setOnClickListener {

            isOpened = if (!isOpened){

                val fragTransaction = fragmenManager.beginTransaction()
                fragTransaction.replace(R.id.myFragment, fragmentOilList)
                fragTransaction.commit()

                true

            }else{

                val fragTransaction = fragmenManager.beginTransaction()
                fragTransaction.replace(R.id.myFragment, fragmentOilMain)
                fragTransaction.commit();

                false


            }




        }

    }

    override fun onStart() {
        super.onStart()

        val fragTransaction = fragmenManager.beginTransaction()
        fragTransaction.replace(R.id.myFragment, fragmentOilMain)
        fragTransaction.commit();

    }


}
