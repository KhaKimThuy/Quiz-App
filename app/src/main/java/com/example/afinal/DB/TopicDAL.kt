package com.example.afinal.DB

import android.util.Log
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Fragment.SearchResultFragment
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Domain.TopicFolderDomain
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.android.gms.common.internal.service.Common
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class TopicDAL : MyDB() {
    fun RecyclerSearchPublicTopic(): FirebaseRecyclerOptions<TopicDomain> {
        val query = GetTopic().orderByChild("public").equalTo(true)
        return FirebaseRecyclerOptions.Builder<TopicDomain>()
            .setQuery(query, TopicDomain::class.java)
            .build()
    }

    fun GetListPublicTopic(activity: SearchResultFragment, topicList: ArrayList<TopicDomain>, search: String){
        val query = GetTopic().orderByChild("public").equalTo(true)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(TopicDomain::class.java)
                    if (yourObject != null) {
                        topicList.add(yourObject)
                        Log.d("TAG", "Topic name: " + yourObject.topicName)
                    }
                }
                activity.adapter.notifyDataSetChanged();
                activity.searchList(search);
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }

    fun GetUserTopicList() {
        val query = GetTopic().orderByChild("userPK").equalTo(UserDTO.currentUser?.GetPK())
        query .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val topic = snapshot.getValue(TopicDomain::class.java)
                    if (topic != null) {
                        TopicDTO.topicList.add(topic)
                    }
                }
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }

}