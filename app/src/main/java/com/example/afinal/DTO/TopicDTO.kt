package com.example.afinal.DTO

import com.example.afinal.Domain.Item
import com.example.afinal.Domain.Topic
import com.example.afinal.Domain.TopicRanking

class TopicDTO {
    companion object {
        // Current specific topic
        var currentTopic: Topic? = null
        var itemList : ArrayList<Item> = ArrayList<Item>()
        var numItems  = "0"

        // User's list of topics
        var topicList : ArrayList<Topic> = ArrayList<Topic>()
        var rankingTopicList : ArrayList<TopicRanking> = ArrayList<TopicRanking>()
    }
}