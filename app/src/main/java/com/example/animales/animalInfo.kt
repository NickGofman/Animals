package com.example.animales

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView

class animalInfo : AppCompatActivity() {
    lateinit var animalNameView:TextView
    lateinit var animalImageView:ImageView
    lateinit var animalAgeView:TextView
    lateinit var animalFaveFoodView:TextView
    lateinit var animalLocationView:TextView
    lateinit var animalRatingBar:RatingBar
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_info)
        animalNameView=findViewById(R.id.AnimalName)
        animalImageView=findViewById(R.id.AnimalPhoto)
        animalAgeView=findViewById(R.id.AnimalAge)
        animalFaveFoodView=findViewById(R.id.AnimalFavoriteFood)
        animalLocationView=findViewById(R.id.AnimalFavLocation)
        animalRatingBar=findViewById<RatingBar>(R.id.AnimalRating)

        //get the data from the page that send the intent
        var animalName=intent.extras?.getString("animalName")
        var animalImage= intent.extras?.getInt("animalImage")
        var animalAge=intent.extras?.getInt("animalAge")
        var animalFaveFood=intent.extras?.getString("animalFaveFood")
        var animalLocation=intent.extras?.getString("animalLocation")
        animalNameView.text="Animal name: "+animalName
        animalImage?.let {
            animalImageView.setImageResource(it)
        }
        animalAgeView.text="Animal Age: "+animalAge.toString()
        animalFaveFoodView.text="Animal Favorite Food: "+animalFaveFood
        animalLocationView.text="Animal Favorite Location: "+animalLocation

        //get the saved rating bar value
        animalRatingBar.rating = animalName?.let { getSavedRating(it) }!!

        //save rating bar value with sharedPreferences
        animalRatingBar.setOnRatingBarChangeListener { _, rating, _ ->
            //save the new rating
            if (animalName != null) {
                saveRating(animalName,rating)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu1, menu)
        return true
    }
    //make toolBar work
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

    private fun saveRating(animalName: String, rating: Float) {
        //create sharedPreferences
        val sharedPreferences = getSharedPreferences("ratings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat(animalName, rating)
        //save the data
        editor.commit()
    }

    private fun getSavedRating(animalName: String): Float {
        //gat sharedPreferences
        val sharedPreferences = getSharedPreferences("ratings", Context.MODE_PRIVATE)
        //return the data
        return sharedPreferences.getFloat(animalName, 0.0f)
    }
}