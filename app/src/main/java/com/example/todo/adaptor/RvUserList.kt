package com.example.todo.adaptor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R

class RvUserList(list1: ArrayList<String>) : RecyclerView.Adapter<RvUserList.ViewHolder>() {

  private  var list=list1

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_list, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.text.text= list.get(position)
    }

    override fun getItemCount(): Int {
       return list.size
    }

    inner class ViewHolder(itemVIew: View):RecyclerView.ViewHolder(itemVIew){
        val text=itemVIew.findViewById<TextView>(R.id.todo_text)

    }
}