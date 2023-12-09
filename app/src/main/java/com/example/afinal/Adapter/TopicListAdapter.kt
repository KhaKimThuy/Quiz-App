package com.example.afinal.Adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
import com.example.afinal.ViewHolder.TopicViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class TopicListAdapter(
    options: FirebaseRecyclerOptions<TopicDomain>,
    isAdd: Boolean = false,
    isDel: Boolean = false,
    activity: DetailFolderActivity? = null) :
    FirebaseRecyclerAdapter<TopicDomain, TopicViewHolder>(options) {
    var isAdd : Boolean = false
    var isDel : Boolean = false
    var activity: DetailFolderActivity? = null
    var selectedTopics: ArrayList<TopicDomain> = ArrayList<TopicDomain>()
    init {
        this.isAdd = isAdd
        this.isDel = isDel
        this.activity = activity
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_topic, parent, false)
        return TopicViewHolder(view, this.isAdd, this.isDel)
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int, model: TopicDomain) {
        holder.bind(model, selectedTopics, activity)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }

}