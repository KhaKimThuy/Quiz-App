package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.RankingAdapter
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.Domain.TopicPublic
import com.example.afinal.Domain.User
import com.example.afinal.databinding.ActivityRankingBinding
import com.squareup.picasso.Picasso

class ActivityRanking : AppCompatActivity() {
    private lateinit var binding: ActivityRankingBinding
    private lateinit var userRanking: ArrayList<TopicPublic>
    private lateinit var topicDAL : TopicDAL
    private lateinit var adapter: RankingAdapter
    private lateinit var topicId: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        topicDAL = TopicDAL()
        userRanking = ArrayList<TopicPublic>()
//        topicDAL.GetRankingOfTopic(this)
        // fakeData()
        topicId = intent.getStringExtra("topicId").toString()
        loadUserRanking()

        binding.imageViewBack.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    fun loadUserRanking() {
        var topNum = 0
        TopicDAL().GetRankingTable(topicId) {
            if (it.size > 0) {
                if (it.size == 1) {
                    binding.username1.text = it[0].username
                    binding.score1.text = it[0].highestScore.toString()
                    val url = it[0].avatarUrl
                    if (url != "") {
                        Picasso.get().load(url).into(binding.ava1)
                    }
                    topNum = 1
                }

                else if (it.size == 2) {
                    binding.username1.text = it[0].username
                    binding.score1.text = it[0].highestScore.toString()
                    val url1 = it[0].avatarUrl
                    if (url1 != "") {
                        Picasso.get().load(url1).into(binding.ava1)
                    }

                    binding.username2.text = it[1].username
                    binding.score2.text = it[1].highestScore.toString()
                    val url2 = it[1].avatarUrl
                    if (url2 != "") {
                        Picasso.get().load(url2).into(binding.ava2)
                    }
                    topNum = 2
                }

                else {
                    binding.username1.text = it[0].username
                    binding.score1.text = it[0].highestScore.toString()
                    val url1 = it[0].avatarUrl
                    if (url1 != "") {
                        Picasso.get().load(url1).into(binding.ava1)
                    }

                    binding.username2.text = it[1].username
                    binding.score2.text = it[1].highestScore.toString()
                    val url2 = it[1].avatarUrl
                    if (url2 != "") {
                        Picasso.get().load(url2).into(binding.ava2)
                    }


                    binding.username3.text = it[2].username
                    binding.score3.text = it[2].highestScore.toString()
                    val url3 = it[2].avatarUrl
                    if (url3 != "") {
                        Picasso.get().load(url2).into(binding.ava3)
                    }
                    topNum = 3
                }

                for (topIdx in 0..<topNum) {
                    it.removeAt(topIdx)
                }
            }
            binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
            adapter = RankingAdapter(it, topNum)
            binding.recyclerView.adapter = adapter
        }
    }

    fun fakeData() {
        var topicPublics : ArrayList<TopicPublic> = ArrayList<TopicPublic>()
        var users : ArrayList<User> = ArrayList<User>()
        for (i in 0..10) {
            var d = TopicPublic()
            d.highestScore = 230 + i
            topicPublics.add(d)

            var e = User()
            e.username = "User $i"
            users.add(e)
        }
        //loadUserRanking(topicPublics, users)
    }
}