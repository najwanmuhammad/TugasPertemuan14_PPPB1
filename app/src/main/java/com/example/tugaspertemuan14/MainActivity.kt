package com.example.tugaspertemuan14

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.tugaspertemuan14.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager

    private var username = "najwan"
    private var password = "tes"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        prefManager = PrefManager(this)

        with(binding) {
            logIn.setOnClickListener{
                val usernameInput = namaLogIn.text.toString()
                val passInput = passwordLogIn.text.toString()
                if (usernameInput == username &&
                    passInput == password){
                    prefManager.saveUsername(usernameInput)
                    checking()
                } else{
                    Toast.makeText(this@MainActivity,
                        "Username atau Password Salah!",
                        Toast.LENGTH_SHORT).show()
                }
            }
        }

    }

    private fun checking(){
        val username = prefManager.getUsername()
        if (username.isNotEmpty()){
            startActivity(
                Intent(this@MainActivity,
                    WelcomeActivity::class.java)
            )
            finish()
        }
    }

}