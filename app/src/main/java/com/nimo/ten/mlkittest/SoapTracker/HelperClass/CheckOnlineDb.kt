package com.nimo.ten.mlkittest.SoapTracker.HelperClass

import android.app.Activity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nimo.ten.mlkittest.SoapTracker.Activities.SingleSoapDetails
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import java.util.*

class CheckOnlineDb {

    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var databaseHelper: DatabaseHelper

    fun getDataFromDb(singleSoapDetails: SingleSoapDetails) {

        auth = FirebaseAuth.getInstance()

        databaseHelper = DatabaseHelper(singleSoapDetails)

        val txtUserUID : String = auth.currentUser!!.uid
        databaseReference = FirebaseDatabase.getInstance().reference.child("user_details").child(txtUserUID)

        val eventListner = object : ValueEventListener {
            override fun onCancelled(p0: DatabaseError) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onDataChange(p0: DataSnapshot) {

                val Usename :String = p0.child("username").value.toString()
                val Email : String = p0.child("email").value.toString()
                val PhoneNumber :String = p0.child("phone_number").value.toString()

                databaseHelper.AddProfilesDetails(Usename, Email, PhoneNumber, "")



            }
        }

        databaseReference.addValueEventListener(eventListner)
    }

}