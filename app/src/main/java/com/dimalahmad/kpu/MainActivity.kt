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
    private lateinit var binding: ActivityMainBinding
    private lateinit var prefManager: PrefManager
    private lateinit var dataPemilihDao: DataPemilihDao
    private lateinit var executorService: ExecutorService
    private lateinit var adapter: DataPemilihAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prefManager = PrefManager.getInstance(this)
        checkLoginStatus()
        executorService = Executors.newSingleThreadExecutor()
        val db = DataPemilihDatabase.getDatabase(this)
        dataPemilihDao = db!!.dataPemilihDao()
        setupRecyclerView()
        with(binding){
            btnTambah.setOnClickListener {
                val startActivity = Intent(this@MainActivity, CreateActivity::class.java)
                startActivity(startActivity)
            }
            btnLogout.setOnClickListener {
                prefManager.clear()
                finish()

            }
        }

    }
    private fun setupRecyclerView() {
        binding.recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = DataPemilihAdapter(this) { data ->
            deleteData(data)
        }
        binding.recyclerView.adapter = adapter

        // Amati perubahan data dari LiveData
        dataPemilihDao.getAllDataPemilih().observe(this) { dataList ->
            adapter.submitList(dataList) // Perbarui dataset
        }
    }

    private fun deleteData(dataPemilih: DataPemilih) {
        executorService.execute {
            dataPemilihDao.delete(dataPemilih)
            runOnUiThread {
                Toast.makeText(this, "Data berhasil dihapus", Toast.LENGTH_SHORT).show()
            }
        }
    }
    fun checkLoginStatus() {
        val isLoggedIn = prefManager.isLoggedIn()
        if (!isLoggedIn) {
            startActivity(Intent(this@MainActivity, LoginActivity::class.java))
            finish()
        }
    }
}
