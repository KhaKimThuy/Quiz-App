package com.example.afinal.DAL

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.afinal.Activity.ActivityRanking
import com.example.afinal.Activity.CreateStudyModuleActivity
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.Item
import com.example.afinal.Domain.Folder
import com.example.afinal.Domain.ItemRanking
import com.example.afinal.Domain.RankingUser
import com.example.afinal.Domain.Topic
import com.example.afinal.Domain.TopicFolder
import com.example.afinal.Domain.TopicPublic
import com.example.afinal.Domain.TopicRanking
import com.example.afinal.Domain.User
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.Query
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

    fun GetItemOfTopic(topicId: String, callback: (ArrayList<Item>) -> Unit) {
        val documentRef = MyDB().db.collection("item")
        val query = documentRef.whereEqualTo("topicPK", topicId)

        val itemList = ArrayList<Item>()
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (item in querySnapshot) {
                        val itemObject = item.toObject(Item::class.java)
                        val isMarked = item.getBoolean("isMarked")
                        if (isMarked != null) {
                            itemObject.isMarked = isMarked
                        }
                        Log.d("markedItem", "(GetItemOfTopic-TopicDAL) Marked item : " + itemObject.isMarked)
                        itemList.add(itemObject)
                    }
                    if (TopicDTO.currentTopic is TopicRanking) {
                        Log.d("TAG", "(GetItemOfTopic-TopicDAL) is topic ranking")
                        GetRankingItem(itemList, TopicDTO.currentTopic as TopicRanking) {
                            Log.d("TAG", "(GetItemOfTopic-TopicDAL) ranking item list size : " + it.size)
                            callback(it)
                        }
                    } else {
                        callback(itemList)
                        Log.d("TAG", "(GetItemOfTopic-TopicDAL) fail")
                    }
                }
                Log.d("TAG", "(GetItemOfTopic-TopicDAL) Item list size from callback = " + itemList.size)
            }
            .addOnFailureListener {  e ->
                Log.d("TAG", "(GetItemOfTopic-TopicDAL) fail : " + e)
            }
    }


    fun GetRankingItem(itemList : ArrayList<Item>, topicRanking: TopicRanking, callback: (ArrayList<Item>) -> Unit) {
        val itemIds = itemList.map { it.itemPK } as ArrayList
        val documentRef = MyDB().db.collection("rankingItem")
        val query = documentRef.whereEqualTo("topicRankingPK", topicRanking.rankingTopicPK)
        var rankingItems = ArrayList<Item>()
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (rankingItemFB in querySnapshot) {
                        val rankingItem = rankingItemFB.toObject(ItemRanking::class.java)
                        val isMarked = rankingItemFB.getBoolean("isMarked")
                        val rootItem = itemList.find { it.itemPK == rankingItem.itemPK }
                        rankingItem.engLanguage = rootItem?.engLanguage
                        rankingItem.vnLanguage = rootItem?.vnLanguage

                        Log.d("TAG", "State of ranking item  : "+ rankingItem.state)
                        if (isMarked != null) {
                            rankingItem.isMarked = isMarked
                        }

                        if (rootItem != null) {
                            rankingItems.add(rankingItem)
                            itemList.remove(rootItem)
                        }
                    }
                    Log.d("TAG", "(GetRankingItem) ranking item list size : " + rankingItems.size)
                    callback(rankingItems)
                }
            }
            .addOnFailureListener { _ ->
            }
    }

    fun GetTopicOfUser(userId: String, callback: (ArrayList<Topic>) -> Unit) {
        val documentRef = MyDB().db.collection("topic")
        val query = documentRef.whereEqualTo("userPK", userId)
            .orderBy("createdTime", com.google.firebase.firestore.Query.Direction.DESCENDING)

        var topics = ArrayList<Topic>()
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (topic in querySnapshot) {
                        val isPublic = topic.getBoolean("isPublic")
                        val topicObject = topic.toObject(Topic::class.java)
                        if (isPublic != null) {
                            topicObject.isPublic = isPublic
                        }
                        Log.d("TAG", "(GetTopicOfUser) TOPIC is public : " + topicObject.isPublic)
                        topics.add(topicObject)
                    }
//                     Get ranking topic saved by user
                    GetRankingTopicUser(userId, topics) {
                        var sortedTopics = ArrayList<Topic>(topics.sortedByDescending { it.createdTime.time })
                        callback(sortedTopics)
                    }
                } else {
                    callback(topics)
                }
            }
            .addOnFailureListener { _ ->
                callback(topics)
            }
    }

    fun GetRankingTopicUser (userId: String, topics : ArrayList<Topic>, callback: () -> Unit) {
        val documentRef = MyDB().db.collection("rankingTopic")
        val query = documentRef.orderBy("userPK")
                                .whereEqualTo("userPK", userId)

        var rankingTopicList : ArrayList<TopicRanking> = ArrayList<TopicRanking>()
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (rankingTopicFB in querySnapshot) {
                        val rTopic = rankingTopicFB.toObject(TopicRanking::class.java)
                        rankingTopicList.add(rTopic)
                    }

                    val topicIds = rankingTopicList.map { it.topicPK }

                    val topicDocumentRef = MyDB().db.collection("topic")
                    val query = topicDocumentRef.whereIn("topicPK", topicIds)
                    query.get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val topic = document.toObject(Topic::class.java)
                                val rTopic = rankingTopicList.find { it.topicPK == topic.topicPK}

                                rTopic?.userPK = topic.userPK
                                rTopic?.topicName = topic.topicName
                                rTopic?.isPublic = true
                                rTopic?.topicPK = topic.topicPK

                                if (rTopic != null) {
                                    Log.d("TAG", "(GetRankingTopicUser) TOPIC is public : " + rTopic.isPublic)
                                    topics.add(rTopic)
                                }

                            }
                            Log.d("TAG", "In " + topics.size)
                            // Order topic according to created time
                            callback()
                        }
                        .addOnFailureListener { _ ->
                            // Handle any errors that occurred while retrieving the documents
                            callback()
                        }
                }
                callback()
            }
            .addOnFailureListener { _ ->
            }
    }

    fun GetPublicTopic(callback: (ArrayList<Topic>) -> Unit) {
        val userPK = MyDB().dbAuth.currentUser?.uid.toString()
        val documentRef = MyDB().db.collection("topic")

        val query = documentRef .orderBy("userPK")
                                .whereNotEqualTo("userPK", userPK)
                                .orderBy("createdTime", com.google.firebase.firestore.Query.Direction.DESCENDING)
                                .whereEqualTo("isPublic", true)

        val publicTopicList : ArrayList<Topic> = ArrayList<Topic>()

        // Get 10 latest public topic
        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (topic in querySnapshot) {
                        val topicObject = topic.toObject(Topic::class.java)
                        topicObject.isPublic = true
                        publicTopicList.add(topicObject)
                        Log.d("TAG", "(GetPublicTopic) Topic is public : " + topicObject.isPublic)
                    }
                }

                callback(publicTopicList)
            }
            .addOnFailureListener { e ->
                Log.d("TAG", "Fail - The number of public topic " + e)
                callback(publicTopicList)
            }
    }

    fun AddTopic(topic : Topic, itemList : ArrayList<Item>, callback: () -> Unit) {
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

                    callback()

                }

                .addOnFailureListener { e -> Log.w("TAG", "Tạo học phần không thành công", e) }

//            if (topic.isPublic) {
//                AddPublicTopic(topic, itemList){}
//            }
        }
    }


    fun UpdateTopic(topic : Topic, newItemList : ArrayList<Item>, callback: () -> Unit) {
        // Save user information in cloud storage
        Log.d("TAG", "(UpdateTopic - TopicDAL) Topic name edit : " + topic.topicName)
        Log.d("TAG", "(UpdateTopic - TopicDAL) Topic PK edit : " + topic.topicPK)
        Log.d("TAG", "(UpdateTopic - TopicDAL) Topic PK isPublic : " + topic.isPublic)
        val updateTopic = mapOf (
            "topicName" to topic.topicName,
            "createdTime" to Calendar.getInstance().time,
            "highestScore" to 0.0
        )
        db.collection("topic").document(topic.topicPK).update(updateTopic)
        // Add item to topic

        Log.d("TAG", "(UpdateTopic - TopicDAL) The number of item in edit topic : " + newItemList.size)
        for (item in newItemList) {
            if (item.itemPK == "") {
                item.topicPK = topic.topicPK
                ItemDAL().AddItem(item)
            } else {
                ItemDAL().UpdateItem(item)
            }
        }
        TopicDTO.numItems = TopicDTO.itemList.size.toString()
        callback()
    }

    fun UpdateTopicScore(topic : Topic, newScore : Double) {
        Log.d("TAG", "newScore =  " + newScore)
        if (topic.highestScore < newScore) {
            Log.d("TAG", "Starting update score ... ")
            val updateTopic = mapOf (
                "highestScore" to newScore,
            )
            if (topic is TopicRanking) {
                db.collection("rankingTopic").document(topic.rankingTopicPK).update(updateTopic)
            } else {
                Log.d("TAG", "normal ... ")
                db.collection("topic").document(topic.topicPK).update(updateTopic)
            }
        }
    }

    fun AddPublicTopic(orinTopic : Topic, itemList : ArrayList<Item>, callback: (Boolean) -> Unit) {
        // Sign in success, update UI with the signed-in user's information
        val rankingTopicPK = db.collection("rankingTopic").document().id
        // Save user information in cloud storage
        if (rankingTopicPK != null) {
            val rankingTopicFB = hashMapOf (
                "rankingTopicPK" to rankingTopicPK,
                "topicPK" to orinTopic.topicPK,
                "userPK" to UserDTO.currentUser?.userPK,
                "highestScore" to 0,
                "timeStudy" to 0,
                "createdTime" to Calendar.getInstance().time,
            )

            db.collection("rankingTopic").document(rankingTopicPK)
                .set(rankingTopicFB)
                .addOnSuccessListener {
                    // Add item to topic
                    for (item in itemList) {
                        var rankingItem = ItemRanking()
                        rankingItem.itemPK = item.itemPK
                        rankingItem.topicRankingPK = rankingTopicPK
                        Log.d("AddRankingItem", "Add item ranking")
                        ItemDAL().AddPublicItem(rankingItem)
                    }

                    callback(true)
                }
                .addOnFailureListener {
                    callback(false)
                }
        }
    }

    fun DeleteTopic(topic: Topic) {

        var documentTopicRef : DocumentReference
        var documentTFRef = MyDB().db.collection("topic_folder")
        var queryTF : Query

        // Delete your topic
        if (topic.userPK == UserDTO.currentUser?.userPK ?: "") {
            Log.d("TAG", "Starting delete topic ...")
            documentTopicRef = MyDB().db.collection("topic").document(topic.topicPK)
            queryTF = documentTFRef.whereEqualTo("topicPK", topic.topicPK)

        } else {
            topic as TopicRanking
            documentTopicRef = MyDB().db.collection("rankingTopic").document(topic.rankingTopicPK)
            queryTF = documentTFRef.whereEqualTo("topicPK", topic.rankingTopicPK)
        }
            // Delete topic_folder containing the topic
            queryTF.get()
                .addOnSuccessListener { querySnapshot ->
                    if (querySnapshot.size() > 0) {
                        for (tf in querySnapshot) {
                            val tfObject = tf.toObject(TopicFolder::class.java)
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

            Log.d("TAG", "Ending delete topic")
    }

    fun GetRankingTable(topicId : String, callback: (ArrayList<RankingUser>) -> Unit) {

        var rankingObj = ArrayList<RankingUser>()

        val documentRef = MyDB().db.collection("topic")
        val query1 = documentRef.whereEqualTo("topicPK", topicId)

        Log.d("TAG", "Root topic pk : " + topicId)
        val documentRef1 = MyDB().db.collection("rankingTopic")
        val query = documentRef1.whereEqualTo("topicPK", topicId)
            .orderBy(
                "highestScore",
                com.google.firebase.firestore.Query.Direction.DESCENDING
            )

        query1.get()
            .addOnSuccessListener { querySnapshot ->
                Log.d("Owner", "Result = " + querySnapshot.size())
                if (querySnapshot.size() > 0) {
                    for (topic in querySnapshot) {
                        val topicObject = topic.toObject(Topic::class.java)
                        val rObj = RankingUser()
                        rObj.userPK = topicObject.userPK
                        rObj.highestScore = topicObject.highestScore
                        rankingObj.add(rObj)

                        Log.d("Owner", "User pk = " + topicObject.userPK)

                    }

                    val userIds = rankingObj.map { it.userPK }


                    val topicDocumentRef = MyDB().db.collection("user")
                    val query_user = topicDocumentRef.whereIn("userPK", userIds)
                    query_user.get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val user = document.toObject(User::class.java)
//                                rTopic?.topicPK = topic.topicPK

                                var rTopic =
                                    rankingObj.find { it.userPK == user.userPK }
                                if (rTopic != null) {
                                    rTopic.username = user.username
                                    rTopic.avatarUrl = user.avatarUrl
                                    Log.d(
                                        "TAG",
                                        "(GetRankingTopic) User ranking : " + rTopic.username
                                    )
                                }

                            }
                        }

                    query.get()
                        .addOnSuccessListener { querySnapshot ->
                            if (querySnapshot.size() > 0) {
                                for (topic in querySnapshot) {
                                    val topicObject = topic.toObject(TopicRanking::class.java)

                                    val rObj = RankingUser()
                                    rObj.userPK = topicObject.userPK
                                    rObj.highestScore = topicObject.highestScore
                                    rankingObj.add(rObj)
                                }

                                val userIds = rankingObj.map { it.userPK }


                                val topicDocumentRef = MyDB().db.collection("user")
                                val query = topicDocumentRef.whereIn("userPK", userIds)
                                query.get()
                                    .addOnSuccessListener { documents ->
                                        for (document in documents) {
                                            val user = document.toObject(User::class.java)
//                                rTopic?.topicPK = topic.topicPK

                                            var rTopic =
                                                rankingObj.find { it.userPK == user.userPK }
                                            if (rTopic != null) {
                                                rTopic.username = user.username
                                                rTopic.avatarUrl = user.avatarUrl
                                                Log.d(
                                                    "TAG",
                                                    "(GetRankingTopic) User ranking : " + rTopic.username
                                                )
                                            }

                                        }
                                        val sorted = ArrayList<RankingUser>(rankingObj.sortedByDescending { it.highestScore })
                                        callback(sorted)
                                    }
                                    .addOnFailureListener { _ ->
                                    }
                                Log.d(
                                    "TAG",
                                    "(GetRankingTopic) *** The number of user join topic : " + querySnapshot.size()
                                )

                            }

                            callback(rankingObj)
                        }
                        .addOnFailureListener { e ->
                            Log.d("TAG", "Fail - The number of public topic " + e)
                        }
                }

            }

        fun SearchTopic(content: String) {
            val query =
                GetTopic().orderByChild("userPK").equalTo(UserDTO.currentUser?.GetPK())
        }
    }

}