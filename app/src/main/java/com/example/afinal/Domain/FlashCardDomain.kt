package com.example.afinal.Domain

import java.io.Serializable
class FlashCardDomain(
    val engLanguage : String,
    val vnLanguage : String,
    val picUrl : String,
    val mark : Boolean
) : Serializable{}