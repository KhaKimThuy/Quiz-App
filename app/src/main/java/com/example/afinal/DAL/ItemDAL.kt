package com.example.afinal.DAL

import android.util.Log
import com.example.afinal.Domain.Item
import com.example.afinal.Domain.ItemRanking

class ItemDAL : MyDB() {
    fun AddItem(item : Item) {
        val itemPK = db.collection("item").document().id
        item.itemPK = itemPK

        // Save user information in cloud storage
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

    fun AddPublicItem(rankingItem : ItemRanking) {
        val rankingItemPK = db.collection("rankingItem").document().id

        // Save user information in cloud storage
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

    fun UpdateItem(item : Item) {
        val updateItem = mapOf(
            "engLanguage" to item.engLanguage,
            "vnLanguage" to item.vnLanguage,
        )
        Log.d("TAG", "Udate item")
        db.collection("item").document(item.itemPK).update(updateItem)
    }

    fun UpdateMarkedItem(item : Item) {
        val updateItem = mapOf (
            "isMarked" to item.isMarked,
        )
        if (item is ItemRanking) {
            Log.d("Marker" , "Mark on update item")
            db.collection("rankingItem").document(item.itemRankingPK).update(updateItem)
        } else {
            Log.d("Marker" , "Mark on item")
            db.collection("item").document(item.itemPK).update(updateItem)
        }
    }

    fun UpdateMarkedRankingItem(item : Item) {
        if (item is ItemRanking) {
            val updateItem = mapOf (
                "isMarked" to item.isMarked,
            )
            db.collection("rankingItem").document(item.itemRankingPK).update(updateItem)
        }
    }

    fun UpdateScoreItem(item : Item) {
        item.numRights += 1
        if (item.state == "Chưa được học") {
            item.state = "Đang được học"
        } else {
            if (item.numRights > 3) {
                item.state = "Đã thành thạo"
            }
        }
        val updateItem = mapOf (
            "numRights" to item.numRights,
            "state" to item.state,
        )
        if (item is ItemRanking) {
            db.collection("rankingItem").document(item.itemRankingPK).update(updateItem)
        } else {
            db.collection("item").document(item.itemPK).update(updateItem)
        }
    }

    fun UpdateFirstLearningItem(item : Item) {
        item.state = "Đang được học"
        val updateItem = mapOf (
            "state" to item.state,
        )
        if (item is ItemRanking) {
            db.collection("rankingItem").document(item.itemRankingPK).update(updateItem)
        } else {
            db.collection("item").document(item.itemPK).update(updateItem)
        }
    }

    fun DeleteFC(item: Item) {
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

    fun DeleteRankingItem(item: Item) {
        item as ItemRanking
        val documentRef = MyDB().db.collection("rankingItem").document(item.itemRankingPK)
        documentRef.delete()
            .addOnSuccessListener {
                // File deleted successfully
                Log.w("TAG", "Delete ranking item successfully")
            }
            .addOnFailureListener { _ ->
                // Handle any errors
                Log.w("TAG", "Fail to delete ranking item")
            }
    }
}