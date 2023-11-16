package com.example.afinal.Activity

import android.R
import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.FlashCardAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.databinding.ActivityDetailTopicBinding


class DetailTopicActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTopicBinding
    private lateinit var db: MyDB
    private lateinit var adapter : FlashCardAdapter
    private lateinit var topic : TopicDomain
    private lateinit var numItems : String
    private lateinit var itemList: ArrayList<FlashCardDomain>


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Firebase
        db = MyDB()
        itemList = ArrayList<FlashCardDomain>()


        // Load topic
        topic = intent.getParcelableExtra("topic")!!
        numItems = intent.getStringExtra("numItems").toString()

        // Load items
        binding.recyclerviewFlashcard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loadInfoTopic()

        binding.itemViewFlashCard.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FlashCardStudyActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelableArrayList("itemList", itemList)
            intent.putExtras(bundle)
            startActivity(intent)
        })

        binding.imgMore.setOnClickListener(View.OnClickListener {
            showOptionsMenu(it)
        })

        binding.imgBack.setOnClickListener(View.OnClickListener {
//            onBackPressed()
            finish()
        })
    }

    private fun showOptionsMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView)
        val inflater = popupMenu.menuInflater
        inflater.inflate(com.example.afinal.R.menu.detail_topic_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                com.example.afinal.R.id.edit -> {
                    // Handle edit action
                    val intent = Intent(this, EditTopicActivity::class.java)
                    intent.putExtra("topic", topic)
                    val bundle = Bundle()
                    val itemList: ArrayList<FlashCardDomain> = ArrayList<FlashCardDomain>()
                    for (i in 0 until adapter.itemCount) {
                        val item = adapter.getItem(i)
                        itemList.add(item)
                    }
                    bundle.putParcelableArrayList("itemList", itemList)
                    intent.putExtras(bundle)
                    startActivity(intent)
                    true
                }
                com.example.afinal.R.id.delete -> {
                    // Handle delete action
                    deleteDialog()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    @SuppressLint("SetTextI18n")
    private fun loadInfoTopic() {
        // Load name and the number of items in current topic
        binding.tvTopicName.text = topic.topicName
        binding.textViewNumItems.text = "$numItems Thuật ngữ"

        // Load items of current topic
        val options = db.RecyclerItem(topic.topicPK)
        adapter = FlashCardAdapter(options)
        binding.recyclerviewFlashcard.adapter = adapter

        // Add item to arraylist
        for (i in 0 until adapter.itemCount) {
            val item = adapter.getItem(i)
            itemList.add(item)
        }
    }

    @Throws(Resources.NotFoundException::class)
    private fun deleteDialog() {
        AlertDialog.Builder(this)
            .setMessage("Bạn chắc chắn muốn xóa học phần này vĩnh viễn?")
            .setPositiveButton("Xóa") { dialog, which ->
//                 Delete topic and items in it
                for (it in itemList) {
                    db.DeleteItem(it)
                }
                itemList.clear()
                db.DeleteTopic(topic)
//                 Return to library fragment
//                onDestroy()
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onPause() {
        super.onPause()
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