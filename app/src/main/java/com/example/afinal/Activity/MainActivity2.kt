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
import com.example.afinal.Dialog.AddFolderDialog
import com.example.afinal.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
        //setContentView(R.layout.activity_main2)

        replaceFragment(FragmentHome())
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
            val folderDialog = AddFolderDialog()
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
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(com.example.afinal.R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}