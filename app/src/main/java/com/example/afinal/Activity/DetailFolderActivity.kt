package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.FlashCardAdapter
import com.example.afinal.Adapter.FolderListAdapter
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.UserDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.example.afinal.R
import com.example.afinal.databinding.ActivityDetailFolderBinding
import com.example.afinal.databinding.ActivityDetailTopicBinding
import com.google.firebase.database.DatabaseError

class DetailFolderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailFolderBinding
    private lateinit var db: MyDB
    private lateinit var adapter : TopicListAdapter
    private lateinit var curFolder : FolderDomain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Firebase
        db = MyDB()
        curFolder = FolderDomain()


        // Get selected folder PK
        val intentPK = intent
        val curFolderPK = intentPK.getStringExtra("folderPK").toString()
        db.GetFolderByID(curFolderPK, object : ValueEventListenerCallback {
            override fun onDataChange(dataSnapshot: Long) {
            }

            override fun onDataChange(dataSnapshot: FolderDomain) {
                curFolder = dataSnapshot
                binding.tvFolderCurName.text = curFolder.folderName
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }
        })

        Toast.makeText(this, curFolder.folderName, Toast.LENGTH_LONG).show()
        // Load topic in selected folder
        binding.recyclerViewTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        loadTopic()

        binding.imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed();
        })
    }
    private fun loadTopic(){
        val options = db.RecyclerTopic()
        adapter = TopicListAdapter(options)
        binding.recyclerViewTopic.adapter = adapter
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