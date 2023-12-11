package com.example.afinal.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import com.example.afinal.Adapter.FlashCardAdapter
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Domain.TopicPublicDomain
import com.example.afinal.databinding.ActivityDetailTopicBinding


class DetailTopicActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTopicBinding
    private lateinit var topicDAL: TopicDAL
    private lateinit var adapter : FlashCardAdapter
    private var isMine : Boolean = false
    var itemList: ArrayList<FlashCardDomain> = ArrayList<FlashCardDomain>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailTopicBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init ()

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

        if (isMine) {
            binding.saveTopic.visibility = View.GONE
            binding.imgMore.visibility = View.VISIBLE
            binding.imgMore.setOnClickListener(View.OnClickListener {
                showOptionsMenu(it)
            })

        } else {
            binding.imgMore.visibility = View.GONE
            binding.saveTopic.visibility = View.VISIBLE
            binding.saveTopic.setOnClickListener(View.OnClickListener {
                if (TopicDTO.currentTopic != null) {
                    TopicDAL().AddPublicTopic(TopicDTO.currentTopic!!, TopicDTO.itemList) {
                        if (it) {
                            Toast.makeText(this, "Okkkk", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    private fun init () {
        isMine = intent.getBooleanExtra("isMine", false)
        binding.recyclerviewFlashcard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerviewFlashcard)

        loadInfoTopic()
    }



    private fun showOptionsMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView)
        val inflater = popupMenu.menuInflater
        inflater.inflate(com.example.afinal.R.menu.detail_topic_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                com.example.afinal.R.id.edit -> {
//                    // Handle edit action
//                    val intent = Intent(this, EditTopicActivity::class.java)
//                    intent.putExtra("topic", topic)
//                    val bundle = Bundle()
//                    val itemList: ArrayList<FlashCardDomain> = ArrayList<FlashCardDomain>()
//                    for (i in 0 until adapter.itemCount) {
//                        val item = adapter.getItem(i)
//                        itemList.add(item)
//                    }
//                    bundle.putParcelableArrayList("itemList", itemList)
//                    intent.putExtras(bundle)
//                    startActivity(intent)
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
        binding.textViewNumItems.text = "${TopicDTO.numItems} Thuật ngữ"

        TopicDTO.currentTopic?.let { it ->
            TopicDAL().GetItemOfTopic(it.topicPK) {
                TopicDTO.itemList = it
                
                // Load items of current topic
                adapter = FlashCardAdapter(TopicDTO.itemList, this)
                Log.d("TAG", "Item list size in detail topic activity = " + adapter.itemCount)
                binding.recyclerviewFlashcard.adapter = adapter
            }
        }
    }

    @Throws(Resources.NotFoundException::class)
    private fun deleteDialog() {
        AlertDialog.Builder(this)
            .setMessage("Bạn chắc chắn muốn xóa học phần này vĩnh viễn?")
            .setPositiveButton("Xóa") { _, _ ->
                TopicDTO.currentTopic?.let { TopicDAL().DeleteTopic(it) }
//                 Return to library fragment
//                onDestroy()
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

}