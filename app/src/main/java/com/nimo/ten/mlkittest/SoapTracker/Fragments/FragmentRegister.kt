package com.nimo.ten.mlkittest.SoapTracker.Fragments

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Activities.SoapHeal_Healing

class FragmentRegister : Fragment() {


    lateinit var etEmail : EditText
    lateinit var etPassword : EditText
    lateinit var etRetypePassword : EditText

    lateinit var btnRegister : Button

    private lateinit var auth: FirebaseAuth

    lateinit var progressDialog : ProgressDialog

    private var mDatabase: DatabaseReference? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_register, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)
        etRetypePassword = view.findViewById(R.id.etRetypePassword)

        mDatabase = FirebaseDatabase.getInstance().getReference("user_details")

        auth = FirebaseAuth.getInstance()

        progressDialog = ProgressDialog(activity)

        btnRegister = view.findViewById(R.id.btnRegister)

        btnRegister.setOnClickListener {

            val txtEmail : String = etEmail.text.toString()
            val txtPassWord : String = etPassword.text.toString()
            val txtRetypePassword : String = etRetypePassword.text.toString()

            progressDialog.setTitle("Account Registration..")
            progressDialog.setMessage("Please wait as we create your account..")
            progressDialog.setCanceledOnTouchOutside(true)

            if (txtEmail.isNotEmpty() && txtPassWord.isNotEmpty() && txtRetypePassword.isNotEmpty()){

                if (txtRetypePassword == txtPassWord){

                    progressDialog.show()

                    auth.createUserWithEmailAndPassword(txtEmail, txtPassWord)
                            .addOnCompleteListener { task ->

                                if (task.isSuccessful){

                                    val userId : String = auth.currentUser!!.uid
                                    val defaultUserName = getUsernameFromEmail(txtEmail)

                                    val newPost : DatabaseReference = mDatabase!!.child(userId)

                                    newPost.child("email").setValue(txtEmail)
                                    newPost.child("user_id").setValue(userId)
                                    newPost.child("username").setValue(defaultUserName)

                                    progressDialog.dismiss()

                                    val intent = Intent(activity, SoapHeal_Healing::class.java)
                                    startActivity(intent)

                                }else{

                                    progressDialog.dismiss()

                                    val errorMessage : String? = task.exception?.message

                                    Toast.makeText(activity, errorMessage, Toast.LENGTH_SHORT).show()
                                }

                            }

                }else{

                    Toast.makeText(activity, "Passwords do not match", Toast.LENGTH_SHORT).show()

                }

            }else{

                if (txtEmail.isEmpty()) etEmail.error = "Email cannot be empty.."
                if (txtPassWord.isEmpty()) etPassword.error = "Password cannot be empty.."
                if (txtRetypePassword.isEmpty()) etRetypePassword.error = "Password cannot be empty.."

            }

        }

    }

    private fun getUsernameFromEmail(email: String?):String{

        return if (email!!.contains("@")){

            email.split("@".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[0]

        }else{

            email
        }

    }

}