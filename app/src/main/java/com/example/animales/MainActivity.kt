package com.example.animales

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AlertDialog
import java.util.Objects



class MainActivity : AppCompatActivity() {
    //listView object to insert in to
    lateinit var animalList: ListView
    //MediaPlayer for a button
    lateinit var mediaPlayer: MediaPlayer
    //Spinner  object to insert in to
    lateinit var spinner: Spinner
    lateinit var ageSpinner:Spinner

    //AutoCompleteTextView  object to set
    lateinit var  textFilter: AutoCompleteTextView
    lateinit var  locationFilter: AutoCompleteTextView
    lateinit var exitDialog: AlertDialog

    lateinit var oldestAnimal:Button
    lateinit var goToZoo:Button



    //build arrays
    var animalNames = arrayOf("squirrel","panda","dog","seal","polar bear")
    var animalImages = arrayOf(R.drawable.squirrel,
        R.drawable.panda,
        R.drawable.dog,
        R.drawable.seal,
        R.drawable.polar_bear)
    val animalVoices = arrayOf(R.raw.squirrel,R.raw.panda,R.raw.dog,R.raw.seal,R.raw.polar_bear)
    val animalAge = arrayOf(33,20,10,13,40)
    val animalFavFood= arrayOf("Nuts","Bamboo","Dogly","Fish","Seals")
    val animalFavLocation = arrayOf("New York","Japan","Park","In the water","northern Arctic")
    var zoo=ArrayList<String>()
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //get listView object
        animalList= findViewById(R.id.animal_list)
        oldestAnimal=findViewById(R.id.showOldestBtn)
        locationFilter=findViewById(R.id.autoCompleteLocation)
        goToZoo=findViewById(R.id.goToZooBtn)
        //set the data for the animal list view(show list!)
        animalList.adapter =ShowList(animalNames,animalImages,animalVoices)

        //get AutoCompleteTextView object
        textFilter = findViewById<AutoCompleteTextView>(R.id.autoComplet)

        //set the animal names to the auto complete field
        val adapter = ArrayAdapter(this,android.R.layout.simple_list_item_1,animalNames)
        textFilter.setAdapter(adapter)
        //start filtering from the first letter
        textFilter.threshold = 1

        // listener for the AutoCompleteTextView
        textFilter.setOnItemClickListener{
                parent,view,position,id ->
            // get the selected animal name
            val selectedAnimal = parent.getItemAtPosition(position).toString()
            //find the index-of the animal name in the animal array
            var indexAnimal = animalNames.indexOf(selectedAnimal)
            //filter data
            val filteredAnimalNames = animalNames.filter { it == selectedAnimal }.toTypedArray()
            val filteredAnimalImages = animalImages.filterIndexed{index,_ -> index==indexAnimal}.toTypedArray()
            val filteredAnimalSound = animalVoices.filterIndexed{index,_ -> index==indexAnimal}.toTypedArray()
            //set the data for the animal list view
            animalList.adapter = ShowList(filteredAnimalNames, filteredAnimalImages,filteredAnimalSound)
        }
        val adapterFavLocation = ArrayAdapter(this,android.R.layout.simple_list_item_1,animalFavLocation)
        locationFilter.setAdapter(adapterFavLocation)
        //start filtering from the first letter
        locationFilter.threshold = 1

        // listener for the AutoCompleteTextView
        locationFilter.setOnItemClickListener{
                parent,view,position,id ->
            // get the selected animal name
            val selectedLocation = parent.getItemAtPosition(position).toString()
            //find the index-of the animal name in the animal array
            var indexAnimal = animalFavLocation.indexOf(selectedLocation)
            //filter data
            val filteredAnimalNames = animalNames.filterIndexed { index,_ -> index==indexAnimal }.toTypedArray()
            val filteredAnimalImages = animalImages.filterIndexed{index,_ -> index==indexAnimal}.toTypedArray()
            val filteredAnimalSound = animalVoices.filterIndexed{index,_ -> index==indexAnimal}.toTypedArray()
            //set the data for the animal list view
            animalList.adapter = ShowList(filteredAnimalNames, filteredAnimalImages,filteredAnimalSound)
        }

        //get spinner object
        spinner = findViewById(R.id.mySpinner)
        //create an adapter for array to spinner
        val arrayAdapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,animalNames)
        //set the animal names to the spinner field
        spinner.adapter = arrayAdapter
        //remove auto selection on the first initialization
        spinner.setSelection(0,false)

        // listener for the spinner
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                // get the selected animal name, image and voice by position
                val selectedAnimal = animalNames[position]
                val selectedAnimalImage = animalImages[position]
                val selectedAnimaSound = animalVoices[position]
                //filter data
                val filteredAnimalNames = animalNames.filter { it == selectedAnimal }.toTypedArray()
                val filteredAnimalImages = animalImages.filter{ it == selectedAnimalImage  }.toTypedArray()
                val filteredAnimalSound = animalVoices.filter{ it == selectedAnimaSound  }.toTypedArray()
                //set the data for the animal list view
                animalList.adapter = ShowList(filteredAnimalNames, filteredAnimalImages,filteredAnimalSound)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        ageSpinner=findViewById(R.id.agesSpinner)
        val ages=ArrayList<String>()
        ages.add("All")
        //insert elements from regular array to ArrayList
        for (age in animalAge) {
            ages.add(age.toString())
        }
        //spinnet for filter by age
        val adapter2 = ArrayAdapter(this, android.R.layout.simple_spinner_item, ages)
        ageSpinner.adapter = adapter2
        ageSpinner.setSelection(0,false)
        ageSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
                if(position==0){
                    animalList.adapter =ShowList(animalNames, animalImages,animalVoices)
                }
                else {
                    // get the selected animal name, image and voice by position
                    val selectedAnimal = animalNames[position - 1]
                    val selectedAnimalImage = animalImages[position - 1]
                    val selectedAnimaSound = animalVoices[position - 1]
                    //filter data
                    val filteredAnimalNames =
                        animalNames.filter { it == selectedAnimal }.toTypedArray()
                    val filteredAnimalImages =
                        animalImages.filter { it == selectedAnimalImage }.toTypedArray()
                    val filteredAnimalSound =
                        animalVoices.filter { it == selectedAnimaSound }.toTypedArray()
                    //set the data for the animal list view
                    animalList.adapter =
                        ShowList(filteredAnimalNames, filteredAnimalImages, filteredAnimalSound)
                }
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
            }
        }

        //show to oldest animal info
        oldestAnimal.setOnClickListener{
            var max=0
            var i=0
            animalAge.forEachIndexed{index,age->
                if(age>max) {
                    max = age
                    i=index
                }
            }
            showAnimalInfoPage(animalNames[i],animalImages[i],animalAge[i],animalFavFood[i],animalFavLocation[i])
        }
        goToZoo.setOnClickListener {
            //create new Intent
            var intent =Intent(this,Zoo::class.java)
            //send the ArrayList to the activity Zoo
            intent.putExtra("zoo_array",zoo)
            startActivity(intent)
        }

    }
    //create toolBar
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu1, menu)
        return true
    }
    //make toolBar work
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_about -> {
                var intent = Intent(this,about::class.java)
                startActivity(intent)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    //class to show the list of animals
    inner class ShowList(private var animalNames: Array<String>, private var animalImages: Array<Int>, private val animalSound :Array<Int> ): BaseAdapter()
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
            // get the animal_list_item view layout, to get his fields
            var  view = layoutInflater.inflate(R.layout.animal_list_item,perent,false)
            var animalImage:ImageView
            var animalName:TextView
            var deleteBtn:Button
            var addToZoo:Button

            addToZoo=view.findViewById(R.id.add_to_zoo_button)
            //get animal name from the animal_list_item layout
            animalName = view.findViewById(R.id.animal_name)
            //get the selected animal name
            animalName.text = animalNames[position]
            //get animal image from the animal_list_item layout
            animalImage = view.findViewById(R.id.animal_image)
            //get the selected animal image
            animalImage.setImageResource(animalImages[position])

            deleteBtn=view.findViewById(R.id.delete_button)
            deleteBtn.setOnClickListener {
                animalNames = animalNames.filterIndexed { index, _ -> index != position }.toTypedArray()
                animalImages = animalImages.filterIndexed { index, _ -> index != position }.toTypedArray()
                animalList.adapter = ShowList(animalNames, animalImages, animalVoices)
            }
            addToZoo.setOnClickListener {
                //add Animal name to the ArrayList
                zoo.add(animalNames[position])
            }


            // set listener for clicking on one of the animals in the list
            view.setOnClickListener {
                val animal = animalNames[position]
                val animalImage = animalImages[position]
                val animalSound= animalSound[position]
                val animalAge=animalAge[position]
                val animalFaveFood=animalFavFood[position]
                val animalLocation=animalFavLocation[position]
                showAnimalInfoPage(animal,animalImage,animalAge,animalFaveFood,animalLocation)
                //show dialog
//                showAnimalDialog(animal, animalImage,animalSound,position)
            }


            return view

        }



    }

    private fun showAnimalInfoPage(animalName: String, animalImage: Int,animalAge: Int,animalFaveFood:String,animalLocation:String){
        //function send animal info to another page
        var intent =Intent(this,animalInfo::class.java)
        intent.putExtra("animalName",animalName)
        intent.putExtra("animalImage",animalImage)
        intent.putExtra("animalAge",animalAge)
        intent.putExtra("animalFaveFood",animalFaveFood)
        intent.putExtra("animalLocation",animalLocation)

        startActivity(intent)
    }
    @SuppressLint("MissingInflatedId")
    private fun showAnimalDialog(animal: String, animalImage: Int,animalSound: Int,position: Int) {
        //create dialog
        val dialogBuilder = AlertDialog.Builder(this)
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.dialog_animal_info, null)
        //get fields
        val animalImageView: ImageView = dialogView.findViewById(R.id.dialog_animal_image)
        val animalNameTextView: TextView = dialogView.findViewById(R.id.dialog_animal_name)
        val animalRating:RatingBar = dialogView.findViewById(R.id.rating)
        val makeSound:Button = dialogView.findViewById(R.id.btn_add_animal)
        //listener for btn making animal sound
        makeSound.setOnClickListener {
            mediaPlayer = MediaPlayer.create(this, animalSound)
            mediaPlayer.start()
        }
        //set animal name
        animalNameTextView.text = animal
        //set animal image
        animalImageView.setImageResource(animalImage)
        //set the animal saved rating
        animalRating.rating = getSavedRating(animalNames[position])
        //listener for animal rating changed
        animalRating.setOnRatingBarChangeListener { _, rating, _ ->
            //save the new rating
            saveRating(animalNames[position],rating)
        }

        //set dialogView
        dialogBuilder.setView(dialogView)
        //close dialog with ok btn
        dialogBuilder.setPositiveButton("OK") { dialogInterface, _ ->
            dialogInterface.dismiss()
        }
        //create dialog
        val alertDialog = dialogBuilder.create()
        alertDialog.show()
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
    override fun onBackPressed() {
        //function show the dialog before closing
        //the app
        showExitDialog()
    }
    private fun showExitDialog() {
        //function create and show dialog before exit the app
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Exit App")
        builder.setMessage("Are you sure you want to exit?")
        builder.setPositiveButton("Yes") { _, _ ->
            // User clicked "Yes," so exit the app
            finish()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            // User clicked "No," so dismiss the dialog
            dialog.dismiss()
        }
        exitDialog = builder.create()
        exitDialog.show()
    }


}