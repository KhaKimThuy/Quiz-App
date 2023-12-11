package com.example.afinal.Activity

import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.UserDAL
import com.example.afinal.Domain.UserDomain
import com.example.afinal.R
import com.example.afinal.databinding.ActivityRegisterBinding


class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnRegister.setOnClickListener(View.OnClickListener {
            binding.progressBar5.visibility = View.VISIBLE

            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()
            val confirm = binding.edtPasswordConfirm.text.toString()

            if (email.isNullOrEmpty() || pass.isNullOrEmpty() && confirm.isNullOrEmpty()){
                if (email.isEmpty())  binding.edtEmail.error = "Email is empty";
                if (pass.isEmpty())  binding.edtPassword.error = "Password is empty";
                if (confirm.isEmpty())  binding.edtPasswordConfirm.error = "Please, confirm password";
            }else{
                if (!isValidEmail(email)){
                    binding.edtEmail.error = "Email form is invalid";
                }else{
                    if (pass == confirm){
                        writeNewUser(email, pass)
                    }else{
                        binding.edtPasswordConfirm.error = "Password is not matching";
//                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

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

    fun isValidEmail(target: String): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun writeNewUser(email: String, pass: String) {
        val user = UserDomain()
        user.email = email
        user.password = pass
        UserDAL().AddUser(user, this)
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