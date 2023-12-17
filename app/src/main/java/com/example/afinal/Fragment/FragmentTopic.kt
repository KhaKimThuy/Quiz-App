package com.example.afinal.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Adapter.TopicAdapter
import com.example.afinal.DAL.MyDB
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.Topic
import com.example.afinal.ViewModel.TopicViewModel
import com.example.afinal.databinding.FragmentStudyModuleBinding

class FragmentTopic : Fragment() {
    private lateinit var adapter: TopicAdapter
    private lateinit var binding : FragmentStudyModuleBinding

    private lateinit var topicViewModel: TopicViewModel
    private val DETAIL_TOPIC_CODE = 444

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        //loadTopic()
    }

    private fun init() {
        // Processing smarter later
        TopicDTO.topicList.clear()

        topicViewModel = ViewModelProvider(this)[TopicViewModel::class.java]
        topicViewModel.getListTopicLiveData().observe(requireActivity()) { topics ->
            adapter = TopicAdapter(topics, object : TopicAdapter.IClickTopicListener {
                override fun onClickTopicListener(topicView: TopicAdapter.TopicViewHolder, position: Int) {
                    TopicDTO.currentTopic = topics[position]
                    Log.d("TAG", "Topic public in fragment topic : " + TopicDTO.currentTopic!!.isPublic)

                    TopicDTO.numItems = topicView.numItem.text.toString()

                    val intent = Intent(activity, DetailTopicActivity::class.java)
                    if (topics[position].userPK == UserDTO.currentUser?.userPK) {
                        intent.putExtra("isMine", true)
                    } else {
                        intent.putExtra("isMine", false)
                    }
                    intent.putExtra("isSaved", true)
                    intent.putExtra("from", "FragmentTopic")
                    Log.d("fragmentHome", "Before - Topic list size : " + TopicDTO.topicList.size)
                    startActivityForResult(intent, DETAIL_TOPIC_CODE)
                }

                override fun onLongClickTopicListener(
                    topicView: TopicAdapter.TopicViewHolder,
                    position: Int
                ) {
                    TODO("Not yet implemented")
                }
            })
            binding.recyclerViewTopicList.adapter = adapter
        }

        binding.recyclerViewTopicList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerViewTopicList)
    }

    override fun onStart() {
        Log.d("State", "onStart")
        topicViewModel.refresh()
        super.onStart()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.d("fragmentHome", "onActivityResult")

        if (requestCode == DETAIL_TOPIC_CODE) {
            val operation = data?.getStringExtra("operation")
            if (operation == "delete") {
                TopicDTO.currentTopic?.let { topicViewModel.removeTopic(it) }
                // Refresh current topic value
                TopicDTO.itemList.clear()
                TopicDTO.currentTopic = null
                Log.d("fragmentHome", "After - Topic list size : " + TopicDTO.topicList.size)
            }
            else if (operation == "add") {
                topicViewModel.refresh()
                Log.d("fragmentHome", "Add : " + TopicDTO.topicList.size)
            }
        }
    }
}