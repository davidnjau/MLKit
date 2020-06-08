package com.nimo.ten.mlkittest.SoapTracker.HelperClass

class User {

    var email: String? = null
    var user_id: String? = null
    var username: String? = null

    constructor(){

    }

    constructor(email: String?, user_id: String?, username: String?) {
        this.email = email
        this.user_id = user_id
        this.username = username
    }


}


