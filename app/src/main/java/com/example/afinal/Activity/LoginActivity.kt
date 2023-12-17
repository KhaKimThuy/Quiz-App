package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.FolderDAL
import com.example.afinal.DAL.MyDB
import com.example.afinal.DAL.TopicDAL
import com.example.afinal.DAL.UserDAL
import com.example.afinal.R
import com.example.afinal.databinding.ActivityLoginBinding


class   LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db : MyDB
    private lateinit var topicDAL: TopicDAL
    private lateinit var folderDAL: FolderDAL
    private lateinit var userDAL: UserDAL



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        })

        // Setup database
        db = MyDB()
        topicDAL = TopicDAL()
        folderDAL = FolderDAL()
        userDAL = UserDAL()

        

        // To login
        binding.btnLogin.setOnClickListener {
            val email = binding.edtUsername.text.toString()
            val password = binding.edtPassword.text.toString()
            if (validateLoginInput(email, password)) {
                UserDAL().UserLogin(email, password, this)
            }
//            binding.tvError.setText("Tên người dùng hoặc mật khẩu không đúng")
        }

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvPasss.setText("")
                binding.tvError.setText("")
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        binding.edtUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvPasss.setText("")
                binding.tvError.setText("")
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

        // To forgot password activity
        binding.tvFogotPw.setOnClickListener {
            val intent = Intent(this, ActivityForgotPassword::class.java)
            startActivity(intent)
        }

        var isPasswordVisible = false
        binding.imgShowP.setOnClickListener(View.OnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.edtPassword, isPasswordVisible, binding.imgShowP)
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
    }

    private fun validateLoginInput(email: String, password: String): Boolean {
        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            binding.edtUsername.error = "Email không hợp lệ"
//            Toast.makeText(this, "Invalid email address", Toast.LENGTH_SHORT).show()
            return false
        }
        if (password.isEmpty()) {
            binding.tvPasss.setText("Mật khẩu không được để trống")
//            Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            return false
        }
        return true
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