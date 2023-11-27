package com.example.afinal.DB

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import com.example.afinal.Activity.DetailFolderActivity
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Domain.TopicDomain
import com.example.afinal.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ValueEventListener
import com.squareup.picasso.Picasso
import com.squareup.picasso.Target

class UserDAL : MyDB() {
    fun PicassoToBitmap(imgUrl : String) {
        if (imgUrl == "") {
            val drawableId: Int = R.drawable.person
            UserDTO.userAvatar = BitmapFactory.decodeResource(Resources.getSystem(), drawableId)
        }
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

