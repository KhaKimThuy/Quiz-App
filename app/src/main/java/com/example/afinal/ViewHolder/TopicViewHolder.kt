package com.example.afinal.ViewHolder

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.DTO.UserDTO
import com.example.afinal.DB.MyDB
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.example.afinal.R
import com.google.firebase.database.DatabaseError


class TopicViewHolder(view : View, isAdd: Boolean = false, isDel: Boolean = false): RecyclerView.ViewHolder(view){
    val topicName = view.findViewById<TextView>(R.id.tv_folderName)
    val numberItems = view.findViewById<TextView>(R.id.textView_numberItems)
    val owner = view.findViewById<TextView>(R.id.textView_name)
    val avatar = view.findViewById<ImageView>(R.id.circleImageView_avatar)
    var mainView = view.findViewById<ConstraintLayout>(R.id.itemView_flashCard)

    val db = MyDB()
    var isAdd : Boolean = false
    var isDel : Boolean = false

    init {
        owner.text = UserDTO.currentUser?.username ?: "Error"
        this.isAdd = isAdd
        this.isDel = isDel
        avatar.setImageBitmap(UserDTO.userAvatar)
    }

    @SuppressLint("SetTextI18n")
    fun bind(topic: TopicDomain, selectedTopic: ArrayList<TopicDomain>? = null, activity: DetailFolderActivity?) {
        topicName.text = topic.topicName
        db.GetTheNumberOfItemsInTopic(topic.topicPK, object : ValueEventListenerCallback{
            override fun onDataChange(dataSnapshot: Long) {
                numberItems.text = "$dataSnapshot"
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
                numberItems.text = "0"
            }
        }).toString()

        itemView.setOnClickListener{
            if(this.isAdd){
                val backgroundDrawable = mainView.background
                val backgroundColor = (backgroundDrawable as ColorDrawable).color
                if (backgroundColor ==  Color.GREEN){
                    mainView.setBackgroundColor(Color.WHITE)
                    if (selectedTopic != null) {
                        selectedTopic.remove(topic)
                    }
                } else {
                    mainView.setBackgroundColor(Color.GREEN)
                    if (selectedTopic != null) {
                        selectedTopic.add(topic)
                    }
                }
                if (selectedTopic != null) {
                    Log.d("TAG", "Items = " + selectedTopic.size)
                }
            }
            else{
                val backgroundDrawable = mainView.background
                val backgroundColor = (backgroundDrawable as ColorDrawable).color
                if (backgroundColor ==  Color.MAGENTA) {
                    mainView.setBackgroundColor(Color.WHITE)
                    if (selectedTopic != null) {
                        selectedTopic.remove(topic)
                    }
                    activity!!.checkDeleteItem()
                } else {

                    TopicDTO.currentTopic = topic
                    TopicDTO.numItems = numberItems.text.toString()
                    db.GetListItemOfTopic(topic.topicPK)

                    Log.d("TAG", "search: " + TopicDTO.currentTopic!!.topicName)

//                    val intent = Intent(itemView.context, DetailTopicActivity::class.java)
//                    itemView.context.startActivity(intent)
                }
            }
        }

        itemView.setOnLongClickListener{
            if (this.isDel) {
                val backgroundDrawable = mainView.background
                val backgroundColor = (backgroundDrawable as ColorDrawable).color
                if (backgroundColor ==  Color.MAGENTA) {
                    mainView.setBackgroundColor(Color.WHITE)
                    if (selectedTopic != null) {
                        selectedTopic.remove(topic)
                    }
                } else {
                    mainView.setBackgroundColor(Color.MAGENTA)
                    if (selectedTopic != null) {
                        selectedTopic.add(topic)
                    }
                }
                activity!!.checkDeleteItem()
                true
            } else {
                Log.d("Delete folder", "Fail to set on long click")
                false
            }
        }
    }
}