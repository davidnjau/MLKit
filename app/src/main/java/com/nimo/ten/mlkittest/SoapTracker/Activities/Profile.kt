package com.nimo.ten.mlkittest.SoapTracker.Activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageButton
import android.widget.TextView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Database.DatabaseHelper
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.GetText
import com.nimo.ten.mlkittest.SoapTracker.HelperClass.User
import com.nimo.ten.mlkittest.SoapTracker.Pojo.ProfilesPojo
import kotlinx.android.synthetic.main.activity_profile.*
import kotlinx.android.synthetic.main.fragment_login.*

class Profile : AppCompatActivity(), View.OnClickListener {

    private lateinit var tvTotalSoaps1 : TextView
    private lateinit var tvHealing1 : TextView
    private lateinit var tvCuredSoaps1 : TextView

    private lateinit var tvUsername1 : TextView
    private lateinit var tvEmail1 : TextView

    private lateinit var databaseReference : DatabaseReference

    private lateinit var txtUserUID : String

    private lateinit var auth: FirebaseAuth

    private lateinit var tvPhoneNumber1 : TextView
    private lateinit var tvIgLink1 : TextView

    private lateinit var etUsername : EditText
    private lateinit var imgBtnName : ImageButton

    private lateinit var etEmail : EditText
    private lateinit var imgBtnEmail : ImageButton

    private lateinit var etPhoneNumber : EditText
    private lateinit var imgBtnPhone : ImageButton

    private lateinit var databaseHelper: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        tvTotalSoaps1 = findViewById(R.id.tvTotalSoaps)
        tvHealing1 = findViewById(R.id.tvHealing)
        tvCuredSoaps1 = findViewById(R.id.tvCuredSoaps)

        databaseHelper = DatabaseHelper(this)

        tvUsername1 = findViewById(R.id.tvUsername)
        tvEmail1 = findViewById(R.id.tvEmail)
        tvPhoneNumber1 = findViewById(R.id.tvPhoneNumber)
        tvIgLink1 = findViewById(R.id.tvIgLink)

        etUsername = findViewById(R.id.etUsername)
        imgBtnName = findViewById(R.id.imgBtn)

        etEmail = findViewById(R.id.etEmail)
        imgBtnEmail = findViewById(R.id.imgBtnEmail)

        etPhoneNumber = findViewById(R.id.etPhoneNumber)
        imgBtnPhone = findViewById(R.id.imgBtnPhone)

        auth = FirebaseAuth.getInstance()

        imgBtnPhone.setOnClickListener(this)
        imgBtnEmail.setOnClickListener(this)
        imgBtnName.setOnClickListener(this)

    }

    override fun onStart() {
        super.onStart()

//        var profilesPojo : ProfilesPojo = databaseHelper.profilesInformation[0]
//        println("-*-*-* " + profilesPojo)

    }

    fun EditText(view: View) {

        val txtId = view.id

        if(txtId == R.id.tvSettings){

            val intent = Intent(this, Settings::class.java)
            startActivity(intent)

        }else if (txtId == R.id.tvUsername){

            GetText().Visibility(etUsername, tvUsername1, imgBtnName, "")

        }else if (txtId == R.id.tvEmail){

            GetText().Visibility(etEmail, tvEmail1, imgBtnEmail, "")

        }else if (txtId == R.id.tvPhoneNumber){

            GetText().Visibility(etPhoneNumber, tvPhoneNumber1, imgBtnPhone, "")

        }

    }

    override fun onClick(v: View?) {

        val  txtId = v?.id
        if (txtId == R.id.imgBtn){

            GetText().SetDatabaseData(etUsername, tvUsername1, imgBtnName, "username")

        }else if (txtId == R.id.imgBtnEmail){

            GetText().SetDatabaseData(etEmail, tvEmail1, imgBtnEmail, "email")


        }else if (txtId == R.id.imgBtnPhone){

            GetText().SetDatabaseData(etPhoneNumber, tvPhoneNumber1, imgBtnPhone, "phone_number")

        }
    }


}
