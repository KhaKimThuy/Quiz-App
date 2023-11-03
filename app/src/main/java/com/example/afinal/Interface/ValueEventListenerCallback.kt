package com.example.afinal.Interface

import com.example.afinal.Domain.FolderDomain
import com.google.firebase.database.DatabaseError

interface ValueEventListenerCallback {
    fun onDataChange(dataSnapshot: Long)
    fun onDataChange(dataSnapshot: FolderDomain)
    fun onCancelled(databaseError: DatabaseError)
}