package com.example.afinal.Adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.example.afinal.R
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderAdapter(private val imageurl: ArrayList<Int>) :
    SliderViewAdapter<SliderAdapter.SliderViewHolder>() {

    override fun getCount(): Int {
        return imageurl.size
    }

    override fun onCreateViewHolder(parent: ViewGroup): SliderViewHolder {
        val inflater: View = LayoutInflater.from(parent.context).inflate(R.layout.item_photos_start, parent, false)
        return SliderViewHolder(inflater)
    }

    override fun onBindViewHolder(viewHolder: SliderViewHolder, position: Int) {
        val imageUrl = imageurl[position]
        Glide.with(viewHolder.itemView.context).load(imageUrl).fitCenter().into(viewHolder.imageView)
    }

    class SliderViewHolder(itemView: View) : SliderViewAdapter.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.myimage)
    }
}