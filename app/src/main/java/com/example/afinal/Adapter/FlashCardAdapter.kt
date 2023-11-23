package com.example.afinal.Adapter

import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Domain.FlashCardDomain
import com.firebase.ui.database.FirebaseRecyclerAdapter
import com.firebase.ui.database.FirebaseRecyclerOptions
import java.util.Locale
import java.util.UUID


class FlashCardAdapter(activity: DetailTopicActivity, options: FirebaseRecyclerOptions<FlashCardDomain>) :
    FirebaseRecyclerAdapter<FlashCardDomain, FlashCardAdapter.myviewholder>(options) {
    private var textToSpeech : TextToSpeech
    private var ready : Boolean = false

    init {
        textToSpeech = TextToSpeech(activity.applicationContext) { setTextToSpeechLanguage() }
    }


    override fun onBindViewHolder(holder: myviewholder, position: Int, model: FlashCardDomain) {
        holder.eng_lang.text = model.engLanguage
        holder.vn_lang.text = model.vnLanguage
        holder.state.text = model.state
        holder.speaker.setOnClickListener(View.OnClickListener {
            speakOut(holder.eng_lang.text.toString())
        })
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): myviewholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(com.example.afinal.R.layout.viewholder_flashcard, parent, false)
        return myviewholder(view)
    }

    private fun speakOut(engVocab : String) {
        Log.d("TAG", "Language valid: $ready")
        if (!ready) {
            return
        }

        // Văn bản cần đọc.
        val utteranceId = UUID.randomUUID().toString()
        textToSpeech.speak(engVocab, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
    }

    private fun getUserSelectedLanguage(): Locale? {
        return Locale.ENGLISH
    }

    private fun setTextToSpeechLanguage() {
        val language = getUserSelectedLanguage()
        if (language == null) {
            ready = false
            Log.d("TAG" , "Language error 1")
            return
        }
        val result: Int = textToSpeech.setLanguage(language)
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            ready = false
            Log.d("TAG" , "Language error 2")
            return
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            ready = false
            Log.d("TAG" , "Language error 3")
            return
        } else {
            ready = true
//            val currentLanguage: Locale = textToSpeech.voice.locale
            Log.d("TAG" , "Language error 4")
        }
    }

    inner class myviewholder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eng_lang = itemView.findViewById<TextView>(com.example.afinal.R.id.textView_engLang)
        val vn_lang = itemView.findViewById<TextView>(com.example.afinal.R.id.textView_vnLang)
        val state = itemView.findViewById<TextView>(com.example.afinal.R.id.tvState)
        val speaker = itemView.findViewById<ImageView>(com.example.afinal.R.id.imageView_speaker)
    }
}