package com.example.afinal.DTO

import androidx.lifecycle.ViewModelProvider
import com.example.afinal.Activity.MainActivity2
import com.example.afinal.Adapter.TopicAdapter
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Domain.TopicRanking
import com.example.afinal.Domain.UserDomain
import com.example.afinal.Fragment.FragmentTopic
import com.example.afinal.ViewModel.TopicViewModel

class TopicDTO {
    companion object {
        // Current specific topic
        var currentTopic: TopicDomain? = null
        var itemList : ArrayList<FlashCardDomain> = ArrayList<FlashCardDomain>()
        var numItems  = "0"

        // User's list of topics
        var topicList : ArrayList<TopicDomain> = ArrayList<TopicDomain>()
        var rankingTopicList : ArrayList<TopicRanking> = ArrayList<TopicRanking>()
    }
}