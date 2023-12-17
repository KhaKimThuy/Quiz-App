package com.example.afinal.Activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Patterns
import android.view.View
import android.widget.EditText
import android.widget.ImageView
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

        var isPasswordVisible = false
        binding.imgShow1.setOnClickListener(View.OnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.edtPassword, isPasswordVisible, binding.imgShow1)
        })
        binding.imgShow2.setOnClickListener(View.OnClickListener {
            isPasswordVisible = !isPasswordVisible
            togglePasswordVisibility(binding.edtPasswordConfirm, isPasswordVisible, binding.imgShow2)
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
<<<<<<< HEAD
                            binding.edtPassword.error = "Mật khẩu phải có ít nhất 8 kí tự"
=======
//                            binding.edtPassword.error = "The password must be longer than 8 characters"
                            binding.tvPassword.setText("Mật khẩu phải nhiều hơn 8 ký tự")
>>>>>>> 1fb543880d7be2a23c4dcc3a1376427550402204
                        }
                        else {
                            writeNewUser(email, pass)
                        }
                    }else{
<<<<<<< HEAD
                        binding.edtPasswordConfirm.error = "Mật khẩu không khớp";
=======
                        binding.tvConfirmP.setText("Xác nhận mật khẩu không trùng khớp")
//                        binding.edtPasswordConfirm.error = "Password is not matching";
>>>>>>> 1fb543880d7be2a23c4dcc3a1376427550402204
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

        binding.edtPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                binding.tvPassword.setText("")
                binding.tvConfirmP.setText("")
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })

    }

    private fun togglePasswordVisibility(editText: EditText, isVisible: Boolean, img : ImageView) {
        if (isVisible) {
            // Hiển thị mật khẩu
            editText.transformationMethod = HideReturnsTransformationMethod.getInstance()
            editText.setSelection(editText.text.length)
            img.setImageResource(R.drawable.hidden)
        } else {
            // Ẩn mật khẩu
            editText.transformationMethod = PasswordTransformationMethod.getInstance()
            editText.setSelection(editText.text.length)
            img.setImageResource(R.drawable.view)
        }
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