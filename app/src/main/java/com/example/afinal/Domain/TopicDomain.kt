package com.example.afinal.Domain

import java.io.Serializable

class TopicDomain(
    val topicName : String,
    val name : String,
    val avatarUrl : String,
    val numWords : Int,
) : Serializable{}