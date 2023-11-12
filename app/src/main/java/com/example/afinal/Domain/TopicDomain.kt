package com.example.afinal.Domain

import android.os.Parcel
import android.os.Parcelable
import android.text.Editable
import com.example.afinal.Common.CommonUser
import java.io.Serializable
import java.math.BigInteger
import java.security.MessageDigest

class TopicDomain() : Parcelable{
    var topicName = ""
    var userPK = CommonUser.currentUser?.GetPK()
    var topicPK = ""
    var folderPK = ""

    constructor(parcel: Parcel) : this() {
        topicName = parcel.readString().toString()
        userPK = parcel.readString()
        topicPK = parcel.readString().toString()
        folderPK = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topicName)
        parcel.writeString(userPK)
        parcel.writeString(topicPK)
        parcel.writeString(folderPK)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TopicDomain> {
        override fun createFromParcel(parcel: Parcel): TopicDomain {
            return TopicDomain(parcel)
        }

        override fun newArray(size: Int): Array<TopicDomain?> {
            return arrayOfNulls(size)
        }
    }
}