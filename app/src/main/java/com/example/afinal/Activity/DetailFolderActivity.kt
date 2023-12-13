package com.example.afinal.Activity

import android.annotation.SuppressLint
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.TopicAdapter
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DTO.FolderDTO
import com.example.afinal.Domain.Topic
import com.example.afinal.R
import com.example.afinal.databinding.ActivityDetailFolderBinding


class DetailFolderActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDetailFolderBinding
    private lateinit var adapter : TopicAdapter
    private var selectedTopic : ArrayList<Topic> = ArrayList<Topic>()
    val ADD_TOPIC_TO_FOLDER = 100

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        init()

        binding.imgMore.setOnClickListener(View.OnClickListener {
            showOptionsMenu(it)
        })

        binding.imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed();
        })
        binding.imageViewAddItem.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, AddTopicToFolderActivity::class.java)
            startActivityForResult(intent, ADD_TOPIC_TO_FOLDER)
        })


//        binding.imgDel.setOnClickListener(View.OnClickListener {
//            if (adapter.selectedTopics.size > 0) {
//                var count = Integer.parseInt(binding.textViewNumTopic.text.toString()) - adapter.selectedTopics.size
//                for (i in adapter.selectedTopics) {
//                    db.DeleteTopicFromFolder(i, curFolder)
//                    adapter.selectedTopics.remove(i)
//                    adapter.notifyDataSetChanged()
//                }
//                binding.textViewNumTopic.text = "$count"
//                binding.imgDel.visibility = View.INVISIBLE
//            }
//        })
    }

    private fun init() {
        binding.recyclerViewTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        loadFolder()
    }

    fun loadFolder() {
        if (FolderDTO.currentFolder != null) {
            FolderDAL().GetTopicOfFolder(FolderDTO.currentFolder!!) {
                binding.textViewNumTopic.text = FolderDTO.topicList.size.toString()
                adapter = TopicAdapter(FolderDTO.topicList, object : TopicAdapter.IClickTopicListener {
                    override fun onClickTopicListener(topicView: TopicAdapter.TopicViewHolder, position: Int) {
                        TODO("Not yet implemented")
                    }

                    @SuppressLint("ResourceAsColor")
                    override fun onLongClickTopicListener(topicView: TopicAdapter.TopicViewHolder, position: Int) {
                        val backgroundDrawable = topicView.mainView.background
                        val backgroundColor = (backgroundDrawable as ColorDrawable).color
                        if (backgroundColor ==  R.color.delete_topic_marker_color) {
                            topicView.mainView.setBackgroundColor(Color.WHITE)
                            if (selectedTopic != null) {
                                selectedTopic.remove(FolderDTO.topicList[position])
                            }
                        } else {
                            topicView.mainView.setBackgroundColor(R.color.delete_topic_marker_color)
                            if (selectedTopic != null) {
                                selectedTopic.add(FolderDTO.topicList[position])
                            }
                        }
                    }

                })
                binding.recyclerViewTopic.adapter = adapter
                binding.progressBar2.visibility = View.GONE
            }
        }

        // The number of topics in current folder
        binding.tvFolderCurName.text = FolderDTO.currentFolder?.folderName
    }

    fun checkDeleteItem() {
//        if (adapter.selectedTopics.size > 0) {
//            binding.imgDel.visibility = View.VISIBLE
//        } else {
//            binding.imgDel.visibility = View.INVISIBLE
//        }
    }


    private fun showOptionsMenu(anchorView: View) {
//        val popupMenu = PopupMenu(this, anchorView)
//        val inflater = popupMenu.menuInflater
//        inflater.inflate(com.example.afinal.R.menu.detail_folder_menu, popupMenu.menu)
//
//        popupMenu.setOnMenuItemClickListener { item ->
//            when (item.itemId) {
//                com.example.afinal.R.id.edit -> {
//                    // Handle edit action
//                    val dialog = Dialog(this)
//                    val folderDialog = FolderDialog("Sửa thư mục", curFolder.folderName, curFolder.folderDesc, this)
//                    folderDialog.show(supportFragmentManager, "Folder dialog")
//                    dialog.dismiss()
////                    onPositiveButtonClick()
//                    true
//                }
//                com.example.afinal.R.id.delete -> {
////                     Handle delete action
//                    deleteDialog()
//                    true
//                }
//                else -> false
//            }
//        }
//        popupMenu.show()
    }

    @Throws(Resources.NotFoundException::class)
    private fun deleteDialog() {
//        AlertDialog.Builder(this)
//            .setMessage("Bạn chắc chắn muốn xóa thư mục này vĩnh viễn?")
//            .setPositiveButton("Xóa") { dialog, which ->
//                db.DeleteFolder(curFolder)
//            }
//            .setNegativeButton("Hủy", null)
//            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == ADD_TOPIC_TO_FOLDER) {
            if (data != null) {
                val moreAddedTopics = data.getParcelableArrayExtra("moreAddedTopics") as ArrayList<Topic>
                FolderDTO.topicList.addAll(moreAddedTopics)
                adapter.notifyDataSetChanged()
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "OnStart in detail folder")
    }

    override fun onResume() {
        super.onResume()
//        Log.d("TAG", "OnResume in detail folder " + curFolder.folderName)
    }

    override fun onStop() {
        super.onStop()
    }

//    override fun onPositiveButtonClick() {
//        Log.d("TAG", "NONOONONONONON")
//    }

}