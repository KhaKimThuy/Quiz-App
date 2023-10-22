package com.example.afinal.Adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
import com.example.afinal.ViewHolder.TopicViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class TopicListAdapter(options: FirebaseRecyclerOptions<TopicDomain>) :
    FirebaseRecyclerAdapter<TopicDomain, TopicViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.viewholder_topic, parent, false)
        return TopicViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int, model: TopicDomain) {
        holder.topic_name.text = model.topicName
    }

}