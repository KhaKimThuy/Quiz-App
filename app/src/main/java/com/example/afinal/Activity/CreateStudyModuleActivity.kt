package com.example.afinal.Activity

import android.Manifest.permission.WRITE_EXTERNAL_STORAGE
import android.R
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Build.VERSION.SDK_INT
import android.os.Bundle
import android.os.Environment
import android.provider.Settings
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.AddItemTopicAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.databinding.ActivityCreateStudyModuleBinding
import java.io.BufferedReader
import java.io.File
import java.io.FileNotFoundException
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.util.Scanner


class CreateStudyModuleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateStudyModuleBinding
    private lateinit var itemList: ArrayList<FlashCardDomain>
    private lateinit var adapter: AddItemTopicAdapter
    private lateinit var db: MyDB
    private lateinit var topic : TopicDomain
    private val READ_EXTERNAL_STORAGE_REQUEST = 101
    private val PICK_CSV_FILE_REQUEST = 102
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStudyModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup DB
        db = MyDB()
        itemList = ArrayList()

        // Init data
        itemList.add(FlashCardDomain())
        itemList.add(FlashCardDomain())

        adapter = AddItemTopicAdapter(itemList)
        binding.recyclerViewAddTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewAddTopic.adapter = adapter

        binding.imageViewAddItem.setOnClickListener(View.OnClickListener {
            itemList.add(FlashCardDomain())
            adapter.notifyItemInserted(itemList.size - 1)
        })

        binding.checkOk.setOnClickListener(View.OnClickListener {
            addTopic()
        })

        binding.imgImport.setOnClickListener(View.OnClickListener {
            importCSVFile()
        })

        binding.imgBack.setOnClickListener(View.OnClickListener {
            onBackPressed();
        })

    }

    private fun loadCSVFile(uri: Uri) {
        try {
            itemList.clear()
            val inputStream: InputStream? = contentResolver.openInputStream(uri)
            val reader = BufferedReader(InputStreamReader(inputStream))
            var line : String?
            while (reader.readLine().also { line = it } != null){
                val row : List<String> = line!!.split(",")

                var fc = FlashCardDomain()
                fc.engLanguage = row[0]
                fc.vnLanguage = row[1]

                itemList.add(fc)
                adapter.notifyDataSetChanged()
            }
            inputStream?.close() // Close the inputStream when done
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(this, "Import file error", Toast.LENGTH_SHORT).show()
        }
    }

    private fun importCSVFile() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE), READ_EXTERNAL_STORAGE_REQUEST)
        } else {
            openFilePicker()
        }

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            READ_EXTERNAL_STORAGE_REQUEST -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    openFilePicker()
                } else {
                }
            }
        }
    }

    private fun openFilePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            type = "*/*"
            addCategory(Intent.CATEGORY_OPENABLE)
        }
        intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(intent, PICK_CSV_FILE_REQUEST)
    }

    private fun addTopic() {
        topic = TopicDomain()
        val topicPK = db.GetTopic().push().key
        topic.topicName = binding.edtTopicName.text.toString()
        topic.isPublic = binding.switchIsPublic.isChecked

        if (topicPK != null) {
            topic.topicPK = topicPK
        }

        // Add topic
        db.CreateTopicWithItems(topic, itemList)

        TopicDTO.currentTopic = topic
        TopicDTO.numItems = itemList.size.toString()

        val intent = Intent(this, DetailTopicActivity::class.java)
        startActivity(intent)

        finish()
    }

    private var fileuri: Uri? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_CSV_FILE_REQUEST && data != null) {
            fileuri = data.data
            fileuri?.let { loadCSVFile(it) }
        }
    }
}












































