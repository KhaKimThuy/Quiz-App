package com.example.afinal.Activity

import android.app.Dialog
import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.TopicInFolderAdapter
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.Dialog.FolderDialog
import com.example.afinal.Domain.FolderDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.Interface.ValueEventListenerCallback
import com.example.afinal.databinding.ActivityDetailFolderBinding
import com.google.firebase.database.DatabaseError


class DetailFolderActivity : AppCompatActivity(){
    private lateinit var binding : ActivityDetailFolderBinding
    private lateinit var db: MyDB
    private lateinit var adapter : TopicInFolderAdapter
    lateinit var curFolder : FolderDomain
    val ADD_TOPIC_TO_FOLDER = 100
    var topicList : ArrayList<TopicDomain> = ArrayList<TopicDomain>()
    var topicIDs : ArrayList<String> = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init Firebase
        db = MyDB()
        //curFolder = FolderDomain()

        // Get selected folder PK
        curFolder = intent.getParcelableExtra("folder")!!

        db.GetListTopicIDFromTF(this, topicIDs, curFolder)

        binding.imgMore.setOnClickListener(View.OnClickListener {
            showOptionsMenu(it)
        })

        binding.imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed();
        })

        binding.imageViewAddItem.setOnClickListener(View.OnClickListener {
            val intent = Intent(application, AddTopicToFolderActivity::class.java)
            intent.putExtra("folder", curFolder)
            startActivityForResult(intent, ADD_TOPIC_TO_FOLDER)
        })


        binding.imgDel.setOnClickListener(View.OnClickListener {
            if (adapter.selectedTopics.size > 0) {
                var count = Integer.parseInt(binding.textViewNumTopic.text.toString()) - adapter.selectedTopics.size
                for (i in adapter.selectedTopics) {
                    db.DeleteTopicFromFolder(i, curFolder)
                    adapter.selectedTopics.remove(i)
                    adapter.notifyDataSetChanged()
                }
                binding.textViewNumTopic.text = "$count"
                binding.imgDel.visibility = View.INVISIBLE
            }
        })
    }
    fun loadFolder(topicList : ArrayList<TopicDomain>){
        binding.recyclerViewTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = TopicInFolderAdapter(this, topicList)
        binding.recyclerViewTopic.adapter = adapter

        db.GetTheNumberOfTopicsInFolder(curFolder.folderPK, object : ValueEventListenerCallback{
            override fun onDataChange(dataSnapshot: Long) {
                binding.textViewNumTopic.text = "$dataSnapshot"
            }

            override fun onDataChange(dataSnapshot: FolderDomain) {
                TODO("Not yet implemented")
            }

            override fun onDataChange(topicIDs: ArrayList<String>) {
                TODO("Not yet implemented")
            }

            override fun onDataChangeTopic(dataSnapshot: TopicDomain) {
                TODO("Not yet implemented")
            }

            override fun onCancelled(databaseError: DatabaseError) {
            }

        }).toString()

        // The number of topics in current folder
        binding.textViewNumTopic.text = binding.recyclerViewTopic.childCount.toString()
        binding.tvFolderCurName.text = curFolder.folderName
    }

    fun checkDeleteItem() {
        if (adapter.selectedTopics.size > 0) {
            binding.imgDel.visibility = View.VISIBLE
        } else {
            binding.imgDel.visibility = View.INVISIBLE
        }
    }

    fun updateUI() {
        binding.tvFolderCurName.text = curFolder.folderName
    }

    private fun showOptionsMenu(anchorView: View) {
        val popupMenu = PopupMenu(this, anchorView)
        val inflater = popupMenu.menuInflater
        inflater.inflate(com.example.afinal.R.menu.detail_folder_menu, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item ->
            when (item.itemId) {
                com.example.afinal.R.id.edit -> {
                    // Handle edit action
                    val dialog = Dialog(this)
                    val folderDialog = FolderDialog("Sửa thư mục", curFolder.folderName, curFolder.folderDesc, this)
                    folderDialog.show(supportFragmentManager, "Folder dialog")
                    dialog.dismiss()
//                    onPositiveButtonClick()
                    true
                }
                com.example.afinal.R.id.delete -> {
//                     Handle delete action
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
                db.DeleteFolder(curFolder)
            }
            .setNegativeButton("Hủy", null)
            .show()
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == ADD_TOPIC_TO_FOLDER && resultCode == RESULT_OK) {
            Log.d("TAG", "Result from intent")
//            curFolder = db.GetFolderByID(curFolder.folderPK) as FolderDomain
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d("TAG", "OnStart in detail folder")
    }

    override fun onResume() {
        super.onResume()
        Log.d("TAG", "OnResume in detail folder " + curFolder.folderName)
    }

    override fun onStop() {
        super.onStop()
    }

//    override fun onPositiveButtonClick() {
//        Log.d("TAG", "NONOONONONONON")
//    }

}