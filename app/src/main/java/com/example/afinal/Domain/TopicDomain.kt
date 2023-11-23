package com.example.afinal.Domain

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.afinal.DTO.UserDTO

class TopicDomain() : Parcelable{
    var topicName = ""
    var userPK = UserDTO.currentUser?.GetPK()
    var guestPK = ""
    var topicPK = ""
    var folderPK = ""
    var isPublic = false
    var highestScore = 0
    var timeStudy = 0

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this() {
        topicName = parcel.readString().toString()
        userPK = parcel.readString()
        guestPK = parcel.readString().toString()
        topicPK = parcel.readString().toString()
        folderPK = parcel.readString().toString()
        isPublic = parcel.readBoolean()
        highestScore = parcel.readInt()
        timeStudy = parcel.readInt()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topicName)
        parcel.writeString(userPK)
        parcel.writeString(guestPK)
        parcel.writeString(topicPK)
        parcel.writeString(folderPK)
        parcel.writeBoolean(isPublic)
        parcel.writeInt(highestScore)
        parcel.writeInt(timeStudy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TopicDomain> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): TopicDomain {
            return TopicDomain(parcel)
        }

        override fun newArray(size: Int): Array<TopicDomain?> {
            return arrayOfNulls(size)
        }
    }
}