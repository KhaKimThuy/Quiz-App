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
import com.example.afinal.R
import com.example.afinal.databinding.ActivityFlashCardStudyBinding


class FlashCardStudyActivity : AppCompatActivity() {

    private val handler = Handler()
    private val delayDuration = 5000L // Duration in milliseconds
    private var eventRunnable: Runnable? = null

    private lateinit var adapter: FCLearningAdapter
    lateinit var binding:ActivityFlashCardStudyBinding
    private var loadNumber : Int = 0
    var auto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        loadFlashCards()

        binding.imgViewQuit.setOnClickListener(View.OnClickListener {
            finish()
        })

        binding.imageViewPlay.setOnClickListener(View.OnClickListener {
            if (auto) {
                binding.imageViewPlay.setImageResource(R.drawable.play_icon)
                auto = false
            } else {
                binding.imageViewPlay.setImageResource(R.drawable.pause)
                auto = true
            }
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
        //handler = Handler(Looper.myLooper()!!)



        adapter = FCLearningAdapter(this, TopicDTO.itemList)
        binding.recyclerView.adapter = adapter


//        // Cancel any existing event runnable
//        eventRunnable?.let { handler.removeCallbacks(it) }
//        // Schedule a new event runnable
//        eventRunnable = Runnable {
//            // Perform the desired event after the duration
//            var nextIdx = position
//            if (position < itemCount) {
//                nextIdx = position + 1
//            }
//            activity.moveToNext(nextIdx)
//        }
//        handler.postDelayed(eventRunnable!!, delayDuration)

    }


}