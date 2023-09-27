package com.example.afinal.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.example.afinal.Adapter.FCLearningAdapter
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.R


class FlashCardStudyActivity : AppCompatActivity() {

    private lateinit var viewPager2: ViewPager2
    private lateinit var handler: Handler
    private lateinit var cardList: ArrayList<FlashCardDomain>
    private lateinit var adapter: FCLearningAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_flash_card_study)
        init()
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
        viewPager2.currentItem = viewPager2.currentItem + 1
    }

    private fun setUpTransformer(){
        val transformer = CompositePageTransformer()
        transformer.addTransformer(MarginPageTransformer(40))
        transformer.addTransformer { page, position ->
            val r = 1 - kotlin.math.abs(position)
            page.scaleY = 0.85f + r * 0.14f
        }

        viewPager2.setPageTransformer(transformer)
    }
    private fun init(){
        viewPager2 = findViewById(R.id.viewPaper2)
        handler = Handler(Looper.myLooper()!!)
        cardList = ArrayList()

        cardList.add(FlashCardDomain("Apple", "Trái táo", "apple", false))
        cardList.add(FlashCardDomain("Banana", "Trái chuối", "banana", false))
        cardList.add(FlashCardDomain("Strawberry", "Trái dâu", "strawberry", false))
        cardList.add(FlashCardDomain("Persimmon", "Trái hồng", "persimmon", false))
        cardList.add(FlashCardDomain("Guava", "Trái ổi", "guava", false))
        cardList.add(FlashCardDomain("Rambutant", "Trái chôm chôm", "rambutant", false))
        cardList.add(FlashCardDomain("Passionfruit", "Trái chanh dây", "passfruit", false))
        cardList.add(FlashCardDomain("Lychee", "Trái vải", "lychee", false))
        cardList.add(FlashCardDomain("Plum", "Trái mận", "plum", false))

        adapter = FCLearningAdapter(cardList, viewPager2)

        viewPager2.adapter = adapter

        viewPager2.offscreenPageLimit = 3
        viewPager2.clipChildren = false
        viewPager2.clipToPadding = false

        viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER
    }
}