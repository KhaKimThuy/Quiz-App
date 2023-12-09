package com.example.afinal.Activity

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Adapter.MultipleChoiceAdapter
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Dialog.WrongMutipleChoiceDialog
import com.example.afinal.R
import com.example.afinal.databinding.ActivityMultiChoiceStudyBinding

class MultiChoiceStudyActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMultiChoiceStudyBinding
    private lateinit var adapter: MultipleChoiceAdapter
    private lateinit var rightToast : View
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMultiChoiceStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvLoad.text = TopicDTO.numItems
        binding.tvGet.text = "0"

        binding.progressBar.max = TopicDTO.numItems.toInt()

        val toastWrapper = findViewById<ConstraintLayout>(R.id.toastWrapper)
        rightToast = layoutInflater.inflate(R.layout.right_choice_toast, toastWrapper)

//        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)
        binding.recyclerView.layoutManager = object : LinearLayoutManager(this) {
            override fun canScrollHorizontally(): Boolean {
                return super.canScrollHorizontally()
            }
            override fun canScrollVertically(): Boolean {
                return super.canScrollHorizontally()
            }
        }
//        binding.recyclerView.isNestedScrollingEnabled = false
        loadMultipleChoice()
    }

    private fun loadMultipleChoice(){
        adapter = MultipleChoiceAdapter(this, TopicDTO.itemList)
        binding.recyclerView.adapter = adapter
    }

    private fun nextTest(position : Int)
    {
        Log.d("TAG", "Position: $position")
        if (position < (adapter.itemCount)) {
            binding.recyclerView.layoutManager?.scrollToPosition(position)
        } else {
            val result = "Done with " + adapter.rightAnswer + " / " + TopicDTO.numItems
            Toast.makeText(this, result , Toast.LENGTH_SHORT).show()
        }
        val newProgress = binding.progressBar.progress + 1
        binding.progressBar.progress = newProgress
        binding.tvGet.text = position.toString()
    }

    fun showRightToast(position: Int) {
        Toast(this).apply {
            duration = Toast.LENGTH_SHORT
            setGravity(Gravity.CENTER, 0, 0)
            view = rightToast
        }.show()
        nextTest(position)
    }

    fun shoWrongDialog(question : String, answer : String, selection :String, position: Int) {
        val wrongDialog = WrongMutipleChoiceDialog(question, answer, selection)
        wrongDialog.show(supportFragmentManager, "Wrong choice dialog")
        nextTest(position)
    }

}