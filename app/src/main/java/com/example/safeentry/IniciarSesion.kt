package com.example.safeentry

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.textfield.TextInputEditText

class IniciarSesion : AppCompatActivity() {

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var usernameEditText: TextInputEditText
    private lateinit var passwordEditText: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_iniciar_sesion)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { view, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            view.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        usernameEditText = findViewById(R.id.etNombreDeUsuario)
        passwordEditText = findViewById(R.id.etContrasena)

        val loginButton: Button = findViewById(R.id.btnIniciarSesion)
        loginButton.setOnClickListener { loginUser() }

        findViewById<TextView>(R.id.OlvidasteContrasena).setOnClickListener {
            startActivity(Intent(this, OlvidasteContrasena::class.java))
        }

        findViewById<TextView>(R.id.TvRegistrar).setOnClickListener {
            startActivity(Intent(this, Registrar::class.java))
        }
    }

    private fun loginUser() {
        try {
            val username = usernameEditText.text.toString().trim()
            val password = passwordEditText.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT).show()
                return
            }

            val storedPassword = sharedPreferences.getString(username, null)

            if (storedPassword != null && storedPassword == password) {
                sharedPreferences.edit().apply {
                    putString("currentUser", username)
                    putStringSet("currentUserPuertas", sharedPreferences.getStringSet("$username puertas", null)) // Carga las puertas
                    apply()
                }

                Toast.makeText(this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else {
                Toast.makeText(this, "Nombre de usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Ocurrió un error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }

}
