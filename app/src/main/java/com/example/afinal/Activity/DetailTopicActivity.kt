package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Adapter.FlashCardListAdapter
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R
import android.annotation.SuppressLint

class DetailTopicActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_topic)

        initRecyclerView()

    }
    private fun initRecyclerView(){
        val cardList = ArrayList<FlashCardDomain>()

        cardList.add(FlashCardDomain("Apple", "Trái táo", "apple", false))
        cardList.add(FlashCardDomain("Banana", "Trái chuối", "banana", false))
        cardList.add(FlashCardDomain("Strawberry", "Trái dâu", "strawberry", false))
        cardList.add(FlashCardDomain("Persimmon", "Trái hồng", "persimmon", false))
        cardList.add(FlashCardDomain("Guava", "Trái ổi", "guava", false))
        cardList.add(FlashCardDomain("Rambutant", "Trái chôm chôm", "rambutant", false))
        cardList.add(FlashCardDomain("Passionfruit", "Trái chanh dây", "passfruit", false))
        cardList.add(FlashCardDomain("Lychee", "Trái vải", "lychee", false))
        cardList.add(FlashCardDomain("Plum", "Trái mận", "plum", false))

        val recyclerViewTopic = findViewById<RecyclerView>(R.id.recyclerview_flashcard)
        recyclerViewTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapterTopicList = FlashCardListAdapter(cardList)
        recyclerViewTopic.adapter = adapterTopicList
    }
}