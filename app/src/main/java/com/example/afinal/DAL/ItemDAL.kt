package com.example.afinal.DAL

import android.util.Log
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.ItemRanking
import com.example.afinal.Domain.TopicDomain

class ItemDAL : MyDB() {
    fun AddItem(item : FlashCardDomain) {
        val itemPK = db.collection("item").document().id
        item.itemPK = itemPK

        // Save user information in cloud storage
        if (itemPK != null) {
            val itemFB = hashMapOf(
                "itemPK" to item.itemPK,
                "topicPK" to item.topicPK,
                "engLanguage" to item.engLanguage,
                "vnLanguage" to item.vnLanguage,
                "isMarked" to item.isMarked,
                "state" to item.state,
                "numRights" to item.numRights,
                "imgUrl" to item.imgUrl,
            )
            db.collection("item").document(itemPK)
                .set(itemFB)
                .addOnSuccessListener { Log.d("TAG", "Item successfully written!") }
                .addOnFailureListener { e -> Log.w("TAG", "Error writing item document", e) }
        }
    }

    fun AddPublicItem(rankingItem : ItemRanking) {
        val rankingItemPK = db.collection("rankingItem").document().id

        // Save user information in cloud storage
        if (rankingItemPK != null) {
            val itemRankingFB = hashMapOf (
                "itemRankingPK" to rankingItemPK,
                "topicRankingPK" to rankingItem.topicRankingPK,
                "itemPK" to rankingItem.itemPK,
                "isMarked" to rankingItem.isMarked,
                "state" to rankingItem.state,
                "numRights" to rankingItem.numRights,
            )
            db.collection("rankingItem").document(rankingItemPK)
                .set(itemRankingFB)
                .addOnSuccessListener { Log.d("TAG", "Ranking item successfully written!") }
                .addOnFailureListener { e -> Log.w("TAG", "Error writing ranking item document", e) }
        }
    }

    fun UpdateItem(item : FlashCardDomain) {
        val updateItem = mapOf(
            "isMarked" to item.isMarked,
            "state" to item.state,
            "numRights" to item.numRights,
        )
        db.collection("item").document(item.itemPK).update(updateItem)
    }

    fun DeleteFC(item: FlashCardDomain) {
        val documentRef = MyDB().db.collection("item").document(item.itemPK)
        documentRef.delete()
            .addOnSuccessListener {
                // File deleted successfully
                Log.w("TAG", "Delete item successfully")
            }
            .addOnFailureListener { _ ->
                // Handle any errors
                Log.w("TAG", "Fail to delete item")
            }
    }
}