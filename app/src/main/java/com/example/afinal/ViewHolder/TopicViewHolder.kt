package com.example.afinal.ViewHolder

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.example.afinal.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class TopicViewHolder(view : View): RecyclerView.ViewHolder(view){
    val topicName = view.findViewById<TextView>(R.id.textView_topicname)
    val numberItems = view.findViewById<TextView>(R.id.textView_numberItems)
    val owner = view.findViewById<TextView>(R.id.textView_name)
    val avatar = view.findViewById<ImageView>(R.id.circleImageView_avatar)
    val db = MyDB()

    init {
        owner.text = CommonUser.currentUser?.username ?: "Error"
        Picasso.get().load(CommonUser.currentUser?.avatarUrl).into(avatar)
    }

    @SuppressLint("SetTextI18n")
    fun bind(topic: TopicDomain) {
        topicName.text = topic.topicName
        db.getDataFromQuery(topic.topicPK, object : ValueEventListenerCallback{
            override fun onDataChange(dataSnapshot: Long) {
                numberItems.text = "$dataSnapshot Thuật ngữ"
            }

            override fun onCancelled(databaseError: DatabaseError) {
                numberItems.text = "0 Thuật ngữ"
            }

        }).toString()

        itemView.setOnClickListener{
            val intent = Intent(itemView.context, DetailTopicActivity::class.java)
            intent.putExtra("topic", topic.topicPK)
            itemView.context.startActivity(intent)
        }
    }
}