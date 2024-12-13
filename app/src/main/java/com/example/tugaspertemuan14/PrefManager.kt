package com.example.tugaspertemuan14

import android.content.Context

class PrefManager (context: Context) {

    private val sharedPreferences = context.getSharedPreferences("UserSession", Context.MODE_PRIVATE)
    private val editor = sharedPreferences.edit()

    fun saveUsername(username: String) {
        editor.putString("username", username)
        editor.apply()
    }

    fun getUsername(): String {
        return sharedPreferences.getString("username", "") ?: ""
    }

    fun clear() {
        editor.clear()
        editor.apply()
    }

}