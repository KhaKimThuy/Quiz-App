package com.example.afinal.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelStoreOwner
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.TopicDomain

class TopicViewModel : ViewModel() {
    private var listTopicLiveData: MutableLiveData<List<TopicDomain>>

    init {
        listTopicLiveData = MutableLiveData<List<TopicDomain>>()
        initData()
    }

    private fun initData() {
        val userId = MyDB().dbAuth.currentUser?.uid.toString()
        TopicDAL().GetTopicOfUser(userId) {
            listTopicLiveData.value = TopicDTO.topicList
        }
    }

    fun getListTopicLiveData() : MutableLiveData<List<TopicDomain>> {
        return listTopicLiveData
    }

    fun addTopic(topic : TopicDomain) {
        TopicDTO.topicList.add(topic)
        listTopicLiveData.value = TopicDTO.topicList
    }

    fun removeTopic(topic : TopicDomain) {
        TopicDTO.topicList.remove(topic)
        listTopicLiveData.value = TopicDTO.topicList
    }

    fun updateTopic(oldTopic : TopicDomain, updateTopic : TopicDomain) {
        val index: Int = TopicDTO.topicList.indexOf(oldTopic)
        TopicDTO.topicList[index] = updateTopic
        listTopicLiveData.value = TopicDTO.topicList
    }
}