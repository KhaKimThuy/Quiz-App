package com.example.afinal.Adapter

import android.os.Handler
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.FlashCardStudyActivity
import com.example.afinal.Domain.Item
import com.example.afinal.R
import java.util.Locale
import java.util.UUID

class FCLearningAdapter(
    activity: FlashCardStudyActivity,
    private val cardList: ArrayList<Item>
) : RecyclerView.Adapter<FCLearningAdapter.FCViewHolder>(){

    private var textToSpeech : TextToSpeech
    private var ready : Boolean = false
    private var activity = activity




    init {
        textToSpeech = TextToSpeech(activity.applicationContext) { setTextToSpeechLanguage() }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FCViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_flashcard_learning, parent, false)
        return FCViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: FCViewHolder, position: Int) {
        holder.eng_lang.text = cardList[position].engLanguage
        holder.vn_lang.text = cardList[position].vnLanguage

        // Auto
        speakOut(holder.eng_lang.text.toString())
        holder.speaker.setOnClickListener(View.OnClickListener {
            speakOut(holder.eng_lang.text.toString())
        })

//        if (activity.auto) {
//            // Cancel any existing event runnable
//            eventRunnable?.let { handler.removeCallbacks(it) }
//            // Schedule a new event runnable
//            eventRunnable = Runnable {
//                // Perform the desired event after the duration
//                var nextIdx = position
//                if (position < itemCount) {
//                    nextIdx = position + 1
//                }
//                activity.moveToNext(nextIdx)
//            }
//            handler.postDelayed(eventRunnable!!, delayDuration)
//        }
    }

//    private val runnable = Runnable {
//        cardList.addAll(cardList)
//        notifyDataSetChanged()
//    }

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

    class FCViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val eng_lang = itemView.findViewById<TextView>(R.id.textView_engLang)
        val vn_lang = itemView.findViewById<TextView>(R.id.textView_vnLang)
        val speaker = itemView.findViewById<ImageView>(R.id.imageView_speaker)
    }

}
