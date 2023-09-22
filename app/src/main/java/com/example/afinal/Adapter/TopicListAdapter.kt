package com.example.afinal.Adapter


import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.GranularRoundedCorners
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R


class TopicListAdapter(
    private val topicList:ArrayList<TopicDomain>,
) : RecyclerView.Adapter<MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val listItem = layoutInflater.inflate(R.layout.viewholder_topic, parent, false)
        return MyViewHolder(listItem)
    }

    override fun getItemCount(): Int {
        return topicList.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.topic_name.text = topicList[position].topicName
        holder.name.text = topicList[position].name
        holder.word_num.text = topicList[position].numWords.toString()
        val drawableResourceId : Int = holder.itemView.resources.getIdentifier(topicList[position].avatarUrl, "drawable", holder.itemView.context.packageName)

        Glide.with(holder.itemView.context)
            .load(drawableResourceId)
            .transform(GranularRoundedCorners(30F, 30F, 0F, 0F))
            .into(holder.avatar)

        holder.itemView.setOnClickListener{
            val intent = Intent(holder.itemView.context, DetailTopicActivity::class.java)
            intent.putExtra("object", topicList[position])
            holder.itemView.context.startActivity(intent)
        }
    }
}
class MyViewHolder(view : View):RecyclerView.ViewHolder(view){
    val topic_name = view.findViewById<TextView>(R.id.textView_topicname)
    val name = view.findViewById<TextView>(R.id.textView_name)
    val word_num = view.findViewById<TextView>(R.id.textView_num)
    val avatar = view.findViewById<ImageView>(R.id.circleImageView_avatar)
}