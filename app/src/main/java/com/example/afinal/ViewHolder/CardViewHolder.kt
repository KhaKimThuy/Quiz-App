package com.example.afinal.ViewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R

class CardViewHolder(view : View): RecyclerView.ViewHolder(view){
    val eng_lang = view.findViewById<TextView>(R.id.textView_engLang)
    val vn_lang = view.findViewById<TextView>(R.id.textView_vnLang)
    fun bind(item: FlashCardDomain) {
        eng_lang.text = item.engLanguage
        vn_lang.text = item.vnLanguage
    }
}