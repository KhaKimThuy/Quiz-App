package com.example.afinal.DAL

import android.util.Log
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.Item
import com.example.afinal.Domain.Folder
import com.example.afinal.Domain.Topic
import com.example.afinal.Domain.TopicFolder
import com.firebase.ui.database.FirebaseRecyclerOptions
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
import com.google.firebase.firestore.firestore
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference


open class MyDB() {
    var database: FirebaseDatabase = FirebaseDatabase.getInstance()
    var reference: DatabaseReference = database.reference
    var db = Firebase.firestore
    var dbAuth = FirebaseAuth.getInstance()
    var storageRef : StorageReference = FirebaseStorage.getInstance().reference

//    fun PicassoToBitmap(srcBitmap, imgUrl : String) {
//        val target = object : Target {
//            override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
//                if (bitmap != null) {
//                    srcBitmap = bitmap
//                    return bitmap
//                }
//            }
//            override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
//            }
//            override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
//            }
//        }
//
//        // Load the image using Picasso
//        Picasso.get().load(imgUrl).into(target)
//    }


    fun GetUser(): DatabaseReference {
        return database.getReference("User")
    }

    fun GetTopic(): DatabaseReference {
        return database.getReference("Topic")
    }

    fun GetTopicPublic(): DatabaseReference {
        return database.getReference("TopicPublic")
    }

    fun GetFolder(): DatabaseReference {
        return database.getReference("Folder")
    }

    fun GetTopicFolder(): DatabaseReference {
        return database.getReference("TopicFolder")
    }

    fun GetFolderByID(folderPK: String) : DatabaseReference {
        return GetFolder().child(folderPK)
    }

    fun GetTopicFolderByID(tfPK: String) : DatabaseReference {
        return GetTopicFolder().child(tfPK)
    }

    fun CreateFolder(folderName : String, folderDesc : String) {
        val folder = Folder()
        folder.folderName = folderName
        folder.folderDesc = folderDesc
        folder.folderPK = GetFolder().push().key!!

        GetFolder().addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (folder.folderPK?.let { snapshot.hasChild(it) } == true){
                    Log.d("Create folder", "Folder PK error")
                }else{
                    if (folder.folderPK != null) {
                        GetFolder().child(folder.folderPK).setValue(folder)
                    }
                }
            }
            override fun onCancelled(error: DatabaseError) {
                Log.d("Create folder", "System error")
            }
        })
    }

    fun EditFolder(folder: Folder, folderName : String, folderDesc : String) {
        GetFolderByID(folder.folderPK).child("folderName").setValue(folderName)
        GetFolderByID(folder.folderPK).child("folderDesc").setValue(folderDesc)
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

    fun RecyclerItem(topicPK: String): FirebaseRecyclerOptions<Item> {
        val query = GetItem().orderByChild("topicPK").equalTo(topicPK)
        return FirebaseRecyclerOptions.Builder<Item>()
            .setQuery(query, Item::class.java)
            .build()
    }

    fun CreateTopicWithItems(topic : Topic, itemList : ArrayList<Item>) {
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
        GetItem().addListenerForSingleValueEvent(object : ValueEventListener {
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

    fun AddTopicForFolder(topic : Topic, folder: Folder) {
        val topicFolder = TopicFolder()
        topicFolder.topicPK = topic.topicPK
        topicFolder.folderPK = folder.folderPK
        val tfPK = topicFolder.topicPK + topicFolder.folderPK
        GetTopicFolder().addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                GetTopicFolder().child(tfPK).setValue(topicFolder)
            }
            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })
    }

    fun GetListTopicIDFromTF(activity: DetailFolderActivity, topicIDs: ArrayList<String>, folder: Folder){
        val query = GetTopicFolder().orderByChild("folderPK").equalTo(folder.folderPK)
        query.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                topicIDs.clear()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(TopicFolder::class.java)
                    if (yourObject != null) {
                        topicIDs.add(yourObject.topicPK)
                    }
                }
                GetListTopicFromTF(activity, topicIDs)
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }

    fun GetUserByID(userPK: String? = null): DatabaseReference? {
        return if (userPK == null) {
            var userPK = UserDTO.currentUser?.GetPK()
            userPK?.let { database.getReference("User").child(it) }
        } else {
            userPK?.let { database.getReference("User").child(it) }
        }
    }

    fun GetListTopicFromTF(activity : DetailFolderActivity, topicIDs: ArrayList<String>){
//        val topicList : ArrayList<TopicDomain> = ArrayList<TopicDomain>()
//        val valueEventListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (key in topicIDs) {
//                    val snapshot = dataSnapshot.child(key)
//                    val yourObject = snapshot.getValue(TopicDomain::class.java)
//                    yourObject?.let { topicList.add(it) }
//                }
//                activity.loadFolder(topicList)
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle any errors
//            }
//        }
//
//        GetTopic().addListenerForSingleValueEvent(valueEventListener)
    }

    fun GetListItemOfTopic(topicID: String){
        val query = GetItem().orderByChild("topicPK").equalTo(topicID)
        query.addValueEventListener(object : ValueEventListener  {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                TopicDTO.itemList.clear()
                for (snapshot in dataSnapshot.children) {
                    val yourObject = snapshot.getValue(Item::class.java)
                    if (yourObject != null) {
                        TopicDTO.itemList.add(yourObject)
                    }
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                // Handle any errors
            }
        })
    }

    fun CreateItem(item : Item, topicPK : String = "") {
        val itemPK = GetItem().push().key
        if (itemPK != null) {
            item.itemPK = itemPK
            item.topicPK = topicPK
            GetItem().child(itemPK).setValue(item)
        }
    }

    fun DeleteItem(item : Item){
        GetItem().child(item.itemPK).removeValue()
            .addOnSuccessListener {
                // Object deleted successfully
                Log.d("Delete item", "Delete item success")
            }
            .addOnFailureListener { error ->
                // Handle the error
                Log.d("Delete item", "Delete item success")
            }
    }

//
//    fun DeleteFolder(folder : FolderDomain) {
//        // Remove all topic-folder included in that folder
//        val query = GetTopicFolder().orderByChild("folderPK").equalTo(folder.folderPK)
//        query.addValueEventListener(object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                for (snapshot in dataSnapshot.children) {
//                    val tfObject = snapshot.getValue(TopicFolderDomain::class.java)
//                    val tfPK = (tfObject?.topicPK ?: "") + (tfObject?.folderPK ?: "")
//                    GetTopicFolderByID(tfPK).removeValue()
//                }
//                GetFolder().child(folder.folderPK).removeValue()
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Handle any errors
//            }
//        })
//    }

    // Because folderPK of topic is the folderPk of current folder, give folder as argument is enough
    fun DeleteTopicFromFolder(topic : Topic, folder : Folder) {
        val tfPK = topic.topicPK + folder.folderPK
        GetTopicFolderByID(tfPK).removeValue()
    }

//    fun GetTheNumberOfItemsInTopic(topicPK: String, callback: ValueEventListenerCallback) {
//        val query = GetItem().orderByChild("topicPK").equalTo(topicPK)
//        val valueEventListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Khi dữ liệu thay đổi, hoặc được tải về lần đầu tiên
//                callback.onDataChange(dataSnapshot.childrenCount)
//                query.removeEventListener(this) // Hủy đăng ký listener
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Xử lý lỗi nếu có
//                callback.onCancelled(databaseError)
//                query.removeEventListener(this) // Hủy đăng ký listener
//            }
//        }
//        query.addValueEventListener(valueEventListener)
//    }
//
//    fun GetTheNumberOfTopicsInFolder(folderPK: String, callback: ValueEventListenerCallback) {
//        val query = GetTopicFolder().orderByChild("folderPK").equalTo(folderPK)
//        val valueEventListener = object : ValueEventListener {
//            override fun onDataChange(dataSnapshot: DataSnapshot) {
//                // Khi dữ liệu thay đổi, hoặc được tải về lần đầu tiên
//                callback.onDataChange(dataSnapshot.childrenCount)
//                query.removeEventListener(this) // Hủy đăng ký listener
//            }
//            override fun onCancelled(databaseError: DatabaseError) {
//                // Xử lý lỗi nếu có
//                callback.onCancelled(databaseError)
//                query.removeEventListener(this) // Hủy đăng ký listener
//            }
//        }
//        query.addValueEventListener(valueEventListener)
//    }

    fun RecyclerTopic(folderPK : String = ""): FirebaseRecyclerOptions<Topic> {
        val query : Query
        if (folderPK == "") {
            query = GetTopic().orderByChild("userPK")
                .equalTo(UserDTO.currentUser?.GetPK())

        } else {
            query = GetTopic()
                .orderByChild("folderPK").equalTo(folderPK)
        }

        return FirebaseRecyclerOptions.Builder<Topic>()
            .setQuery(query, Topic::class.java)
            .build()
    }



    fun extractPK(email : String) : String{
        var pk = email.replace("@", "")
        pk = pk.replace(".", "")
        return pk
    }

    fun RecyclerFolder(): FirebaseRecyclerOptions<Folder> {
        val query = GetFolder().orderByChild("userPK").equalTo(UserDTO.currentUser?.GetPK())
        return FirebaseRecyclerOptions.Builder<Folder>()
            .setQuery(query, Folder::class.java)
            .build()
    }
}
//https://www.youtube.com/watch?v=kGWN_Krbcms - Search