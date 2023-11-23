package com.example.afinal.DB

import android.util.Log
import com.example.afinal.DTO.FolderDTO
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.FolderDomain
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class FolderDAL : MyDB() {
    fun GetUserFolderList() {
        val query = GetFolder().orderByChild("userPK").equalTo(UserDTO.currentUser?.GetPK())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val folder = snapshot.getValue(FolderDomain::class.java)
                    if (folder != null) {
                        FolderDTO.folderList.add(folder)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }
}