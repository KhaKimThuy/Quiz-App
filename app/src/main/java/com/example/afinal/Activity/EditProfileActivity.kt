package com.example.afinal.Activity

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DTO.UserDTO
import com.example.afinal.DB.MyDB
import com.example.afinal.Domain.UserDomain
import com.example.afinal.databinding.ActivityEditProfileBinding
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.tasks.Task
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.squareup.picasso.Picasso
import java.io.ByteArrayOutputStream
import com.example.afinal.DB.UserDAL


open class EditProfileActivity : AppCompatActivity() {
    lateinit var db : MyDB
    private lateinit var userDAL: UserDAL
    private lateinit var storageRef: StorageReference
    private lateinit var pk : String
    private lateinit var binding : ActivityEditProfileBinding

    private lateinit var emailUser : String
    private lateinit var usernameUser : String
    private lateinit var passwordUser : String

    private var avatarChange = false
    private var newAvatarUrl = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Init database
        db = MyDB()
        userDAL = UserDAL()

        pk = UserDTO.currentUser?.GetPK() ?: ""
        storageRef = FirebaseStorage.getInstance().reference.child("UserImages")

        // Load user information
        showData()

        binding.cam.setOnClickListener(View.OnClickListener {
//            val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//            startActivityForResult(cameraIntent, CAMERAREQUEST)

            ImagePicker.with(this)
                .crop()	    			//Crop image(Optional), Check Customization for more option
                .compress(1024)			//Final image size will be less than 1 MB(Optional)
                .maxResultSize(1080, 1080)	//Final image resolution will be less than 1080 x 1080(Optional)
                .start()

        })

        binding.buttonUpdate.setOnClickListener(View.OnClickListener {

            // Update information
            if (isNameChanged() || isEmailChanged() || isPasswordChanged() || avatarChange) {
                if (avatarChange) {
                    uploadAvatar()
                }
                UserDTO.UpdateInfo(binding.edEmail.text.toString(), binding.edPass.text.toString(), binding.edName.text.toString(), newAvatarUrl)

                Toast.makeText(applicationContext, "Saved", Toast.LENGTH_SHORT).show();

                val intent = Intent()
                setResult(RESULT_OK, intent)

            } else {
                Toast.makeText(applicationContext, "No Changes Found", Toast.LENGTH_SHORT).show();
            }
            finish()
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val uri = data?.data
        binding.ava.setImageURI(uri)
        val inputStream = uri?.let { contentResolver.openInputStream(it) }
        UserDTO.userAvatar =  BitmapFactory.decodeStream(inputStream)
        avatarChange = true
    }


    fun loadUserInfo(){
        binding.edName.setText(UserDTO.currentUser?.username)
        binding.edEmail.setText(UserDTO.currentUser?.email)
        binding.edPass.setText(UserDTO.currentUser?.password)

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

    private fun uploadAvatar() {
        if (avatarChange){
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
                        newAvatarUrl = uri.toString()

                        // Save image url in realtime database
                        root?.child("avatarUrl")?.setValue(newAvatarUrl)

                        // Update information in local
                        UserDTO.currentUser?.let { userDAL.PicassoToBitmap(it.avatarUrl) }

//                        Toast.makeText(applicationContext, "Upload successfully", Toast.LENGTH_LONG).show()
                    }
                }?.addOnFailureListener {
//                    Toast.makeText(applicationContext, "Fail to Upload", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun isNameChanged(): Boolean {
        return if (usernameUser != binding.edName.text.toString()) {
            db.GetUserByID()?.child("username")?.setValue(binding.edName.text.toString())
            usernameUser = binding.edName.text.toString()
            true
        } else {
            false
        }
    }

    private fun isEmailChanged(): Boolean {
        return if (emailUser != binding.edEmail.text.toString()) {
            db.GetUserByID()?.child("email")?.setValue(binding.edEmail.text.toString())
            emailUser = binding.edEmail.text.toString()
            true
        } else {
            false
        }
    }

    private fun isPasswordChanged(): Boolean {
        return if (passwordUser != binding.edPass.text.toString()) {
            db.GetUserByID()?.child("password")?.setValue(binding.edPass.text.toString())
            passwordUser = binding.edPass.text.toString()
            true
        } else {
            false
        }
    }

    private fun showData() {
        emailUser = UserDTO.currentUser?.email ?: "Error"
        usernameUser = UserDTO.currentUser?.username ?: "Error"
        passwordUser = UserDTO.currentUser?.password ?: "Error"

        binding.edEmail.setText(emailUser)
        binding.edName.setText(usernameUser)
        binding.edPass.setText(passwordUser)

        if (UserDTO.currentUser?.avatarUrl ?: ""  != "") {
            binding.ava.setImageBitmap(UserDTO.userAvatar)
        }
    }
}