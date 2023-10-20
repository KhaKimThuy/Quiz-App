package com.example.afinal.Common

import com.example.afinal.Domain.UserDomain

class CommonUser{

    companion object {
        var currentUser: UserDomain? = null
        fun UpdateInfo(newEmail : String, newPass : String, newName : String, newAvatarUrl : String) {
            currentUser?.email = newEmail
            currentUser?.username = newName
            currentUser?.password = newPass
            currentUser?.avatarUrl = newAvatarUrl
        }
    }
}