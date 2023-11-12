package com.example.afinal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions

class FCLearningAdapter(
    private val cardList: ArrayList<FlashCardDomain>,
    private val viewPaper: ViewPager2
) : RecyclerView.Adapter<FCLearningAdapter.FCViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FCViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_flashcard_learning, parent, false)
        return FCViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: FCViewHolder, position: Int) {
        holder.eng_lang.text = cardList[position].engLanguage
        holder.vn_lang.text = cardList[position].vnLanguage

        if (position == cardList.size-1){
            viewPaper.post(runnable)
        }
    }

    private val runnable = Runnable {
        cardList.addAll(cardList)
        notifyDataSetChanged()
    }

    class FCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val eng_lang = itemView.findViewById<TextView>(R.id.textView_engLang)
        val vn_lang = itemView.findViewById<TextView>(R.id.textView_vnLang)
    }

}
