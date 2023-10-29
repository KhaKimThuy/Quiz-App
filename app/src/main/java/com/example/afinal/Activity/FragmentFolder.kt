package com.example.afinal.Activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.viewpager2.widget.ViewPager2
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.R
import com.google.android.material.tabs.TabLayout

class FragmentFolder : Fragment() {
    private lateinit var adapter: TopicListAdapter
    private lateinit var db: MyDB
    private lateinit var recyclerView: RecyclerView
    private lateinit var tabLayout: TabLayout
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_folder, container, false)
        recyclerView = rootView.findViewById(R.id.recyclerView_topicList)
        tabLayout = rootView.findViewById(R.id.tabLayout)

        db = MyDB()
        recyclerView.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        loadTopic()
        // Inflate the layout for this fragment
        return rootView
    }
    private fun loadTopic(){
        val options = db.RecyclerTopic()
        adapter = TopicListAdapter(options)
        recyclerView.adapter = adapter
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