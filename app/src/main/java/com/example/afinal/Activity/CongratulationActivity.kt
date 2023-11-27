package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.R
import com.example.afinal.databinding.ActivityCongratulationBinding
import com.example.afinal.databinding.ActivityFlashCardStudyBinding

class CongratulationActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCongratulationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCongratulationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadStatistic()
    }

    private fun loadStatistic() {
        var s1 = 0
        var s2 = 0
        var s3 = 0
        for (i in TopicDTO.itemList) {
            if (i.state == "Đã thành thạo") {
                s1 += 1
            }
            else if (i.state == "Đang được học") {
                s2 += 1
            }
            else if (i.state == "Chưa được học") {
                s3 += 1
            }
        }
        binding.tvLearned.text = s1.toString()
        binding.tvLearning.text = s2.toString()
        binding.tvNotLearn.text = s3.toString()
    }
}