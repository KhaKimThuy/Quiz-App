package com.example.afinal.ViewHolder

import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R

class AddItemTopicHolder(view : View)
    : RecyclerView.ViewHolder(view) {
    val eng_lang = view.findViewById<TextView>(R.id.edt_eng)
    val vn_lang = view.findViewById<TextView>(R.id.edt_vn)
    init {
        eng_lang.text = ""
        vn_lang.text = ""
    }

}