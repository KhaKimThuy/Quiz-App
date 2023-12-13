package com.example.afinal.Domain

import android.os.Build
import android.os.Parcel
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.google.firebase.database.PropertyName

open class Item () : Parcelable {
    @PropertyName("engLanguage")
    var engLanguage: String? = ""

    @PropertyName("vnLanguage")
    var vnLanguage: String? = ""

    @PropertyName("topicPK")
    var topicPK : String = ""

    @PropertyName("itemPK")
    var itemPK : String = ""

    @PropertyName("isMarked")
    var isMarked : Boolean = false

    @PropertyName("state")
    var state : String = "Chưa được học"

    @PropertyName("numRights")
    var numRights : Int = 0

    @PropertyName("imgUrl")
    var imgUrl : String = ""

    @RequiresApi(Build.VERSION_CODES.Q)
    constructor(parcel: Parcel) : this() {
        engLanguage = parcel.readString()
        vnLanguage = parcel.readString()
        topicPK = parcel.readString().toString()
        itemPK = parcel.readString().toString()
        isMarked = parcel.readBoolean()
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

    companion object CREATOR : Parcelable.Creator<Item> {
        @RequiresApi(Build.VERSION_CODES.Q)
        override fun createFromParcel(parcel: Parcel): Item {
            return Item(parcel)
        }

        override fun newArray(size: Int): Array<Item?> {
            return arrayOfNulls(size)
        }
    }
}

