package com.example.afinal.DAL

import android.content.Intent
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.afinal.Activity.LoginActivity
import com.example.afinal.Activity.MainActivity2
import com.example.afinal.Activity.RegisterActivity
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.UserDomain
import com.example.afinal.R
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class UserDAL : MyDB() {

    fun SaveUserLocal(userId : String){
        // Save user information locally
        MyDB().db.collection("user").document(userId).get().addOnCompleteListener{
            val user = it.result.toObject(UserDomain::class.java)
            if (user != null) {
                UserDTO.currentUser = user
                SaveAvatarLocal(user.avatarUrl)
            }
        }
    }


    fun GetUserObject(userId: String, callback: (UserDomain?) -> Unit) {
        val documentRef = MyDB().db.collection("user").document(userId)
        documentRef.get()
            .addOnSuccessListener { documentSnapshot ->
                if (documentSnapshot.exists()) {
                    val myObject = documentSnapshot.toObject(UserDomain::class.java)
                    callback(myObject)
                } else {
                    callback(null)
                }
            }
            .addOnFailureListener { _ ->
                callback(null)
            }
    }


    fun AddUser(user : UserDomain, activity : RegisterActivity) {

        // Authentication firebase
        MyDB().dbAuth.createUserWithEmailAndPassword(user.email, user.password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val userId = MyDB().dbAuth.currentUser?.uid

                    // Save user information in cloud storage
                    if (userId != null) {
                        val userFB = hashMapOf(
                            "email" to user.email,
                            "password" to user.password,
                            "username" to user.username,
                            "avatarUrl" to user.avatarUrl,
                        )
                        db.collection("user").document(userId)
                            .set(userFB)
                            .addOnSuccessListener {
                                activity.binding.progressBar5.visibility = View.GONE
                                Log.d("TAG", "DocumentSnapshot successfully written!") }
                            .addOnFailureListener { e -> Log.w("TAG", "Error writing user document", e) }

                        // If sign in fails, display a message to the user.
                        Toast.makeText(
                            activity,
                            "Authentication successfully.",
                            Toast.LENGTH_SHORT,
                        ).show()

                        val intent = Intent(activity, LoginActivity::class.java)
                        activity.startActivity(intent)
                    }

                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        activity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }

    fun UserLogin(email : String, password : String, activity: LoginActivity) {
        MyDB().dbAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(activity) { task ->
                if (task.isSuccessful) {
                    val intent = Intent(activity, MainActivity2::class.java)
                    activity.startActivity(intent)
                    activity.finish()
                } else {
                    // If sign in fails, display a message to the user.
                    Toast.makeText(
                        activity,
                        "Authentication failed.",
                        Toast.LENGTH_SHORT,
                    ).show()
                }
            }
    }


    fun SaveAvatarLocal(imgUrl : String) {
        if (imgUrl == "") {
            val drawableId: Int = R.drawable.person
            UserDTO.userAvatar = BitmapFactory.decodeResource(Resources.getSystem(), drawableId)
        } else {
            val target = object : Target {
                override fun onBitmapLoaded(bitmap: Bitmap?, from: Picasso.LoadedFrom?) {
                    if (bitmap != null) {
                        UserDTO.userAvatar = bitmap
                    }
                }
                override fun onBitmapFailed(e: Exception?, errorDrawable: Drawable?) {
                }
                override fun onPrepareLoad(placeHolderDrawable: Drawable?) {
                }
            }
            Picasso.get().load(imgUrl).into(target)
        }
    }
}

