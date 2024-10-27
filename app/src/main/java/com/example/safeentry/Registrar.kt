package com.example.safeentry

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton
import com.google.android.material.textfield.TextInputEditText

class Registrar : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar)

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<MaterialButton>(R.id.btnCrearCuenta).setOnClickListener {
            registerUser()
        }

        findViewById<TextView>(R.id.TvTienesCuenta).setOnClickListener {
            startActivity(Intent(this, IniciarSesion::class.java))
        }
    }

    private fun registerUser() {
        try {
            val username =
                findViewById<TextInputEditText>(R.id.etNombreUsuario).text.toString().trim()
            val password = findViewById<TextInputEditText>(R.id.etPassword).text.toString()
            val passwordConfirm =
                findViewById<TextInputEditText>(R.id.etPasswordConfirm).text.toString()

            if (username.isEmpty() || password.isEmpty() || passwordConfirm.isEmpty()) {
                Toast.makeText(this, "Por favor, complete todos los campos", Toast.LENGTH_SHORT)
                    .show()
                return
            }

            if (password != passwordConfirm) {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                return
            }

            if (sharedPreferences.getString(username, null) != null) {
                Toast.makeText(this, "El nombre utilizado ya está en uso", Toast.LENGTH_SHORT)
                    .show()
                return
            }

            sharedPreferences.edit().apply {
                putString(username, password)
                apply()
            }

            // Almacena el nombre de usuario como el usuario actual
            sharedPreferences.edit().putString("current_user", username).apply()

            Toast.makeText(this, "La cuenta ha sido creada con éxito", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, IniciarSesion::class.java))
            finish()
        } catch (e: Exception) {
            Toast.makeText(this, "Ocurrió un error: ${e.message}", Toast.LENGTH_SHORT).show()
        }
    }
}
