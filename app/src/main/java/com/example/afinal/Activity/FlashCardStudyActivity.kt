package com.example.afinal.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Adapter.FCLearningAdapter
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.databinding.ActivityFlashCardStudyBinding


class FlashCardStudyActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var adapter: FCLearningAdapter
    lateinit var binding:ActivityFlashCardStudyBinding
    private var loadNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        loadFlashCards()

        binding.imgViewQuit.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun init() {
        binding.tvLoad.text = TopicDTO.numItems
        binding.tvGet.text = loadNumber.toString()

        binding.progressBar.max = TopicDTO.numItems.toInt()
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.layoutManager = object : LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
    }

    fun moveToNext(position : Int) {
        if (position < (adapter.itemCount)) {
            binding.recyclerView.layoutManager?.scrollToPosition(position)
        }
    }


    private fun loadFlashCards(){
        handler = Handler(Looper.myLooper()!!)
        adapter = FCLearningAdapter(this, TopicDTO.itemList)
        binding.recyclerView.adapter = adapter
    }


}