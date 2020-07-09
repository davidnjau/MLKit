package com.nimo.ten.mlkittest.SoapTracker.HelperClass

import androidx.annotation.IdRes
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

fun AppCompatActivity.replaceFragmenty(fragment: Fragment,
                                       allowStateLoss: Boolean = false,
                                       @IdRes containerViewId: Int){

    val fragmentManager = supportFragmentManager

    val fragmentTransaction = fragmentManager.beginTransaction().replace(containerViewId, fragment)

    if (!supportFragmentManager.isStateSaved){
        fragmentTransaction.commit()
        println("*-*-*-*-*")
    }else if (allowStateLoss){
        fragmentTransaction.commitAllowingStateLoss()
    }

}