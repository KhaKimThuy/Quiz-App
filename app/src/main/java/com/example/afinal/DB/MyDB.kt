package com.example.afinal.DB

import android.util.Log
import android.view.View.OnClickListener
import com.example.afinal.Common.CommonUser
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.tasks.await

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

    fun RecyclerItem(topicPK : String): FirebaseRecyclerOptions<FlashCardDomain> {
        val query = GetItem().orderByChild("topicPK").equalTo(topicPK)
        return FirebaseRecyclerOptions.Builder<FlashCardDomain>()
            .setQuery(query, FlashCardDomain::class.java)
            .build()
    }

//    fun GetNumberOfItemInTopic(topicPK:String, listener: OnClickListener) : String{
        fun GetNumberOfItemInTopic(topicPK:String) : String{
//        val query = GetItem().orderByChild("topicPK").equalTo(topicPK)
//        var p = "0"
//        query.get().addOnSuccessListener { results ->
//            p = results.childrenCount.toString()
//        }.addOnFailureListener {
//        }
//        return p
//
//        val results = query.get().await()
//        return results.childrenCount.toString()

        var count = "null"
//        query.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                Log.d("TAG", "In query " + dataSnapshot.childrenCount.toString())
//                count = dataSnapshot.childrenCount.toString()
//                listener.onClick(count);
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                count = "0"
//            }
//        })
//        Log.e("AAA", "BBB");

        return count
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