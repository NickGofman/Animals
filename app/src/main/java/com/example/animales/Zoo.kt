package com.example.animales

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class Zoo : AppCompatActivity() {
    lateinit var zooList: ListView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoo)
        zooList=findViewById(R.id.animal_list)

        //get an ArrayList from the intent
        var animalZooList = intent.extras?.getStringArrayList("zoo_array") ?: arrayListOf() // Initialize an empty ArrayList if null

        //show the ArrayList in the ListView
        var adapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,animalZooList)
        zooList.adapter=adapter
    }
}