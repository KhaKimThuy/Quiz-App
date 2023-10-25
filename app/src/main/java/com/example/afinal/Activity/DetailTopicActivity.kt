package com.example.afinal.Activity

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.FlashCardAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.databinding.ActivityDetailTopicBinding

class DetailTopicActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTopicBinding
    private lateinit var db: MyDB
    private lateinit var adapter : FlashCardAdapter
    private lateinit var curTopicPK : String

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Firebase
        db = MyDB()

        // Load topic
        val intentPK = intent
        curTopicPK = intentPK.getStringExtra("topic").toString()

        // Set user info
        //binding.textViewUname.text = CommonUser.currentUser?.email ?: "NoEmail"

        // Load items
        binding.recyclerviewFlashcard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        loadItem()
    }

    private fun loadItem() {
        val options = db.RecyclerItem(curTopicPK)
        adapter = FlashCardAdapter(options)
        binding.recyclerviewFlashcard.adapter = adapter
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