package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.afinal.R
import com.example.afinal.databinding.ActivityEndTestBinding
import com.example.afinal.databinding.ActivityMultiChoiceStudyBinding

class EndTestActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEndTestBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEndTestBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.tvResult.text = intent.getStringExtra("result")
        binding.btnBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }
}