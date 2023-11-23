package com.example.afinal.Domain

import android.os.Parcel
import android.os.Parcelable
import com.example.afinal.DTO.UserDTO

class FolderDomain() : Parcelable{
    var folderName = ""
    var folderDesc = ""
    var userPK = UserDTO.currentUser?.GetPK()
    var folderPK = ""

    constructor(parcel: Parcel) : this() {
        folderName = parcel.readString().toString()
        folderDesc = parcel.readString().toString()
        userPK = parcel.readString()
        folderPK = parcel.readString().toString()
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(folderName)
        parcel.writeString(folderDesc)
        parcel.writeString(userPK)
        parcel.writeString(folderPK)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<FolderDomain> {
        override fun createFromParcel(parcel: Parcel): FolderDomain {
            return FolderDomain(parcel)
        }

        override fun newArray(size: Int): Array<FolderDomain?> {
            return arrayOfNulls(size)
        }
    }
}