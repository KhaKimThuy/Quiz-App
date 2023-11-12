package com.example.afinal.DB

import android.content.Intent
import android.util.Log
import android.widget.Toast
import com.example.afinal.Activity.CreateStudyModuleActivity
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Activity.MainActivity2
import com.example.afinal.Common.CommonUser
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.currentCoroutineContext

class MyDB() {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var storage: FirebaseStorage = FirebaseStorage.getInstance()
    var reference: DatabaseReference = database.reference

    fun GetUser(): DatabaseReference {
        return database.getReference("User")
    }

    fun GetTopic(): DatabaseReference {
        return database.getReference("Topic")
    }

    fun GetFolder(): DatabaseReference {
        return database.getReference("Folder")
    }

    fun GetUserByID(): DatabaseReference? {
        var pk = CommonUser.currentUser?.GetPK()
        return pk?.let { database.getReference("User").child(it) }
    }

    fun GetFolderByID(folderPK: String, callback: ValueEventListenerCallback) {
        val query = GetFolder().orderByChild("folderPK").equalTo(folderPK)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                val folder = dataSnapshot.child(folderPK).getValue(FolderDomain::class.java)
                if (folder != null) {
                    callback.onDataChange(folder)
                }
                query.removeEventListener(this)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callback.onCancelled(databaseError)
                query.removeEventListener(this) // Hủy đăng ký listener
            }
        }
        query.addValueEventListener(valueEventListener)
    }

    fun GetTopicByID(topicPK: String) : DatabaseReference {
        return GetTopic().child(topicPK)
    }

    fun GetItemByID(itemPK: String) : DatabaseReference {
        return GetItem().child(itemPK)
    }

    fun GetItem(): DatabaseReference {
        return database.getReference("Item")
    }

    fun RecyclerItem(topicPK: String): FirebaseRecyclerOptions<FlashCardDomain> {
        val query = GetItem().orderByChild("topicPK").equalTo(topicPK)
        return FirebaseRecyclerOptions.Builder<FlashCardDomain>()
            .setQuery(query, FlashCardDomain::class.java)
            .build()
    }

    fun CreateTopicWithItems(topic : TopicDomain, itemList : ArrayList<FlashCardDomain>) {
        // Add topic
        GetTopic().addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (topic.topicPK?.let { snapshot.hasChild(it) } == true){
                    Log.d("AddTopic", "Topic name is already used")
                }else{
                    if (topic.topicPK != null) {
                        GetTopic().child(topic.topicPK).setValue(topic)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        // Add items
        GetItem().addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in itemList){
                    if(!item.engLanguage.isNullOrEmpty() && !item.vnLanguage.isNullOrEmpty()){
                        if (topic.topicPK != null) {
                            CreateItem(item, topic.topicPK)
                        }
                    }
                }
                Log.d("AddTopic", "Add topic successfully")
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("AddTopic", "Fail to add topic")
            }
        })
    }

    fun CreateItem(item : FlashCardDomain, topicPK : String = "") {
        val itemPK = GetItem().push().key
        if (itemPK != null) {
            item.itemPK = itemPK
            item.topicPK = topicPK
            GetItem().child(itemPK).setValue(item)
        }
    }

    fun GetTheNumberOfItemsInTopic(topicPK: String, callback: ValueEventListenerCallback) {
        val query = GetItem().orderByChild("topicPK").equalTo(topicPK)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Khi dữ liệu thay đổi, hoặc được tải về lần đầu tiên
                callback.onDataChange(dataSnapshot.childrenCount)
                query.removeEventListener(this) // Hủy đăng ký listener
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý lỗi nếu có
                callback.onCancelled(databaseError)
                query.removeEventListener(this) // Hủy đăng ký listener
            }
        }
        query.addValueEventListener(valueEventListener)
    }

    fun GetTheNumberOfTopicsInFolder(folderPK: String, callback: ValueEventListenerCallback) {
        val query = GetTopic().orderByChild("folderPK").equalTo(folderPK)
        val valueEventListener = object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                // Khi dữ liệu thay đổi, hoặc được tải về lần đầu tiên
                callback.onDataChange(dataSnapshot.childrenCount)
                query.removeEventListener(this) // Hủy đăng ký listener
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Xử lý lỗi nếu có
                callback.onCancelled(databaseError)
                query.removeEventListener(this) // Hủy đăng ký listener
            }
        }
        query.addValueEventListener(valueEventListener)
    }

    fun RecyclerTopic(folderPK : String = ""): FirebaseRecyclerOptions<TopicDomain> {
        val query : Query
        if (folderPK == "") {
            query = GetTopic().orderByChild("userPK")
                .equalTo(CommonUser.currentUser?.GetPK())
        } else {
            query = GetTopic()
                .orderByChild("folderPK").equalTo(folderPK)
        }
        return FirebaseRecyclerOptions.Builder<TopicDomain>()
            .setQuery(query, TopicDomain::class.java)
            .build()
    }




    fun extractPK(email : String) : String{
        var pk = email.replace("@", "")
        pk = pk.replace(".", "")
        return pk
    }

    fun RecyclerFolder(): FirebaseRecyclerOptions<FolderDomain> {
        val query = GetFolder().orderByChild("userPK").equalTo(CommonUser.currentUser?.GetPK())
        return FirebaseRecyclerOptions.Builder<FolderDomain>()
            .setQuery(query, FolderDomain::class.java)
            .build()
    }
}