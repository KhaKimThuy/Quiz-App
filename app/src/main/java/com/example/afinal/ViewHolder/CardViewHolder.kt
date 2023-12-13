package com.example.afinal.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Domain.Item
import com.example.afinal.R

class CardViewHolder(view : View): RecyclerView.ViewHolder(view){
    val eng_lang = view.findViewById<TextView>(R.id.textView_engLang)
    val vn_lang = view.findViewById<TextView>(R.id.textView_vnLang)
    fun bind(item: Item) {
        eng_lang.text = item.engLanguage
        vn_lang.text = item.vnLanguage
    }
}