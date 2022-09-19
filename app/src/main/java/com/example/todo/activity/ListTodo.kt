package com.example.todo.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.adaptor.RvTodoList
import com.example.todo.adaptor.RvUserList
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.databinding.ActivityListTodoBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ListTodo : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivityListTodoBinding
    private var list1=ArrayList<String>()
    private var keyVal=ArrayList<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityListTodoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        overridePendingTransition(R.anim.bottom_up, R.anim.nothing,)
        auth= FirebaseAuth.getInstance()
        val db = Firebase.database
        val key=intent.getIntExtra("Body",0)
        Log.e("TAG",key.toString())
        val myRef = db.getReference("TODO").child(key.toString())
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val snap= snapshot.hasChild("Notes")
                if(snap){
                    var count=snapshot.child("Notes").childrenCount
                    for(i in 0 ..count-1){
                        list1.add(snapshot.child("Notes").child(i.toString()).value.toString())
                    }

                }
                binding.recycler.layoutManager = LinearLayoutManager(applicationContext, RecyclerView.VERTICAL, false)
                binding.recycler.adapter= RvTodoList(list1)



            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(applicationContext, error.message.toString(), Toast.LENGTH_SHORT).show()

            }
        })


    }

    override fun onBackPressed() {
        var intent= Intent(applicationContext, HomeActivity::class.java)
        startActivity(intent)
        overridePendingTransition(R.anim.nothing, R.anim.bottom_down)
        super.onBackPressed()
    }

    fun setKey(keys: ArrayList<String>) {
   for(it in keys){
       keyVal.add(it.toString())

   }
    }
}
//
//myRef.addValueEventListener(object : ValueEventListener {
//    override fun onDataChange(snapshot: DataSnapshot) {
//

//    }
//
//    override fun onCancelled(error: DatabaseError) {
//        Toast.makeText(applicationContext, error.message.toString(), Toast.LENGTH_SHORT).show()
//
//    }
//
//})

