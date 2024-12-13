package com.example.tugaspertemuan14

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotifLogout : BroadcastReceiver() {

    private lateinit var prefManager: PrefManager

    override fun onReceive(context: Context?, intent: Intent?) {
        context?.let {
            val prefManager = PrefManager(it)
            prefManager.clear()

            val loginIntent = Intent(it, MainActivity::class.java)
            loginIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            it.startActivity(loginIntent)
        }
    }

}