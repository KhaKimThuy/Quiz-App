package com.example.afinal.Activity

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.UserDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityProfileBinding
import com.example.afinal.databinding.ActivitySettingBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.values
import com.squareup.picasso.Picasso

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
            val intent = Intent(applicationContext, EditProfileActivity::class.java)
            startActivity(intent)
        })
        // Load user information
        loadUserInfo()
    }

    fun loadUserInfo(){

        binding.textViewEmail.text = CommonUser.currentUser?.email ?: "Error"
        binding.textViewPwd.text = CommonUser.currentUser?.password ?: "Error"
        binding.textViewAlias.text = CommonUser.currentUser?.username ?: "Error"

        // Load avatar
        val databaseRef = db.GetUser()
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.child(pk).getValue(UserDomain::class.java)
                if (user != null) {
                    if (user.avatarUrl==""){
                        binding.ava.setImageResource(R.drawable.person)
                    }else{
                        databaseRef.child(pk).child("avatarUrl").addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val imageUrl = dataSnapshot.getValue(String::class.java)
                                Picasso.get().load(imageUrl).into(binding.ava)
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Toast.makeText(applicationContext,"Profile display error",Toast.LENGTH_SHORT).show()
                            }
                        })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }
        })

    }
}