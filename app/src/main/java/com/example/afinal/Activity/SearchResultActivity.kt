package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.R

class SearchResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_result)

        initRecyclerView()
    }
    private fun initRecyclerView(){
        val topicList = ArrayList<TopicDomain>()

        topicList.add(TopicDomain("Food and drink", "James", "cat", 35))
        topicList.add(TopicDomain("Water", "John", "cat", 15))
        topicList.add(TopicDomain("Fast food", "Mary", "cat", 50))
        topicList.add(TopicDomain("Machine", "Josh", "cat", 11))
        topicList.add(TopicDomain("Engineer", "Mat", "cat", 90))
        topicList.add(TopicDomain("Science", "Anh Vo", "cat", 150))
        topicList.add(TopicDomain("Environment", "Watson", "cat", 75))
        topicList.add(TopicDomain("Politic", "Emma", "cat", 58))
        topicList.add(TopicDomain("Vehicle", "William", "cat", 87))

        val recyclerViewTopic = findViewById<RecyclerView>(R.id.recyclerview_topic)
        recyclerViewTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        val adapterTopicList = TopicListAdapter(topicList)
        recyclerViewTopic.adapter = adapterTopicList
    }
}