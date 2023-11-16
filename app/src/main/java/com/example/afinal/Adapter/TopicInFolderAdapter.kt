package com.example.afinal.Adapter

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.example.afinal.R
import com.example.afinal.ViewHolder.TopicViewHolder
import com.google.firebase.database.DatabaseError

class TopicInFolderAdapter(
    private val activity: DetailFolderActivity,
    private val topicList: ArrayList<TopicDomain>,
) : RecyclerView.Adapter<TopicViewHolder>(){

    private val db = MyDB()
    val selectedTopics = ArrayList<TopicDomain>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_topic, parent, false)
        return TopicViewHolder(view)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.topicName.text = topicList[position].topicName
        db.GetTheNumberOfItemsInTopic(topicList[position].topicPK, object : ValueEventListenerCallback {
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

        holder.itemView.setOnClickListener(View.OnClickListener {
            val backgroundDrawable = holder.mainView.background
            val backgroundColor = (backgroundDrawable as ColorDrawable).color
            if (backgroundColor ==  Color.MAGENTA) {
                holder.mainView.setBackgroundColor(Color.WHITE)
                selectedTopics.remove(topicList[position])
                activity!!.checkDeleteItem()
            } else {
                val intent = Intent(activity, DetailTopicActivity::class.java)
                intent.putExtra("numItems", holder.numberItems.text)
                intent.putExtra("topic", topicList[position])
                activity.startActivity(intent)
            }
        })

        holder.itemView.setOnLongClickListener{
            val backgroundDrawable = holder.mainView.background
            val backgroundColor = (backgroundDrawable as ColorDrawable).color
            if (backgroundColor ==  Color.MAGENTA) {
                holder.mainView.setBackgroundColor(Color.WHITE)
                selectedTopics.remove(topicList[position])
            } else {
                holder.mainView.setBackgroundColor(Color.MAGENTA)
                selectedTopics.add(topicList[position])
            }
            activity!!.checkDeleteItem()
            true
        }

    }

}
