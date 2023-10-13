package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.renderscript.Sampler.Value
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.Domain.UserDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityRegisterBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.childEvents
import java.util.Objects

class RegisterActivity : AppCompatActivity() {

    private lateinit var binding : ActivityRegisterBinding
    private lateinit var database : FirebaseDatabase
    private lateinit var reference: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener(View.OnClickListener {
            database = FirebaseDatabase.getInstance()
            reference = database.getReference("users")

            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()
            val confirm = binding.edtPassword.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirm.isNotEmpty()){
                if (pass == confirm){
                    writeNewUser(email, pass)
                }else{
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            }else{
                Toast.makeText(this, "Empty field is not allowed", Toast.LENGTH_SHORT).show()
            }
        })


//        btnGoogle = findViewById(R.id.btnGoogle)
//        btnFacebook = findViewById(R.id.btnFacebook)
//
//        edtPassword = findViewById(R.id.edtPassword)
//        edtEmail = findViewById(R.id.edtEmail)
//
//        tvBirthday = findViewById(R.id.tvBirthday)
//        tvPassword = findViewById(R.id.tvPassword)
//        tvEmail = findViewById(R.id.tvEmail)
//
//        val sdf = SimpleDateFormat("dd/M/yyyy")
//        val currentDate = sdf.format(Date())
//        edtBirthday.hint = currentDate
//
//        edtBirthday.setOnClickListener {
//            showDatePickerDialog()
//        }


//        edtBirthday.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
//            if(!b) {
//                if(edtBirthday.text.toString().endsWith(currentDate)) {
//                    tvBirthday.setText(R.string.day_invalid);
//                    tvBirthday.setTextColor(resources.getColor(R.color.red))
//                }
//                else {
//                    tvBirthday.setText(R.string.birthday);
//                    tvBirthday.setTextColor(resources.getColor(R.color.black))
//                }
//            }
//        }


        binding.edtEmail.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                if (binding.edtEmail.text.toString().endsWith("@gmail.com")) {
                    binding.tvEmail.text = getString(R.string.email_of_parents)
                    binding.tvEmail.setTextColor(resources.getColor(R.color.black))
                } else {
                    binding.tvEmail.text = getString(R.string.email_invalid)
                    binding.tvEmail.setTextColor(resources.getColor(R.color.red))
                }
            }
        }

        binding.edtPassword.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                if (binding.edtPassword.text.toString().length >= 8) {
                    binding.tvPassword.setText(R.string.password);
                    binding.tvPassword.setTextColor(resources.getColor(R.color.black))
                } else {
                    binding.tvPassword.setText(R.string.pass_invalid);
                    binding.tvPassword.setTextColor(resources.getColor(R.color.red))
                }
            }
        }

        binding.btnRegister.isEnabled = false;
        binding.btnRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_gray));

        binding.edtPassword.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                if(!binding.edtPassword.text.isEmpty() && !binding.edtEmail.text.isEmpty()) {
                    binding.btnRegister.isEnabled = true;
                    binding.btnRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_blue));
                }
                else {
                    binding.btnRegister.isEnabled = false;
                    binding.btnRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_gray));
                }
            }
        }

    }

    private fun writeNewUser(email: String, pass: String) {
        val user = UserDomain()
        user.email = email
        user.password = pass
        var pk = email.replace("@", "")
        pk = pk.replace(".", "")

        reference.addListenerForSingleValueEvent(object : ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
                if (snapshot.hasChild(pk)){
                    Toast.makeText(applicationContext,"Email is already registered",Toast.LENGTH_SHORT).show()
                }else{
                    reference.child(pk).setValue(user)
                    Toast.makeText(applicationContext, "Register successfully", Toast.LENGTH_SHORT).show()
                    var intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }
//    private fun showDatePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val year = calendar.get(Calendar.YEAR)
//        val month = calendar.get(Calendar.MONTH)
//        val day = calendar.get(Calendar.DAY_OF_MONTH)
//
//        val datePickerDialog = DatePickerDialog(this,
//            DatePickerDialog.OnDateSetListener { view: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
//                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
//                edtBirthday.setText(selectedDate)
//            }, year, month, day )
//        datePickerDialog.show()
//    }


}