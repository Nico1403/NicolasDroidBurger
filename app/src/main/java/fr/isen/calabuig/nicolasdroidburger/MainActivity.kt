package fr.isen.calabuig.nicolasdroidburger

import android.app.TimePickerDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi

class MainActivity : AppCompatActivity() {
    companion object{ const val sharedPrefFile = "nicolasdroidburger" }
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val burger_list = resources.getStringArray(R.array.burger_list)
        val spinner = findViewById<Spinner>(R.id.spinner)
        val selectTime = findViewById<Button>(R.id.selectTime)
        val button = findViewById<Button>(R.id.button)
        val editTextNom = findViewById<EditText>(R.id.editTextNom)
        val editTextPrenom = findViewById<EditText>(R.id.editTextPrenom)
        val editTextAdresse = findViewById<EditText>(R.id.editTextAdresse)
        val editTextTelephone = findViewById<EditText>(R.id.editTextTelehone)
        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile, Context.MODE_PRIVATE)
        var position_burger : String = "Burger_Error"

        //val TimePickerDialog
        val mTimePicker: TimePickerDialog
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)

        mTimePicker = TimePickerDialog(this, object : TimePickerDialog.OnTimeSetListener {
            override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
                selectTime.text=(String.format("%d : %d", hourOfDay, minute))
            }
        }, hour, minute, true)

        selectTime.setOnClickListener({ v ->
            mTimePicker.show()
        })


// set on-click listener
        button.setOnClickListener {
            if (selectTime.text.equals("Select Time") || selectTime.text.isEmpty() || editTextPrenom.text.isEmpty() || editTextNom.text.isEmpty() || editTextAdresse.text.isEmpty() || editTextTelephone.text.isEmpty() || editTextTelephone.text.length != 10){
            Toast.makeText(this@MainActivity, "Merci de remplir tout les champs correctement avant de valider.", Toast.LENGTH_SHORT).show()
            }
            else { Toast.makeText(this@MainActivity, "Merci", Toast.LENGTH_SHORT).show()
                val prenomConfirme:String = editTextPrenom.text.toString()
                val NomConfirme:String = editTextNom.text.toString()
                val adresseConfirme:String = editTextAdresse.text.toString()
                val telephoneConfirme:String = editTextTelephone.text.toString()
                val burgerConfirme: String = position_burger
                val saveHeure:String = selectTime.text.toString()
                val editor:SharedPreferences.Editor =  sharedPreferences.edit()
                editor.putString("Prenom",prenomConfirme)
                editor.putString("Nom",NomConfirme)
                editor.putString("Adresse",adresseConfirme)
                editor.putString("Telephone",telephoneConfirme)
                editor.putString("Burger",burgerConfirme)
                editor.putString("Heure",saveHeure)
                editor.apply()
                editor.commit()
                System.out.println(sharedPreferences.all)
                val intent = Intent(this, ConfirmationActivity::class.java)
                startActivity(intent)
            }
        }


        if (spinner != null) {
            val adapter = ArrayAdapter(
                this,
                android.R.layout.simple_spinner_item, burger_list
            )
            spinner.adapter = adapter

            spinner.onItemSelectedListener = object :
                AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View, position: Int, id: Long
                ) {
                    position_burger = burger_list[position]
                    Toast.makeText(
                        this@MainActivity,
                        getString(R.string.selected_item) + " " +
                                "" + burger_list[position], Toast.LENGTH_SHORT

                    ).show()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {
                    // write code to perform some action
                }
            }
        }
    }
}