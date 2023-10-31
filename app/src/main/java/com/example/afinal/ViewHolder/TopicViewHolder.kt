package com.example.afinal.ViewHolder

import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
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

    fun bind(topic: TopicDomain) {
        topicName.text = topic.topicName
        numberItems.text = db.GetNumberOfItemInTopic(topic.topicPK) + " Thuật ngữ";

        itemView.setOnClickListener{
            val intent = Intent(itemView.context, DetailTopicActivity::class.java)
            intent.putExtra("topic", topic.topicPK)
            itemView.context.startActivity(intent)
        }
    }
}