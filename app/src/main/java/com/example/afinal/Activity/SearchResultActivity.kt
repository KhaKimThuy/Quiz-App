package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
//import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.Adapter.VPAdapter
import com.example.afinal.Fragment.FragmentAllResult
import com.example.afinal.Fragment.FragmentCourse
import com.example.afinal.databinding.ActivitySearchResultBinding

class SearchResultActivity : AppCompatActivity() {
    private lateinit var binding : ActivitySearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //initRecyclerView()
        setUpTabs()
    }

    private fun setUpTabs() {
        val adapter = VPAdapter(supportFragmentManager);
        adapter.addFragment(FragmentAllResult(), "Tất cả kết quả");
        adapter.addFragment(FragmentCourse(), "Học phần");
        binding.viewPager.adapter = adapter;
        binding.tabLayout.setupWithViewPager(binding.viewPager);
    }


}