package com.example.afinal.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.UserDAL
import com.example.afinal.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import android.view.View
import android.widget.Toast
import com.example.afinal.DTO.UserDTO
import com.google.firebase.auth.EmailAuthCredential
import com.google.firebase.auth.EmailAuthProvider

class ActivityChangePassword : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityChangePasswordBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        auth = FirebaseAuth.getInstance()
        binding = ActivityChangePasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnChangePw.setOnClickListener {
            changePassword()
        }

        binding.imageViewBackf.setOnClickListener(View.OnClickListener {
            finish()
        })
    }

    private fun changePassword() {
        val oldPassword = binding.edtOldPassword.text.toString()
        val newPassword = binding.edtNewPassword.text.toString()
        val confirmPassword = binding.edtConfirmPassword.text.toString()
        if (oldPassword.isNotEmpty() && newPassword.isNotEmpty() && confirmPassword.isNotEmpty()) {
            val user = auth.currentUser
            if(user != null && user.email != null) {
                val credential = EmailAuthProvider.getCredential(user.email!!, oldPassword)
                user?.reauthenticate(credential)?.addOnCompleteListener {
                    if(it.isSuccessful) {
                        user?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                UserDTO.currentUser?.password = newPassword
                                UserDTO.currentUser?.let { it1 -> UserDAL().UpdateUserInfo(it1) }
                                Toast.makeText(this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show()
                                auth.signOut()
                                startActivity(Intent(this, LoginActivity::class.java))
                                finish()
                            }

                        }
                    }
                    else {
                        binding.tvInvalid.setText("Mật khẩu hiện tại không đúng")
//                        Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                finish()
            }
        }
        else {
            binding.tvInvalid.setText("Vui lòng điền đủ thông tin")
            Toast.makeText(this, "Enter information", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToNextScreen() {
        // Optionally, you can redirect the user to another screen
        val intent = Intent(this, ProfileActivity::class.java)
        startActivity(intent)
        finish()
    }
}
