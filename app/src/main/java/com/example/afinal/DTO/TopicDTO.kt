package com.example.afinal.DTO

import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Domain.UserDomain

class TopicDTO {
    companion object {
        // Current specific topic
        var currentTopic: TopicDomain? = null
        var itemList : ArrayList<FlashCardDomain> = ArrayList<FlashCardDomain>()
        var numItems  = "0"

        // User's list of topics
        var topicList : ArrayList<TopicDomain> = ArrayList<TopicDomain>()
    }
}