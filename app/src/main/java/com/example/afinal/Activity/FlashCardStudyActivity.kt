package com.example.afinal.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.afinal.Adapter.FCLearningAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.databinding.ActivityFlashCardStudyBinding


class FlashCardStudyActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var adapter: FCLearningAdapter
    private lateinit var binding:ActivityFlashCardStudyBinding
    private lateinit var db : MyDB
    lateinit var itemList : ArrayList<FlashCardDomain>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyDB()
        itemList = ArrayList<FlashCardDomain>()

        val topicPK = intent.getStringExtra("topicPK")
        if (topicPK != null) {
            db.GetListItemOfTopic(this, topicPK)
        }
//        val bundle = intent.extras
//        if (bundle != null) {
//            //itemList = bundle.getParcelableArrayList<FlashCardDomain>("itemList")!!
//            //itemList = bundle.getPar("itemList")!!
//            itemList = this.intent.extras?.getParcelableArrayList<FlashCardDomain>("itemList")!!
//            Log.d("TAG", "list size: " + itemList.size)
//            for (i in itemList) {
//                if (i == null) {
//                    Log.d("TAG", "Eng: null")
//                }else{
//                    Log.d("TAG", "Eng: " + i.engLanguage)
//                }
//            }
//        }
//        itemList = intent.getParcelable"itemList")!! as ArrayList<FlashCardDomain>

        loadFlashCards()


        binding.imageViewQuit.setOnClickListener(View.OnClickListener {
            onDestroy()
        })
        //setUpTransformer()

//        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
//            override fun onPageSelected(position: Int) {
//                super.onPageSelected(position)
//                handler.removeCallbacks(runnable)
//                handler.postDelayed(runnable , 2000)
//            }
//        })
    }

    override fun onPause() {
        super.onPause()
        handler.removeCallbacks(runnable)
    }

    override fun onResume() {
        super.onResume()

        handler.postDelayed(runnable , 2000)
    }

    private val runnable = Runnable {
        binding.viewPaper2.currentItem = binding.viewPaper2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        binding.viewPaper2.setPageTransformer(transformer)
    }
    private fun loadFlashCards(){
        handler = Handler(Looper.myLooper()!!)
        adapter = FCLearningAdapter(itemList, binding.viewPaper2)

        binding.viewPaper2.adapter = adapter

        binding.viewPaper2.offscreenPageLimit = 3
        binding.viewPaper2.clipChildren = false
        binding.viewPaper2.clipToPadding = false

        binding.viewPaper2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}