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
}