package com.example.todo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.todo.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class ProfileFragment :Fragment() {
    private lateinit var auth: FirebaseAuth
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view= inflater.inflate(R.layout.fragment_profile,container,false)
            val email=view.findViewById<TextView>(R.id.SetEmail)
            val age=view.findViewById<TextView>(R.id.setAge)
            val dob=view.findViewById<TextView>(R.id.setDOB)
            val note=view.findViewById<TextView>(R.id.noteCount)
        val back=view.findViewById<Button>(R.id.button)
        back.setOnClickListener {
            val manager = requireActivity().supportFragmentManager
            manager.beginTransaction().remove(this).commit()
        }
        auth= FirebaseAuth.getInstance()
        val admin= auth.currentUser?.email
        val db = Firebase.database
        val myRef = db.getReference("TODO").child(admin?.replace('.','_').toString())
        myRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                    email.text = snapshot.child("EMAIL").value.toString()
                age.text = snapshot.child("AGE").value.toString()
                  dob.text=snapshot.child("DOB").value.toString()
                note.text=snapshot.child("Notes").childrenCount.toString()




            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(view.context, error.message.toString(), Toast.LENGTH_SHORT).show()

            }
        })

        return view
    }


}