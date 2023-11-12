package com.example.afinal.Adapter

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R
import com.example.afinal.ViewHolder.AddItemTopicHolder
class AddItemTopicAdapter (
    private val itemList:ArrayList<FlashCardDomain>
) : RecyclerView.Adapter<AddItemTopicHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddItemTopicHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_addtopic, parent, false)
        return AddItemTopicHolder(view)
    }

    override fun onBindViewHolder(holder: AddItemTopicHolder, @SuppressLint("RecyclerView") position: Int) {

        // Edit topic activity
        if (!itemList[position].engLanguage.isNullOrEmpty() &&
            !itemList[position].vnLanguage.isNullOrEmpty()) {
            holder.eng_lang.text = itemList[position].engLanguage
            holder.vn_lang.text = itemList[position].vnLanguage
        }

        // Create new item
        holder.eng_lang.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemList[position].engLanguage = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemList[position].engLanguage = p0.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                itemList[position].engLanguage = s.toString()
            }
        })

        holder.vn_lang.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemList[position].vnLanguage = p0.toString()
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                itemList[position].vnLanguage = p0.toString()
            }

            override fun afterTextChanged(s: Editable?) {
                itemList[position].vnLanguage = s.toString()
            }
        })


    }

    override fun getItemCount(): Int {
        return itemList.size
    }

}


