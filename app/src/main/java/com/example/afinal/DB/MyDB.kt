package com.example.afinal.DB

import com.example.afinal.Common.CommonUser
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.storage.FirebaseStorage

class MyDB(){
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var reference: DatabaseReference = database.reference

    fun GetUser(): DatabaseReference {
        return database.getReference("User")
    }

    fun GetTopic(): DatabaseReference {
        return database.getReference("Topic")
    }

    fun GetUserByID(): DatabaseReference? {
        var pk = CommonUser.currentUser?.GetPK()
        return pk?.let { database.getReference("User").child(it) }
    }

    fun GetItem(): DatabaseReference {
        return database.getReference("Item")
    }

    fun GetFlashCard(): Query {
        // Extract pk
        var email = CommonUser.currentUser?.email
        var pk = email?.replace("@", "")
        if (pk != null) {
            pk = pk.replace(".", "")
        }
        return reference.orderByChild("userID").equalTo(pk)
    }

    fun RecyclerFlashCard(): FirebaseRecyclerOptions<FlashCardDomain> {
        return FirebaseRecyclerOptions.Builder<FlashCardDomain>()
            .setQuery(GetFlashCard(), FlashCardDomain::class.java)
            .build()
    }
    fun RecyclerTopic(): FirebaseRecyclerOptions<TopicDomain> {
        val query = GetTopic().orderByChild("userPK").equalTo(CommonUser.currentUser?.GetPK())
        return FirebaseRecyclerOptions.Builder<TopicDomain>()
            .setQuery(query, TopicDomain::class.java)
            .build()
    }

    fun extractPK(email : String) : String{
        var pk = email.replace("@", "")
        pk = pk.replace(".", "")
        return pk
    }
}