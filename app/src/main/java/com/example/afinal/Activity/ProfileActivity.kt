package com.example.afinal.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.R
import com.example.afinal.databinding.ActivityProfileBinding
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream


class ProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityProfileBinding
    private lateinit var db : MyDB
    private lateinit var pk : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        db = MyDB()
        pk = CommonUser.currentUser?.GetPK() ?: ""

        binding.edit.setOnClickListener(View.OnClickListener{
            passUserData()
        })

        // Load user information
        loadUserInfo()
    }

    fun loadUserInfo(){

        binding.textViewEmail.text = CommonUser.currentUser?.email ?: "Error"
        binding.textViewPwd.text = CommonUser.currentUser?.password ?: "Error"
        binding.textViewAlias.text = CommonUser.currentUser?.username ?: "Error"

        if (CommonUser.currentUser?.avatarUrl == ""){
            binding.ava.setImageResource(R.drawable.person)
        }else{
            Picasso.get().load(CommonUser.currentUser?.avatarUrl).into(binding.ava)
        }
    }

    fun passUserData() {
        val emailFromDB = CommonUser.currentUser?.email
        val usernameFromDB = CommonUser.currentUser?.username
        val passwordFromDB = CommonUser.currentUser?.password

        val intent = Intent(applicationContext, EditProfileActivity::class.java)
        intent.putExtra("email", emailFromDB)
        intent.putExtra("username", usernameFromDB)
        intent.putExtra("password", passwordFromDB)

        val drawable = binding.ava.drawable
//        val bitmap = (drawable as BitmapDrawable).bitmap
        var bitmap: Bitmap? = null
        if (drawable is BitmapDrawable) {
            bitmap = drawable.bitmap
        } else {
            // If the drawable is not a BitmapDrawable, create a new Bitmap and draw the drawable on it
            bitmap = Bitmap.createBitmap(
                drawable.intrinsicWidth,
                drawable.intrinsicHeight,
                Bitmap.Config.ARGB_8888
            )
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.width, canvas.height)
            drawable.draw(canvas)
        }
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
        val byteArray = stream.toByteArray()

        intent.putExtra("avatar", byteArray)
        startActivityForResult(intent, 1001)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1001) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(applicationContext, CommonUser.currentUser?.username ?: "Error", Toast.LENGTH_LONG).show();
                loadUserInfo()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        loadUserInfo()
    }
}