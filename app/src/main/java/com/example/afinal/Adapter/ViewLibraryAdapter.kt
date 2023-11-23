package com.example.afinal.Activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.afinal.Fragment.FragmentClass
import com.example.afinal.Fragment.FragmentLibrary
import com.example.afinal.Fragment.FragmentTopic

class ViewLibraryAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3 //SL Fragment
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                FragmentTopic()
            }
            1 -> {
                FragmentLibrary()
            }
            else -> {
                FragmentClass()
            }
        }
    }
}