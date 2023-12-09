package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.afinal.Adapter.SliderAdapter
import com.example.afinal.R
import com.smarteist.autoimageslider.SliderView

class StartActivity : AppCompatActivity() {

    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        sliderView = findViewById(R.id.imageSlider)

        val imageurl = arrayListOf(
            R.drawable.banner1,
            R.drawable.banner2,
            R.drawable.banner3,
            R.drawable.banner4
        )

        sliderAdapter = SliderAdapter(imageurl)
        sliderView.autoCycleDirection = SliderView.LAYOUT_DIRECTION_LTR
        sliderView.setSliderAdapter(sliderAdapter)
        sliderView.scrollTimeInMillis = 3500
        sliderView.isAutoCycle = true
        sliderView.startAutoCycle()

    }
}