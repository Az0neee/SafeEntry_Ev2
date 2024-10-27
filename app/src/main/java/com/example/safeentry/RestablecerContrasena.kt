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

class RestablecerContrasena : AppCompatActivity() {

    private lateinit var nombreUsuario: String
    private lateinit var etNuevaContrasena: TextInputEditText
    private lateinit var etConfirmarContrasena: TextInputEditText
    private lateinit var btnReiniciar: MaterialButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_restablecer_contrasena)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        inicializarComponentes()

        nombreUsuario = intent.getStringExtra("nombre_usuario") ?: ""

        btnReiniciar.setOnClickListener { reiniciarContrasena() }

        findViewById<TextView>(R.id.TvTienesCuenta).setOnClickListener {
            startActivity(Intent(this, IniciarSesion::class.java))
        }
    }

    private fun inicializarComponentes() {
        etNuevaContrasena = findViewById(R.id.etContrasenaNueva)
        etConfirmarContrasena = findViewById(R.id.etConfirmarContrasena)
        btnReiniciar = findViewById(R.id.btnReiniciar)
    }

    private fun reiniciarContrasena() {
        val nuevaContrasena = etNuevaContrasena.text.toString()
        val confirmarContrasena = etConfirmarContrasena.text.toString()

        when {
            nuevaContrasena.isEmpty() -> {
                etNuevaContrasena.error = "Por favor, introduce una nueva contraseña."
            }
            confirmarContrasena.isEmpty() -> {
                etConfirmarContrasena.error = "Por favor, confirma la nueva contraseña."
            }
            nuevaContrasena != confirmarContrasena -> {
                etConfirmarContrasena.error = "Las contraseñas no coinciden."
            }
            else -> {
                actualizarContraseña(nuevaContrasena)
            }
        }
    }

    private fun actualizarContraseña(nuevaContrasena: String) {
        val sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)
        sharedPreferences.edit().apply {
            putString(nombreUsuario, nuevaContrasena)
            apply()
        }

        Toast.makeText(this, "Contraseña cambiada exitosamente", Toast.LENGTH_SHORT).show()
        startActivity(Intent(this, IniciarSesion::class.java))
        finish()
    }
}
