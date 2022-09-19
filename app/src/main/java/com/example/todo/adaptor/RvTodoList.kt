package com.example.todo.adaptor

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.activity.ListTodo

class RvTodoList(list1: ArrayList<String>) : RecyclerView.Adapter<RvTodoList.ViewHolder>() {


    private  var list=list1


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text= list.get(position)
        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }



    inner class ViewHolder(itemVIew: View): RecyclerView.ViewHolder(itemVIew){
        val text=itemVIew.findViewById<TextView>(R.id.todo_text)
        var context= itemVIew.context

    }
}