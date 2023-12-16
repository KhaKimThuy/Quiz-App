package com.example.afinal.Domain

import android.os.Parcel
import android.os.Parcelable
import java.util.Date

class TopicRanking() : Topic(), Parcelable {
    var rankingTopicPK = ""
//    var topicPK = ""
//    var userPK = ""
//    var highestScore = 0
//    var timeStudy = 0

    constructor(parcel: Parcel) : this() {
        rankingTopicPK = parcel.readString().toString()
        topicPK = parcel.readString().toString()
        userPK = parcel.readString().toString()
        highestScore = parcel.readDouble()
        timeStudy = parcel.readInt()
        createdTime = Date(parcel.readLong())
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(rankingTopicPK)
        parcel.writeString(topicPK)
        parcel.writeString(userPK)
        parcel.writeDouble(highestScore)
        parcel.writeInt(timeStudy)
        parcel.writeLong(createdTime.time)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<TopicRanking> {
        override fun createFromParcel(parcel: Parcel): TopicRanking {
            return TopicRanking(parcel)
        }

        override fun newArray(size: Int): Array<TopicRanking?> {
            return arrayOfNulls(size)
        }
    }


}