package com.example.afinal.Domain

import com.example.afinal.Common.CommonUser

class UserDomain {
    var email : String = ""
    var password : String = ""
    var username : String = "Newbie"
    var pk : String
    init {
        pk = GetPK()
    }
    private fun GetPK() : String{
        var pk = email.replace("@", "")
        pk.replace(".", "")
        return pk
    }
}