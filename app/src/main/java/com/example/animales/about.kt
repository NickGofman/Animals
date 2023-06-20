package com.example.animales

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Button


class about : AppCompatActivity() {
    lateinit var contact_button: Button
    lateinit var share_button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        contact_button = findViewById(R.id.contact_button)
        share_button = findViewById(R.id.share_button)

        // Contact button click listener
        contact_button.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO)
            emailIntent.data = Uri.parse("mailto:animalProject@gmail.com") // Set email address as recipient
            startActivity(emailIntent)
        }

        // Share button click listener
        share_button.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, "Check out this awesome Animal App!")
            startActivity(Intent.createChooser(shareIntent, "Share via"))
        }
    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu1, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_animalList-> {
                var intent = Intent(this,MainActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.menu_about -> {
                var intent = Intent(this,about::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}