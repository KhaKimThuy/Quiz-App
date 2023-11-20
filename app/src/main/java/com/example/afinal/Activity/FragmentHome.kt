package com.example.afinal.Activity

import android.R.attr
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.afinal.DB.MyDB
import com.example.afinal.databinding.FragmentHomeBinding


class FragmentHome : Fragment() {
    private lateinit var binding : FragmentHomeBinding
    private lateinit var db: MyDB
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.search.setOnKeyListener(object : View.OnKeyListener {
//            override fun onKey(v: View?, keyCode: Int, event: KeyEvent): Boolean {
//                // If the event is a key-down event on the "enter" button
//                if (event.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER) {
//                    // Perform action on key press
//                    Toast.makeText(context, "ddddddd", Toast.LENGTH_SHORT).show()
//                    return true
//                }
//                return false
//            }
//        })


        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

            override fun onQueryTextSubmit(query: String?): Boolean {
                // Do your task here
                if (fragmentManager != null) {
                    fragmentManager!!.popBackStack()
                    val fragment = SearchResultFragment()
                    val arguments = Bundle()
                    arguments.putString("search", binding.search.query.toString())
                    fragment.arguments = arguments

                    val fragmentTransaction: FragmentTransaction = fragmentManager!!.beginTransaction()
                    if (fragment != null) {
                        fragmentTransaction.replace(com.example.afinal.R.id.frameLayout, fragment)
                    }
                    if (fragment != null) {
                        fragmentTransaction.addToBackStack(fragment.tag)
                    }
                    fragmentTransaction.commit()
                }
                return false
            }
        })

    }
}