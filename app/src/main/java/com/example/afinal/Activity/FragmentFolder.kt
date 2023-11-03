package com.example.afinal.Activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.FolderListAdapter
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.Adapter.VPAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.R
import com.example.afinal.databinding.FragmentFolderBinding
import com.example.afinal.databinding.FragmentLibraryBinding
import com.example.afinal.databinding.FragmentStudyModuleBinding

class FragmentFolder : Fragment() {
    private lateinit var adapter: FolderListAdapter
    private lateinit var db: MyDB
    private lateinit var binding : FragmentFolderBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFolderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        db = MyDB()
        binding.recyclerViewFolder.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        loadFolder()
    }

    private fun loadFolder(){
        val options = db.RecyclerFolder()
        adapter = FolderListAdapter(options)
        binding.recyclerViewFolder.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        adapter.startListening()
    }

    override fun onStop() {
        super.onStop()
        adapter.stopListening()
    }

}