package com.example.afinal.Adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.example.afinal.R
import com.example.afinal.ViewHolder.TopicViewHolder
import com.google.firebase.database.DatabaseError

class SearchTopicAdapter(private var dataList: List<TopicDomain>) : RecyclerView.Adapter<TopicViewHolder>() {
    val db = MyDB()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.viewholder_topic, parent, false)
        return TopicViewHolder(view)
    }
    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.topicName.text = dataList[position].topicName
        db.GetTheNumberOfItemsInTopic(dataList[position].topicPK, object : ValueEventListenerCallback {
            override fun onDataChange(dataSnapshot: Long) {
                holder.numberItems.text = "$dataSnapshot"
            }

            override fun onDataChange(dataSnapshot: FolderDomain) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(topicIDs: ArrayList<String>) {
                TODO("Not yet implemented")
            }

            override fun onDataChangeTopic(dataSnapshot: TopicDomain) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(databaseError: DatabaseError) {
                holder.numberItems.text = "0"
            }

        }).toString()

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailTopicActivity::class.java)
            intent.putExtra("numItems", holder.numberItems.text)
            intent.putExtra("topic", dataList[position])
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun searchDataList(searchList: List<TopicDomain>) {
        dataList = searchList
        notifyDataSetChanged()
    }
}