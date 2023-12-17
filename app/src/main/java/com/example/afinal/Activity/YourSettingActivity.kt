package com.example.afinal.Activity;

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View
import android.widget.Toast
import com.example.afinal.DAL.MyDB
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Fragment.FragmentSettings

import com.example.afinal.R;
import com.example.afinal.databinding.ActivityEditProfileBinding
import com.example.afinal.databinding.ActivityYourSettingBinding

class YourSettingActivity : AppCompatActivity() {
    private lateinit var binding : ActivityYourSettingBinding
    private val EDIT_PROFILE = 222
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYourSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadUserInfo()

        binding.btnEditProfile.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivityForResult(intent, EDIT_PROFILE)
        })

        binding.btnChangePassword.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ActivityChangePassword::class.java)
            startActivity(intent)
        })

        binding.imgBack.setOnClickListener {
            finish()
        }

        binding.btnPolicies.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, ProvisionsAndPolicies::class.java)
            startActivity(intent)
        })

        binding.btnNightLight.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Chức năng sắp được ra mắt", Toast.LENGTH_SHORT).show()
        })

        binding.btnUpgrade.setOnClickListener(View.OnClickListener {
            Toast.makeText(this, "Chức năng sắp được ra mắt", Toast.LENGTH_SHORT).show()
        })

    }

    private fun loadUserInfo() {
        binding.UserName.text = UserDTO.currentUser?.username ?: "No user"
        if (UserDTO.currentUser?.avatarUrl != ""){
            binding.ava.setImageBitmap(UserDTO.userAvatar)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == EDIT_PROFILE) {
            if (resultCode == RESULT_OK) {
                loadUserInfo()
            }
        }
    }

}