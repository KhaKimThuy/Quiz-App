package com.example.afinal.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.afinal.Adapter.SliderAdapter
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.UserDAL
import com.example.afinal.R
import com.example.afinal.databinding.ActivityStartBinding
import com.example.afinal.databinding.FirstStartBinding
import com.smarteist.autoimageslider.SliderView

class StartActivity : AppCompatActivity() {

    lateinit var sliderView: SliderView
    lateinit var sliderAdapter: SliderAdapter
    private lateinit var binding : ActivityStartBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkUser()

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

        binding.btnOrLogin.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        })

        binding.btnRegiterFree.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
            finish()
        })
    }

    private fun checkUser() {
        super.onStart()
        var userId = MyDB().dbAuth.currentUser?.uid
        if (userId != null) {
            // Save user information locally
            UserDAL().SaveUserLocal(userId)
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}