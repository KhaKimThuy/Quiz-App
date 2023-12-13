package com.example.afinal.Adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.DAL.MyDB
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.Topic
import com.example.afinal.Domain.User
import com.example.afinal.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso

class SearchTopicAdapter(private var dataList: List<Topic>) : RecyclerView.Adapter<SearchTopicAdapter.SearchTopicViewHolder>() {
    val db = MyDB()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchTopicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.viewholder_topic, parent, false)
        return SearchTopicViewHolder(view)
    }
    override fun onBindViewHolder(holder: SearchTopicViewHolder, @SuppressLint("RecyclerView") position: Int) {
        db.GetUserByID(dataList[position].userPK)?.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val user = dataSnapshot.getValue(User::class.java)
                    holder.topicName.text = dataList[position].topicName
                    if (user != null) {
                        holder.owner.text = user.username
                    }
                    if (user != null) {
                        Picasso.get()
                            .load(user.avatarUrl)
                            .into(holder.avatar)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

//        db.GetTheNumberOfItemsInTopic(dataList[position].topicPK, object : ValueEventListenerCallback {
//            override fun onDataChange(dataSnapshot: Long) {
//                holder.numberItems.text = "$dataSnapshot"
//            }
//
//            override fun onDataChange(dataSnapshot: FolderDomain) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onDataChange(topicIDs: ArrayList<String>) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onDataChangeTopic(dataSnapshot: TopicDomain) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(databaseError: DatabaseError) {
//                holder.numberItems.text = "0"
//            }
//
//        }).toString()

        holder.itemView.setOnClickListener{

            TopicDTO.currentTopic = dataList[position]
            TopicDTO.numItems = holder.numberItems.text.toString()
            db.GetListItemOfTopic(dataList[position].topicPK)

            val intent = Intent(holder.itemView.context, DetailTopicActivity::class.java)
            holder.itemView.context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
    fun searchDataList(searchList: List<Topic>) {
        dataList = searchList
        notifyDataSetChanged()
    }
    class SearchTopicViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val topicName = itemView.findViewById<TextView>(R.id.tv_folderName)
        val numberItems = itemView.findViewById<TextView>(R.id.textView_numberItems)
        val owner = itemView.findViewById<TextView>(R.id.textView_name)
        val avatar = itemView.findViewById<ImageView>(R.id.circleImageView_avatar)
    }
}