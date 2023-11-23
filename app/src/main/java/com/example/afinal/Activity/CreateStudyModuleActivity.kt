package com.example.afinal.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.AddItemTopicAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.databinding.ActivityCreateStudyModuleBinding

class CreateStudyModuleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateStudyModuleBinding
    private lateinit var itemList: ArrayList<FlashCardDomain>
    private lateinit var adapter: AddItemTopicAdapter
    private lateinit var db: MyDB
    private lateinit var topic : TopicDomain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStudyModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup DB
        db = MyDB()
        itemList = ArrayList()

        // Init data
        itemList.add(FlashCardDomain())
        itemList.add(FlashCardDomain())

        adapter = AddItemTopicAdapter(itemList)
        binding.recyclerViewAddTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewAddTopic.adapter = adapter

        binding.imageViewAddItem.setOnClickListener(View.OnClickListener {
            itemList.add(FlashCardDomain())
            adapter.notifyItemInserted(itemList.size - 1)
        })

        binding.checkOk.setOnClickListener(View.OnClickListener {
            addTopic()
        })

        binding.imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed();
        })

    }

    private fun addTopic() {
        topic = TopicDomain()
        val topicPK = db.GetTopic().push().key
        topic.topicName = binding.edtTopicName.text.toString()
        topic.isPublic = binding.switchIsPublic.isChecked

        if (topicPK != null) {
            topic.topicPK = topicPK
        }

        // Add topic
        db.CreateTopicWithItems(topic, itemList)

        val intent = Intent(this, DetailTopicActivity::class.java)
        intent.putExtra("numItems", itemList.size.toString())
        intent.putExtra("topic", topic)
        startActivity(intent)
    }

}