package com.example.afinal.Domain

import android.os.Parcel
import android.os.Parcelable
import java.util.Calendar

class TopicRanking() : Parcelable {
    var topicRankingPK = ""
    var topicPK = ""
    var userPK = ""
    var highestScore = 0
    var timeStudy = 0
    var savedTime = Calendar.getInstance().time

    constructor(parcel: Parcel) : this() {
        topicRankingPK = parcel.readString().toString()
        topicPK = parcel.readString().toString()
        userPK = parcel.readString().toString()
        highestScore = parcel.readInt()
        timeStudy = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(topicRankingPK)
        parcel.writeString(topicPK)
        parcel.writeString(userPK)
        parcel.writeInt(highestScore)
        parcel.writeInt(timeStudy)
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