package com.dimalahmad.kpu.Login

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dimalahmad.kpu.Database.DataPemilih
import com.dimalahmad.kpu.Database.DataPemilihDao
import com.dimalahmad.kpu.Database.DataPemilihDatabase
import com.dimalahmad.kpu.MainActivity
import com.dimalahmad.kpu.PrefManager.PrefManager
import com.dimalahmad.kpu.databinding.ActivityEditBinding
import com.dimalahmad.kpu.databinding.ActivityRegisterBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// RegisterActivity adalah Activity yang menangani proses registrasi pengguna baru.
class RegisterActivity : AppCompatActivity() {

    // Inisialisasi variabel untuk binding dan PrefManager
    private lateinit var binding: ActivityRegisterBinding
    private lateinit var prefManager: PrefManager

    // Fungsi onCreate untuk mempersiapkan layout dan inisialisasi variabel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan layout dengan Activity melalui ViewBinding
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inisialisasi PrefManager untuk mengelola preferensi pengguna
        prefManager = PrefManager.getInstance(this)

        // Menambahkan listener untuk tombol registrasi
        with(binding) {
            btnRegister.setOnClickListener {
                val username = edtUsername.text.toString()
                val password = edtPassword.text.toString()
                val confirmPassword = Confirmpassword.text.toString()

                // Validasi jika ada data yang kosong
                if (username.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                    Toast.makeText(this@RegisterActivity, "Mohon isi semua data", Toast.LENGTH_SHORT).show()
                } else if (password != confirmPassword) {
                    // Validasi jika password dan konfirmasi password tidak sama
                    Toast.makeText(this@RegisterActivity, "Password tidak sama", Toast.LENGTH_SHORT).show()
                } else {
                    // Menyimpan username dan password ke dalam preferensi dan mengatur status login
                    prefManager.saveUsername(username)
                    prefManager.savePassword(password)
                    prefManager.setLoggedIn(true)

                    // Navigasi kembali ke LoginActivity setelah registrasi berhasil
                    navigateToLoginActivity()
                }
            }

            // Menambahkan listener untuk tombol login, yang akan membuka LoginActivity
            btnLogin.setOnClickListener {
                navigateToLoginActivity()
            }
        }
    }

    // Fungsi untuk menavigasi ke LoginActivity
    private fun navigateToLoginActivity() {
        val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
        startActivity(intent)  // Memulai LoginActivity
        finish()  // Menutup RegisterActivity agar pengguna tidak dapat kembali ke halaman registrasi
    }
}
