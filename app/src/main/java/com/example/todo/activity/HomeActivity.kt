package com.example.todo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.MyPreferences
import com.example.todo.R
import com.example.todo.adaptor.RvUserList
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.fragment.CreateTodoFragment
import com.example.todo.fragment.ProfileFragment
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class HomeActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityHomeBinding
    private var list1=ArrayList<String>()
    private var keys=ArrayList<String>()
    private lateinit var name:String
    var  state=true

    override fun onCreate(savedInstanceState: Bundle?) {

        if(MyPreferences(this).darkMode==1){
            setTheme(R.style.Dark_Todo)
        }else{
            setTheme(R.style.Theme_Todo)
        }
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth= FirebaseAuth.getInstance()
        val admin= auth.currentUser?.email
        val db = Firebase.database
        val myRef = db.getReference("TODO")
        checkTheme()
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            if(MyPreferences(this).darkMode==1){
                state=false
            }
            when (it.itemId) {

                R.id.home -> {
                    if(state){

                        intent= Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        MyPreferences(this).darkMode = 1
                        state=false
                    }else{

                        intent= Intent(this, HomeActivity::class.java)
                        startActivity(intent)
                        finish()
                        MyPreferences(this).darkMode = 0
                        state=true

                    }
                    return@setOnNavigationItemReselectedListener
                }
                R.id.home2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment()).commit()
                    return@setOnNavigationItemReselectedListener
                }
            }
        }
        binding.userInfo.setOnClickListener{
            var intent= Intent(applicationContext, ListTodo::class.java)
            intent.putExtra("Body",name)
            startActivity(intent)
        }
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {

                            val snap= snapshot.children
                list1.clear()
                  for(it in snap){
                      keys.add(snapshot.child(it.key.toString()).key.toString())
                      if(admin.toString().replace('.','_') != it.key.toString()) {
                          list1.add(snapshot.child(it.key.toString()).child("NAME").value.toString())
                      }else{
                          name=snapshot.child(it.key.toString()).child("NAME").value.toString()
                      }
                  }

                binding.userId.text=snapshot.child(admin.toString().replace('.','_')).child("NAME").value.toString()


                binding.recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                binding.recycler.adapter= RvUserList(list1)


            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message.toString(), Toast.LENGTH_SHORT).show()

            }

        })

        binding.fab.setOnClickListener {
            supportFragmentManager.beginTransaction().replace(R.id.frame, CreateTodoFragment()).commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.logout,menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        auth.signOut()
        intent = Intent(this, LoginActivity::class.java)
        startActivity(intent)
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {

        val a = Intent(Intent.ACTION_MAIN)
        a.addCategory(Intent.CATEGORY_HOME)
        a.flags = Intent.FLAG_ACTIVITY_NEW_TASK
        finish()
        startActivity(a)
        super.onBackPressed()
    }
    private fun checkTheme() {
        when (MyPreferences(this).darkMode) {
            0 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                delegate.applyDayNight()
            }
            1 -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                delegate.applyDayNight()
            }

        }
    }
}




