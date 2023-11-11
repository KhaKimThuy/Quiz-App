package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityAddTopicToFolderBinding
import com.example.afinal.databinding.ActivityDetailFolderBinding
import com.example.afinal.databinding.FragmentStudyModuleBinding

class AddTopicToFolderActivity : AppCompatActivity() {
    private lateinit var adapter: TopicListAdapter
    private lateinit var db: MyDB
    private lateinit var binding : ActivityAddTopicToFolderBinding
    private lateinit var curFolderPK : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTopicToFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val intentPK = intent
        curFolderPK = intentPK.getStringExtra("folderPK").toString()


        // Init Firebase
        db = MyDB()
        binding.recyclerViewTopicList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        loadTopic()

        binding.checkOk.setOnClickListener(View.OnClickListener {
            addTopicToFolder()
            finish()
        })
    }

    private fun addTopicToFolder() {
        var selectedTopics = adapter.selectedTopics
        for (topic in selectedTopics) {
            db.GetTopicByID(topic.topicPK).child("folderPK").setValue(curFolderPK)
        }
    }


    private fun loadTopic() {
        val options = db.RecyclerTopic()
        adapter = TopicListAdapter(options, true)
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
}