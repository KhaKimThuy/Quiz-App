package com.example.afinal.ViewHolder

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Common.CommonUser
import com.example.afinal.R
import com.squareup.picasso.Picasso

class TopicViewHolder(view : View): RecyclerView.ViewHolder(view){
    val topic_name = view.findViewById<TextView>(R.id.textView_topicname)
    val owner = view.findViewById<TextView>(R.id.textView_name)
    val avatar = view.findViewById<ImageView>(R.id.circleImageView_avatar)

    init {
        owner.text = CommonUser.currentUser?.username ?: "Error"
        Picasso.get().load(CommonUser.currentUser?.avatarUrl).into(avatar)
    }
}