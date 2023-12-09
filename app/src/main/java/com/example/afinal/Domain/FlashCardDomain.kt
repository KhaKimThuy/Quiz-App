package com.example.afinal.Domain

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.android.material.internal.ParcelableSparseArray
import java.io.Serializable

class FlashCardDomain () : Parcelable {
    var engLanguage: String? = ""
    var vnLanguage: String? = ""
    var topicPK : String = ""
    var itemPK : String = ""
    var isMarked : Boolean = false
    var state : String = "Chưa được học"
    var numRights : Int = 0
    var imgUrl : String = ""

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this() {
        engLanguage = parcel.readString()
        vnLanguage = parcel.readString()
        topicPK = parcel.readString().toString()
        itemPK = parcel.readString().toString()
        state = parcel.readString().toString()
        numRights = parcel.readInt()
        imgUrl = parcel.readString().toString()
    }

    @RequiresApi(Build.VERSION_CODES.Q)
    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(engLanguage)
        parcel.writeString(vnLanguage)
        parcel.writeString(topicPK)
        parcel.writeString(itemPK)
        parcel.writeString(state)
        parcel.writeBoolean(isMarked)
        parcel.writeInt(numRights)
        parcel.writeString(imgUrl)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FlashCardDomain> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): FlashCardDomain {
            return FlashCardDomain(parcel)
        }

        override fun newArray(size: Int): Array<FlashCardDomain?> {
            return arrayOfNulls(size)
        }
    }
}

