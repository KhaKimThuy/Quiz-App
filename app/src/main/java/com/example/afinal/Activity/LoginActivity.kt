package com.example.afinal.Activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.example.afinal.R

class LoginActivity : AppCompatActivity() {

    lateinit var btnLogin : AppCompatButton
    lateinit var btnGoogle : AppCompatButton
    lateinit var btnFacebook : AppCompatButton
    lateinit var edtUsername : EditText
    lateinit var edtPassword : EditText
    lateinit var tvUsername : TextView
    lateinit var tvPassword : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin = findViewById(R.id.btnLogin)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)
        edtUsername = findViewById(R.id.edtUsername)
        edtPassword = findViewById(R.id.edtPassword)
        tvUsername = findViewById(R.id.tvUsername)
        tvPassword = findViewById(R.id.tvPassword)

        btnLogin.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {
                if(edtUsername.getText().isEmpty()) {
                    tvUsername.setText(R.string.username_empty)
                    tvUsername.setTextColor(resources.getColor(R.color.red))
                }
                else {
                    if(edtPassword.getText().isEmpty()) {
                        tvPassword.setText(R.string.password_empty)
                        tvPassword.setTextColor(resources.getColor(R.color.red))
                    }
                }
            }
        })

        edtUsername.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvUsername.setText(R.string.email_or_username)
                tvUsername.setTextColor(resources.getColor(R.color.black))
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })

        edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                tvPassword.setText(R.string.password)
                tvPassword.setTextColor(resources.getColor(R.color.black))
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }
}