package com.example.afinal.Activity

import android.R
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.Common.CommonUser
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.UserDomain
import com.example.afinal.databinding.ActivityEditProfileBinding
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream





open class EditProfileActivity : AppCompatActivity() {
    lateinit var db : MyDB
    private lateinit var storageRef: StorageReference
    private lateinit var bitmap: Bitmap
    private lateinit var pk : String
    private lateinit var binding : ActivityEditProfileBinding
    private var CAMERAREQUEST = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init database
        db = MyDB()
        pk = CommonUser.currentUser?.GetPK() ?: ""
        storageRef = FirebaseStorage.getInstance().reference.child("UserImages")

        // Load user information
        loadUserInfo()

        binding.cam.setOnClickListener(View.OnClickListener {
            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(cameraIntent, CAMERAREQUEST)
        })

        binding.buttonUpdate.setOnClickListener(View.OnClickListener {
            uploadToFirebase()
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CAMERAREQUEST) {
            val photo = data?.extras?.get("data") as Bitmap
            binding.ava.setImageBitmap(photo)
            bitmap = photo
        }
    }

    fun loadUserInfo(){
        binding.edName.setText(CommonUser.currentUser?.username)
        binding.edEmail.setText(CommonUser.currentUser?.email)
        binding.edPass.setText(CommonUser.currentUser?.password)

        // Load avatar
        val databaseRef = db.GetUser()
        databaseRef.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.child(pk).getValue(UserDomain::class.java)
                if (user != null) {
                    if (user.avatarUrl==""){
                        binding.ava.setImageResource(com.example.afinal.R.drawable.person)
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

    private fun uploadToFirebase() {

        // Extract pk
        var email = CommonUser.currentUser?.email

        // Convert image -> byte[]
        val drawable = binding.ava.drawable as BitmapDrawable
        val bitmap = drawable.bitmap
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()

        // Save image value in storage
        val uploadTask = pk?.let { storageRef.child("$it.jpeg").putBytes(byteArray) }
        if (uploadTask != null) {
            uploadTask.addOnSuccessListener { taskSnapshot ->
                    val downloadUrlTask: Task<Uri> = taskSnapshot.storage.downloadUrl
                    downloadUrlTask.addOnSuccessListener { uri ->

                    // Refer to current user node
                    val root = pk?.let { it1 -> db.GetUser().child(it1) }

                    // Extract image url
                    val avaUrl = uri.toString()

                    // Save image url in realtime database
                    root?.child("avatarUrl")?.setValue(avaUrl)
                    Toast.makeText(applicationContext, "Upload successfully", Toast.LENGTH_LONG).show()
                }
            }?.addOnFailureListener {
                Toast.makeText(applicationContext, "Fail to Upload", Toast.LENGTH_LONG).show()
            }
        }
    }
}