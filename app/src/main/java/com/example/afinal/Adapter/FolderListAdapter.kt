package com.example.afinal.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.R
import com.example.afinal.ViewHolder.FolderViewHolder
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FolderListAdapter(options: FirebaseRecyclerOptions<FolderDomain>) :
    FirebaseRecyclerAdapter<FolderDomain, FolderViewHolder>(options) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FolderViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.viewholder_folder, parent, false)
        return FolderViewHolder(listItem)
    }

    override fun onBindViewHolder(holder: FolderViewHolder, position: Int, model: FolderDomain) {
        holder.bind(model)
    }
}