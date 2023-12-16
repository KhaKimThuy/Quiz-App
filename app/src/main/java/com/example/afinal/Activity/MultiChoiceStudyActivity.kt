package com.example.afinal.Activity

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Adapter.MultipleChoiceAdapter
import com.example.afinal.DAL.TopicDAL
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

        init()

        binding.imageViewBack.setOnClickListener(View.OnClickListener {
            finish()
        })
//        binding.recyclerView.isNestedScrollingEnabled = false
    }

    private fun init() {

        loadMultipleChoice()

        binding.tvGet.text = "0"

        val toastWrapper = findViewById<ConstraintLayout>(R.id.toastWrapper)
        rightToast = layoutInflater.inflate(R.layout.right_choice_toast, toastWrapper)

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
    }

    private fun loadMultipleChoice(){
        showOptionsDialog()
    }

    private fun showOptionsDialog() {
        val options = arrayOf("Học tất cả", "Chỉ học từ vựng nổi bật")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Choose an option")
            .setItems(options) { dialogInterface: DialogInterface, optionIndex: Int ->
                // Handle option selection
                when (optionIndex) {
                    0 -> {
                        adapter = MultipleChoiceAdapter(this, TopicDTO.allItemList)
                        binding.recyclerView.adapter = adapter
                        binding.tvLoad.text = TopicDTO.allItemList.size.toString()
                        binding.progressBar.max = TopicDTO.allItemList.size
                        TopicDTO.numItems = TopicDTO.allItemList.size.toString()

                    }
                    1 -> {
                        for (topic in TopicDTO.allItemList) {
                            if (topic.isMarked) {
                                TopicDTO.itemList.add(topic)
                            }
                        }
                        adapter = MultipleChoiceAdapter(this, TopicDTO.itemList)
                        binding.recyclerView.adapter = adapter
                        binding.tvLoad.text = TopicDTO.itemList.size.toString()
                        binding.progressBar.max = TopicDTO.itemList.size
                        TopicDTO.numItems = TopicDTO.itemList.size.toString()

                        // Option 2 selected
                        // Perform desired action
                    }
                }
                dialogInterface.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    private fun nextTest(position : Int)
    {
        Log.d("TAG", "Position: $position")
        if (position < (adapter.itemCount)) {
            binding.recyclerView.layoutManager?.scrollToPosition(position)
        } else {
            val result = "Bạn đã trả lời đúng " + adapter.rightAnswer + " / " + TopicDTO.numItems + " câu hỏi"
            val intent = Intent(this, EndTestActivity::class.java)

            // Update highest score of current topic
            val score = (adapter.rightAnswer.toDouble() / TopicDTO.allItemList.size.toDouble()) * 10.0
            Log.d("score", "Right score = " + adapter.rightAnswer)
            Log.d("score", "All score = " + TopicDTO.allItemList.size)
            Log.d("score", "Total score = " + score)
            TopicDTO.currentTopic?.let { TopicDAL().UpdateTopicScore(it, score) }

            intent.putExtra("result", result)
            startActivity(intent)
            finish()
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