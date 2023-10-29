package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.UserDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import java.util.Objects


class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var db : MyDB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Setup database
        db = MyDB()

        // To register activity
        binding.textView2Register.setOnClickListener {
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


    fun validateUsername(): Boolean {
        val email = binding.edtUsername.text.toString()
        if (email.isEmpty()) {
            binding.edtUsername.setText(R.string.username_empty)
            binding.edtUsername.setTextColor(resources.getColor(R.color.red))
            return false
        }else{
            return true
        }
    }
    fun validatePassword(): Boolean {
        val pass = binding.edtPassword.text.toString()
        if (pass.isEmpty()) {
            binding.edtUsername.setText(R.string.username_empty)
            binding.edtUsername.setTextColor(resources.getColor(R.color.red))
            return false
        }else{
            return true
        }
    }

    fun login() {
//        val email = binding.edtUsername.text.toString()
//        val pass = binding.edtPassword.text.toString()
        val email = "khathuy243@gmail.com"
        val pass = "123123"

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
                            CommonUser.currentUser = user

                            Toast.makeText(applicationContext, "Login successfully", Toast.LENGTH_SHORT).show()
                            val intent = Intent(applicationContext, MainActivity2::class.java)
                            startActivity(intent)
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