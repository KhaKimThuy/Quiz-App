package com.example.afinal.DTO

import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain

class FolderDTO {
    companion object {
        // Current specific topic
        var currentFolder: FolderDomain? = null
        var topicList : ArrayList<TopicDomain> = ArrayList<TopicDomain>()
        var numTopics  = "0"
    }
}