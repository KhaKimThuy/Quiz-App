package com.example.afinal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.DAL.UserDAL
import com.example.afinal.Domain.RankingUser
import com.example.afinal.Domain.TopicPublic
import com.example.afinal.Domain.User
import com.example.afinal.R
import com.squareup.picasso.Picasso

class RankingAdapter(private var rankingList: ArrayList<RankingUser>, var topNum : Int) : RecyclerView.Adapter<RankingAdapter.RankingViewholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_ranking, parent, false)
        return RankingViewholder(view)
    }

    override fun onBindViewHolder(holder: RankingViewholder, position: Int) {
        holder.rank.text = (position + topNum).toString()

        holder.score.text = rankingList[position].highestScore.toString()

        val url = rankingList[position].avatarUrl
        if (url != "") {
            Picasso.get().load(url).into(holder.avatar)
        }
    }

    override fun getItemCount(): Int {
        return rankingList.size
    }

    class RankingViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rank = itemView.findViewById<TextView>(R.id.number)
        val avatar = itemView.findViewById<ImageView>(R.id.avatarRank)
        val score = itemView.findViewById<TextView>(R.id.score)
    }
}