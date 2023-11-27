package com.example.afinal.DB

import android.util.Log
import com.example.afinal.Activity.ActivityRanking
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Fragment.SearchResultFragment
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Domain.TopicFolderDomain
import com.example.afinal.Domain.TopicPublicDomain
import com.example.afinal.Domain.UserDomain
import com.firebase.ui.database.FirebaseRecyclerOptions
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

    fun GetRankingOfTopic(activity : ActivityRanking) {
        val topicList : ArrayList<TopicPublicDomain> = ArrayList<TopicPublicDomain>()
        val query = GetTopicPublic().orderByChild("highestScore")
        query .addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val topic = snapshot.getValue(TopicPublicDomain::class.java)
                    if (topic != null) {
                        topicList.add(topic)
                    }
                }
                GetRankingUserOfTopic(activity, topicList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }

    fun GetRankingUserOfTopic(activity : ActivityRanking, topicList : ArrayList<TopicPublicDomain>){
        val userList : ArrayList<UserDomain> = ArrayList<UserDomain>()
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (topic in topicList) {
                    val snapshot = dataSnapshot.child(topic.guestPK)
                    val yourObject = snapshot.getValue(UserDomain::class.java)
                    yourObject?.let { userList.add(it) }
                }
                activity.loadUserRanking(topicList, userList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        }
        GetUser().addListenerForSingleValueEvent(valueEventListener)
    }

    fun AddPublicTopic(topic : TopicPublicDomain) {
        GetTopicPublic().addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (topic.topicPublicPK?.let { snapshot.hasChild(it) } == true){
                    Log.d("AddTopic", "Topic name is already used")
                }else{
                    if (topic.topicPublicPK != null) {
                        GetTopicPublic().child(topic.topicPublicPK).setValue(topic)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }


    fun GetListTopicPublicIdOfUser(activity: DetailTopicActivity, topicIDs: ArrayList<String>, folder: FolderDomain){
        val query = GetTopicPublic().orderByChild("guestPK").equalTo(UserDTO.currentUser?.GetPK())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                topicIDs.clear()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(TopicPublicDomain::class.java)
                    if (yourObject != null) {
                        topicIDs.add(yourObject.topicPK)
                    }
                }
                GetListTopicIdOfUser(activity, topicIDs)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }

    fun GetListTopicIdOfUser(activity: DetailTopicActivity, topicIDs: ArrayList<String>){
        val query = GetTopic().orderByChild("userPK").equalTo(UserDTO.currentUser?.GetPK())
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(TopicDomain::class.java)
                    if (yourObject != null) {
                        topicIDs.add(yourObject.topicPK)
                    }
                }
                GetListTopicOfUser(activity, topicIDs)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }

    fun GetListTopicOfUser(activity: DetailTopicActivity, topicIDs: ArrayList<String>){
        val topicList : ArrayList<TopicDomain> = ArrayList<TopicDomain>()
        GetTopic().addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                for (key in topicIDs) {
                    val snapshot = dataSnapshot.child(key)
                    val yourObject = snapshot.getValue(TopicDomain::class.java)
                    yourObject?.let { topicList.add(it) }
                }
                activity.loadTopic(topicList)
            }

            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }
}