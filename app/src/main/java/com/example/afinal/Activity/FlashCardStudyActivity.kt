package com.example.afinal.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.afinal.Adapter.FCLearningAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityFlashCardStudyBinding


class FlashCardStudyActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var adapter: FCLearningAdapter
    lateinit var binding:ActivityFlashCardStudyBinding
    private lateinit var db : MyDB
    private var itemList : ArrayList<FlashCardDomain> = ArrayList<FlashCardDomain>()
    private var loadNumber : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyDB()
        init()

        loadFlashCards()

        binding.imgViewQuit.setOnClickListener(View.OnClickListener {
            onDestroy()
        })
    }

    private fun init(){
        itemList = TopicDTO.itemList
        binding.tvLoad.text = TopicDTO.numItems
        binding.tvGet.text = loadNumber.toString()

        binding.progressBar.max = TopicDTO.numItems.toInt()
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

    fun moveToNext(position : Int) {
        if (position < (adapter.itemCount)) {
            binding.recyclerView.layoutManager?.scrollToPosition(position)
        }
    }


    private fun loadFlashCards(){
        handler = Handler(Looper.myLooper()!!)
        adapter = FCLearningAdapter(this, itemList)
        binding.recyclerView.adapter = adapter
    }


    override fun onPause() {
        super.onPause()
//        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

//        handler.postDelayed(runnable , 2000)
    }

//    private val runnable = Runnable {
//        binding.viewPaper2.currentItem = binding.viewPaper2.currentItem + 1
//    }
//
//    private fun loadFlashCards(){
//        handler = Handler(Looper.myLooper()!!)
//        adapter = FCLearningAdapter(this, itemList, binding.viewPaper2)
//
//        binding.viewPaper2.adapter = adapter
//        binding.viewPaper2.offscreenPageLimit = 3
//        binding.viewPaper2.clipChildren = false
//        binding.viewPaper2.clipToPadding = false
//
//        binding.viewPaper2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
//    }
//
//    override fun onDestroy() {
//        super.onDestroy()
//    }

}