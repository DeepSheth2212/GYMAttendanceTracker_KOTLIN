package com.example.gymattendancetracker.Model

import java.time.LocalDate
import java.util.Date

//class User{
//    var name : String? = null
//    var phone : String? = null
//    var email : String? = null
//    var password : String? = null
//
//
//    constructor(){}
//
//    constructor(name : String , phone : String , email : String , password : String){
//        this.name = name
//        this.phone = phone
//        this.email = email
//        this.password = password
//    }
//}

data class User(
    var name : String,
    var phone : String,
    var email : String,
    var password : String,
)
