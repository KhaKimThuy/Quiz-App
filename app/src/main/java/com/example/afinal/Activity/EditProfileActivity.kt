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
    private lateinit var pk : String
    private lateinit var binding : ActivityEditProfileBinding

    private lateinit var emailUser : String
    private lateinit var usernameUser : String
    private lateinit var passwordUser : String

    private var avatarChange = false
    private var newAvatarUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)


        pk = UserDTO.currentUser?.GetPK() ?: ""
        storageRef = FirebaseStorage.getInstance().reference.child("UserImages")

        // Load user information
        showData()

        binding.cam.setOnClickListener(View.OnClickListener {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(cameraIntent, CAMERAREQUEST)

            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()

        })

        binding.buttonUpdate.setOnClickListener(View.OnClickListener {

            // Update information
            val updatedUser = UserDTO.currentUser
            updatedUser?.username = binding.edName.text.toString()
            updatedUser?.email = binding.edEmail.text.toString()
            updatedUser?.password = binding.edPass.text.toString()
            Log.d("TAG", "serDTO.currentUser : " + updatedUser?.userPK)

            if (updatedUser != null) {
                UserDAL().UpdateUserInfo(updatedUser)
            }

            // Update avatar
            if (avatarChange) {
                uploadAvatar()
            }

            Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show();

            val intent = Intent()
            setResult(RESULT_OK, intent)
            finish()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data
        binding.ava.setImageURI(uri)
        val inputStream = uri?.let { contentResolver.openInputStream(it) }
        UserDTO.userAvatar =  BitmapFactory.decodeStream(inputStream)
        avatarChange = true
    }


    // Upload avatar user to database
    private fun uploadAvatar() {
            val drawable = binding.ava.drawable as BitmapDrawable
            val bitmap = drawable.bitmap

            // Save new avatar lacally
            UserDTO.userAvatar = bitmap
            UserDAL().UpdateUserAvatar(bitmap);
            Log.d("TAG" , "upload avatar part")
    }

//    private fun isNameChanged(): Boolean {
//        return if (usernameUser != binding.edName.text.toString()) {
//            db.GetUserByID()?.child("username")?.setValue(binding.edName.text.toString())
//            usernameUser = binding.edName.text.toString()
//            true
//        } else {
//            false
//        }
//    }
//
//    private fun isEmailChanged(): Boolean {
//        return if (emailUser != binding.edEmail.text.toString()) {
//            db.GetUserByID()?.child("email")?.setValue(binding.edEmail.text.toString())
//            emailUser = binding.edEmail.text.toString()
//            true
//        } else {
//            false
//        }
//    }
//
//    private fun isPasswordChanged(): Boolean {
//        return if (passwordUser != binding.edPass.text.toString()) {
//            db.GetUserByID()?.child("password")?.setValue(binding.edPass.text.toString())
//            passwordUser = binding.edPass.text.toString()
//            true
//        } else {
//            false
//        }
//    }

    private fun showData() {
        if (UserDTO.userAvatar  != null) {
            Log.d("TAG" , "Bitmap is not null")
            binding.ava.setImageBitmap(UserDTO.userAvatar)
        }

        Log.d("TAG" , "User avatar url : " + UserDTO.currentUser?.avatarUrl)
        emailUser = UserDTO.currentUser?.email ?: "Error"
        usernameUser = UserDTO.currentUser?.username ?: "Error"
        passwordUser = UserDTO.currentUser?.password ?: "Error"

        binding.edEmail.setText(emailUser)
        binding.edName.setText(usernameUser)
        binding.edPass.setText(passwordUser)

    }
}