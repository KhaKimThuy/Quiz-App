package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.afinal.R
import com.example.afinal.databinding.ActivitySettingBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

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

        btn = rootView.findViewById(R.id.cardView_profile)
        btn.setOnClickListener(View.OnClickListener{
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        })


        // Inflate the layout for this fragment
        return rootView
    }

}