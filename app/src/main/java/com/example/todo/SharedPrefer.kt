package com.example.todo

import android.content.Context
import android.content.SharedPreferences

class SharedPrefer {
    fun sharedSetName(b: String,context: Context) {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val editor: SharedPreferences.Editor =  sharedPreferences.edit()
        editor.putString("taken",b)
        editor.apply()
        editor.commit()
    }
    fun sharedGetName(context: Context): String? {
        val sharedPreferences: SharedPreferences = context.getSharedPreferences("session", Context.MODE_PRIVATE)
        val activity = sharedPreferences.getString("taken","")
        return activity
    }
}