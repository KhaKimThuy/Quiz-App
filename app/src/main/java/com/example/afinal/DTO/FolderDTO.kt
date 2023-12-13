package com.example.afinal.DTO

import com.example.afinal.Domain.Folder
import com.example.afinal.Domain.Topic

class FolderDTO {
    companion object {
        // Current specific topic
        var currentFolder: Folder? = null
        var topicList : ArrayList<Topic> = ArrayList<Topic>()
        var numTopics  = "0"
    }
}