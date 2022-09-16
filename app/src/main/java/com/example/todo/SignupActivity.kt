package com.example.todo

import android.app.DatePickerDialog
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivitySignupBinding
import java.sql.Date
import java.text.SimpleDateFormat
import java.util.*

class SignupActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.signUpAge.setOnClickListener {
            getAge()
        }
    }

    private fun getAge() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        val simpleDateFormat = SimpleDateFormat("yyyy")
        val currentYear: String = simpleDateFormat.format(Date())
        val dpd = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            var age=currentYear.toInt()-year
            binding.signUpAge.setText(age.toString())
            binding.hintAge.hint = "AGE"
        }, year, month, day)
        dpd.show()

    }


}