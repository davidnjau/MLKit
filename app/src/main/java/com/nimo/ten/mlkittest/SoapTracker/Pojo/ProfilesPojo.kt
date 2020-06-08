package com.nimo.ten.mlkittest.SoapTracker.Pojo

class ProfilesPojo {

    private lateinit var userName : String
    private lateinit var userEmail : String
    private lateinit var userPhone : String

    constructor(userName: String, userEmail: String, userPhone: String) {
        this.userName = userName
        this.userEmail = userEmail
        this.userPhone = userPhone
    }

    constructor()


}