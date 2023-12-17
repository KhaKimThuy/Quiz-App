package com.example.afinal.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import com.example.afinal.Activity.ActivityChangePassword
import com.example.afinal.Activity.EditProfileActivity
import com.example.afinal.Activity.LoginActivity
import com.example.afinal.Activity.OustandingVocabularyActivity
import com.example.afinal.Activity.ProvisionsAndPolicies
import com.example.afinal.Activity.YourSettingActivity
import com.example.afinal.DAL.MyDB
import com.example.afinal.DTO.UserDTO
import com.example.afinal.R
import com.example.afinal.databinding.FragmentSettingBinding
import com.example.afinal.databinding.FragmentSettingsBinding

class FragmentSettings : Fragment() {
    private lateinit var btn : CardView
    private lateinit var binding : FragmentSettingBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSettingBinding.inflate(inflater, container, false)
        return binding.root

        loadUserInfo()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnOustanding.setOnClickListener(View.OnClickListener{
            val intent = Intent(activity, OustandingVocabularyActivity::class.java)
            startActivity(intent)
        })
        binding.btnLogout.setOnClickListener(View.OnClickListener {
            // Sign out current user
            MyDB().dbAuth.signOut()

            val intent = Intent(requireActivity(), LoginActivity::class.java)
            startActivity(intent)
            requireActivity().finish()
        })

        binding.btnEditProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, EditProfileActivity::class.java)
            startActivity(intent)
        })

        binding.btnChangePassword.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, ActivityChangePassword::class.java)
            startActivity(intent)
        })

        binding.btnPolicies.setOnClickListener(View.OnClickListener {
            val intent = Intent(activity, ProvisionsAndPolicies::class.java)
            startActivity(intent)
        })

        binding.btnNightLight.setOnClickListener(View.OnClickListener {
            Toast.makeText(activity, "Chức năng sắp được ra mắt", Toast.LENGTH_SHORT).show()
        })

        binding.btnUpgrade.setOnClickListener(View.OnClickListener {
            Toast.makeText(activity, "Chức năng sắp được ra mắt", Toast.LENGTH_SHORT).show()
        })
    }

    private fun loadUserInfo() {
        binding.UserName.text = UserDTO.currentUser?.username ?: "Username"
        binding.tvName.text = UserDTO.currentUser?.username ?: "Chưa cập nhật"
        binding.tvEmailSt.text = UserDTO.currentUser?.email ?: "Email"

        val phone = arguments?.getString("phone")
        binding.tvPhone.text = phone ?: ""

        if (UserDTO.currentUser?.avatarUrl == "") {
            binding.ava.setImageResource(R.drawable.avt)
        } else {
            // Toast.makeText(requireContext(), UserDTO.currentUser?.avatarUrl, Toast.LENGTH_SHORT).show()
            binding.ava.setImageBitmap(UserDTO.userAvatar)
        }
    }


}