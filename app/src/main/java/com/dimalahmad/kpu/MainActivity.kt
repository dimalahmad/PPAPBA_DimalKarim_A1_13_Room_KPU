package com.dimalahmad.kpu

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.dimalahmad.kpu.Database.DataPemilih
import com.dimalahmad.kpu.Database.DataPemilihDao
import com.dimalahmad.kpu.Database.DataPemilihDatabase
import com.dimalahmad.kpu.Login.LoginActivity
import com.dimalahmad.kpu.PrefManager.PrefManager
import com.dimalahmad.kpu.databinding.ActivityMainBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding // Binding untuk mengakses tampilan dari layout XML
    private lateinit var prefManager: PrefManager // Manajer preferensi untuk memeriksa status login
    private lateinit var dataPemilihDao: DataPemilihDao // Data access object untuk mengakses database pemilih
    private lateinit var executorService: ExecutorService // Executor untuk menjalankan operasi database di background thread
    private lateinit var adapter: DataPemilihAdapter // Adapter untuk RecyclerView yang menampilkan data pemilih

    // Fungsi onCreate yang dipanggil ketika aktivitas pertama kali dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater) // Inisialisasi binding untuk layout
        setContentView(binding.root) // Menetapkan layout sebagai tampilan aktivitas
        prefManager = PrefManager.getInstance(this) // Inisialisasi PrefManager untuk memeriksa status login

        checkLoginStatus() // Memeriksa status login

        executorService = Executors.newSingleThreadExecutor() // Menyiapkan executor untuk operasi di background

        val db = DataPemilihDatabase.getDatabase(this) // Mendapatkan instance database
        dataPemilihDao = db!!.dataPemilihDao() // Menginisialisasi DAO untuk operasi database

        setupRecyclerView() // Mengatur RecyclerView untuk menampilkan daftar pemilih

        // Menyiapkan listener untuk tombol tambah
        with(binding) {
            btnTambah.setOnClickListener {
                startActivity(Intent(this@MainActivity, CreateActivity::class.java)) // Pindah ke CreateActivity untuk menambah data
            }

            // Tombol logout, hanya menghapus status login, tanpa menghapus data
            btnLogout.setOnClickListener {
                prefManager.setLoggedIn(false)  // Mengubah status login menjadi false
                navigateToLoginActivity()      // Pindah ke LoginActivity
            }
        }
    }

    // Fungsi untuk mengatur RecyclerView dan adapter
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this) // Menetapkan layout manager untuk RecyclerView
        adapter = DataPemilihAdapter(this) { data -> deleteData(data) } // Menetapkan adapter untuk RecyclerView
        binding.recyclerView.adapter = adapter // Menetapkan adapter pada RecyclerView

        // Observasi data dari database dan memperbarui adapter
        dataPemilihDao.getAllDataPemilih().observe(this) { dataList ->
            adapter.submitList(dataList) // Update dataset di adapter
        }
    }

    // Fungsi untuk menghapus data pemilih
    private fun deleteData(dataPemilih: DataPemilih) {
        executorService.execute {
            dataPemilihDao.delete(dataPemilih) // Menghapus data pemilih dari database
            runOnUiThread {
                // Menampilkan toast bahwa data berhasil dihapus
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Fungsi untuk memeriksa apakah pengguna sudah login atau belum
    private fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn() // Mengecek status login
        if (!isLoggedIn) {
            navigateToLoginActivity() // Jika belum login, pindah ke LoginActivity
        }
    }

    // Fungsi untuk menavigasi ke LoginActivity
    private fun navigateToLoginActivity() {
        val intent = Intent(this@MainActivity, LoginActivity::class.java) // Membuat intent untuk LoginActivity
        startActivity(intent) // Memulai aktivitas login
        finish() // Menutup MainActivity setelah berpindah ke LoginActivity
    }
}

