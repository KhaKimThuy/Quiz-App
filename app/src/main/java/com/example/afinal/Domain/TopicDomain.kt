package com.example.afinal.Domain

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.afinal.DTO.UserDTO
import java.time.LocalDateTime
import java.util.Calendar
import java.util.Date

class TopicDomain() : Parcelable{
    var topicPK = ""
    var userPK = ""
    var topicName = ""
    var isPublic = false
    var highestScore = 0
    var timeStudy = 0
    var createdTime = Calendar.getInstance().time

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this() {
        topicPK = parcel.readString().toString()
        userPK = parcel.readString().toString()
        topicName = parcel.readString().toString()
        isPublic = parcel.readBoolean()
        highestScore = parcel.readInt()
        timeStudy = parcel.readInt()
        createdTime = Date(parcel.readLong())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topicPK)
        parcel.writeString(userPK)
        parcel.writeString(topicName)
        parcel.writeBoolean(isPublic)
        parcel.writeInt(highestScore)
        parcel.writeInt(timeStudy)
        parcel.writeLong(createdTime.time)
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