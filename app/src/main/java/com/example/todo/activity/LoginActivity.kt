package com.example.todo.activity

import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.example.todo.MyPreferences
import com.example.todo.R
import com.example.todo.databinding.ActivityLoginBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)

        auth= FirebaseAuth.getInstance()
        if(auth.currentUser!=null) {
            intent= Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }else{
            binding=ActivityLoginBinding.inflate(layoutInflater)
            setContentView(binding.root)
            binding.progressCircular2.visibility= View.INVISIBLE
            binding.signup.setOnClickListener{
                intent= Intent(this, SignupActivity::class.java)
                startActivity(intent)
            }
            binding.login.setOnClickListener {
                     loginUser()
            }
        }
        }



    private fun loginUser() {
        val email=binding.loginUser.text.toString()
        val password=binding.loginPassword.text.toString()

        if(email.isEmpty()){
            binding.loginUser.error = "FILL"
            binding.loginUser.requestFocus()
            binding.progressCircular2.visibility= View.INVISIBLE
        }else if(password.isEmpty()){
            var toast=Toast.makeText(applicationContext,"Fill password", Toast.LENGTH_LONG)
            toast?.setGravity(Gravity.TOP,0,0)
            toast?.show()

        }else{
            binding.progressCircular2.visibility=View.VISIBLE
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener { signIn ->
                    if (signIn.isSuccessful) {

                        intent= Intent(this, HomeActivity::class.java)
                        startActivity(intent)

                        binding.progressCircular2.visibility=View.INVISIBLE
                        Toast.makeText(applicationContext,"registered", Toast.LENGTH_LONG).show()

                    } else {
                        binding.progressCircular2.visibility=View.INVISIBLE
                        Toast.makeText(applicationContext,"Enter the valid credentials", Toast.LENGTH_SHORT).show()
                    }
                }
        }

    }

    override fun onBackPressed() {
        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(a)
        super.onBackPressed()
    }
}




