package com.example.afinal.DAL

import android.util.Log
import android.view.ScrollCaptureCallback
import com.example.afinal.DTO.FolderDTO
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.Folder
import com.example.afinal.Domain.Topic
import com.example.afinal.Domain.TopicFolder
import com.example.afinal.Domain.TopicRanking

class TopicFolderDAL : MyDB() {
    fun AddTopicFolder (topics : ArrayList<Topic>, folder : Folder) {
        for (topic in topics) {
            val topicFolder = TopicFolder()
            if (topic is TopicRanking) {
                topicFolder.topicPK = topic.rankingTopicPK
                Log.w("TAG", "Writing topic - ranking")
            } else {
                topicFolder.topicPK = topic.topicPK
                Log.w("TAG", "Writing topic - normal")
            }

            topicFolder.folderPK = folder.folderPK

            val folderTopicPK = db.collection("topic_folder").document().id
            topicFolder.topicFolderPK = folderTopicPK

            // Save user information in cloud storage
            val folderFB = hashMapOf (
                "folderPK" to topicFolder.folderPK,
                "topicPK" to topicFolder.topicPK,
                "topicFolderPK" to topicFolder.topicFolderPK,
            )
            db.collection("topic_folder").document(topicFolder.topicFolderPK)
                .set(folderFB)
                .addOnSuccessListener {Log.w("TAG", "Writing topic - folder document successfully") }
                .addOnFailureListener { e -> Log.w("TAG", "Error writing topic - folder document", e) }
        }
    }

    fun DeleteTFs(topics : ArrayList<Topic>, folder : Folder, callback: () -> Unit) {

        val topicIds = ArrayList<String>()
        for (i in topics) {
            if (i is TopicRanking) {
                topicIds.add(i.rankingTopicPK)
            } else {
                topicIds.add(i.topicPK)
            }
        }


        val documentRef = MyDB().db.collection("topic_folder")
        val query = documentRef.whereEqualTo("folderPK", folder.folderPK)
            .whereIn("topicPK", topicIds)
        query.get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val tfId = document.getString("topicFolderPK")
                    if (tfId != null) {
                        documentRef.document(tfId).delete()
                    }
                }
                Log.d("GetTopicOfFolder", "Topic size : " + FolderDTO.topicList.size)
                callback()
            }
            .addOnFailureListener { _ ->
                // Handle any errors that occurred while retrieving the documents
            }
        }


    fun DeleteTF(topicFolder : TopicFolder) {
            val documentRef = MyDB().db.collection("topic_folder").document(topicFolder.topicFolderPK)
            documentRef.delete()
                .addOnSuccessListener {
                    // File deleted successfully
                    Log.w("TAG", "Delete topic_folder successfully")
                }
                .addOnFailureListener { _ ->
                    // Handle any errors
                    Log.w("TAG", "Fail to delete topic_folder")
                }
    }

}