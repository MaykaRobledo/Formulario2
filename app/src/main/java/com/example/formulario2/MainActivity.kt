package com.example.formulario2

import android.os.Bundle
import android.util.Patterns
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val Nombre = findViewById<EditText>(R.id.etNombre)
        val Apellido = findViewById<EditText>(R.id.etApellidos)
        val Correo = findViewById<EditText>(R.id.etCorreo)
        val Genero = findViewById<RadioGroup>(R.id.radioGroupGenero)
        val Pais = findViewById<Spinner>(R.id.spinnerPaisorigen)
        val Lectura = findViewById<CheckBox>(R.id.checkBoxLectura)
        val Deporte = findViewById<CheckBox>(R.id.checkBoxDeporte)
        val Musica = findViewById<CheckBox>(R.id.checkBoxMúsica)
        val Arte = findViewById<CheckBox>(R.id.checkBoxArte)
        val seekbarSatisfaccion = findViewById<SeekBar>(R.id.seekBarSatisfaccion)
        val Satisfaccion = findViewById<TextView>(R.id.textViewMostrarSatisfacción)
        val Boletin = findViewById<Switch>(R.id.switchBoletín)
        val Guardar = findViewById<Button>(R.id.buttonGuardar)
        val Resumen = findViewById<TextView>(R.id.textViewRespuestas)

        //Spinner Pais Origen
        val paises = arrayOf("España", "Francia", "Alemania", "Italia", "Reino Unido", "Andorra")
        val spinnerPaises = ArrayAdapter(this, android.R.layout.simple_spinner_item, paises)
        spinnerPaises.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        Pais.adapter = spinnerPaises

        //Seekbar Nivel Satisfaccion
        seekbarSatisfaccion.max = 10
        seekbarSatisfaccion.min = 0
        seekbarSatisfaccion.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                Satisfaccion.text = "Nivel de satisfaccion: $progress"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
            }
        })


        //Boton Guardar
        Guardar.setOnClickListener {
            val nombre = Nombre.text.toString()
            val apellidos = Apellido.text.toString()
            val correo = Correo.text.toString()

            //Validacion de datos (nombre, apellidos y correo vacios) (correo sin @, sin .com o sin .es)
            if (nombre.isEmpty() || apellidos.isEmpty() || correo.isEmpty()) {
                Toast.makeText(
                    this,
                    "Por favor complete todos los campos obligatorios.",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if(!Patterns.EMAIL_ADDRESS.matcher(correo).matches()){
                Toast.makeText(this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            //Género no seleccionado
            val generoID = Genero.checkedRadioButtonId
            val genero =
                if (generoID != -1) {
                    findViewById<RadioButton>(generoID).text.toString()
                } else {
                    "Género no especificado."
                }

            //Hobbies seleccionados
            val hobbies = mutableListOf<String>()
            if (Lectura.isChecked) {
                hobbies.add("Lectura")
            }
            if (Deporte.isChecked) {
                hobbies.add("Deporte")
            }
            if (Musica.isChecked) {
                hobbies.add("Música")
            }
            if (Arte.isChecked) {
                hobbies.add("Arte")
            }


            //Seekbar Satisfaccion, Boletín, Pais
            val nivelSatisfaccion = seekbarSatisfaccion.progress
            val boletin = if (Boletin.isChecked) {
                "Sí"
            } else {
                "No"
            }
            val pais = Pais.selectedItem.toString()


            //Resumen
            val resumen =
                "Nombre: $nombre\n" +
                        "Apellido: $apellidos\n" +
                        "Correo: $correo\n" +
                        "Género: $genero\n" +
                        "País: $pais\n" +
                        "Hobbies: ${if(hobbies.isEmpty()) "Ninguno" else hobbies.joinToString(", ")}\n" +
                        "Nivel de satisfacción: $nivelSatisfaccion\n" +
                        "Suscripción al boletín: $boletin"


            Resumen.text = resumen
        }
        Nombre.requestFocus()

    }
}


