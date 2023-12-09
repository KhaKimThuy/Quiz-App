package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager.widget.ViewPager
import com.example.afinal.Domain.TopicDomain
//import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.Adapter.VPAdapter
import com.example.afinal.Fragment.FragmentAllResult
import com.example.afinal.Fragment.FragmentCourse
import com.example.afinal.R
import com.example.afinal.databinding.ActivityRegisterBinding
import com.example.afinal.databinding.ActivitySearchResultBinding
import com.google.android.material.tabs.TabLayout

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