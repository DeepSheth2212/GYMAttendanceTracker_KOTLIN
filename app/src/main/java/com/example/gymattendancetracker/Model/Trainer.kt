package com.example.gymattendancetracker.Model

class Trainer{
    var name : String? = null
    var isLive : Boolean = false

    constructor(){}

    constructor(name : String , isLive : Boolean){
        this.name = name
        this.isLive = isLive
    }
}
