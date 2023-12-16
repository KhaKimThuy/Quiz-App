package com.example.afinal.Domain

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import java.util.Calendar
import java.util.Date

open class Topic() : Parcelable {
    var topicPK = ""
    var userPK = ""
    var topicName = ""
    var isPublic: Boolean = false
    var highestScore : Double = 0.0
    var timeStudy = 0
    var createdTime: Date = Calendar.getInstance().time

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this() {
        topicPK = parcel.readString().toString()
        userPK = parcel.readString().toString()
        topicName = parcel.readString().toString()
        isPublic = parcel.readBoolean()
        highestScore = parcel.readDouble()
        timeStudy = parcel.readInt()
        createdTime = Date(parcel.readLong())
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topicPK)
        parcel.writeString(userPK)
        parcel.writeString(topicName)
        parcel.writeBoolean(isPublic)
        parcel.writeDouble(highestScore)
        parcel.writeInt(timeStudy)
        parcel.writeLong(createdTime.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Topic> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Topic {
            return Topic(parcel)
        }

        override fun newArray(size: Int): Array<Topic?> {
            return arrayOfNulls(size)
        }
    }
}