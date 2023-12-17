package com.example.afinal.Activity

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.TopicAdapter
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DAL.TopicFolderDAL
import com.example.afinal.DTO.FolderDTO
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Dialog.EditFolderDialog
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

        binding.imgDel.setOnClickListener(View.OnClickListener {
            Log.d("DeleteTopicFolder", "Selected topic size : " + selectedTopic.size)
            if (selectedTopic.size > 0) {
                var count = Integer.parseInt(binding.textViewNumTopic.text.toString()) - selectedTopic.size
                FolderDTO.currentFolder?.let { it1 ->
                    TopicFolderDAL().DeleteTFs(selectedTopic,
                        it1
                    ) {
                        FolderDTO.topicList.removeAll(selectedTopic)
                        adapter.notifyDataSetChanged()
                        selectedTopic.clear()
                    }
                }
//                for (i in selectedTopic) {
//                    FolderDTO.currentFolder?.let { it1 ->
//                        TopicFolderDAL().DeleteTF()(i,
//                            it1
//                        )
//                    }
//
//                }
                binding.textViewNumTopic.text = "$count"
                binding.imgDel.visibility = View.INVISIBLE
            }
        })
    }

    private fun init() {
        binding.recyclerViewTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        loadFolder()
    }

    fun loadFolder() {
        if (FolderDTO.currentFolder != null) {
            FolderDTO.topicList.clear()
            FolderDAL().GetTopicOfFolder(FolderDTO.currentFolder!!) {
                binding.textViewNumTopic.text = FolderDTO.topicList.size.toString()

                if (FolderDTO.topicList.size == 0) {
                    binding.emptyFolder.visibility = View.VISIBLE
                }

                binding.tvDesc.text =  FolderDTO.currentFolder!!.folderDesc
                binding.emptyFolder.visibility = View.GONE
                adapter = TopicAdapter(FolderDTO.topicList, object : TopicAdapter.IClickTopicListener {

                    @SuppressLint("ResourceAsColor")
                    override fun onClickTopicListener(topicView: TopicAdapter.TopicViewHolder, position: Int) {
                        if (selectedTopic.size == 0) {
                            TopicDTO.currentTopic = FolderDTO.topicList[position]
                            TopicDTO.numItems = topicView.numItem.text.toString()
                            val intent = Intent(applicationContext, DetailTopicActivity::class.java)
                            if (FolderDTO.topicList[position].userPK == UserDTO.currentUser?.userPK) {
                                intent.putExtra("isMine", true)
                            } else {
                                intent.putExtra("isMine", false)
                            }
                            intent.putExtra("isSaved", true)
                            intent.putExtra("from", "TopicFromFolder")
                            startActivity(intent)
                        }
                    }

                    @SuppressLint("ResourceAsColor")
                    override fun onLongClickTopicListener(topicView: TopicAdapter.TopicViewHolder, position: Int) {
                        val backgroundDrawable = topicView.mainView.background
                        val backgroundColor = (backgroundDrawable as ColorDrawable).color
                        if (backgroundColor ==  R.color.delete_topic_marker_color) {
                            topicView.mainView.setBackgroundColor(Color.WHITE)
                            selectedTopic.remove(FolderDTO.topicList[position])
                        } else {
                            topicView.mainView.setBackgroundColor(R.color.delete_topic_marker_color)
                            selectedTopic.add(FolderDTO.topicList[position])
                        }
                        if (selectedTopic.size > 0) {
                            binding.imgDel.visibility = View.VISIBLE
                        } else {
                            binding.imgDel.visibility = View.GONE
                        }
                    }

                })


                binding.recyclerViewTopic.adapter = adapter
                binding.progressBar2.visibility = View.GONE

                if (FolderDTO.topicList.size == 0) {
                    binding.emptyFolder.visibility = View.VISIBLE
                }
            }
        }

        binding.progressBar2.visibility = View.GONE
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
        val popupMenu = PopupMenu(this, anchorView)
        val inflater = popupMenu.menuInflater
        inflater.inflate(com.example.afinal.R.menu.detail_folder_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                R.id.edit -> {
                    // Handle edit action
                    val dialog = Dialog(this)
                    FolderDTO.currentFolder?.let { EditFolderDialog("Sửa thư mục", it, this) }
                        ?.show(supportFragmentManager, "Folder dialog")
                    dialog.dismiss()
                    true
                }
                R.id.delete -> {
                    deleteDialog()
                    true
                }
                else -> false
            }
        }
        popupMenu.show()
    }

    @Throws(Resources.NotFoundException::class)
    private fun deleteDialog() {
        AlertDialog.Builder(this)
            .setMessage("Bạn chắc chắn muốn xóa thư mục này vĩnh viễn?")
            .setPositiveButton("Xóa") { dialog, which ->
                FolderDTO.currentFolder?.let { FolderDAL().DeleteFolder(it) }
                finish()
            }
            .setNegativeButton("Hủy", null)
            .show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == RESULT_OK && requestCode == ADD_TOPIC_TO_FOLDER) {
            if (data != null) {
                if (FolderDTO.topicList.size > 0) {
                    binding.textViewNumTopic.text = FolderDTO.topicList.size.toString()
                    binding.emptyFolder.visibility = View.GONE
//                    adapter.notifyDataSetChanged()

                    loadFolder()
                }
            }
        }
    }

    fun updateUI() {
        binding.tvFolderCurName.text = FolderDTO.currentFolder?.folderName ?: "No folder"
    }

}