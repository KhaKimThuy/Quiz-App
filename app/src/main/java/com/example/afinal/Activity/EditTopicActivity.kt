package com.example.afinal.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.AddItemTopicAdapter
import com.example.afinal.DAL.MyDB
import com.example.afinal.Domain.Item
import com.example.afinal.Domain.Topic
import com.example.afinal.databinding.ActivityEditTopicBinding

class EditTopicActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditTopicBinding
    private lateinit var topic : Topic
    private lateinit var itemList : ArrayList<Item>
    private lateinit var adapter: AddItemTopicAdapter
    private lateinit var db : MyDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyDB()

        topic = intent.getParcelableExtra("topic")!!
        val bundle = intent.extras
        if (bundle != null) {
            itemList = bundle.getParcelableArrayList<Item>("itemList")!!
        }

        // Load current topic information
        loadCurrentTopic()

        // Create new item
        binding.imageViewAddItem.setOnClickListener(View.OnClickListener {
            itemList.add(Item())
            adapter.notifyItemInserted(itemList.size - 1)
        })

        binding.checkOk.setOnClickListener(View.OnClickListener {
            // Get change from user
            UpdateChange()

            // Update change to database
            UpdateTopic()

            val intent = Intent(this, DetailTopicActivity::class.java)
            intent.putExtra("numItems", itemList.size.toString())
            intent.putExtra("topic", topic)
            startActivity(intent)
        })

    }

    private fun UpdateTopic() {

        // Update topic
        val topicDB = db.GetTopicByID(topic.topicPK)
        if (topic.topicName != topicDB.child("topicName").toString())
            topicDB.child("topicName").setValue(topic.topicName)

        // Update items
        for (item in itemList) {
            if (item.topicPK.isNullOrEmpty()) {
                db.CreateItem(item, topic.topicPK)
            } else {
                val itemDB = db.GetItemByID(item.itemPK)

                if (itemDB.child("vnLanguage").toString() != item.vnLanguage)
                    itemDB.child("vnLanguage").setValue(item.vnLanguage)

                if (itemDB.child("engLanguage").toString() != item.engLanguage)
                    itemDB.child("engLanguage").setValue(item.engLanguage)
            }
        }

    }

    private fun loadCurrentTopic() {
        binding.edtTopicName.setText(topic.topicName)
        adapter = AddItemTopicAdapter(itemList)
        binding.recyclerViewAddTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewAddTopic.adapter = adapter
    }

    private fun UpdateChange() {
        topic.topicName = binding.edtTopicName.text.toString()
    }

}