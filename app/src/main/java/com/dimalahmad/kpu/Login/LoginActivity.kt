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

// LoginActivity adalah Activity yang menangani proses login pengguna.
class LoginActivity : AppCompatActivity() {

    // Inisialisasi variabel untuk binding dan PrefManager
    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefManager: PrefManager

    // Fungsi onCreate untuk mempersiapkan layout dan inisialisasi variabel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan layout dengan Activity melalui ViewBinding
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi PrefManager untuk mengelola preferensi pengguna
        prefManager = PrefManager.getInstance(this)

        // Menambahkan listener untuk tombol login
        with(binding) {
            btnLogin.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()

                // Validasi jika username atau password kosong
                if (username.isEmpty() || password.isEmpty()) {
                    Toast.makeText(this@LoginActivity, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
                } else {
                    // Cek apakah username dan password valid
                    if (isValidUsernamePassword(username, password)) {
                        // Menyimpan status login dan pindah ke MainActivity jika login berhasil
                        prefManager.setLoggedIn(true)
                        navigateToMainActivity()
                    } else {
                        // Menampilkan pesan kesalahan jika login gagal
                        Toast.makeText(this@LoginActivity, "Username atau password salah", Toast.LENGTH_SHORT).show()
                    }
                }
            }

            // Menambahkan listener untuk tombol registrasi, yang akan membuka RegisterActivity
            txtRegister.setOnClickListener {
                startActivity(Intent(this@LoginActivity, RegisterActivity::class.java))
            }
        }
    }

    // Fungsi untuk memvalidasi username dan password dengan data yang disimpan
    private fun isValidUsernamePassword(username: String, password: String): Boolean {
        val savedUsername = prefManager.getUsername()  // Mengambil username yang disimpan
        val savedPassword = prefManager.getPassword()  // Mengambil password yang disimpan
        return username == savedUsername && password == savedPassword  // Membandingkan dengan input pengguna
    }

    // Fungsi untuk menavigasi ke MainActivity setelah login berhasil
    private fun navigateToMainActivity() {
        val intent = Intent(this@LoginActivity, MainActivity::class.java)
        startActivity(intent)  // Memulai MainActivity
        finish()  // Menutup LoginActivity agar pengguna tidak dapat kembali ke halaman login
    }
}