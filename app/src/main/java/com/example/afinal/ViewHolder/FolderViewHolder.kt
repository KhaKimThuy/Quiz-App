package com.example.afinal.ViewHolder

import android.annotation.SuppressLint
import android.content.Intent
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.example.afinal.R
import com.google.firebase.database.DatabaseError
import com.squareup.picasso.Picasso

class FolderViewHolder(view : View): RecyclerView.ViewHolder(view){
    val folderName = view.findViewById<TextView>(R.id.tv_folderName)
    val owner = view.findViewById<TextView>(R.id.textView_name)
    val avatar = view.findViewById<ImageView>(R.id.circleImageView_avatar)
    val db = MyDB()

    init {
        owner.text = CommonUser.currentUser?.username ?: "Error"
        Picasso.get().load(CommonUser.currentUser?.avatarUrl).into(avatar)
    }

    fun bind(folder: FolderDomain) {
        folderName.text = folder.folderName
        itemView.setOnClickListener {
            val intent = Intent(itemView.context, DetailFolderActivity::class.java)
            intent.putExtra("folder", folder)
            itemView.context.startActivity(intent)
        }
    }
}