package com.example.afinal.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.AddItemTopicAdapter
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.FlashCardDomain
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.databinding.ActivityCreateStudyModuleBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import kotlin.properties.Delegates

class CreateStudyModuleActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateStudyModuleBinding
    private lateinit var itemList: ArrayList<FlashCardDomain>
    private lateinit var adapter: AddItemTopicAdapter
    private lateinit var db: MyDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateStudyModuleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup DB
        db = MyDB()
        itemList = ArrayList()

        // Init data
        itemList.add(FlashCardDomain("", ""))
        itemList.add(FlashCardDomain("", ""))

        adapter = AddItemTopicAdapter(itemList)
        binding.recyclerViewAddTopic.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        binding.recyclerViewAddTopic.adapter = adapter

        binding.imageViewAddItem.setOnClickListener(View.OnClickListener {
            itemList.add(FlashCardDomain("", ""))
            adapter.notifyItemInserted(itemList.size - 1)
        })

        binding.checkOk.setOnClickListener(View.OnClickListener {
            addTopic()
        })

    }

    private fun addTopic(){

        // Add topic
        val topicRef = db.GetTopic()
        val topic = TopicDomain()
        val topicPK = topicRef.push().key

        topic.topicName = binding.edtTopicName.text.toString()
        if (topicPK != null) {
            topic.topicPK = topicPK
        }

        topicRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                if (topicPK?.let { snapshot.hasChild(it) } == true){
                    Toast.makeText(applicationContext,"Topic name is already used", Toast.LENGTH_SHORT).show()
                }else{
                    if (topicPK != null) {
                        topicRef.child(topicPK).setValue(topic)
                    }
                    var intent = Intent(applicationContext, MainActivity2::class.java)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

        // Add items
        val itemRef = db.GetItem()
        itemRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                for (item in itemList){
                    if(!item.engLanguage.isNullOrEmpty() && !item.vnLanguage.isNullOrEmpty()){
                        if (topicPK != null) {
                            item.topicPK = topicPK
                        }
                        itemRef.child(itemRef.push().key!!).setValue(item)
                    }
                }
                Toast.makeText(applicationContext, "Add topic successfully", Toast.LENGTH_SHORT).show()
            }
            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "Fail to add topic", Toast.LENGTH_SHORT).show()
            }
        })
    }
}