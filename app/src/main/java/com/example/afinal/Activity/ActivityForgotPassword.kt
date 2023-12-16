package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.UserDAL
import com.example.afinal.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ActivityForgotPassword : AppCompatActivity() {

    private lateinit var binding: ActivityForgotPasswordBinding

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
            email.error = "Vui lòng điền Email"
            return
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(emailText).matches()) {
            email.error = "Email không hợp lệ"
            return
        }

        // Gửi yêu cầu đặt lại mật khẩu sử dụng Firebase Authentication
        MyDB().dbAuth.sendPasswordResetEmail(emailText)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Kiểm tra email của bạn", Toast.LENGTH_SHORT).show()
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this, "Lỗi hệ thống", Toast.LENGTH_SHORT).show()
                }
            }
    }
}
