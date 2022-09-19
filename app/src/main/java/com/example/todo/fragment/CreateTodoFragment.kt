package com.example.todo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.google.android.material.textfield.TextInputLayout
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class CreateTodoFragment :Fragment() {
    private lateinit var auth: FirebaseAuth
    private var notes=ArrayList<String>()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view=inflater.inflate(R.layout.fragment_todo,container,false)
        val text1=view.findViewById<TextInputLayout>(R.id.todo_edit_text)
        val button=view.findViewById<Button>(R.id.create_todo)
        auth= FirebaseAuth.getInstance()
        val admin= auth.currentUser?.email
        val db = Firebase.database
        val myRef = db.getReference("TODO").child(admin?.replace('.','_').toString())
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val snap= snapshot.hasChild("Notes")

                if(snap){
                    var count=snapshot.child("Notes").childrenCount
                    for(i in 0 ..count-1){
                        notes.add(snapshot.child("Notes").child(i.toString()).value.toString())
                    }

                }



            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(view.context, error.message.toString(), Toast.LENGTH_SHORT).show()

            }
        })
        button.setOnClickListener {

                var todos= text1.editText?.text.toString()
                notes.add(todos)
                myRef.child("Notes").setValue(notes)




        }
        return view
    }
}