package com.example.afinal.Activity

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Adapter.OustandingVocabularyAdapter
import com.example.afinal.DAL.ItemDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.databinding.ActivityOustandingVocabularyBinding

class OustandingVocabularyActivity : AppCompatActivity() {

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

        binding.listVocal.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    }

    private fun loadFlashCards() {
        ItemDAL().GetOutstandingItem {
            Log.d("Out", "Size = " + it.size)
            if (it.size > 0) {
                binding.empty.visibility = View.GONE
                adapter = OustandingVocabularyAdapter(it, this)
                binding.listVocal.adapter = adapter
            } else {
                binding.empty.visibility = View.VISIBLE
            }
        }
    }
}
