package com.dimalahmad.kpu

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dimalahmad.kpu.Database.DataPemilih
import com.dimalahmad.kpu.Database.DataPemilihDao
import com.dimalahmad.kpu.Database.DataPemilihDatabase
import com.dimalahmad.kpu.databinding.ActivityEditBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

// Aktivitas untuk mengedit data pemilih
class EditActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditBinding // Binding untuk mengakses tampilan dari layout XML
    private lateinit var dataPemilihDao: DataPemilihDao // Data access object untuk mengakses database
    private lateinit var executorService: ExecutorService // Executor untuk menjalankan operasi database di background thread

    // Fungsi onCreate yang dipanggil ketika aktivitas pertama kali dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater) // Inisialisasi binding untuk layout
        setContentView(binding.root) // Menetapkan layout sebagai tampilan aktivitas

        // Mendapatkan database dan DAO (Data Access Object)
        val db = DataPemilihDatabase.getDatabase(this)
        dataPemilihDao = db!!.dataPemilihDao() // Menginisialisasi DAO untuk operasi database
        executorService = Executors.newSingleThreadExecutor() // Menyiapkan executor untuk operasi di background

        // Mengambil data yang dikirimkan melalui Intent
        val id = intent.getIntExtra("id", -1) // ID dari data pemilih yang akan diedit
        val nama = intent.getStringExtra("nama")
        val nik = intent.getStringExtra("nik")
        val jenisKelamin = intent.getStringExtra("jenis_kelamin")
        val alamat = intent.getStringExtra("alamat")

        // Mengisi tampilan dengan data yang ada
        with(binding) {
            etNamaPemilih.setText(nama) // Menampilkan nama yang ada dalam field input
            etNik.setText(nik) // Menampilkan NIK yang ada dalam field input
            // Menentukan jenis kelamin yang sesuai dengan pilihan sebelumnya
            if (jenisKelamin == "Laki-laki") rgGender.check(rbMale.id) else rgGender.check(rbFemale.id)
            etAlamat.setText(alamat) // Menampilkan alamat yang ada dalam field input

            // Tombol simpan yang akan memperbarui data pemilih
            btnSimpan.setOnClickListener {
                val updatedGender = if (rgGender.checkedRadioButtonId == rbMale.id) "Laki-laki" else "Perempuan"
                // Membuat objek DataPemilih yang baru dengan data yang sudah diperbarui
                val updatedData = DataPemilih(
                    id = id,
                    nama = etNamaPemilih.text.toString(),
                    nik = etNik.text.toString(),
                    jenisKelamin = updatedGender,
                    alamat = etAlamat.text.toString()
                )
                updateData(updatedData) // Menyimpan perubahan data ke database
            }
        }
    }

    // Fungsi untuk memperbarui data pemilih di database
    private fun updateData(dataPemilih: DataPemilih) {
        executorService.execute {
            // Melakukan update data pemilih di database secara background
            dataPemilihDao.update(dataPemilih)
            runOnUiThread {
                // Menampilkan pesan bahwa data berhasil diperbarui
                Toast.makeText(this, "Data berhasil diperbarui", Toast.LENGTH_SHORT).show()
                finish() // Menutup aktivitas setelah data berhasil diperbarui
            }
        }
    }
}

