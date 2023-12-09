package com.example.afinal.DAL

import android.content.ClipData
import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.afinal.Activity.ActivityRanking
import com.example.afinal.Activity.CreateStudyModuleActivity
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Activity.LoginActivity
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Fragment.SearchResultFragment
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Domain.TopicFolderDomain
import com.example.afinal.Domain.TopicPublicDomain
import com.example.afinal.Domain.UserDomain
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import java.util.Calendar

class TopicDAL : MyDB() {

    fun GetTopicObject(topicId : String) {
//        // Save user information locally
//        MyDB().db.collection("user").document(topicId).get().addOnCompleteListener{
//            if (it != null) {
//                TopicDTO.currentUser = it.result.toObject(UserDomain::class.java)
//            }
//        }
    }

    fun GetNumItem(topicId: String, callback: (Int?) -> Unit) {
        val documentRef = MyDB().db.collection("item")
        val query = documentRef.whereEqualTo("topicPK", topicId)
        query.get()
            .addOnSuccessListener { querySnapshot ->
                callback(querySnapshot.size())
            }
            .addOnFailureListener { _ ->
                callback(0)
            }
    }

    fun GetItemOfTopic(topicId: String, callback: (ArrayList<FlashCardDomain>) -> Unit) {
        val documentRef = MyDB().db.collection("item")
        val query = documentRef.whereEqualTo("topicPK", topicId)

        val itemList = ArrayList<FlashCardDomain>()
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (querySnapshot in querySnapshot) {
                        val itemObject = querySnapshot.toObject(FlashCardDomain::class.java)
                        itemList.add(itemObject)
                    }
                }
                Log.d("TAG", "Item list size from callback = " + itemList.size)
                callback(itemList)
            }
            .addOnFailureListener { _ ->
                callback(itemList)
            }
    }

    fun GetTopicOfUser(userId: String, callback: () -> Unit) {
        val documentRef = MyDB().db.collection("topic")
        val query = documentRef.whereEqualTo("userPK", userId)
            .orderBy("createdTime", com.google.firebase.firestore.Query.Direction.ASCENDING)

        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (querySnapshot in querySnapshot) {
                        val topicObject = querySnapshot.toObject(TopicDomain::class.java)
                        TopicDTO.topicList.add(topicObject)
                    }
                }
                callback()
            }
            .addOnFailureListener { _ ->
                callback()
            }
    }

    fun AddTopic(topic : TopicDomain, itemList : ArrayList<FlashCardDomain>, activity : CreateStudyModuleActivity) {
        // Sign in success, update UI with the signed-in user's information
        topic.userPK = MyDB().dbAuth.currentUser?.uid.toString()

        val topicPK = db.collection("topic").document().id
        topic.topicPK = topicPK

        // Save user information in cloud storage
        if (topicPK != null) {
            val topicFB = hashMapOf (
                "topicPK" to topic.topicPK,
                "userPK" to topic.userPK,
                "topicName" to topic.topicName,
                "isPublic" to topic.isPublic,
                "highestScore" to topic.highestScore,
                "timeStudy" to topic.timeStudy,
                "createdTime" to Calendar.getInstance().time,
            )

            db.collection("topic").document(topicPK)
                .set(topicFB)
                .addOnSuccessListener {

                    // Add item to topic
                    for (item in itemList) {
                        Log.d("TAG", "Add item = " + item.engLanguage)
                        item.topicPK = topicPK
                        ItemDAL().AddItem(item)
                        TopicDTO.itemList.add(item)
                    }

                    activity.binding.progressBar3.visibility = View.GONE
                    Toast.makeText(activity, "Create topic successfully", Toast.LENGTH_SHORT).show()

                    // Save topic locally
                    TopicDTO.currentTopic = topic
                    TopicDTO.numItems = TopicDTO.itemList.size.toString()

                    val intent = Intent(activity, DetailTopicActivity::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                }

                .addOnFailureListener { e -> Log.w("TAG", "Error writing topic document", e) }
        }
    }

    fun DeleteTopic(topic: TopicDomain) {
        Log.d("TAG", "Starting delete topic ...")

        val documentTopicRef = MyDB().db.collection("topic").document(topic.topicPK)

        val documentTFRef = MyDB().db.collection("topic_folder")
        val queryTF = documentTFRef.whereEqualTo("topicPK", topic.topicPK)

        val documentItemRef = MyDB().db.collection("item")

        // Delete topic_folder containing the topic
        queryTF.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (tf in querySnapshot) {
                        val tfObject = tf.toObject(TopicFolderDomain::class.java)
                        TopicFolderDAL().DeleteTF(tfObject)
                    }
                }
            }
            .addOnFailureListener { _ ->
            }

        for (item in TopicDTO.itemList) {
            ItemDAL().DeleteFC(item)
        }

        // Delete topic
        documentTopicRef.delete()
            .addOnSuccessListener {
                Log.w("TAG", "Delete topic successfully")
            }
            .addOnFailureListener { _ ->
                Log.w("TAG", "Fail to delete topic")
            }

        // Refresh current topic value
        TopicDTO.itemList.clear()
        TopicDTO.currentTopic = null

        Log.d("TAG", "Ending delete topic")
    }












    fun RecyclerSearchPublicTopic(): FirebaseRecyclerOptions<TopicDomain> {
        val query = GetTopic().orderByChild("public").equalTo(true)
        return FirebaseRecyclerOptions.Builder<TopicDomain>()
            .setQuery(query, TopicDomain::class.java)
            .build()
    }

    fun GetListPublicTopic(activity: SearchResultFragment, topicList: ArrayList<TopicDomain>, search: String) {
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
//        val topicList : ArrayList<TopicDomain> = ArrayList<TopicDomain>()
//        GetTopic().addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (key in topicIDs) {
//                    val snapshot = dataSnapshot.child(key)
//                    val yourObject = snapshot.getValue(TopicDomain::class.java)
//                    yourObject?.let { topicList.add(it) }
//                }
//                activity.loadTopic(topicList)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle any errors
//            }
//        })
    }
}