package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DAL.UserDAL
import com.example.afinal.databinding.ActivityLoginBinding


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
        binding.btnLogin.setOnClickListener {
            val email = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            UserDAL().UserLogin(email, password, this)
        }
    }

    override fun onStart() {
        super.onStart()
        var userId = MyDB().dbAuth.currentUser?.uid
        if (userId != null) {
            // Save user information locally
            UserDAL().SaveUserLocal(userId)
            val intent = Intent(this, MainActivity2::class.java)
            startActivity(intent)
            finish()
        }
    }
}