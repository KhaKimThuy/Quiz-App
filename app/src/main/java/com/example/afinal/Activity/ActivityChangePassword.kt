package com.example.afinal.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.UserDAL
import com.example.afinal.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
import android.widget.Toast
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
                        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show()
                        user?.updatePassword(newPassword)?.addOnCompleteListener { task ->
                            if(task.isSuccessful) {
                                Toast.makeText(this, "Password is changed successfully", Toast.LENGTH_SHORT).show()
                                auth.signOut()
                                startActivity(Intent(this, ProfileActivity::class.java))
                                finish()
                            }

                        }
                    }
                    else {
                        binding.tvInvalid.setText("Current password is incorrect")
                        Toast.makeText(this, "Current password is incorrect", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            else {
                startActivity(Intent(this, ProfileActivity::class.java))
                finish()
            }
        }
        else {
            binding.tvInvalid.setText("Please enter complete information")
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
