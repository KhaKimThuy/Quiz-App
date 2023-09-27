package com.example.afinal.Activity

import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import java.util.Date
import com.example.afinal.R

class RegisterActivity : AppCompatActivity() {

    private lateinit var dayNow: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        dayNow = findViewById(R.id.dayNow)

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        dayNow.setHint(currentDate)
    }
}