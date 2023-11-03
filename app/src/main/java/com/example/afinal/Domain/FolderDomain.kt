package com.example.afinal.Domain

import com.example.afinal.Common.CommonUser

class FolderDomain {
    var folderName = ""
    var folderDesc = ""
    var userPK = CommonUser.currentUser?.GetPK()
    var folderPK = ""
}