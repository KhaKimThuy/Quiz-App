package com.example.afinal.DAL

import android.content.Intent
import android.util.Log
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.Activity.MainActivity2
import com.example.afinal.DTO.FolderDTO
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.Folder
import com.example.afinal.Domain.TopicFolder

class FolderDAL : MyDB() {
    fun AddFolder(folder : Folder, activity : MainActivity2) {
        val folderPK = db.collection("folder").document().id
        folder.folderPK = folderPK
        folder.userPK = dbAuth.currentUser?.uid.toString()

        // Save user information in cloud storage
        if (folderPK != null) {
            val folderFB = hashMapOf(
                "folderPK" to folder.folderPK,
                "userPK" to folder.userPK,
                "folderName" to folder.folderName,
                "folderDesc" to folder.folderDesc,
            )
            db.collection("folder").document(folderPK)
                .set(folderFB)
                .addOnSuccessListener {
                    val intent = Intent(activity, DetailFolderActivity::class.java)

                    // Save folder locally
                    FolderDTO.currentFolder = folder

                    activity.startActivity(intent)
                    activity.finish()
                }
                .addOnFailureListener { e -> Log.w("TAG", "Error writing folder document", e) }
        }
    }



    fun GetFolderOfUser(userId: String, callback: (ArrayList<Folder>) -> Unit) {
        val documentRef = MyDB().db.collection("folder")
        val query = documentRef.whereEqualTo("userPK", userId)

        val folderList = ArrayList<Folder>()

        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (folder in querySnapshot) {
                        val folderObject = folder.toObject(Folder::class.java)
                        folderList.add(folderObject)
                    }
                }
                Log.d("TAG", "Folder size from callback = " + folderList.size)
                callback(folderList)
            }
            .addOnFailureListener { _ ->
                callback(folderList)
            }
    }

    fun GetTopicOfFolder (folder: Folder, callback: () -> Unit) {
        val documentTFRef = MyDB().db.collection("topic_folder")

        val query = documentTFRef.whereEqualTo("folderPK",folder.folderPK)

        val topicIds = ArrayList<String>()

        query.get()
            .addOnSuccessListener { querySnapshot ->
                if (querySnapshot.size() > 0) {
                    for (tf in querySnapshot) {
                        val topicId = tf.getString("topicPK")
                        if (topicId != null) {
                            topicIds.add(topicId)
                        }
                    }
                    val query = MyDB().db.collection("topic").whereIn("topicPK", topicIds)
                    query.get()
                        .addOnSuccessListener { documents ->
                            for (document in documents) {
                                val topicId = document.getString("topicPK")
                                val topic = TopicDTO.topicList.find{ it.topicPK == topicId }
                                if (topic != null) {
                                    FolderDTO.topicList.add(topic)
                                }
                            }
                            callback()
                        }
                        .addOnFailureListener { _ ->
                            // Handle any errors that occurred while retrieving the documents
                        }
                }
            }
            .addOnFailureListener { _ ->
            }
    }

    fun DeleteFolder (folder : Folder) {
        // Remove all topic-folder included in that folder
        val documentFolderRef = MyDB().db.collection("folder").document(folder.folderPK)
        val documentTFRef = MyDB().db.collection("topic_folder")
        val queryTF = documentTFRef.whereEqualTo("folderPK", folder.folderPK)

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

        // Delete topic
        documentFolderRef.delete()
            .addOnSuccessListener {
                Log.w("TAG", "Delete folder successfully")
            }
            .addOnFailureListener { _ ->
                Log.w("TAG", "Fail to delete folder")
            }

        // Refresh current topic value
        FolderDTO.topicList.clear()
        FolderDTO.currentFolder = null

        Log.d("TAG", "Ending delete folder")
    }
}