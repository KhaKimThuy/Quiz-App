package com.example.afinal.Activity

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.service.autofill.UserData
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DB.FolderDAL
import com.example.afinal.DTO.UserDTO
import com.example.afinal.DB.MyDB
import com.example.afinal.DB.TopicDAL
import com.example.afinal.DB.UserDAL
import com.example.afinal.DTO.TopicDTO
import com.example.afinal.Domain.UserDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityLoginBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db : MyDB
    private lateinit var topicDAL: TopicDAL
    private lateinit var folderDAL: FolderDAL
    private lateinit var userDAL: UserDAL

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup database
        db = MyDB()
        topicDAL = TopicDAL()
        folderDAL = FolderDAL()
        userDAL = UserDAL()

        // To register activity
        binding.buttonRegister.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        // To login
        binding.btnLogin.setOnClickListener{
//            if (validatePassword() && validateUsername()){
//                login()
//            }
            login()
        }
    }


//    fun validateUsername(): Boolean {
//        val email = binding.edtUsername.text.toString()
//        if (email.isEmpty()) {
//            binding.edtUsername.setText(R.string.username_empty)
//            binding.edtUsername.setTextColor(resources.getColor(R.color.red))
//            return false
//        }else{
//            return true
//        }
//    }
//    fun validatePassword(): Boolean {
//        val pass = binding.edtPassword.text.toString()
//        return if (pass.isEmpty()) {
//            binding.edtUsername.setText(R.string.username_empty)
//            binding.edtUsername.setTextColor(resources.getColor(R.color.red))
//            false
//        }else{
//            true
//        }
//    }

    suspend fun loadUserDataFromFirebase(){

        topicDAL.GetUserTopicList()
        folderDAL.GetUserFolderList()
    }

    private fun login() {
        val email = binding.edtUsername.text.toString()
        val pass = binding.edtPassword.text.toString()
//        val email = "khathuy243@gmail.com"
//        val pass = "12345"

        // Extract pk from email
        var pk = db.extractPK(email)
        var reference = db.GetUser()

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                // Check if pk in database
                if (snapshot.hasChild(pk)){
                    val user = snapshot.child(pk).getValue(UserDomain::class.java)
                    if (user != null) {
                        if(user.password == pass){
                            // Save current user
                            UserDTO.currentUser = user

                            // Save user's avatar to local
                            if (user.avatarUrl != "") {
                                UserDTO.currentUser?.let { userDAL.PicassoToBitmap(it.avatarUrl) }
                            }

                            GlobalScope.launch {
                                loadUserDataFromFirebase()
                            }

                            Toast.makeText(applicationContext, "Login successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, MainActivity2::class.java)
                            startActivity(intent)
                            finish()
                        }else{
                            binding.edtUsername.error = "Invalid Credentials"
                            binding.edtPassword.requestFocus()
                        }
                    }
                }else{
                    binding.edtUsername.error = "User is not exist"
                    binding.edtUsername.requestFocus()
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, "System error", Toast.LENGTH_LONG).show()
            }
        })
    }



}