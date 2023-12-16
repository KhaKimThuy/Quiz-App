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
            TopicDTO.allItemList.add(Item())
            adapter.notifyItemInserted(TopicDTO.allItemList.size - 1)
        })

        binding.checkOk.setOnClickListener(View.OnClickListener {
            binding.progressBar7.visibility = View.VISIBLE
            GetChange()
            UpdateTopic()
        })

        binding.imgBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun UpdateTopic() {
        TopicDTO.currentTopic?.let { TopicDAL().UpdateTopic(it, TopicDTO.allItemList) {
                binding.progressBar7.visibility = View.GONE
                val resultIntent = Intent()
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
            }
        }
    }

    private fun loadCurrentTopic() {
        binding.edtTopicName.setText(TopicDTO.currentTopic?.topicName)
        adapter = EditItemTopicAdapter(TopicDTO.allItemList, this)
        binding.recyclerViewAddTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewAddTopic.adapter = adapter
    }

    private fun GetChange() {
        TopicDTO.currentTopic?.topicName = binding.edtTopicName.text.toString()
    }
}