package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Patterns
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.afinal.DAL.UserDAL
import com.example.afinal.Domain.User
import com.example.afinal.R
import com.example.afinal.databinding.ActivityRegisterBinding

class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val e = intent.getStringExtra("email")
        val p = intent.getStringExtra("pass")

        if (e != null && p != null) {
            writeNewUser(e, p)
        }

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.imgBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        })

        binding.btnRegister.setOnClickListener(View.OnClickListener {
            binding.progressBar5.visibility = View.VISIBLE

            val email = binding.edtEmail.text.toString()
            val pass = binding.edtPassword.text.toString()
            val confirm = binding.edtPasswordConfirm.text.toString()

            if (email.isNullOrEmpty() || pass.isNullOrEmpty() && confirm.isNullOrEmpty()){
                if (email.isEmpty())  binding.edtEmail.error = "Email không đươcj trống";
                if (pass.isEmpty())  binding.edtPassword.error = "Mật khẩu không được trống";
                if (confirm.isEmpty())  binding.edtPasswordConfirm.error = "Vui lòng xác nhận lại mật khẩu";
            }else{
                if (!isValidEmail(email)){
                    binding.edtEmail.error = "Email không hợp lệ";
                }else{
                    if (pass == confirm){
                        if(pass.length < 8) {
                            binding.edtPassword.error = "Mật khẩu phải có ít nhất 8 kí tự"
                        }
                        else {
                            writeNewUser(email, pass)
                        }
                    }else{
                        binding.edtPasswordConfirm.error = "Mật khẩu không khớp";
//                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

//        binding.edtEmail.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
//            if (!b) {
//                if (binding.edtEmail.text.toString().endsWith("@gmail.com")) {
//                    binding.tvEmail.text = ""
//                    binding.tvEmail.setTextColor(resources.getColor(R.color.black))
//                } else {
//                    binding.tvEmail.text = getString(R.string.email_invalid)
//                    binding.tvEmail.setTextColor(resources.getColor(R.color.red))
//                }
//            }
//        }
//
//        binding.edtPassword.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
//            if (!b) {
//                if (binding.edtPassword.text.toString().length < 8) {
//                    binding.tvPassword.setText(R.string.pass_invalid);
//                } else {
//                    binding.tvPassword.setText("");
//                }
//            }
//        }

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

        binding.imgBack.setOnClickListener(View.OnClickListener {
            val intent = Intent(this, StartActivity::class.java)
            startActivity(intent)
        })

    }

    fun isValidEmail(target: String): Boolean {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }

    private fun writeNewUser(email: String, pass: String) {
        val user = User()
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