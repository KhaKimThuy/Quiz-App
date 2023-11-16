package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.databinding.ActivityLibraryBinding
import com.google.android.material.tabs.TabLayout

class LibraryActivity : AppCompatActivity() {
    private lateinit var binding : ActivityLibraryBinding
    private lateinit var adapter: TopicListAdapter
    private lateinit var db: MyDB

    private lateinit var tabLayout: TabLayout
    private lateinit var viewPaper: ViewPager2
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLibraryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup db
        db = MyDB()
        binding.recyclerViewTopicList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        loadTopic()

//        val adapterPP = ViewLibraryAdapter(supportFragmentManager, lifecycle)
//        viewPaper.adapter = adapterPP

//        TabLayoutMediator(binding.tabLayout, binding.paperTab) {tab, pos ->
//            when(pos) {
//                0 -> {tab.text = resources.getText(R.string.module_study)}
//                1 -> {tab.text = resources.getText(R.string.folder)}
//                2 -> {tab.text = resources.getText(R.string._class)}
//            }
//        }.attach()
    }
//    private fun setUpTabs() {
//        val adapter = VPAdapter(supportFragmentManager);
//        adapter.addFragment(FragmentAllResult(), "Tất cả kết quả");
//        adapter.addFragment(FragmentCourse(), "Học phần");
//        binding.viewPager.adapter = adapter;
//        binding.tabLayout.setupWithViewPager(binding.viewPager);
//    }

    private fun loadTopic(){
        val options = db.RecyclerTopic()
        adapter = TopicListAdapter(options)
        binding.recyclerViewTopicList.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }
}