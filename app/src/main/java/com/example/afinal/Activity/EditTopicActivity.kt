package com.example.afinal.Activity

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.AddItemTopicAdapter
import com.example.afinal.Adapter.EditItemTopicAdapter
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.Item
import com.example.afinal.Domain.Topic
import com.example.afinal.R
import com.example.afinal.databinding.ActivityEditTopicBinding

class EditTopicActivity : AppCompatActivity() {
    private lateinit var binding : ActivityEditTopicBinding
//    private lateinit var newItemList : ArrayList<Item>
    private lateinit var adapter: EditItemTopicAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.progressBar7.visibility = View.GONE

        // Load current topic information
//        newItemList = ArrayList<Item>()
//        newItemList.addAll(TopicDTO.itemList)

        loadCurrentTopic()

        // Create new item
        binding.imageViewAddItem.setOnClickListener(View.OnClickListener {
            TopicDTO.itemList.add(Item())
            adapter.notifyItemInserted(TopicDTO.itemList.size - 1)
        })

        binding.checkOk.setOnClickListener(View.OnClickListener {
            binding.progressBar7.visibility = View.VISIBLE
            GetChange()
            UpdateTopic()
        })
    }

    private fun UpdateTopic() {
        TopicDTO.currentTopic?.let { TopicDAL().UpdateTopic(it, TopicDTO.itemList) {
                binding.progressBar7.visibility = View.GONE
                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
//        // Update topic
//        val topicDB = db.GetTopicByID(topic.topicPK)
//        if (topic.topicName != topicDB.child("topicName").toString())
//            topicDB.child("topicName").setValue(topic.topicName)
//        // Update items
//        for (item in itemList) {
//            if (item.topicPK.isNullOrEmpty()) {
//                db.CreateItem(item, topic.topicPK)
//            } else {
//                val itemDB = db.GetItemByID(item.itemPK)
//
//                if (itemDB.child("vnLanguage").toString() != item.vnLanguage)
//                    itemDB.child("vnLanguage").setValue(item.vnLanguage)
//
//                if (itemDB.child("engLanguage").toString() != item.engLanguage)
//                    itemDB.child("engLanguage").setValue(item.engLanguage)
//            }
//        }
    }

    private fun loadCurrentTopic() {
        binding.edtTopicName.setText(TopicDTO.currentTopic?.topicName)
        adapter = EditItemTopicAdapter(TopicDTO.itemList, this)
        binding.recyclerViewAddTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewAddTopic.adapter = adapter
    }

    private fun GetChange() {
        TopicDTO.currentTopic?.topicName = binding.edtTopicName.text.toString()
    }


}