package com.example.afinal.DTO

import android.graphics.Bitmap
import com.example.afinal.Domain.UserDomain

class UserDTO{
    companion object {
        var currentUser: UserDomain? = null
        var userAvatar: Bitmap? = null
        fun UpdateInfo(newEmail : String, newPass : String, newName : String, newAvatarUrl : String) {
            currentUser?.email = newEmail
            currentUser?.username = newName
            currentUser?.password = newPass
            currentUser?.avatarUrl = newAvatarUrl
        }
    }
}