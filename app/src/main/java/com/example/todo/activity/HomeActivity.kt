package com.example.todo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.compose.runtime.key
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth= FirebaseAuth.getInstance()
        val admin= auth.currentUser?.email
        val db = Firebase.database
        val myRef = db.getReference("TODO")
        binding.bottomNavigationView.setOnNavigationItemReselectedListener {
            when (it.itemId) {
                R.id.home -> {
                    return@setOnNavigationItemReselectedListener
                }
                R.id.home2 -> {
                    supportFragmentManager.beginTransaction().replace(R.id.frame, ProfileFragment()).commit()
                    return@setOnNavigationItemReselectedListener
                }
            }
        }

        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                            val snap= snapshot.children
                  for(it in snap){
                      keys.add(snapshot.child(it.key.toString()).key.toString())
                  }


                list1.clear()

                binding.userId.text=snapshot.child(admin.toString().replace('.','_')).child("NAME").value.toString()
                                    for(item in snap){
                        if(admin.toString().replace('.','_') != item.key) {
                            list1.add(snapshot.child(item.key.toString()).child("NAME").value.toString())
                        }
                        }
                binding.recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                binding.recycler.adapter= RvUserList(list1)
                ListTodo().setKey(keys)

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
}


//fragment close
//val fragment = SettingFragment.newInstance()
//
//// always create a new transaction to avoid crash
//val mTransaction = fragmentManager.beginTransaction()
//mTransaction.add(R.id.settingDrawer, fragment)
//mTransaction.commit()


