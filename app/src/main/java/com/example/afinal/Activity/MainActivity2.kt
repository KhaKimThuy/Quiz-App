package com.example.afinal.Activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.afinal.R
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
                R.id.homeE -> replaceFragment(FragmentHome())
                R.id.answers -> replaceFragment(FragmentLibrary())
                R.id.library -> replaceFragment(FragmentLibrary())
                R.id.profile -> replaceFragment(FragmentSettings())
            }
            true
        }

//        binding.layoutProfile.setOnClickListener(View.OnClickListener{
//            val intent = Intent(applicationContext, SettingActivity::class.java)
//            startActivity(intent)
//        })
//
//        binding.layoutLibrary.setOnClickListener(View.OnClickListener{
//            val intent = Intent(applicationContext, LibraryActivity::class.java)
//            startActivity(intent)
//        })

        binding.imageViewAdd.setOnClickListener(View.OnClickListener {
            val intent = Intent(applicationContext, CreateStudyModuleActivity::class.java)
            startActivity(intent)
        })
    }
    private fun replaceFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = supportFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout, fragment)
        fragmentTransaction.commit()
    }
}