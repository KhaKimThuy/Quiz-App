package com.example.afinal.Interface

import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.google.firebase.database.DatabaseError

interface ValueEventListenerCallback {
    fun onDataChange(dataSnapshot: Long)
    fun onDataChange(dataSnapshot: FolderDomain)
    fun onDataChangeTopic(dataSnapshot: TopicDomain)
    fun onCancelled(databaseError: DatabaseError)
    fun onDataChange(topicIDs: ArrayList<String>)
}