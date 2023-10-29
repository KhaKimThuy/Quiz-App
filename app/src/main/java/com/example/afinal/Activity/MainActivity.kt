package com.example.afinal.Activity

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.afinal.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        replaceFragment(FragmentHome())
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.home -> replaceFragment(FragmentLibrary())
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
        fragmentTransaction.replace(com.example.afinal.R.id.frameLayout1, fragment)
        fragmentTransaction.commit()
    }
}