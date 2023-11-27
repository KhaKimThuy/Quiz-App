package com.example.afinal.Fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.SearchTopicAdapter
import com.example.afinal.DB.TopicDAL
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.databinding.FragmentSearchResultBinding
import java.util.Locale

class SearchResultFragment : Fragment() {
    lateinit var adapter : SearchTopicAdapter
    private lateinit var topicDAL : TopicDAL
    private lateinit var binding : FragmentSearchResultBinding
    private lateinit var dataList: ArrayList<TopicDomain>

    override fun onCreateView(
        inflater: LayoutInflater, container : ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        topicDAL = TopicDAL()
        dataList = ArrayList<TopicDomain>();
        val arguments = arguments
        val searchContent = arguments!!.getString("search")
        binding.recyclerviewTopic.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        adapter = SearchTopicAdapter(dataList)
        binding.recyclerviewTopic.adapter = adapter;
        if (searchContent != null) {
            topicDAL.GetListPublicTopic(this, dataList, searchContent)
        }
        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                searchList(query)
                //Toast.makeText(context, "Query: $query", Toast.LENGTH_SHORT).show()
                return false
            }
            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    fun searchList(text: String) {
        val searchList = java.util.ArrayList<TopicDomain>()
        for (dataClass in dataList) {
            Log.d("TAG", "Public topic: " + dataClass.topicName)
            if (dataClass.topicName.lowercase()
                    ?.contains(text.lowercase(Locale.getDefault())) == true
            ) {
                searchList.add(dataClass)
            }
        }
        Log.d("TAG", "Search list: " + searchList.size)


        adapter.searchDataList(searchList)
    }

    private fun loadTopic() {
//        val options = topicDAL.RecyclerSearchPublicTopic()
//        adapter = TopicListAdapter(options)
//        binding.recyclerviewTopic.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onResume() {
        super.onResume()
    }
}