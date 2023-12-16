package com.example.afinal.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Adapter.OustandingVocabularyAdapter
import com.example.afinal.DAL.ItemDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.databinding.ActivityOustandingVocabularyBinding

class OustandingVocabularyActivity : AppCompatActivity() {

    private lateinit var handler: Handler
    private lateinit var adapter: OustandingVocabularyAdapter
    lateinit var binding: ActivityOustandingVocabularyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOustandingVocabularyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        loadFlashCards()

        binding.imgBack.setOnClickListener {
            finish()
        }
    }

    private fun init() {
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.listVocal)

        binding.listVocal.layoutManager = object : LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
            override fun canScrollHorizontally(): Boolean {
                return false
            }
        }
    }

    private fun loadFlashCards() {
        handler = Handler(Looper.myLooper()!!)
        adapter = OustandingVocabularyAdapter(TopicDTO.itemList, this)
        binding.listVocal.adapter = adapter
    }
}
