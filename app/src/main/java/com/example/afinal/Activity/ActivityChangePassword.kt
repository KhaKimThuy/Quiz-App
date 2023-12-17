package com.example.afinal.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.UserDAL
import com.example.afinal.databinding.ActivityChangePasswordBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.content.Intent
<<<<<<< HEAD
import android.view.View
import android.widget.Toast
import com.example.afinal.DTO.UserDTO
=======
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import com.example.afinal.R
>>>>>>> 1fb543880d7be2a23c4dcc3a1376427550402204
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

<<<<<<< HEAD
        binding.imageViewBackf.setOnClickListener(View.OnClickListener {
            finish()
        })
=======
        var isPasswordVisible = false
        binding.imgShowP1.setOnClickListener(View.OnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.edtOldPassword, isPasswordVisible, binding.imgShowP1)
        })
        binding.imgShowP2.setOnClickListener(View.OnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.edtNewPassword, isPasswordVisible, binding.imgShowP2)
        })
        binding.imgShowP3.setOnClickListener(View.OnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.edtConfirmPassword, isPasswordVisible, binding.imgShowP3)
        })
    }

    private fun togglePasswordVisibility(editText: EditText, isVisible: Boolean, img : ImageView) {
        if (isVisible) {
            // Hiển thị mật khẩu
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            editText.setSelection(editText.text.length)
            img.setImageResource(R.drawable.hidden)
        } else {
            // Ẩn mật khẩu
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            editText.setSelection(editText.text.length)
            img.setImageResource(R.drawable.view)
        }
>>>>>>> 1fb543880d7be2a23c4dcc3a1376427550402204
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
<<<<<<< HEAD
                                startActivity(Intent(this, LoginActivity::class.java))
=======
                                startActivity(Intent(this, YourSettingActivity::class.java))
>>>>>>> 1fb543880d7be2a23c4dcc3a1376427550402204
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
<<<<<<< HEAD
=======
                startActivity(Intent(this, YourSettingActivity::class.java))
>>>>>>> 1fb543880d7be2a23c4dcc3a1376427550402204
                finish()
            }
        }
        else {
            binding.tvInvalid.setText("Vui lòng điền đủ thông tin")
            Toast.makeText(this, "Enter information", Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToNextScreen() {
        val intent = Intent(this, YourSettingActivity::class.java)
        startActivity(intent)
        finish()
    }
}
