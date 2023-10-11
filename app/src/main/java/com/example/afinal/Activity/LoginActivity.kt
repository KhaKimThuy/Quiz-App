package com.example.afinal.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.afinal.R
import com.example.afinal.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()


        binding.textView2Register.setOnClickListener {
            val intent = Intent(this, RegisterActivity::class.java)
            startActivity(intent)
        }

        binding.btnLogin.setOnClickListener {
            val email = binding.edtUsername.text.toString()
            val pass = binding.edtPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()){
                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
//                        val intent = Intent(applicationContext, FlashCardStudyActivity::class.java);
                            Intent(applicationContext, SearchResultActivity::class.java);
                        startActivity(intent)
                    } else {
                        Toast.makeText(
                            applicationContext,
                            R.string.user_isnot_exist,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }else{
                if (email.isNotEmpty() && pass.isEmpty()){
                    binding.edtPassword.setText(R.string.password_empty)
                    binding.edtPassword.setTextColor(resources.getColor(R.color.red))
                }else if (email.isEmpty() && pass.isNotEmpty()){
                    binding.edtUsername.setText(R.string.username_empty)
                    binding.edtUsername.setTextColor(resources.getColor(R.color.red))
                }else{
                    binding.edtPassword.setText(R.string.password_empty)
                    binding.edtPassword.setTextColor(resources.getColor(R.color.red))
                    binding.edtUsername.setText(R.string.username_empty)
                    binding.edtUsername.setTextColor(resources.getColor(R.color.red))
                }
            }

//            if (email.isEmpty()) {
//                binding.edtUsername.setText(R.string.username_empty)
//                binding.edtUsername.setTextColor(resources.getColor(R.color.red))
//            } else {
//                if (pass.isEmpty()) {
//                    binding.edtPassword.setText(R.string.password_empty)
//                    binding.edtPassword.setTextColor(resources.getColor(R.color.red))
//                } else {
//                    firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
//                        if (it.isSuccessful) {
//                            val intent =
//                                Intent(applicationContext, SearchResultActivity::class.java);
//                            startActivity(intent);
//                        } else {
//                            Toast.makeText(
//                                applicationContext,
//                                it.exception.toString(),
//                                Toast.LENGTH_SHORT
//                            ).show()
//                        }
//                    }
//                }
//            }

//        binding.edtUsername.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                binding.edtUsername.setText(R.string.email_or_username)
//                binding.edtUsername.setTextColor(resources.getColor(R.color.black))
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
//
//        binding.edtPassword.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//            }
//
//            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
//                binding.edtPassword.setText(R.string.password)
//                binding.edtPassword.setTextColor(resources.getColor(R.color.black))
//            }
//
//            override fun afterTextChanged(p0: Editable?) {
//            }
//        })
    }

    }
    override fun onStart() {
        super.onStart()
//        if (firebaseAuth.currentUser != null){
//            val intent = Intent(this, SearchResultActivity::class.java);
//            startActivity(intent);
    }
}