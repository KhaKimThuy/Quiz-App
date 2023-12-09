package com.example.afinal.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afinal.Adapter.VPAdapter2
import com.example.afinal.databinding.FragmentLibraryBinding
import com.google.android.material.tabs.TabLayoutMediator

class FragmentLibrary : Fragment() {
    private lateinit var vpAdapter: VPAdapter2
    private lateinit var binding : FragmentLibraryBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        vpAdapter = activity?.let { VPAdapter2(it.supportFragmentManager, lifecycle) }!!
        binding.viewPager.adapter = vpAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Học phần"
                }

                1 -> {
                    tab.text = "Thư mục"
                }

                2 -> {
                    tab.text = "Lớp học"
                }
            }
        }.attach()


//        val mPagerAdapter = VPAdapter2(childFragmentManager);
//        mPager.setAdapter(mPagerAdapter);
    }

    override fun onResume() {
        super.onResume()
        binding.viewPager.adapter = vpAdapter
    }

//    override fun onPause() {
//        super.onPause()
//        binding.viewPager.adapter = vpAdapter
//    }

//        vpAdapter = VPAdapter(a)
//        vpAdapter.addFragment(FragmentStudyModule(), "Học phần")
//        vpAdapter.addFragment(FragmentFolder(), "Thư mục")
//        vpAdapter.addFragment(FragmentStudyModule(), "Lớp học")
//        viewPager.adapter = vpAdapter
//
//        db = MyDB()
////        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        binding.tabLayout.setupWithViewPager(viewPager)
//
//        //loadTopic()
//        // Inflate the layout for this fragment
//    }
    override fun onDestroy() {
        super.onDestroy()
    }

}