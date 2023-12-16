package com.example.afinal.Activity

import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DTO.UserDTO
import com.example.afinal.databinding.ActivityEditProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.example.afinal.DAL.UserDAL


open class EditProfileActivity : AppCompatActivity() {

    private lateinit var storageRef: StorageReference
    private lateinit var pk: String
    private lateinit var binding: ActivityEditProfileBinding
    private lateinit var emailUser: String
    private lateinit var usernameUser: String
    private lateinit var phone: String

    private var avatarChange = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        phone = binding.edPhone.text.toString()
        initUI()
        initListeners()
    }

    private fun initUI() {
        pk = UserDTO.currentUser?.GetPK() ?: ""
        storageRef = FirebaseStorage.getInstance().reference.child("UserImages")
        showData()
    }

    private fun initListeners() {
        binding.cam.setOnClickListener {
            ImagePicker.with(this)
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.buttonUpdate.setOnClickListener {
            updateUserInfo()
        }
    }

    private fun updateUserInfo() {
        val updatedUser = UserDTO.currentUser?.apply {
            username = binding.edName.text.toString()
            email = binding.edEmail.text.toString()
        }

        updatedUser?.let {
            UserDAL().UpdateUserInfo(it)
        }

        if (avatarChange) {
            uploadAvatar()
        }

        Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show()

//        val intent = Intent()
//        setResult(RESULT_OK, intent)
//        finish()

        val intent = Intent(this, YourSettingActivity::class.java)
        intent.putExtra("phone", binding.edPhone.text.toString())
        startActivity(intent)
        finish()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        data?.data?.let {
            binding.ava.setImageURI(it)
            val inputStream = contentResolver.openInputStream(it)
            UserDTO.userAvatar = BitmapFactory.decodeStream(inputStream)
            avatarChange = true
        }
    }

    private fun uploadAvatar() {
        val drawable = binding.ava.drawable as? BitmapDrawable
        val bitmap = drawable?.bitmap

        bitmap?.let {
            UserDTO.userAvatar = it
            UserDAL().UpdateUserAvatar(it)
            Log.d("TAG", "upload avatar part")
        }
    }

    private fun showData() {
        UserDTO.userAvatar?.let {
            Log.d("TAG", "Bitmap is not null")
            binding.ava.setImageBitmap(it)
        }

        emailUser = UserDTO.currentUser?.email ?: "Error"
        usernameUser = UserDTO.currentUser?.username ?: "Error"

        binding.edEmail.setText(emailUser)
        binding.edName.setText(usernameUser)
        binding.edPhone.setText(phone)

    }

}
