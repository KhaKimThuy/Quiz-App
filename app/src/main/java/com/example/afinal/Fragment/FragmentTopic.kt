package com.example.afinal.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Adapter.TopicAdapter
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.ViewModel.TopicViewModel
import com.example.afinal.databinding.FragmentStudyModuleBinding

class FragmentTopic : Fragment() {
    private lateinit var adapter: TopicAdapter
    private lateinit var binding : FragmentStudyModuleBinding

    private lateinit var topicViewModel: TopicViewModel

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
                    TopicDTO.numItems = topicView.numItem.text.toString()
                    val intent = Intent(activity, DetailTopicActivity::class.java)
                    requireActivity().startActivity(intent)
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

//    private fun loadTopic() {
//        val userId = MyDB().dbAuth.currentUser?.uid.toString()
//        TopicDAL().GetTopicOfUser(userId) {
//            Log.d("TAG", "ReLoad adapter")
//            adapter = TopicAdapter(TopicDTO.topicList, requireActivity())
//            binding.recyclerViewTopicList.adapter = adapter
//        }
//    }
}