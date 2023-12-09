package com.example.afinal.DAL

import android.util.Log
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Domain.TopicFolderDomain

class TopicFolderDAL : MyDB() {
    fun AddTopicFolder (topic : TopicDomain, folder : FolderDomain) {
        val topicFolder = TopicFolderDomain()
        topicFolder.topicPK = topic.topicPK
        topicFolder.folderPK = folder.folderPK

        val folderTopicPK = db.collection("topic_folder").document().id
        topicFolder.topicFolderPK = folderTopicPK

        // Save user information in cloud storage
        if (folderTopicPK != null) {
            val folderFB = hashMapOf (
                "folderPK" to topicFolder.folderPK,
                "topicPK" to topicFolder.topicPK,
                "topicFolderPK" to topicFolder.topicFolderPK,
            )
            db.collection("topic_folder").document(folderTopicPK)
                .set(folderFB)
                .addOnSuccessListener {Log.w("TAG", "Writing topic - folder document successfully") }
                .addOnFailureListener { e -> Log.w("TAG", "Error writing topic - folder document", e) }
        }
    }

    fun DeleteTF(topicFolder: TopicFolderDomain) {
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