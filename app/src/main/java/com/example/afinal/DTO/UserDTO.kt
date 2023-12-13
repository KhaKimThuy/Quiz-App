package com.example.afinal.DTO

import android.graphics.Bitmap
import com.example.afinal.Domain.User

class UserDTO{
    companion object {
        var currentUser: User? = null
        var userAvatar: Bitmap? = null
        fun UpdateInfo(newEmail : String, newPass : String, newName : String, newAvatarUrl : String) {
            currentUser?.email = newEmail
            currentUser?.username = newName
            currentUser?.password = newPass
            currentUser?.avatarUrl = newAvatarUrl
        }
    }
}