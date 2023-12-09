package com.example.afinal.Domain

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.example.afinal.DTO.UserDTO
import java.time.LocalDateTime

class TopicPublicDomain : Parcelable {
    var guestPK = ""
    var topicPK = ""
    var topicPublicPK = ""
    var isPublic = false
    var highestScore = 0
    var timeStudy = 0
    var datetime = LocalDateTime.now().toString()


    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this() {
        guestPK = parcel.readString().toString()
        topicPK = parcel.readString().toString()
        topicPublicPK = parcel.readString().toString()
        datetime = parcel.readString().toString()
        isPublic = parcel.readBoolean()
        highestScore = parcel.readInt()
        timeStudy = parcel.readInt()
    }

    constructor()

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(guestPK)
        parcel.writeString(topicPK)
        parcel.writeString(topicPublicPK)
        parcel.writeString(datetime)
        parcel.writeBoolean(isPublic)
        parcel.writeInt(highestScore)
        parcel.writeInt(timeStudy)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TopicPublicDomain> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): TopicPublicDomain {
            return TopicPublicDomain(parcel)
        }

        override fun newArray(size: Int): Array<TopicPublicDomain?> {
            return arrayOfNulls(size)
        }
    }
}