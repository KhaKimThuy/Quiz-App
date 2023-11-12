package com.example.afinal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Domain.FlashCardDomain
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions


class FlashCardAdapter(options: FirebaseRecyclerOptions<FlashCardDomain>) :
    FirebaseRecyclerAdapter<FlashCardDomain, FlashCardAdapter.myviewholder>(options) {
    override fun onBindViewHolder(holder: myviewholder, position: Int, model: FlashCardDomain) {
        holder.eng_lang.text = model.engLanguage
        holder.vn_lang.text = model.vnLanguage
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(com.example.afinal.R.layout.viewholder_flashcard, parent, false)
        return myviewholder(listItem)
    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eng_lang = itemView.findViewById<TextView>(com.example.afinal.R.id.textView_engLang)
        val vn_lang = itemView.findViewById<TextView>(com.example.afinal.R.id.textView_vnLang)
    }
}