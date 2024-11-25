package com.dimalahmad.kpu

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.room.Insert
import com.dimalahmad.kpu.Database.DataPemilih
import com.dimalahmad.kpu.Database.DataPemilihDao
import com.dimalahmad.kpu.Database.DataPemilihDatabase
import com.dimalahmad.kpu.databinding.ActivityCreateBinding
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private lateinit var executorService: ExecutorService
    private lateinit var dataPemilihDao: DataPemilihDao

    // onCreate adalah fungsi pertama yang dipanggil saat activity ini dibuka
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Menyiapkan executor service untuk operasi latar belakang
        executorService = Executors.newSingleThreadExecutor()

        // Mendapatkan instance database dan DataPemilihDao
        val db = DataPemilihDatabase.getDatabase(this)
        dataPemilihDao = db!!.dataPemilihDao()

        // Menangani klik pada tombol Simpan
        with(binding) {
            btnSimpan.setOnClickListener {
                // Mengambil id radio button yang dipilih
                val selectedGenderId = binding.rgGender.checkedRadioButtonId
                val gender = when (selectedGenderId) {
                    binding.rbMale.id -> "Laki-laki"  // Jika laki-laki dipilih
                    binding.rbFemale.id -> "Perempuan"  // Jika perempuan dipilih
                    else -> "Tidak ada yang dipilih"  // Jika tidak ada yang dipilih
                }

                // Validasi input
                if (etNamaPemilih.text.isNullOrBlank() ||
                    etNik.text.isNullOrBlank() ||
                    gender == "Tidak ada yang dipilih" ||
                    etAlamat.text.isNullOrBlank()
                ) {
                    // Menampilkan pesan jika data tidak lengkap
                    showToast("Semua data harus diisi dengan benar!")
                    return@setOnClickListener
                }

                // Jika data valid, insert data ke database
                insert(
                    DataPemilih(
                        nama = etNamaPemilih.text.toString(),
                        nik = etNik.text.toString(),
                        jenisKelamin = gender,
                        alamat = etAlamat.text.toString(),
                    )
                )
                // Menampilkan pesan berhasil dan membersihkan field
                showToast("Data berhasil disimpan!")
                setEmptyField()

                // Mengarahkan pengguna kembali ke MainActivity
                val startActivity = Intent(this@CreateActivity, MainActivity::class.java)
                startActivity(startActivity)
                finish()  // Menutup activity ini
            }
        }
    }

    // Fungsi untuk menyimpan data pemilih ke dalam database
    private fun insert(dataPemilih: DataPemilih) {
        executorService.execute {
            dataPemilihDao.insert(dataPemilih)  // Menyimpan data ke dalam database
            runOnUiThread {
                // Setelah insert selesai, kembali ke MainActivity
                val intent = Intent(this@CreateActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    // Fungsi untuk menampilkan Toast message
    private fun showToast(message: String) {
        runOnUiThread {
            Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
        }
    }

    // Fungsi untuk membersihkan field setelah data disimpan
    private fun setEmptyField() {
        with(binding) {
            etNamaPemilih.text?.clear()
            etNik.text?.clear()
            rgGender.clearCheck()
            etAlamat.text?.clear()
        }
    }
}
