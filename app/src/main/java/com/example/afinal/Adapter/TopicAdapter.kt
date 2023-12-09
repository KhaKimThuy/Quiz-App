package com.example.afinal.Adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DAL.UserDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
import com.squareup.picasso.Picasso

class TopicAdapter(private var topicList: List<TopicDomain>,
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
            if (UserDTO.currentUser?.avatarUrl != "") {
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

//        holder.itemView.setOnClickListener(View.OnClickListener {
//            // Add topic to folder
//            if (addToFolder) {
//                val backgroundDrawable = holder.mainView.background
//                val backgroundColor = (backgroundDrawable as ColorDrawable).color
//                if (backgroundColor ==  R.color.add_topic_marker_color){
//                    holder.mainView.setBackgroundColor(Color.WHITE)
//                    if (selectedTopic != null) {
//                        selectedTopic.remove(topicList[position])
//                    }
//                } else {
//                    holder.mainView.setBackgroundColor(R.color.add_topic_marker_color)
//                    if (selectedTopic != null) {
//                        selectedTopic.add(topicList[position])
//                    }
//                }
//                Log.d("TAG", "Item size = " + selectedTopic.size)
//            }
//
//            // Go to detail topic
//            else {
//                TopicDTO.currentTopic = topicList[position]
//                TopicDTO.numItems = holder.numItem.text.toString()
//
//                val intent = Intent(activity, DetailTopicActivity::class.java)
//                activity.startActivity(intent)
//            }
//        })
//
//        holder.itemView.setOnLongClickListener(View.OnLongClickListener {
//
//            val backgroundDrawable = holder.mainView.background
//            val backgroundColor = (backgroundDrawable as ColorDrawable).color
//            if (backgroundColor ==  Color.MAGENTA) {
//                holder.mainView.setBackgroundColor(Color.WHITE)
//                if (selectedTopic != null) {
//                    selectedTopic.remove(topicList[position])
//                }
//            } else {
//                holder.mainView.setBackgroundColor(Color.MAGENTA)
//                if (selectedTopic != null) {
//                    selectedTopic.add(topicList[position])
//                }
//            }
//            activity!!.checkDeleteItem()
//
//            false
//        })

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
