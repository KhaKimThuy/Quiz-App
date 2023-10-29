package com.example.afinal.Activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.Adapter.VPAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.R
import com.example.afinal.databinding.FragmentFolderBinding
import com.example.afinal.databinding.FragmentLibraryBinding

class FragmentFolder : Fragment() {
    private lateinit var adapter: TopicListAdapter
    private lateinit var db: MyDB
    private lateinit var vpAdapter: VPAdapter
    private lateinit var binding : FragmentFolderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_folder, container, false)
    }


//    private fun loadTopic(){
//        val options = db.RecyclerTopic()
//        adapter = TopicListAdapter(options)
//        recyclerView.adapter = adapter
//    }
//
//    override fun onStart() {
//        super.onStart()
//        adapter.startListening()
//    }
//
//    override fun onStop() {
//        super.onStop()
//        adapter.stopListening()
//    }
}