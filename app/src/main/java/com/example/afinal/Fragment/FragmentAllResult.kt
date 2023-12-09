package com.example.afinal.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.afinal.databinding.FragmentAllResultBinding

class FragmentAllResult : Fragment() {
    private lateinit var binding : FragmentAllResultBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = FragmentAllResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        initRecyclerView()
    }

//    private fun initRecyclerView(){
//        val topicList = ArrayList<TopicDomain>()
//
//        topicList.add(TopicDomain("Food and drink", "James", "cat", 35))
//        topicList.add(TopicDomain("Water", "John", "cat", 15))
//        topicList.add(TopicDomain("Fast food", "Mary", "cat", 50))
//        topicList.add(TopicDomain("Machine", "Josh", "cat", 11))
//        topicList.add(TopicDomain("Engineer", "Mat", "cat", 90))
//        topicList.add(TopicDomain("Science", "Anh Vo", "cat", 150))
//        topicList.add(TopicDomain("Environment", "Watson", "cat", 75))
//        topicList.add(TopicDomain("Politic", "Emma", "cat", 58))
//        topicList.add(TopicDomain("Vehicle", "William", "cat", 87))
//
//        val recyclerViewTopic = binding.recyclerviewTopic
//        recyclerViewTopic.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//
//        val adapterTopicList = TopicListAdapter(topicList)
//        recyclerViewTopic.adapter = adapterTopicList
//    }
    override fun onDestroyView() {
        super.onDestroyView()
    }
}