package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentOilList
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentOilMain

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
