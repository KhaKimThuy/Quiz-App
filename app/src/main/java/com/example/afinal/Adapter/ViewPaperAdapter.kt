package com.example.afinal.Activity

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.afinal.Fragment.FragmentPolicies
import com.example.afinal.Fragment.FragmentProvisions

class ViewPaperAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 2 //SL Fragment
    }

    override fun createFragment(position: Int): Fragment {
        return when(position) {
            0 -> {
                FragmentProvisions()
            }
            else -> {
                FragmentPolicies()
            }
        }
    }
}