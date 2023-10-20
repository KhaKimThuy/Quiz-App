package com.example.afinal.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.annotation.NonNull
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.UserDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityProfileBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener
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

    fun passUserData() {
        val pk: String = CommonUser.currentUser?.GetPK() ?: ""
        val reference = db.GetUser()
        reference.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(@NonNull snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    val user = snapshot.child(pk).getValue(UserDomain::class.java)

                    val emailFromDB = user?.email
                    val usernameFromDB = user?.username
                    val passwordFromDB = user?.password
                    val avatarFromDB = user?.avatarUrl

                    val intent = Intent(applicationContext, EditProfileActivity::class.java)
                    intent.putExtra("email", emailFromDB)
                    intent.putExtra("username", usernameFromDB)
                    intent.putExtra("password", passwordFromDB)

                    val drawable = binding.ava.drawable
                    val bitmap = (drawable as BitmapDrawable).bitmap
                    val stream = ByteArrayOutputStream()
                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream)
                    val byteArray = stream.toByteArray()
                    intent.putExtra("avatar", byteArray)

                    startActivity(intent)
                }
            }

            override fun onCancelled(@NonNull error: DatabaseError) {}
        })
    }
}