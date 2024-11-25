package com.dimalahmad.kpu

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.dimalahmad.kpu.databinding.ActivityDetailBinding

// Aktivitas untuk menampilkan detail data pemilih
class DetailActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailBinding // Binding untuk mengakses tampilan dari layout XML

    // Fungsi onCreate yang dipanggil ketika aktivitas pertama kali dibuat
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater) // Inisialisasi binding untuk layout
        setContentView(binding.root) // Menetapkan layout sebagai tampilan aktivitas

        // Ambil data yang dikirim melalui Intent
        // Jika data tidak tersedia, gunakan nilai default "Tidak tersedia"
        val nama = intent.getStringExtra("nama") ?: "Tidak tersedia"
        val nik = intent.getStringExtra("nik") ?: "Tidak tersedia"
        val jenisKelamin = intent.getStringExtra("jenis_kelamin") ?: "Tidak tersedia"
        val alamat = intent.getStringExtra("alamat") ?: "Tidak tersedia"

        // Tampilkan data pada TextView yang sudah di-bind ke layout
        with(binding) {
            tvNamaPemilih.text = "Nama: $nama" // Menampilkan nama pemilih
            tvNik.text = "NIK: $nik" // Menampilkan NIK pemilih
            tvGender.text = "Gender: $jenisKelamin" // Menampilkan jenis kelamin pemilih
            tvAlamat.text = "Alamat: $alamat" // Menampilkan alamat pemilih

            // Fungsi tombol kembali yang akan menutup aktivitas ini
            btnBack.setOnClickListener { finish() } // Mengakhiri aktivitas dan kembali ke aktivitas sebelumnya
        }
    }
}
