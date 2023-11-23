package com.example.afinal.Dialog

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import com.example.afinal.Activity.MultiChoiceStudyActivity
import com.example.afinal.R

class WrongMutipleChoiceDialog(
    question: String,
    answer: String,
    selection: String) : AppCompatDialogFragment() {
    private lateinit var questionTV : TextView
    private lateinit var answerTV : TextView
    private lateinit var selectionTV : TextView
    private lateinit var continueBtn : Button

    private val question = question
    private val answer = answer
    private val selection = selection



    @SuppressLint("MissingInflatedId")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val builder = AlertDialog.Builder(requireActivity())
        val inflater = requireActivity().layoutInflater
        val view = inflater.inflate(R.layout.wrong_multiplechoice_dialog, null)
        builder.setView(view).setPositiveButton("") { dialog, _ ->
            dialog.dismiss()
        }



        questionTV = view.findViewById(R.id.tvWrongQuestion)
        answerTV = view.findViewById(R.id.tvAnswer)
        selectionTV = view.findViewById(R.id.tvSelection)
        continueBtn = view.findViewById(R.id.btnContinue)


        questionTV.text = question
        answerTV.text = answer
        selectionTV.text = selection

        val dialog = builder.create()
        continueBtn.setOnClickListener(View.OnClickListener {
            dialog.getButton(DialogInterface.BUTTON_POSITIVE).performClick()
        })

        return dialog
    }

}