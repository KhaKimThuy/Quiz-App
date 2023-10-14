package com.example.afinal.DB

import com.example.afinal.Common.CommonUser
import com.example.afinal.Domain.FlashCardDomain
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query

class MyDB(ref : String){
    var database : FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference: DatabaseReference = database.getReference(ref)
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
}