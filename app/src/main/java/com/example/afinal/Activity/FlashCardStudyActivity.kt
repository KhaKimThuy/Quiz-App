package com.example.afinal.Activity

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Adapter.FCLearningAdapter
import com.example.afinal.DAL.ItemDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.R
import com.example.afinal.databinding.ActivityFlashCardStudyBinding


class FlashCardStudyActivity : AppCompatActivity() {

    private val handler = Handler()
    private val delayDuration = 3000L // Duration in milliseconds
    private var autoScrollRunnable: Runnable? = null

    private var currentPosition = 0
    private lateinit var layoutManager : LinearLayoutManager

    private lateinit var adapter: FCLearningAdapter
    lateinit var binding:ActivityFlashCardStudyBinding
    var auto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        loadFlashCards()

        binding.imgViewQuit.setOnClickListener(View.OnClickListener {
            onDestroy()
        })

        binding.imageViewPlay.setOnClickListener(View.OnClickListener {
            if (auto) {
                binding.imageViewPlay.setImageResource(R.drawable.play_icon)
                stopAutoScroll()
                auto = false

                binding.recyclerView.layoutManager = object : LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return true
                    }
                }

            } else {
                binding.recyclerView.layoutManager = object : LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }
                }

                binding.imageViewPlay.setImageResource(R.drawable.pause)
                startAutoScroll()
                auto = true
            }
        })
    }

    private fun init() {
        binding.tvLoad.text = TopicDTO.numItems
        binding.tvGet.text = "1"
        binding.progressBar.progress += 1

        binding.progressBar.max = TopicDTO.numItems.toInt()
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemPosition > currentPosition) {
                    // User has moved to the next item
                    // Perform the desired action here
                    binding.tvGet.text = (visibleItemPosition + 1).toString()
                    Log.d("TAG", "Not auto scroll in flashcard position : " + visibleItemPosition.toString() + 1)

                    binding.progressBar.progress += 1
                    currentPosition = visibleItemPosition
                }
            }
        })

        autoScrollRunnable = object : Runnable {
            override fun run() {

                Log.d("TAG", "current position : " + currentPosition)
                Log.d("TAG", "item count position : " + adapter.itemCount)
                if (currentPosition < adapter.itemCount) {
//                    binding.recyclerView.smoothScrollToPosition(currentPosition)

                    adapter.autoSpeak() {
                        adapter.flipFC() {
                            currentPosition += 1
                            moveToNext(currentPosition)
                            handler.postDelayed(this, delayDuration)
                        }
                    }

                }
            }
        }
    }

    fun startAutoScroll() {
        currentPosition = layoutManager.findFirstVisibleItemPosition()
        autoScrollRunnable?.let { handler.postDelayed(it, delayDuration) }
    }
    fun stopAutoScroll() {
        autoScrollRunnable?.let { handler.removeCallbacks(it) }
    }

    fun moveToNext(position : Int) {
        if (position < (adapter.itemCount)) {

            Log.d("TAG", "Auto scroll in flashcard")

            binding.recyclerView.layoutManager?.scrollToPosition(position)
            binding.progressBar.progress += 1
            binding.tvGet.text = (position + 1).toString()
        }
    }


    private fun loadFlashCards(){
        //handler = Handler(Looper.myLooper()!!)

        adapter = FCLearningAdapter(this, TopicDTO.itemList, object : FCLearningAdapter.onClickFCLearningListener {


            override fun onClickFCListener(
                itemView: FCLearningAdapter.FCViewHolder,
                position: Int
            ) {
                if (TopicDTO.itemList[position].isMarked) {
                    TopicDTO.itemList[position].isMarked = false
                    itemView.marker.setImageResource(R.drawable.empty_star)
                } else {
                    TopicDTO.itemList[position].isMarked = true
                    itemView.marker.setImageResource(R.drawable.marked_star)
                }
                if (TopicDTO.currentTopic?.isPublic == true) {
                    ItemDAL().UpdateMarkedRankingItem(TopicDTO.itemList[position])
                } else {
                    ItemDAL().UpdateMarkedItem(TopicDTO.itemList[position])
                }
            }

        })
        Log.d("TAG", "Num item in Flashcard study : " + TopicDTO.itemList.size)
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