package com.example.animales

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog

class animalList : AppCompatActivity() {
    lateinit var animalList: ListView
    lateinit var ratingBar: RatingBar

    var animalNames = arrayOf("squirrel","panda","dog","seal","polar bear")
    var animalImages = arrayOf(R.drawable.squirrel,
                                R.drawable.panda,
                                R.drawable.dog,
                                R.drawable.seal,
                                R.drawable.polar_bear)

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animal_list)

        animalList= findViewById(R.id.animal_list)

        animalList.adapter =showList()

    }
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu1, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_animalList -> {
                var intent = Intent(this, com.example.animales.animalList::class.java)
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


    inner class showList() :BaseAdapter()
    {

        override fun getCount(): Int {
            return animalNames.size

        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getItem(position: Int): Any {
            return animalNames[position]
        }

        @SuppressLint("MissingInflatedId")
        override fun getView(position: Int, convertView: View?, perent: ViewGroup?): View {
            var  view = layoutInflater.inflate(R.layout.animal_list_item,perent,false)
           //get animal name
            var animalName:TextView
            animalName = view.findViewById(R.id.animal_name)
            animalName.text = animalNames[position]
            //get animal image
            var animalImage:ImageView
            animalImage = view.findViewById(R.id.animal_image)
            animalImage.setImageResource(animalImages[position])


            view.setOnClickListener {
                val animal = animalNames[position]
                val animalImage = animalImages[position]
                showAnimalDialog(animal, animalImage,position)
            }


            return view

        }

    }


    @SuppressLint("MissingInflatedId")
    private fun showAnimalDialog(animal: String, animalImage: Int,position: Int) {
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_animal_info, null)

        val animalImageView: ImageView = dialogView.findViewById(R.id.dialog_animal_image)
        val animalNameTextView: TextView = dialogView.findViewById(R.id.dialog_animal_name)
        val animalRating:RatingBar = dialogView.findViewById(R.id.rating)
        animalRating.rating = getSavedRating(animalNames[position])
        animalRating.setOnRatingBarChangeListener { _, rating, _ ->
            Log.d("setOnRatingBarChangeListener"," setOnRatingBarChangeListener $rating")
            saveRating(animalNames[position],rating)
        }
        animalImageView.setImageResource(animalImage)
        animalNameTextView.text = animal

        dialogBuilder.setView(dialogView)
        dialogBuilder.setPositiveButton("OK") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }

        val alertDialog = dialogBuilder.create()
        alertDialog.show()
    }
    private fun saveRating(animalName: String, rating: Float) {
        Log.d("saveRating","saveRating $animalName")
        val sharedPreferences = getSharedPreferences("ratings", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putFloat(animalName, rating)
        editor.commit()
    }

    private fun getSavedRating(animalName: String): Float {
        Log.d("getSavedRating"," getSavedRating $animalName")

        val sharedPreferences = getSharedPreferences("ratings", Context.MODE_PRIVATE)
        return sharedPreferences.getFloat(animalName, 0.0f)
    }
}
