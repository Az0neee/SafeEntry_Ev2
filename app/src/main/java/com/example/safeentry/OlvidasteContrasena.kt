package com.example.safeentry

import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class OlvidasteContrasena : AppCompatActivity() {
    private lateinit var nombreUsuarioInput: TextInputEditText
    private lateinit var btnRecuperar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_olvidaste_contrasena)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val insetsPadding = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(insetsPadding.left, insetsPadding.top, insetsPadding.right, insetsPadding.bottom)
            insets
        }

        btnRecuperar = findViewById(R.id.btnRecuperar)
        nombreUsuarioInput = findViewById(R.id.etNombreUsuario)

        btnRecuperar.setOnClickListener { validarUsuario() }

        findViewById<TextView>(R.id.TvTienesCuenta).setOnClickListener {
            startActivity(Intent(this, IniciarSesion::class.java))
        }
    }

    private fun validarUsuario() {
        val nombreUsuario = nombreUsuarioInput.text.toString().trim()

        if (nombreUsuario.isNotEmpty()) {
            val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
            val storedPassword = sharedPreferences.getString(nombreUsuario, null)

            if (storedPassword != null) {
                val intent = Intent(this, RestablecerContrasena::class.java).apply {
                    putExtra("nombre_usuario", nombreUsuario)
                }
                startActivity(intent)
            } else {
                mostrarError("El nombre de usuario ingresado no existe.")
            }
        } else {
            nombreUsuarioInput.error = "Por favor, introduce tu nombre de usuario."
        }
    }

    private fun mostrarError(mensaje: String) {
        Toast.makeText(this, mensaje, Toast.LENGTH_SHORT).show()
    }
}
