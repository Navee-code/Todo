package com.example.todo.activity

import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.todo.databinding.ActivitySignupBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import java.text.SimpleDateFormat
import java.util.*

class SignupActivity : AppCompatActivity() {
    private lateinit var binding:ActivitySignupBinding
    private lateinit var auth:FirebaseAuth
    private  var DOB:String=""
    private val db = Firebase.database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.progressCircular2.visibility= View.INVISIBLE
         auth= FirebaseAuth.getInstance()
        binding.signUp.setOnClickListener {
            createUser()
        }
        binding.signUpAge.setOnClickListener {
            getAge()
        }
    }

    private fun createUser() {
        val name=binding.signUpName.text.toString()
        val email=binding.signUpEmail.text.toString()
        val password=binding.signUpPassword.text.toString()


if(name.isEmpty()){
    binding.signUpName.error = "FILL"
    binding.signUpName.requestFocus()
} else if(email.isEmpty()){
            binding.signUpEmail.error = "FILL"
            binding.signUpEmail.requestFocus()
        }else if(DOB.isEmpty()){
    var toast=Toast.makeText(applicationContext,"Enter your DOB", Toast.LENGTH_LONG)
    toast?.setGravity(Gravity.TOP,0,0)
    toast?.show()
        } else if(password.isEmpty()){
    var toast=Toast.makeText(applicationContext,"Fill password", Toast.LENGTH_LONG)
    toast?.setGravity(Gravity.TOP,0,0)
    toast?.show()
      }else{
    binding.progressCircular2.visibility= View.VISIBLE
    signUp(email
        ,password,name)

}
    }

    private fun signUp(email: String, password: String, name: String) {

                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { signIn ->
                        if (signIn.isSuccessful) {
                            val myRef = db.getReference("TODO").child(email.replace('.','_'))
                            myRef.child("NAME").setValue(name)
                            myRef.child("EMAIL").setValue(email)
                            myRef.child("AGE").setValue(binding.signUpAge.text.toString())
                            myRef.child("DOB").setValue(DOB)
                            Toast.makeText(applicationContext,"registered",Toast.LENGTH_LONG).show()
                            binding.progressCircular2.visibility= View.INVISIBLE
                            intent=Intent(applicationContext, HomeActivity::class.java)
                            startActivity(intent)

                        } else {
                            binding.progressCircular2.visibility= View.INVISIBLE
                            Toast.makeText(applicationContext,"Failed",Toast.LENGTH_LONG).show()
                        }
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
            DOB= "$dayOfMonth - $monthOfYear - $year"

            binding.signUpAge.setText(age.toString())
            binding.hintAge.hint = "AGE"
        }, year, month, day)
        dpd.show()

    }
    override fun onBackPressed() {
       intent= Intent(this, LoginActivity::class.java)
        startActivity(intent)
        super.onBackPressed()
    }


}