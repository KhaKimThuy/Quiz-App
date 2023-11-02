package com.example.afinal.Interface

import com.google.firebase.database.DatabaseError

interface ValueEventListenerCallback {
    fun onDataChange(dataSnapshot: Long)
    fun onCancelled(databaseError: DatabaseError)
}