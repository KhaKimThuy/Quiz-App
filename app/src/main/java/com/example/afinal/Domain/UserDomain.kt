package com.example.afinal.Domain

import kotlin.random.Random

class UserDomain {
    var email : String = ""
    var password : String = ""
    var username : String = RandomName()
    var avatarUrl : String = ""

    fun GetPK() : String{
        var pk = email.replace("@", "")
        pk = pk.replace(".", "")
        return pk
    }

//    fun HashMD5(input:String): String{
//        val md = MessageDigest.getInstance("MD5")
//        return BigInteger(1, md.digest(input.toByteArray())).toString(16).padStart(32, '0')
//    }

    private fun RandomName() : String {
        val random = Random(System.currentTimeMillis())
        val randomNum = random.nextInt(10000, 99999)
        return "Newbie$randomNum";
    }
}