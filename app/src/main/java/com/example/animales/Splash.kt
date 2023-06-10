package com.example.animales

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle


class Splash : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        val thread = Thread {
            try {
                // Add any code you want to execute in the background here
                Thread.sleep(3000) // Simulate a delay of 2 seconds
            } catch (e: Exception) {
                // Handle any exceptions that may occur during background execution
            } finally {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
        thread.start()
    }
}