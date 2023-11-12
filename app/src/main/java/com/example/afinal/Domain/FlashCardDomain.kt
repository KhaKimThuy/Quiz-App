package com.example.afinal.Domain

import android.os.Parcel
import android.os.Parcelable
import com.google.android.material.internal.ParcelableSparseArray
import java.io.Serializable

class FlashCardDomain () : Parcelable {
    var engLanguage: String? = ""
    var vnLanguage: String? = ""
    var topicPK : String = ""
    var itemPK : String = ""

    constructor(parcel: Parcel) : this() {
        engLanguage = parcel.readString()
        vnLanguage = parcel.readString()
        topicPK = parcel.readString().toString()
        itemPK = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(engLanguage)
        parcel.writeString(vnLanguage)
        parcel.writeString(topicPK)
        parcel.writeString(itemPK)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FlashCardDomain> {
        override fun createFromParcel(parcel: Parcel): FlashCardDomain {
            return FlashCardDomain(parcel)
        }

        override fun newArray(size: Int): Array<FlashCardDomain?> {
            return arrayOfNulls(size)
        }
    }
}

