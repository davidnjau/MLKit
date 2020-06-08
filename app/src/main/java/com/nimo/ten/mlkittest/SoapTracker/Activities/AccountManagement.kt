package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import com.google.firebase.auth.FirebaseAuth
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentLogin
import com.nimo.ten.mlkittest.SoapTracker.Fragments.FragmentRegister

class AccountManagement : AppCompatActivity() {

    private val fragmentManager = supportFragmentManager
    private val fragmentLogin = FragmentLogin()
    private val fragmentRegister = FragmentRegister()

    private lateinit var auth : FirebaseAuth

    private lateinit var btnLogin : Button
    private lateinit var btnRegister : Button
    private lateinit var linearLogin : LinearLayout
    private lateinit var linearRegister : LinearLayout


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_account_management)

        auth = FirebaseAuth.getInstance()

        btnLogin = findViewById(R.id.btnLogin)
        btnRegister = findViewById(R.id.btnRegister)

        linearLogin = findViewById(R.id.linearLogin)
        linearRegister = findViewById(R.id.linearRegister)

        btnLogin.setOnClickListener {

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.myFragment, fragmentLogin)
            fragmentTransaction.commit()

            btnLogin.isEnabled = false
            btnRegister.isEnabled = true

            linearLogin.visibility = View.INVISIBLE
            linearRegister.visibility = View.VISIBLE
        }

        btnRegister.setOnClickListener {

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.myFragment, fragmentRegister)
            fragmentTransaction.commit()

            btnLogin.isEnabled = true
            btnRegister.isEnabled = false

            linearRegister.visibility = View.INVISIBLE
            linearLogin.visibility = View.VISIBLE

        }

    }

    override fun onStart() {
        super.onStart()

        val currentUser = auth.currentUser
        if (currentUser != null){

            val intent = Intent(this, SoapHeal_Healing::class.java)
            startActivity(intent)

        }else{

            val fragmentTransaction = fragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.myFragment, fragmentLogin)
            fragmentTransaction.commit()

            linearLogin.visibility = View.INVISIBLE
            linearRegister.visibility = View.VISIBLE
        }
    }
}
