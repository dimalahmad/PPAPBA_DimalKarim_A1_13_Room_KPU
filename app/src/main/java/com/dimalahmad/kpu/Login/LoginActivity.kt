package com.dimalahmad.kpu.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dimalahmad.kpu.Login.RegisterActivity
import com.dimalahmad.kpu.MainActivity
import com.dimalahmad.kpu.PrefManager.PrefManager
import com.dimalahmad.kpu.R
import com.dimalahmad.kpu.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)

        with(binding) {
            btnLogin.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
                } else {
                    if (isValidUsernamePassword(username, password)) {
                        prefManager.setLoggedIn(true)
                        navigateToMainActivity()
                    } else {
                        Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            txtRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    private fun isValidUsernamePassword(username: String, password: String): Boolean {
        val savedUsername = prefManager.getUsername()
        val savedPassword = prefManager.getPassword()
        return username == savedUsername && password == savedPassword
    }

    private fun navigateToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}
