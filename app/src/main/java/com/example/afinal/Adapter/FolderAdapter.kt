package com.example.afinal.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.DTO.FolderDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.R


class FolderAdapter (private val folderList: ArrayList<FolderDomain>, private val activity: FragmentActivity) : RecyclerView.Adapter<FolderAdapter.FolderViewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_folder, parent, false)
        return FolderViewHolder(view)
    }

    override fun getItemCount(): Int {
        return folderList.size
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int) {
        holder.folderName.text = folderList[position].folderName
        holder.itemView.setOnClickListener {
            FolderDTO.currentFolder = folderList[position]
            val intent = Intent(activity, DetailFolderActivity::class.java)
            activity.startActivity(intent)
        }
    }

    class FolderViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val folderName = itemView.findViewById<TextView>(R.id.tv_folderName)
        val owner = itemView.findViewById<TextView>(R.id.textView_name)
        val avatar = itemView.findViewById<ImageView>(R.id.circleImageView_avatar)
        init {
            owner.text = UserDTO.currentUser?.username
            if (UserDTO.userAvatar != null) {
                avatar.setImageBitmap(UserDTO.userAvatar)
            }
        }
    }
}
