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
import com.nimo.ten.mlkittest.R
import com.nimo.ten.mlkittest.SoapTracker.Activities.SoapHeal_Healing

class FragmentLogin : Fragment() {

    lateinit var btn_LoginAccount: Button

    lateinit var etPassword: EditText
    lateinit var etEmail: EditText

    lateinit var auth: FirebaseAuth

    lateinit var progressDialog: ProgressDialog

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {

        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        etEmail = view.findViewById(R.id.etEmail)
        etPassword = view.findViewById(R.id.etPassword)

        progressDialog = activity?.let { ProgressDialog(it) }!!

        auth = FirebaseAuth.getInstance()

        btn_LoginAccount = view.findViewById(R.id.btn_LoginAccount)

//        btnRegister = view.findViewById(R.id.btnRegister)
//        btnRegister.setOnClickListener {
//
//            activity!!
//                    .supportFragmentManager
//                    .beginTransaction()
//                    .replace(R.id.myFragment, FragmentRegister())
//                    .commitNow()
//
//        }

        btn_LoginAccount.setOnClickListener {

            val txtEmail : String = etEmail.text.toString()
            val txPass : String = etPassword.text.toString()

            progressDialog.setTitle("Account Processing")
            progressDialog.setMessage("Please wait as we check your credentials..")
            progressDialog.setCanceledOnTouchOutside(false)

            if (txtEmail.isNotEmpty() && txPass.isNotEmpty()){

                progressDialog.show()

                auth.signInWithEmailAndPassword(txtEmail, txPass)
                        .addOnCompleteListener { task ->

                            if (task.isSuccessful){

                                progressDialog.dismiss()

                                Toast.makeText(activity, "You have successfully signed in..", Toast.LENGTH_SHORT).show()

                                val intent = Intent(activity, SoapHeal_Healing::class.java)
                                startActivity(intent)

                            }else{

                                progressDialog.dismiss()

                                val errorString = task.exception?.message
                                Toast.makeText(activity, errorString, Toast.LENGTH_SHORT).show()


                            }

                        }


            }else{

                if (txtEmail.isEmpty()) etEmail.error = "Enter an email address.."
                if (txPass.isEmpty()) etPassword.error = "Enter a password.."

            }


        }

    }

}