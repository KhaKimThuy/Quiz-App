package com.example.afinal.Activity

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.afinal.DTO.UserDTO
import com.example.afinal.Dialog.FolderDialog
import com.example.afinal.Domain.UserDomain
import com.example.afinal.Fragment.FragmentHome
import com.example.afinal.Fragment.FragmentLibrary
import com.example.afinal.Fragment.FragmentSettings
import com.example.afinal.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivityMain2Binding
    private lateinit var fragmentManager: FragmentManager
    private lateinit var fragmentTransaction: FragmentTransaction
    private lateinit var backupUser : UserDomain
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main2)
        fragmentManager = supportFragmentManager

        fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(com.example.afinal.R.id.frameLayout, FragmentHome())
        //fragmentTransaction.addToBackStack(FragmentHome().tag)
        fragmentTransaction.commitNow()


        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.example.afinal.R.id.homeE -> replaceFragment(FragmentHome())
                com.example.afinal.R.id.answers -> replaceFragment(FragmentLibrary())
                com.example.afinal.R.id.library -> replaceFragment(FragmentLibrary())
                com.example.afinal.R.id.profile -> replaceFragment(FragmentSettings())
            }
            true
        }

//        binding.layoutProfile.setOnClickListener(View.OnClickListener{
//            val intent = Intent(applicationContext, SettingActivity::class.java)
//            startActivity(intent)
//        })


        binding.imageViewAdd.setOnClickListener(View.OnClickListener {
            showBottomDialog();
        })

        backupUser = UserDTO.currentUser!!
    }

    private fun showBottomDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(com.example.afinal.R.layout.bottom_sheet_layout)

        val addItemLayout: LinearLayout = dialog.findViewById(com.example.afinal.R.id.layoutAddItem)
        val addFolderLayout: LinearLayout = dialog.findViewById(com.example.afinal.R.id.layoutAddFolder)

        addItemLayout.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, CreateStudyModuleActivity::class.java)
            startActivity(intent)
            dialog.dismiss()
        })
        addFolderLayout.setOnClickListener(View.OnClickListener {
            val folderDialog = FolderDialog("Tọa thư mục")
            folderDialog.show(supportFragmentManager, "Folder dialog")
            dialog.dismiss()
        })
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.setGravity(Gravity.BOTTOM)
        //https://www.youtube.com/watch?v=ahNruIZX130
    }
    private fun replaceFragment(fragment: Fragment) {
        if (fragmentManager != null) {
            fragmentManager.popBackStack()
            //fragmentManager.popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
            val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
            if (fragment != null) {
                fragmentTransaction.replace(com.example.afinal.R.id.frameLayout, fragment)
            }
            if (fragment != null) {
                fragmentTransaction.addToBackStack(fragment.tag)
            }
            fragmentTransaction.commit()
        }
    }

    override fun onPause() {
        super.onPause()
        backupUser = UserDTO.currentUser!!
    }

    override fun onStart() {
        super.onStart()
        if (UserDTO.currentUser == null) {
            UserDTO.currentUser = backupUser
        }
    }
}