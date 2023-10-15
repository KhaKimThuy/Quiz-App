package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import android.annotation.SuppressLint
import com.example.afinal.Adapter.FlashCardAdapter
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.databinding.ActivityDetailTopicBinding

class DetailTopicActivity : AppCompatActivity() {

    private lateinit var binding : ActivityDetailTopicBinding
    private lateinit var db: MyDB
    private lateinit var adapter : FlashCardAdapter

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Firebase
        db = MyDB()

        // Set user info
        binding.textViewUname.text = CommonUser.currentUser?.email ?: "NoEmail"

        // Load items
        binding.recyclerviewFlashcard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)

        loadItem()

    }
    private fun loadItem(){
        val options = db.RecyclerFlashCard()
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