package com.example.afinal.Activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.afinal.Adapter.TopicListAdapter
import com.example.afinal.Adapter.VPAdapter
import com.example.afinal.DB.MyDB
import com.example.afinal.R
import com.example.afinal.databinding.FragmentLibraryBinding
import com.example.afinal.databinding.FragmentStudyModuleBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentStudyModule.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentStudyModule : Fragment() {
    private lateinit var adapter: TopicListAdapter
    private lateinit var db: MyDB
    private lateinit var binding : FragmentStudyModuleBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStudyModuleBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        db = MyDB()
        binding.recyclerViewTopicList.layoutManager = LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
        loadTopic()
    }
    private fun loadTopic(){
        val options = db.RecyclerTopic()
        adapter = TopicListAdapter(options)
        binding.recyclerViewTopicList.adapter = adapter
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