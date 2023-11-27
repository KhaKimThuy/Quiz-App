package com.example.afinal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.DB.UserDAL
import com.example.afinal.Domain.TopicPublicDomain
import com.example.afinal.Domain.UserDomain
import com.example.afinal.R
import com.squareup.picasso.Picasso

class RankingAdapter(private var topicList: ArrayList<TopicPublicDomain>,
                     private var userList: ArrayList<UserDomain>) : RecyclerView.Adapter<RankingAdapter.RankingViewholder>() {

    private var userDAL = UserDAL()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RankingViewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view: View = layoutInflater.inflate(R.layout.item_ranking, parent, false)
        return RankingViewholder(view)
    }

    override fun onBindViewHolder(holder: RankingViewholder, position: Int) {
        holder.rank.text = (position + 4).toString()

        holder.score.text = topicList[position].highestScore.toString()

        val url = userList[position].avatarUrl
        if (url != "") {
            Picasso.get().load(url).into(holder.avatar)
        }
    }

    override fun getItemCount(): Int {
        return userList.size
    }

    class RankingViewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val rank = itemView.findViewById<TextView>(com.example.afinal.R.id.number)
        val avatar = itemView.findViewById<ImageView>(com.example.afinal.R.id.avatarRank)
        val score = itemView.findViewById<TextView>(com.example.afinal.R.id.score)
    }
}