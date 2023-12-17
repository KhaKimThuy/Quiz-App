package com.example.afinal.Activity

import android.content.DialogInterface
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.PagerSnapHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.afinal.Adapter.FCLearningAdapter
import com.example.afinal.Adapter.MultipleChoiceAdapter
import com.example.afinal.DAL.ItemDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.R
import com.example.afinal.databinding.ActivityFlashCardStudyBinding


class FlashCardStudyActivity : AppCompatActivity() {

    private val handler = Handler()
    private val delayDuration = 3000L // Duration in milliseconds
    private var autoScrollRunnable: Runnable? = null

    private var currentPosition = 0
    private lateinit var layoutManager : LinearLayoutManager

    private lateinit var adapter: FCLearningAdapter
    lateinit var binding:ActivityFlashCardStudyBinding
    var auto = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFlashCardStudyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        showOptionsDialog()

        binding.imgViewQuit.setOnClickListener(View.OnClickListener {
            onDestroy()
        })

        binding.imageViewPlay.setOnClickListener(View.OnClickListener {
            if (auto) {
                binding.imageViewPlay.setImageResource(R.drawable.play_icon)
                stopAutoScroll()
                auto = false

                binding.recyclerView.layoutManager = object : LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return true
                    }
                }

            } else {
                binding.recyclerView.layoutManager = object : LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false) {
                    override fun canScrollHorizontally(): Boolean {
                        return false
                    }
                }

                binding.imageViewPlay.setImageResource(R.drawable.pause)
                startAutoScroll()
                auto = true
            }
        })
    }

    private fun init() {
        binding.tvLoad.text = TopicDTO.numItems

        binding.progressBar.max = TopicDTO.numItems.toInt()
        val snapHelper = PagerSnapHelper()
        snapHelper.attachToRecyclerView(binding.recyclerView)

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        layoutManager = binding.recyclerView.layoutManager as LinearLayoutManager


        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val visibleItemPosition = layoutManager.findFirstVisibleItemPosition()
                if (visibleItemPosition > currentPosition) {
                    // User has moved to the next item
                    // Perform the desired action here
                    binding.tvGet.text = (visibleItemPosition + 1).toString()
                    Log.d("TAG", "Not auto scroll in flashcard position : " + visibleItemPosition.toString() + 1)

                    binding.progressBar.progress += 1
                    currentPosition = visibleItemPosition
                }
            }
        })

        autoScrollRunnable = object : Runnable {
            override fun run() {

                Log.d("TAG", "current position : " + currentPosition)
                Log.d("TAG", "item count position : " + adapter.itemCount)
                if (currentPosition < adapter.itemCount) {
//                    binding.recyclerView.smoothScrollToPosition(currentPosition)

                    adapter.autoSpeak() {
                        adapter.flipFC() {
                            currentPosition += 1
                            moveToNext(currentPosition)
                            handler.postDelayed(this, delayDuration)
                        }
                    }

                }
            }
        }
    }



    private fun showOptionsDialog() {
        val options = arrayOf("Học tất cả", "Chỉ học từ vựng nổi bật")
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Chọn danh sách từ vựng")
            .setItems(options) { dialogInterface: DialogInterface, optionIndex: Int ->
                // Handle option selection
                when (optionIndex) {
                    0 -> {

                        binding.tvLoad.text = TopicDTO.allItemList.size.toString()
                        binding.progressBar.max = TopicDTO.allItemList.size
                        TopicDTO.numItems = TopicDTO.allItemList.size.toString()


                        adapter = FCLearningAdapter(this, TopicDTO.allItemList, object : FCLearningAdapter.onClickFCLearningListener {
                            override fun onClickFCListener(
                                itemView: FCLearningAdapter.FCViewHolder,
                                position: Int
                            ) {
                                if (TopicDTO.allItemList[position].isMarked) {
                                    TopicDTO.allItemList[position].isMarked = false
                                    itemView.marker.setImageResource(R.drawable.empty_star)
                                } else {
                                    TopicDTO.allItemList[position].isMarked = true
                                    itemView.marker.setImageResource(R.drawable.marked_star)
                                }
                                if (TopicDTO.currentTopic?.isPublic == true) {
                                    ItemDAL().UpdateMarkedRankingItem(TopicDTO.allItemList[position])
                                } else {
                                    ItemDAL().UpdateMarkedItem(TopicDTO.allItemList[position])
                                }
                            }

                        })
                        Log.d("TAG", "Num item in Flashcard study : " + TopicDTO.allItemList.size)
                        binding.recyclerView.adapter = adapter

                    }
                    1 -> {

                        binding.tvLoad.text = TopicDTO.itemList.size.toString()
                        binding.progressBar.max = TopicDTO.itemList.size
                        TopicDTO.numItems = TopicDTO.itemList.size.toString()

                        adapter = FCLearningAdapter(this, TopicDTO.itemList, object : FCLearningAdapter.onClickFCLearningListener {
                            override fun onClickFCListener(
                                itemView: FCLearningAdapter.FCViewHolder,
                                position: Int
                            ) {
                                if (TopicDTO.itemList[position].isMarked) {
                                    TopicDTO.itemList[position].isMarked = false
                                    itemView.marker.setImageResource(R.drawable.empty_star)
                                } else {
                                    TopicDTO.itemList[position].isMarked = true
                                    itemView.marker.setImageResource(R.drawable.marked_star)
                                }
                                if (TopicDTO.currentTopic?.isPublic == true) {
                                    ItemDAL().UpdateMarkedRankingItem(TopicDTO.itemList[position])
                                } else {
                                    ItemDAL().UpdateMarkedItem(TopicDTO.itemList[position])
                                }
                            }

                        })
                        Log.d("TAG", "Num item in Flashcard study : " + TopicDTO.itemList.size)
                        binding.recyclerView.adapter = adapter
                    }
                }
                dialogInterface.dismiss()
            }
        val dialog = builder.create()
        dialog.show()
    }

    fun startAutoScroll() {
        currentPosition = layoutManager.findFirstVisibleItemPosition()
        autoScrollRunnable?.let { handler.postDelayed(it, delayDuration) }
    }
    fun stopAutoScroll() {
        autoScrollRunnable?.let { handler.removeCallbacks(it) }
    }

    fun moveToNext(position : Int) {
        if (position < (adapter.itemCount)) {

            Log.d("TAG", "Auto scroll in flashcard")

            binding.recyclerView.layoutManager?.scrollToPosition(position)
            binding.progressBar.progress += 1
            binding.tvGet.text = (position + 1).toString()
        }
    }


    private fun loadFlashCards() {
        //handler = Handler(Looper.myLooper()!!)

        adapter = FCLearningAdapter(this, TopicDTO.itemList, object : FCLearningAdapter.onClickFCLearningListener {


            override fun onClickFCListener(
                itemView: FCLearningAdapter.FCViewHolder,
                position: Int
            ) {
                if (TopicDTO.itemList[position].isMarked) {
                    TopicDTO.itemList[position].isMarked = false
                    itemView.marker.setImageResource(R.drawable.empty_star)
                } else {
                    TopicDTO.itemList[position].isMarked = true
                    itemView.marker.setImageResource(R.drawable.marked_star)
                }
                if (TopicDTO.currentTopic?.isPublic == true) {
                    ItemDAL().UpdateMarkedRankingItem(TopicDTO.itemList[position])
                } else {
                    ItemDAL().UpdateMarkedItem(TopicDTO.itemList[position])
                }
            }

        })
        Log.d("TAG", "Num item in Flashcard study : " + TopicDTO.itemList.size)
        binding.recyclerView.adapter = adapter


    }


}