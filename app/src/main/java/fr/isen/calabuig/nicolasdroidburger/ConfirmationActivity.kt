package fr.isen.calabuig.nicolasdroidburger

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings.Global.getString
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import fr.isen.calabuig.nicolasdroidburger.MainActivity.Companion.sharedPrefFile
import java.util.jar.Attributes

class ConfirmationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)
        val textView4 = findViewById<TextView>(R.id.textView4)
        val button2 = findViewById<Button>(R.id.button2)


        val sharedPreferences: SharedPreferences = this.getSharedPreferences(sharedPrefFile,
            Context.MODE_PRIVATE)

        val Nom: String? = sharedPreferences.getString( "saveNom_key","NomError" )
        val Prenom: String? = sharedPreferences.getString( "savePrenom_key","PrenomError" )
        val Adresse: String? = sharedPreferences.getString( "saveAdresse_key","AdresseError" )
        val Telephone: String? = sharedPreferences.getString( "saveTelephone_key","TelephoneError" )
        val Burger: String? = sharedPreferences.getString( "saveBurger_key","BurgerError" )
        val Heure: String? = sharedPreferences.getString( "saveHeure_key","HeureError" )

        textView4.text = "merci  Mr/Mme ${Nom} ${Prenom} pour votre commande du ${Burger} \n\ril vous sera livré à l'adresse :\r\n ${Adresse} \r\nau plus tard à ${Heure} \r\nNotre livreur vous appellera au ${Telephone}"


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
        button2.setOnClickListener {
            sendEmail("nicolas.calabuig@isen.yncrea.fr","confirmation commande","commande de burger")

         }
    }
}