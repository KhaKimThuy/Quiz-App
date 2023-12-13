package com.example.afinal.Activity

import android.R
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.afinal.Dialog.FolderDialog
import com.example.afinal.Fragment.FragmentHome
import com.example.afinal.Fragment.FragmentLibrary
import com.example.afinal.Fragment.FragmentSettings
import com.example.afinal.databinding.ActivityMain2Binding


class MainActivity2 : AppCompatActivity() {
    private lateinit var binding : ActivityMain2Binding
    private lateinit var fragmentTransaction: FragmentTransaction
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)
//        binding.bottomNavigationView.background = null

        fragmentTransaction = supportFragmentManager.beginTransaction()


        var fragmentHome = FragmentHome()

        // Show homepage fragment
        fragmentTransaction.add(com.example.afinal.R.id.frameLayout, FragmentHome())
        fragmentTransaction.addToBackStack(fragmentHome.javaClass.name)
        Log.d("TAG", "fragmentHome : " + fragmentHome.javaClass.name)
        fragmentTransaction.commit()

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                com.example.afinal.R.id.homeE -> switchToFragment(FragmentHome())
                // com.example.afinal.R.id.answers -> switchToFragment(FragmentLibrary())
                com.example.afinal.R.id.library -> switchToFragment(FragmentLibrary())
                com.example.afinal.R.id.profile -> switchToFragment(FragmentSettings())
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

    private fun switchToFragment(fragment: Fragment) {
//        if (supportFragmentManager != null) {
//            supportFragmentManager.popBackStack()

        // Quay lại Fragment trước đó
            val fragmentManager = supportFragmentManager

            fragmentTransaction = supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(com.example.afinal.R.id.frameLayout, fragment)
            fragmentTransaction.addToBackStack(fragment.javaClass.name)
            Log.d("TAG", "switchtHome : " + fragment.javaClass.name)
            fragmentTransaction.commit()

//            if (fragmentManager.backStackEntryCount > 0) {
//                fragmentManager.popBackStack()
//            } else {
//                super.onBackPressed() // Nếu không có Fragment trong stack, thì thực hiện hành động mặc định
//            }
//        }
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
            val folderDialog = FolderDialog("Tạo thư mục", this)
            folderDialog.show(supportFragmentManager, "Folder dialog")
            dialog.dismiss()
        })
        dialog.show()
        dialog.window?.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.WHITE))
        dialog.window?.setGravity(Gravity.BOTTOM)
        //https://www.youtube.com/watch?v=ahNruIZX130
    }

//    // Switch to Fragment B when needed
//    fun switchToFragment(fragment: Fragment) {
//        if(supportFragmentManager != null) {
//            supportFragmentManager.popBackStack()
//        }
//
//        fragmentTransaction = supportFragmentManager.beginTransaction()
//        fragmentTransaction.replace(com.example.afinal.R.id.frameLayout, fragment)
//        fragmentTransaction.addToBackStack(fragment.javaClass.name)
//        fragmentTransaction.commit()
//    }
}