package com.example.afinal.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.FolderAdapter
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DAL.MyDB
import com.example.afinal.databinding.FragmentFolderBinding

class FragmentFolder : Fragment() {
    private lateinit var adapter: FolderAdapter
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
        binding.recyclerViewFolder.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
//        loadFolder()
    }

    private fun loadFolder(){
        val userId = MyDB().dbAuth.currentUser?.uid.toString()
        FolderDAL().GetFolderOfUser(userId) {
            adapter = FolderAdapter(it, requireActivity())
            binding.recyclerViewFolder.adapter = adapter
        }
    }

    override fun onStart() {
        loadFolder()
        super.onStart()
    }
}