package com.example.afinal.Domain

import com.example.afinal.Common.CommonUser
import kotlin.random.Random

class UserDomain {
    var email : String = ""
    var password : String = ""
    var username : String = RandomName()
    var avatarUrl : String = ""


    public fun GetPK() : String{
        var pk = email.replace("@", "")
        pk = pk.replace(".", "")
        return pk
    }

    private fun RandomName() : String {
        val random = Random(System.currentTimeMillis())
        val randomNum = random.nextInt(10000, 99999)
        return "Newbie$randomNum";
    }
}