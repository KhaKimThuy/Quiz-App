package com.example.afinal.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.afinal.Activity.LoginActivity
import com.example.afinal.Activity.OustandingVocabularyActivity
import com.example.afinal.Activity.ProfileActivity
import com.example.afinal.Activity.YourSettingActivity
import com.example.afinal.DAL.MyDB
import com.example.afinal.databinding.FragmentSettingsBinding

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
            val intent = Intent(activity, YourSettingActivity::class.java)
            startActivity(intent)
        })

        binding.cardViewFlashCard.setOnClickListener(View.OnClickListener{
            val intent = Intent(activity, OustandingVocabularyActivity::class.java)
            startActivity(intent)
        })
        binding.cardViewLogout.setOnClickListener(View.OnClickListener {
            // Sign out current user
            MyDB().dbAuth.signOut()

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })
    }

}