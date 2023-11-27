package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.RankingAdapter
import com.example.afinal.DB.TopicDAL
import com.example.afinal.Domain.TopicPublicDomain
import com.example.afinal.Domain.UserDomain
import com.example.afinal.databinding.ActivityRankingBinding

class ActivityRanking : AppCompatActivity() {
    private lateinit var binding: ActivityRankingBinding
    private lateinit var userRanking: ArrayList<TopicPublicDomain>
    private lateinit var topicDAL : TopicDAL
    private lateinit var adapter: RankingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRankingBinding.inflate(layoutInflater)
        setContentView(binding.root)
        topicDAL = TopicDAL()
        userRanking = ArrayList<TopicPublicDomain>()
//        topicDAL.GetRankingOfTopic(this)
        fakeData()



    }

    fun loadUserRanking(topicPublics : ArrayList<TopicPublicDomain>,
                        userRankings: ArrayList<UserDomain>) {

        binding.username1.text = userRankings[0].username
        binding.score1.text = topicPublics[0].highestScore.toString()

        binding.username2.text = userRankings[1].username
        binding.score2.text = topicPublics[1].highestScore.toString()

        binding.username3.text = userRankings[2].username
        binding.score3.text = topicPublics[2].highestScore.toString()

        for (topIdx in 0..2) {
            topicPublics.removeAt(topIdx)
            userRankings.removeAt(topIdx)
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        adapter = RankingAdapter(topicPublics, userRankings)
        binding.recyclerView.adapter = adapter
    }

    fun fakeData() {
        var topicPublics : ArrayList<TopicPublicDomain> = ArrayList<TopicPublicDomain>()
        var users : ArrayList<UserDomain> = ArrayList<UserDomain>()
        for (i in 0..10) {
            var d = TopicPublicDomain()
            d.highestScore = 230 + i
            topicPublics.add(d)

            var e = UserDomain()
            e.username = "User $i"
            users.add(e)
        }
        loadUserRanking(topicPublics, users)
    }
}