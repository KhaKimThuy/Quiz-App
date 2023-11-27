package com.example.afinal.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.FlashCardAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.DB.TopicDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Domain.TopicPublicDomain
import com.example.afinal.databinding.ActivityDetailTopicBinding
import java.time.LocalDateTime


class DetailTopicActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTopicBinding
    private lateinit var db: MyDB
    private lateinit var topicDAL: TopicDAL
    private lateinit var adapter : FlashCardAdapter
    private lateinit var topic : TopicDomain
    private lateinit var numItems : String
    var itemList: ArrayList<FlashCardDomain> = ArrayList<FlashCardDomain>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if (TopicDTO.currentTopic?.isPublic == true) {
            initPublicTopic()
        }

        // Init Firebase
        db = MyDB()
        topicDAL = TopicDAL()
        itemList = TopicDTO.itemList

        // Load topic
        numItems = TopicDTO.numItems

        // Load items
        binding.recyclerviewFlashcard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        loadInfoTopic()

        // Flashcard
        binding.itemViewFlashCard.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, FlashCardStudyActivity::class.java)
            startActivity(intent)
        })

        // Multiple choice
        binding.itemViewMultipleChoice.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, MultiChoiceStudyActivity::class.java)
            startActivity(intent)
        })

        // Type vocab
        binding.itemViewTypeWord.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, TypeVocabStudyActivity::class.java)
            startActivity(intent)
        })

        // Statistic
        binding.itemViewStatis.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, CongratulationActivity::class.java)
            startActivity(intent)
        })

        binding.imgBack.setOnClickListener(View.OnClickListener {
//            onBackPressed()
            finish()
        })
    }

    private fun initPublicTopic() {
        binding.itemViewRanking.visibility = View.VISIBLE
        binding.saveTopic.visibility = View.VISIBLE
        binding.imgMore.visibility = View.VISIBLE

        // Ranking
        binding.itemViewRanking.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ActivityRanking::class.java)
            startActivity(intent)
        })

        binding.saveTopic.setOnClickListener(View.OnClickListener {
            addPublicTopic()
            Toast.makeText(this, "Saved topic", Toast.LENGTH_SHORT).show()
        })

        binding.imgMore.setOnClickListener(View.OnClickListener {
            showOptionsMenu(it)
        })
    }

    private fun addPublicTopic() {
        val topic = TopicPublicDomain()
        topic.guestPK = UserDTO.currentUser?.GetPK() ?: "Error"
        val topicPK = TopicDTO.currentTopic?.topicPK
        if (topicPK != null) {
            topic.topicPK = topicPK
        }
        val userPK = TopicDTO.currentTopic?.userPK
        val topicPublicPK = topicPK + userPK
        topic.isPublic = true

        if (topicPublicPK != null) {
            topic.topicPublicPK = topicPublicPK
        }

        topicDAL.AddPublicTopic(topic)

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
        binding.tvTopicName.text = TopicDTO.currentTopic?.topicName ?: "Null"
        binding.textViewNumItems.text = "$numItems Thuật ngữ"

        // Load items of current topic
        val options = TopicDTO.currentTopic?.let { db.RecyclerItem(it.topicPK) }
        adapter = options?.let { FlashCardAdapter(this, it) }!!
        binding.recyclerviewFlashcard.adapter = adapter


// Iterate over the options and convert DataSnapshot to MyModel
        // Iterate over the options and convert DataSnapshot to MyModel


        // Add item to arraylist
        for (i in 0 until adapter.itemCount) {
            val item = adapter.getItem(i)
            itemList.add(item)
        }
//        itemList = adapter.itemList
        Log.d("TAG", "loadInfoTopic: " + itemList.size);
    }

    fun loadTopic(topicList : ArrayList<TopicDomain>) {

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