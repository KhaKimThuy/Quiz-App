package com.example.afinal.Adapter

import android.annotation.SuppressLint
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.marginBottom
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.example.afinal.Activity.FlashCardStudyActivity
import com.example.afinal.Activity.MultiChoiceStudyActivity
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R
import java.util.Locale
import java.util.Random
import java.util.UUID

class MultipleChoiceAdapter(
    activity: MultiChoiceStudyActivity,
    private val cardList: ArrayList<FlashCardDomain>,
) : RecyclerView.Adapter<MultipleChoiceAdapter.MultipleViewHolder>(){

    private var activity = activity
    private val choiceIndices : MutableList<Int> = mutableListOf()
    var rightAnswer : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MultipleViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_mutilplechoice, parent, false)
        return MultipleViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: MultipleViewHolder, @SuppressLint("RecyclerView") position: Int) {
        Log.d("TAG", "Current position = $position")
        holder.question.text = cardList[position].engLanguage

        var nextIdx = position
        if (position < itemCount) {
            nextIdx = position + 1
        }
        generateChoice(holder.mainView, position, nextIdx)
    }

    private fun generateChoice(mainView : LinearLayout, answerIdx : Int, currentIdx : Int) {

        mainView.removeAllViews()
        choiceIndices.clear()

        choiceIndices.add(answerIdx)
        val random = Random()

        var limit = 0

        limit = if (cardList.size < 4) { cardList.size } else { 4 }

        for (i in 0..< limit) {
            val randomIndex = random.nextInt(cardList.size)
            if (randomIndex !in choiceIndices) {
                choiceIndices.add(randomIndex)
            }
        }
        choiceIndices.shuffle()

        for (i in choiceIndices) {
            val textView = TextView(activity.applicationContext)

            val layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,0,0,8)

            textView.setBackgroundResource(R.drawable.choice_bg)
            textView.text = cardList[i].vnLanguage
            textView.setPadding(16, 16, 16, 16)
            textView.textSize = 24f
            textView.layoutParams = layoutParams
            mainView.addView(textView)

            textView.setOnClickListener(View.OnClickListener {
                Log.d("TAG", "dddddddd")
//                selectAnswer = i
                if (i == answerIdx) {
                    rightAnswer += 1
                    activity.showRightToast(currentIdx)
                } else {
                    activity.shoWrongDialog(cardList[answerIdx].engLanguage.toString(),
                        cardList[answerIdx].vnLanguage.toString(),
                        cardList[i].vnLanguage.toString(), currentIdx)
                }
            })
        }
    }


    class MultipleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val mainView: LinearLayout = itemView.findViewById<LinearLayout>(R.id.mainView)
        val question: TextView = itemView.findViewById<TextView>(R.id.tvQuestion)
    }

}