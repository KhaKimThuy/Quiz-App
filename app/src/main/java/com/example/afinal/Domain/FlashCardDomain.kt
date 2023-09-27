package com.example.afinal.Domain

import android.os.Parcel
import android.os.Parcelable
import java.io.Serializable

class FlashCardDomain(
    val engLanguage: String?,
    val vnLanguage: String?,
    val picUrl: String?,
    val mark: Boolean
) : Serializable{
}