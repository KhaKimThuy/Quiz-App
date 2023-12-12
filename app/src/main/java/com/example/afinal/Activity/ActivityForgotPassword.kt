package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.UserDAL
import com.example.afinal.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ActivityForgotPassword : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding
    private val firebaseAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnConfimRe.setOnClickListener {
            compareEmail(binding.edtEmailRe)
        }
    }

    private fun compareEmail(email: EditText) {
        val emailText = email.text.toString()

        if (emailText.isEmpty()) {
            email.error = "Email cannot be empty"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.error = "Invalid email format"
            return
        }

        // Gửi yêu cầu đặt lại mật khẩu sử dụng Firebase Authentication
        firebaseAuth.sendPasswordResetEmail(emailText)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Check your email", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
