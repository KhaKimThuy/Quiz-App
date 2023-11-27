package com.example.afinal.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.DTO.UserDTO
import com.example.afinal.databinding.FragmentStudyModuleBinding

class FragmentTopic : Fragment() {
    private lateinit var adapter: TopicListAdapter
    private lateinit var db: MyDB
    private lateinit var binding : FragmentStudyModuleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = MyDB()
        binding.recyclerViewTopicList.layoutManager =
            LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewTopicList)

        loadTopic()
    }

    private fun loadTopic() {
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

    override fun onPause() {
        super.onPause()
        adapter.stopListening()
    }

    override fun onResume() {
        super.onResume()
        adapter.startListening()
    }
}