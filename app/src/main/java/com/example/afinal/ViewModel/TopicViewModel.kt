package com.example.afinal.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.Topic
import com.example.afinal.Domain.TopicRanking

class TopicViewModel : ViewModel() {
    private var listTopicLiveData: MutableLiveData<List<Topic>>

    init {
        listTopicLiveData = MutableLiveData<List<Topic>>()
        initData()
    }

    private fun initData() {
        Log.d("ModelView", "Create new model view")
        val userId = MyDB().dbAuth.currentUser?.uid.toString()
        TopicDAL().GetTopicOfUser(userId) {
            TopicDTO.topicList.clear()
            TopicDTO.topicList.addAll(it)
            listTopicLiveData.value = TopicDTO.topicList
        }
    }

    fun refresh() {
        UserDTO.currentUser?.let {
            TopicDAL().GetTopicOfUser(it.userPK) {
                TopicDTO.topicList.clear()
                TopicDTO.topicList.addAll(it)
                listTopicLiveData.value = TopicDTO.topicList
            }
        }
    }

    fun getListTopicLiveData() : MutableLiveData<List<Topic>> {
        return listTopicLiveData
    }

    fun addTopic(topic : Topic) {
        TopicDTO.topicList.add(topic)
        listTopicLiveData.value = TopicDTO.topicList
    }

    fun removeTopic(topic : Topic) {
        TopicDTO.topicList.remove(topic)
        listTopicLiveData.value = TopicDTO.topicList
    }

    fun updateTopic(oldTopic : Topic, updateTopic : Topic) {
        val index: Int = TopicDTO.topicList.indexOf(oldTopic)
        TopicDTO.topicList[index] = updateTopic
        listTopicLiveData.value = TopicDTO.topicList
    }
}