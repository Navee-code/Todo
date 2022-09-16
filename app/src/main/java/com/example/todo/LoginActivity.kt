package com.example.todo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivityLoginBinding



class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signup.setOnClickListener{
            intent= Intent(this,SignupActivity::class.java)
            startActivity(intent)
        }
        binding.login.setOnClickListener {
            intent= Intent(this,HomeActivity::class.java)
            startActivity(intent)
        }

    }

}