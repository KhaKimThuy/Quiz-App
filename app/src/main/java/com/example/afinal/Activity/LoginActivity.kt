package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DAL.UserDAL
import com.example.afinal.databinding.ActivityLoginBinding


class   LoginActivity : AppCompatActivity() {

    lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        })

        // To login
        binding.btnLogin.setOnClickListener {
            val email = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            if (validateLoginInput(email, password)) {
                UserDAL().UserLogin(email, password, this)
            }
        }

        // To forgot password activity
        binding.tvFogotPw.setOnClickListener {
            val intent = Intent(this, ActivityForgotPassword::class.java)
            startActivity(intent)
        }
    }

    private fun validateLoginInput(email: String, password: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtUsername.error = "Email không hợp lệ"
            return false
        }
        if (password.isEmpty()) {
            binding.edtPassword.error = "Mật khẩu không được trống"
            return false
        }
        return true
    }

//    override fun onStart() {
//        super.onStart()
//        var userId = MyDB().dbAuth.currentUser?.uid
//        if (userId != null) {
//            // Save user information locally
//            UserDAL().SaveUserLocal(userId)
//            val intent = Intent(this, MainActivity2::class.java)
//            startActivity(intent)
//            finish()
//        }
//    }
}