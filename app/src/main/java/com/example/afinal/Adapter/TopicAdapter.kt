package com.example.afinal.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
import com.example.afinal.ViewHolder.TopicViewHolder

class TopicAdapter (
    activity: Activity,
    private val topicList: ArrayList<TopicDomain>,
) : RecyclerView.Adapter<TopicViewHolder>(){
    private var activity = activity
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_topic, parent, false)
        return TopicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.bind(topicList[position])
    }

}
