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
import com.example.afinal.databinding.FragmentSettingsBinding
import com.example.afinal.databinding.FragmentStudyModuleBinding

class FragmentSettings : Fragment() {
    private lateinit var btn : CardView
    private lateinit var binding : FragmentSettingsBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.cardViewProfile.setOnClickListener(View.OnClickListener{
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        })
        binding.cardViewFlashCard.setOnClickListener(View.OnClickListener{
            val intent = Intent(activity, ProfileActivity::class.java)
            startActivity(intent)
        })
    }

}