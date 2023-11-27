package com.example.afinal.Adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Activity.MultiChoiceStudyActivity
import com.example.afinal.Activity.TypeVocabStudyActivity
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R
import java.util.Random

class TypeWordAdapter(
    activity: TypeVocabStudyActivity,
    private val cardList: ArrayList<FlashCardDomain>,
) : RecyclerView.Adapter<TypeWordAdapter.TypeWordViewHolder>(){

    private var activity = activity
    private var currentItemPosition = 0
    private val choiceIndices : MutableList<Int> = mutableListOf()
    var rightAnswer : Int = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TypeWordViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val view = layoutInflater.inflate(R.layout.viewholder_type_word, parent, false)
        return TypeWordViewHolder(view)
    }

    override fun getItemCount(): Int {
        return cardList.size
    }

    override fun onBindViewHolder(holder: TypeWordViewHolder, @SuppressLint("RecyclerView") position: Int) {
        holder.question.text = cardList[position].engLanguage
        var nextIdx = position
        if (position < itemCount) {
            nextIdx = position + 1
        }
        holder.userAnswer.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE || actionId == EditorInfo.IME_ACTION_NEXT) {
                val userAnswer = holder.userAnswer.text.toString().trim().toLowerCase()
                checkAnswer(position, userAnswer, nextIdx)
                false
            } else {
                false
            }
        }
    }

    private fun checkAnswer(answerIdx : Int, userAnswer : String, currentIdx : Int) {

        Log.d("TAG", "Type position: $currentItemPosition")


        if (currentItemPosition < itemCount) {
            currentItemPosition += 1
        }


        val answer = cardList[answerIdx].vnLanguage?.toLowerCase()
        if (answer.equals(userAnswer)) {
            activity.showRightToast(currentIdx)
        } else {
            activity.shoWrongDialog(cardList[answerIdx].engLanguage.toString(),
                cardList[answerIdx].vnLanguage.toString(),
                userAnswer, currentIdx)
        }
    }

    class TypeWordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        val question: TextView = itemView.findViewById<TextView>(R.id.tvTypeQuestion)
        val userAnswer: EditText = itemView.findViewById<EditText>(R.id.edtTypeAnswer)
    }

}