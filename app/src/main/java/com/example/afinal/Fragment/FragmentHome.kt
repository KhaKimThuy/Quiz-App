package com.example.afinal.Fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Activity.DetailTopicActivity
import com.example.afinal.Adapter.FolderAdapter
import com.example.afinal.Adapter.TopicAdapter
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.databinding.FragmentHomeBinding

class FragmentHome : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var adapter: TopicAdapter
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        Log.d("TAG", "Fragment Home")

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do your task here
                if (fragmentManager != null) {
                    fragmentManager!!.popBackStack()
                    val fragment = SearchResultFragment()
                    val arguments = Bundle()
                    arguments.putString("search", binding.search.query.toString())
                    fragment.arguments = arguments

                    val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                    if (fragment != null) {
                        fragmentTransaction.replace(com.example.afinal.R.id.frameLayout, fragment)
                    }
                    if (fragment != null) {
                        fragmentTransaction.addToBackStack(fragment.tag)
                    }
                    fragmentTransaction.commit()
                }
                return false
            }
        })
    }

    private fun init() {
        binding.recyclerViewPublicTopic.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        loadPublicTopic()
    }

    private fun loadPublicTopic() {
        TopicDAL().GetPublicTopic() {
            adapter = TopicAdapter(it, object : TopicAdapter.IClickTopicListener {
                override fun onClickTopicListener(
                    topicView: TopicAdapter.TopicViewHolder,
                    position: Int
                ) {
                    TopicDTO.currentTopic = it[position]
                    TopicDTO.numItems = topicView.numItem.text.toString()
                    val intent = Intent(activity, DetailTopicActivity::class.java)
                    intent.putExtra("isMine", false)
                    intent.putExtra("isSaved", false)
                    intent.putExtra("from", "FragmentHome")
                    requireActivity().startActivity(intent)
                }

                override fun onLongClickTopicListener(
                    topicView: TopicAdapter.TopicViewHolder,
                    position: Int
                ) {
                    TODO("Not yet implemented")
                }
            })
            binding.recyclerViewPublicTopic.adapter = adapter
        }
    }
}