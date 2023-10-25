package com.example.afinal.Domain

import android.text.Editable
import com.example.afinal.Common.CommonUser
import java.io.Serializable
import java.math.BigInteger
import java.security.MessageDigest

class TopicDomain{
    var topicName = ""
    var userPK = CommonUser.currentUser?.GetPK()
    var topicPK = ""
    var folderPK = ""
    fun HashMD5(): String{
        val md = MessageDigest.getInstance("MD5")
        val pk = userPK+topicName
        return BigInteger(1, md.digest(pk.toByteArray())).toString(16).padStart(32, '0')
    }
}