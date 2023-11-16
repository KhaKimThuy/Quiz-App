package com.example.afinal.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.databinding.ActivityAddTopicToFolderBinding

class AddTopicToFolderActivity : AppCompatActivity() {
    private lateinit var adapter: TopicListAdapter
    private lateinit var db: MyDB
    private lateinit var binding : ActivityAddTopicToFolderBinding
    private lateinit var curFolder : FolderDomain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddTopicToFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        curFolder = intent.getParcelableExtra("folder")!!

        // Init Firebase
        db = MyDB()
        binding.recyclerViewTopicList.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        loadTopic()

        binding.checkOk.setOnClickListener(View.OnClickListener {
            addTopicToFolder()
            val intent = Intent()
            setResult(RESULT_OK, intent)
//            st
            finish()
        })
    }

    private fun addTopicToFolder() {
        var selectedTopics = adapter.selectedTopics
        for (topic in selectedTopics) {
            db.AddTopicForFolder(topic, curFolder)
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