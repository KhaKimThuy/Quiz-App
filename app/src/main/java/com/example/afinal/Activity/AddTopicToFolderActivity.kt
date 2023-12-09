package com.example.afinal.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.TopicAdapter
import com.example.afinal.DAL.TopicFolderDAL
import com.example.afinal.DTO.FolderDTO
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityAddTopicToFolderBinding

class AddTopicToFolderActivity : AppCompatActivity() {
    private lateinit var adapter: TopicAdapter
    private lateinit var binding : ActivityAddTopicToFolderBinding
    private var selectedTopic : ArrayList<TopicDomain> = ArrayList<TopicDomain>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTopicToFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        binding.checkOk.setOnClickListener(View.OnClickListener {
            addTopicToFolder()
            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    private fun init () {
        binding.recyclerViewTopicList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        val outTopic = ArrayList<TopicDomain>()
        outTopic.addAll(TopicDTO.topicList)

        // Temporary remove topic already in folder

        outTopic.removeAll(FolderDTO.topicList.toSet())
        loadTopic(outTopic)
    }

    private fun loadTopic(outTopic : ArrayList<TopicDomain>) {
        adapter = TopicAdapter(outTopic, object : TopicAdapter.IClickTopicListener {
            @SuppressLint("ResourceAsColor")
            override fun onClickTopicListener(topicView : TopicAdapter.TopicViewHolder, position : Int) {
                val backgroundDrawable = topicView.mainView.background
                val backgroundColor = (backgroundDrawable as ColorDrawable).color
                if (backgroundColor ==  R.color.add_topic_marker_color){
                    topicView.mainView.setBackgroundColor(Color.WHITE)
                    if (selectedTopic != null) {
                        selectedTopic.remove(outTopic[position])
                    }
                } else {
                    topicView.mainView.setBackgroundColor(R.color.add_topic_marker_color)
                    if (selectedTopic != null) {
                        selectedTopic.add(outTopic[position])
                    }
                }
            }

            override fun onLongClickTopicListener(topicView : TopicAdapter.TopicViewHolder, position : Int) {
                TODO("Not yet implemented")
            }

        })


        binding.recyclerViewTopicList.adapter = adapter
    }

    private fun addTopicToFolder() {
        val moreAddedTopics = ArrayList<TopicDomain>()
        if (selectedTopic.size > 0) {
            for (topic in selectedTopic) {
                // Add topic-folder object to db
                FolderDTO.currentFolder?.let { TopicFolderDAL().AddTopicFolder(topic, it) }
                moreAddedTopics.add(topic)
            }
        }
        val intent = Intent()
        intent.putParcelableArrayListExtra("moreAddedTopics", moreAddedTopics)
        setResult(RESULT_OK, intent)
        finish()
    }

}