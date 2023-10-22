package com.example.afinal.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.afinal.R
import com.example.afinal.databinding.ActivityLoginBinding
import com.example.afinal.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.layoutProfile.setOnClickListener(View.OnClickListener{
            val intent = Intent(applicationContext, SettingActivity::class.java)
            startActivity(intent)
        })

        binding.layoutLibrary.setOnClickListener(View.OnClickListener{
            val intent = Intent(applicationContext, LibraryActivity::class.java)
            startActivity(intent)
        })

        binding.imageViewAdd.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, CreateStudyModuleActivity::class.java)
            startActivity(intent)
        })
    }
}