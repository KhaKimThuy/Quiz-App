package com.example.afinal.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.afinal.Activity.ProfileActivity
import com.example.afinal.R

/**
 * A simple [Fragment] subclass.
 * Use the [FragmentSettings.newInstance] factory method to
 * create an instance of this fragment.
 */
class FragmentSettings : Fragment() {
    private lateinit var btn : CardView
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        btn = rootView.findViewById(R.id.cardView_flashCard)
        btn.setOnClickListener(View.OnClickListener{
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        })


        // Inflate the layout for this fragment
        return rootView
    }

}