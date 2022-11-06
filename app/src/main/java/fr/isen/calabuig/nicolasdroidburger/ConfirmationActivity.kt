package fr.isen.calabuig.nicolasdroidburger

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.icu.util.Calendar
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import fr.isen.calabuig.nicolasdroidburger.MainActivity.Companion.sharedPrefFile
import java.util.jar.Attributes

class ConfirmationActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val buttonConfirmer = findViewById<Button>(R.id.button2)
        val buttonRetour = findViewById<Button>(R.id.button3)


        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        val Nom: String? = sharedPreferences.getString( "Nom","problemeNom" )
        val Prenom: String? = sharedPreferences.getString( "Prenom","problemePrenom" )
        val Adresse: String? = sharedPreferences.getString( "Adresse","problemeAdresse" )
        val Telephone: String? = sharedPreferences.getString( "Telephone","problemeTelephone" )
        val Burger: String? = sharedPreferences.getString( "Burger","problemeBurger" )
        val Heure: String? = sharedPreferences.getString( "Heure","problemeHeure" )
        var jour_nuit: String = "Bonjour"
        val mcurrentTime = Calendar.getInstance()
        val heureMoment = mcurrentTime.get(Calendar.HOUR_OF_DAY)

        if(heureMoment > 18){ jour_nuit = "Bonsoir" }

        textView4.text = "${jour_nuit}  Mr/Mme ${Nom} ${Prenom} \n\rMerci pour votre commande du ${Burger} \n\rIl vous sera livré à l'adresse :\r\n ${Adresse} \r\nau plus tard à ${Heure} \r\nNotre livreur vous appellera au ${Telephone}\n\r"+
                "S'il vous plait vérifiez que tout les champs sont corrects avant de confirmer"


        fun sendEmail(to: String, subject: String, msg: String) {

            val emailIntent = Intent(Intent.ACTION_SEND)

            emailIntent.data = Uri.parse("mailto:")
            emailIntent.type = "text/plain"
            emailIntent.putExtra(Intent.EXTRA_EMAIL, arrayOf(to))
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            emailIntent.putExtra(Intent.EXTRA_TEXT, msg)

            try {
                startActivity(Intent.createChooser(emailIntent, "Send Email"))
            } catch (ex: ActivityNotFoundException) {
                //toast(R.string.text_no_email_client)
            }
        }
        //val recuperationNom: String? = sharedPreferences.getString( "saveNom_key","Nom" )
        buttonConfirmer.setOnClickListener {
            sendEmail("Marc.mollinari@gmail.com","Confirmation commande","Votre commande a bien été enregistrée")
         }
        buttonRetour.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}