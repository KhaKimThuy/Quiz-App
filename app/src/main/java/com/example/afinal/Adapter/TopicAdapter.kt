package com.example.afinal.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DAL.UserDAL
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.Topic
import com.example.afinal.R
import com.squareup.picasso.Picasso

class TopicAdapter(private var topicList: List<Topic>,
                   private val onClickListernerTopic: IClickTopicListener
) : RecyclerView.Adapter<TopicAdapter.TopicViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TopicViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_topic, parent, false)
        return TopicViewHolder(view)
    }



    override fun getItemCount(): Int {
        return topicList.size
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(holder: TopicViewHolder, position: Int) {
        holder.topicName.text = topicList[position].topicName

        TopicDAL().GetNumItem(topicList[position].topicPK) {
            holder.numItem.text = it.toString()
        }

        if (topicList[position].userPK == MyDB().dbAuth.currentUser?.uid) {
            holder.owner.text = UserDTO.currentUser?.username // Get user info later
            if (UserDTO.userAvatar != null) {
                holder.avatar.setImageBitmap(UserDTO.userAvatar)
            }
        } else {
            UserDAL().GetUserObject(topicList[position].userPK) {
                if (it != null) {
                    holder.owner.text = it.username // Get user info later
                    if (it.avatarUrl != "") {
                        Picasso.get().load(it?.avatarUrl).into(holder.avatar)
                    }
                }
            }
        }

        holder.itemView.setOnClickListener(View.OnClickListener {
            onClickListernerTopic.onClickTopicListener(holder, position)
        })

        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
            onClickListernerTopic.onLongClickTopicListener(holder, position)
            false
        })

    }

    class TopicViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val topicName = itemView.findViewById<TextView>(R.id.tv_folderName)
        val numItem = itemView.findViewById<TextView>(R.id.textView_numberItems)
        val owner = itemView.findViewById<TextView>(R.id.textView_name)
        val avatar = itemView.findViewById<ImageView>(R.id.circleImageView_avatar)

        // For marking
        var mainView = itemView.findViewById<ConstraintLayout>(R.id.itemView_flashCard)
    }

    interface IClickTopicListener {
        fun onClickTopicListener(topicView : TopicViewHolder, position: Int)
        fun onLongClickTopicListener(topicView : TopicViewHolder, position: Int)
    }
}
