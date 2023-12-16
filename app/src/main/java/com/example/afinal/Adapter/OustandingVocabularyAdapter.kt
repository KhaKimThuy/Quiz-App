package com.example.afinal.Adapter

import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.DAL.ItemDAL
import com.example.afinal.Domain.Item
import com.example.afinal.R
import java.util.Locale
import java.util.UUID

class OustandingVocabularyAdapter(private val itemList: ArrayList<Item>, private val ownerActivity: AppCompatActivity) : RecyclerView.Adapter<OustandingVocabularyAdapter.ItemViewHolder>() {

    private var textToSpeech: TextToSpeech
    private var ready: Boolean = false

    init {
        textToSpeech = TextToSpeech(ownerActivity.applicationContext) { setTextToSpeechLanguage() }
    }

    private fun speakOut(engVocab: String) {
        Log.d("TAG", "Language valid: $ready")
        if (!ready) {
            return
        }

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
            Log.d("TAG", "Language error 1")
            return
        }
        val result: Int = textToSpeech.setLanguage(language)
        if (result == TextToSpeech.LANG_MISSING_DATA) {
            ready = false
            Log.d("TAG", "Language error 2")
            return
        } else if (result == TextToSpeech.LANG_NOT_SUPPORTED) {
            ready = false
            Log.d("TAG", "Language error 3")
            return
        } else {
            ready = true
            Log.d("TAG", "Language error 4")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.item_oustanding_vocabulary, parent, false)
        return ItemViewHolder(view)
    }

    override fun getItemCount(): Int {
        return itemList.size
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        holder.eng_lang?.text = itemList[position].engLanguage
        holder.vn_lang?.text = itemList[position].vnLanguage
        holder.state?.text = itemList[position].state
        holder.speaker?.setOnClickListener {
            speakOut(holder.eng_lang?.text.toString())
        }
        holder.marker?.setOnClickListener {
            if (itemList[position].isMarked) {
                itemList[position].isMarked = false
                holder.marker?.setImageResource(R.drawable.empty_star)
            } else {
                itemList[position].isMarked = true
                holder.marker?.setImageResource(R.drawable.marked_star)
            }

            // Change item info on the database
//            ItemDAL().updateItem(itemList[position])
        }
    }

    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val eng_lang = itemView.findViewById<TextView?>(R.id.textView_engLang)
        val vn_lang = itemView.findViewById<TextView?>(R.id.textView_vnLang)
        val state = itemView.findViewById<TextView?>(R.id.tvState)
        val speaker = itemView.findViewById<ImageView?>(R.id.imageView_speaker)
        val marker = itemView.findViewById<ImageView?>(R.id.imageView_marker)
    }
}
