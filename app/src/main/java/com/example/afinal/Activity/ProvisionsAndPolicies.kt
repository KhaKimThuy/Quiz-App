package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.example.afinal.R
import com.example.afinal.Activity.ViewPaperAdapter

class ProvisionsAndPolicies : AppCompatActivity() {

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPaper: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_provisions_and_policies)

        tabLayout = findViewById(R.id.tabLayout)
        viewPaper = findViewById(R.id.paperTab)

        val adapterPP = ViewPaperAdapter(supportFragmentManager, lifecycle)
        viewPaper.adapter = adapterPP
        TabLayoutMediator(tabLayout, viewPaper) {tab, pos ->
            when(pos) {
                0 -> {tab.text = "Điều khoản dịch vụ"}
                1 -> {tab.text = "Chính sách quyền riêng tư"}
            }
        }.attach()
    }
}