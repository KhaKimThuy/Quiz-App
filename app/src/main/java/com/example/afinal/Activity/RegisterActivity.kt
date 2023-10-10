package com.example.afinal.Activity

import android.app.DatePickerDialog
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.widget.AppCompatButton
import java.util.Date
import com.example.afinal.R
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    private lateinit var btnRegister : AppCompatButton
    private lateinit var btnGoogle : AppCompatButton
    private lateinit var btnFacebook : AppCompatButton

    private lateinit var edtBirthday: EditText
    private lateinit var edtPassword : EditText
    private lateinit var edtEmail : EditText

    private lateinit var tvBirthday : TextView
    private lateinit var tvPassword : TextView
    private lateinit var tvEmail : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        btnRegister = findViewById(R.id.btnRegister)
        btnGoogle = findViewById(R.id.btnGoogle)
        btnFacebook = findViewById(R.id.btnFacebook)

        edtBirthday = findViewById(R.id.edtBirthday)
        edtPassword = findViewById(R.id.edtPassword)
        edtEmail = findViewById(R.id.edtEmail)

        tvBirthday = findViewById(R.id.tvBirthday)
        tvPassword = findViewById(R.id.tvPassword)
        tvEmail = findViewById(R.id.tvEmail)

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val currentDate = sdf.format(Date())
        edtBirthday.setHint(currentDate)

        edtBirthday.setOnClickListener {
            showDatePickerDialog()
        }

        btnRegister.setOnClickListener(object : View.OnClickListener {
            override fun onClick(p0: View?) {

            }

        })

        edtBirthday.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if(!b) {
                if(edtBirthday.text.toString().endsWith(currentDate)) {
                    tvBirthday.setText(R.string.day_invalid);
                    tvBirthday.setTextColor(resources.getColor(R.color.red))
                }
                else {
                    tvBirthday.setText(R.string.birthday);
                    tvBirthday.setTextColor(resources.getColor(R.color.black))
                }
            }
        }


        edtEmail.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                if (edtEmail.text.toString().endsWith("@gmail.com")) {
                    tvEmail.text = getString(R.string.email_of_parents)
                    tvEmail.setTextColor(resources.getColor(R.color.black))
                } else {
                    tvEmail.text = getString(R.string.email_invalid)
                    tvEmail.setTextColor(resources.getColor(R.color.red))
                }
            }
        }

        edtPassword.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                if (edtPassword.text.toString().length >= 8) {
                    tvPassword.setText(R.string.password);
                    tvPassword.setTextColor(resources.getColor(R.color.black))
                } else {
                    tvPassword.setText(R.string.pass_invalid);
                    tvPassword.setTextColor(resources.getColor(R.color.red))
                }
            }
        }

        btnRegister.isEnabled = false;
        btnRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_gray));

        edtPassword.onFocusChangeListener = View.OnFocusChangeListener { view, b ->
            if (!b) {
                if(!edtBirthday.text.isEmpty() && !edtPassword.text.isEmpty() && !edtEmail.text.isEmpty()) {
                    btnRegister.isEnabled = true;
                    btnRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_blue));
                }
                else {
                    btnRegister.isEnabled = false;
                    btnRegister.setBackgroundDrawable(resources.getDrawable(R.drawable.custom_button_gray));
                }
            }
        }

    }
    private fun showDatePickerDialog() {
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { view: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                val selectedDate = "$selectedDay/${selectedMonth + 1}/$selectedYear"
                edtBirthday.setText(selectedDate)
            }, year, month, day )
        datePickerDialog.show()
    }


}