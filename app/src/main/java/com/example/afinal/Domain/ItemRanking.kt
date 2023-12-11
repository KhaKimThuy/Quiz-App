package com.example.afinal.Domain

import android.os.Parcel
import android.os.Parcelable
import java.util.Calendar

class ItemRanking () : Parcelable {
    var itemRankingPK = ""
    var topicRankingPK = ""
    var itemPK = ""
    var isMarked : Boolean = false
    var state : String = "Chưa được học"
    var numRights : Int = 0

    constructor(parcel: Parcel) : this() {
        itemRankingPK = parcel.readString().toString()
        topicRankingPK = parcel.readString().toString()
        itemPK = parcel.readString().toString()
        isMarked = parcel.readByte() != 0.toByte()
        state = parcel.readString().toString()
        numRights = parcel.readInt()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(itemRankingPK)
        parcel.writeString(topicRankingPK)
        parcel.writeString(itemPK)
        parcel.writeByte(if (isMarked) 1 else 0)
        parcel.writeString(state)
        parcel.writeInt(numRights)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<ItemRanking> {
        override fun createFromParcel(parcel: Parcel): ItemRanking {
            return ItemRanking(parcel)
        }

        override fun newArray(size: Int): Array<ItemRanking?> {
            return arrayOfNulls(size)
        }
    }
}