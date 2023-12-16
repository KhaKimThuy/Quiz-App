package com.example.afinal.Activity

import android.annotation.SuppressLint
import android.app.Activity
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
import com.example.afinal.DAL.ItemDAL
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.Item
import com.example.afinal.Domain.ItemRanking
import com.example.afinal.R
import com.example.afinal.databinding.ActivityDetailTopicBinding


class DetailTopicActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailTopicBinding
    private lateinit var adapter : FlashCardAdapter
    private var isMine : Boolean = false
    private var isSaved : Boolean = true
    private lateinit var topicFrom : String
    private val EDIT_TOPIC = 111

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

        // Ranking
        if (TopicDTO.currentTopic?.isPublic == true) {
            binding.itemViewRanking.visibility = View.VISIBLE
            binding.itemViewRanking.setOnClickListener(View.OnClickListener {
                val intent = Intent(this, ActivityRanking::class.java)
                intent.putExtra("topicId", TopicDTO.currentTopic?.topicPK)
                startActivity(intent)
            })
        } else {
            binding.itemViewRanking.visibility = View.GONE
        }

        binding.imgBack.setOnClickListener(View.OnClickListener {
            finish()
        })

        if (topicFrom == "FragmentHome") {
            // Kiểm tra user đã lưu topic đó hay chưa - later
            binding.relativeLayout.visibility = View.GONE
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
                binding.saveTopic.visibility = View.GONE
            })
        } else {
            binding.saveTopic.visibility = View.GONE
            binding.imgMore.visibility = View.VISIBLE
            binding.imgMore.setOnClickListener(View.OnClickListener {
                showOptionsMenu(it)
            })
        }
    }

    private fun init () {
        isMine = intent.getBooleanExtra("isMine", false)
        isSaved = intent.getBooleanExtra("isSaved", true)
        topicFrom = intent.getStringExtra("from").toString()

        binding.topicScore.text = TopicDTO.currentTopic?.highestScore.toString()

        binding.recyclerviewFlashcard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerviewFlashcard)

        loadInfoTopic()
    }

    private fun showOptionsMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView)
        val inflater = popupMenu.menuInflater
        inflater.inflate(R.menu.detail_topic_menu, popupMenu.menu)

        Log.d("TAG", "User PK = " + UserDTO.currentUser?.userPK)
        Log.d("TAG", "Topic user PK = " + TopicDTO.currentTopic?.userPK)

        if (TopicDTO.currentTopic?.isPublic == true &&
            TopicDTO.currentTopic?.userPK != UserDTO.currentUser?.userPK
        ) {
            val menuItem = popupMenu.menu.findItem(R.id.edit)
            menuItem.isVisible = false
        }

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit -> {
                    // Handle edit action
                    val intent = Intent(this, EditTopicActivity::class.java)
                    startActivityForResult(intent, EDIT_TOPIC)
                    true
                }
                R.id.delete -> {
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
        // Remove old items
        TopicDTO.allItemList.clear()
        TopicDTO.itemList.clear()

        // Load name and the number of items in current topic
        binding.tvTopicName.text = TopicDTO.currentTopic?.topicName ?: "Null"

        TopicDTO.currentTopic?.let { it ->
            TopicDAL().GetItemOfTopic(it.topicPK) {items ->
                Log.d("TAG", "CHECK size : " + items.size)
                TopicDTO.allItemList.addAll(items)

                binding.textViewNumItems.text = "${TopicDTO.allItemList.size} Thuật ngữ"

                Log.d("TAG", "Topic public : " + TopicDTO.currentTopic!!.isPublic)
                if (topicFrom == "FragmentHome") {
                    adapter = FlashCardAdapter(TopicDTO.allItemList, this, false, object :
                        FlashCardAdapter.IClickItemListener {
                        override fun onClickItemListener(itemView: FlashCardAdapter.ItemViewHolder, position: Int) {
                                // User only visit public topic
                        }
                    })
                } else {
                    adapter = FlashCardAdapter(TopicDTO.allItemList, this, true, object :
                        FlashCardAdapter.IClickItemListener {
                        override fun onClickItemListener(itemView: FlashCardAdapter.ItemViewHolder, position: Int) {
                            if (TopicDTO.allItemList[position].isMarked) {
                                TopicDTO.allItemList[position].isMarked = false
                                itemView.marker.setImageResource(R.drawable.empty_star)
                            } else {
                                TopicDTO.allItemList[position].isMarked = true
                                itemView.marker.setImageResource(R.drawable.marked_star)
                            }
                            ItemDAL().UpdateMarkedItem(TopicDTO.allItemList[position])
                            // Change item info on database
                        }
                    })
                }

                // Load items of current topic
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
                val resultIntent = Intent()
                resultIntent.putExtra("operation", "delete")
                setResult(Activity.RESULT_OK, resultIntent)
                finish()
//                val intent = Intent(this, MainActivity2::class.java)
//                startActivity(intent)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_TOPIC) {
            loadInfoTopic()
        }
    }

}