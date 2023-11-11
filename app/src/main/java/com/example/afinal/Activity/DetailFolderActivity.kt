package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.example.afinal.databinding.ActivityDetailFolderBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError


class DetailFolderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailFolderBinding
    private lateinit var db: MyDB
    private lateinit var adapter : TopicListAdapter
    private lateinit var curFolderPK : String
    private lateinit var curFolder : FolderDomain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Firebase
        db = MyDB()
        //curFolder = FolderDomain()


        // Get selected folder PK
        val intentPK = intent
        curFolderPK = intentPK.getStringExtra("folderPK").toString()
        db.GetFolderByID(curFolderPK, object : ValueEventListenerCallback {
            override fun onDataChange(dataSnapshot: Long) {
            }
            override fun onDataChange(dataSnapshot: FolderDomain) {
                curFolder = dataSnapshot
                binding.tvFolderCurName.text = curFolder.folderName
            }

            override fun onDataChangeTopic(dataSnapshot: TopicDomain) {
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })


//        // Load topic in selected folder
        binding.recyclerViewTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        loadTopic()

        // The number of topics in current folder
        binding.textViewNumTopic.text = binding.recyclerViewTopic.childCount.toString()
        binding.imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed();
        })

        binding.imageViewAddItem.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, AddTopicToFolderActivity::class.java)
            intent.putExtra("folderPK", curFolderPK)
            startActivity(intent)
        })
    }
    private fun loadTopic(){
        val options = db.RecyclerTopic(curFolderPK)
        adapter = TopicListAdapter(options)
        binding.recyclerViewTopic.adapter = adapter


        db.GetTheNumberOfTopicsInFolder(curFolderPK, object : ValueEventListenerCallback{
            override fun onDataChange(dataSnapshot: Long) {
                binding.textViewNumTopic.text = "$dataSnapshot"
//                if (dataSnapshot.toInt() == 0) {
//
//                }
            }

            override fun onDataChange(dataSnapshot: FolderDomain) {
                TODO("Not yet implemented")
            }

            override fun onDataChangeTopic(dataSnapshot: TopicDomain) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }

        }).toString()

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