package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.viewpager2.widget.ViewPager2
import com.example.afinal.R
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class LibraryActivity : AppCompatActivity() {
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPaper: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_library)

        tabLayout = findViewById(R.id.tabLayout)
        viewPaper = findViewById(R.id.paperTab)

        val adapterPP = ViewLibraryAdapter(supportFragmentManager, lifecycle)
        viewPaper.adapter = adapterPP
        TabLayoutMediator(tabLayout, viewPaper) {tab, pos ->
            when(pos) {
                0 -> {tab.text = resources.getText(R.string.module_study)}
                1 -> {tab.text = resources.getText(R.string.folder)}
                2 -> {tab.text = resources.getText(R.string._class)}
            }
        }.attach()
    }
}