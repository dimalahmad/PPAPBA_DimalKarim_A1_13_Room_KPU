package com.dimalahmad.kpu.Database

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// Anotasi @Entity menandakan bahwa kelas ini akan menjadi entitas dalam Room Database.
// Nama tabel yang digunakan adalah "dataPemilih_table"
@Entity(tableName = "dataPemilih_table")
data class DataPemilih (

    // Anotasi @PrimaryKey menandakan bahwa 'id' adalah primary key untuk entitas ini.
    // autoGenerate = true berarti ID akan otomatis di-generate oleh Room (misalnya, dengan increment).
    @PrimaryKey(autoGenerate = true)
    @NonNull
    val id: Int = 0, // ID unik untuk setiap pemilih, otomatis ter-generate

    // Anotasi @ColumnInfo digunakan untuk menentukan nama kolom dalam tabel database.
    // Kolom 'nama' menyimpan nama pemilih.
    @ColumnInfo(name = "nama")
    val nama: String, // Nama pemilih

    // Kolom 'NIK' untuk menyimpan Nomor Induk Kependudukan pemilih.
    @ColumnInfo(name = "NIK")
    val nik: String, // Nomor Induk Kependudukan (NIK) pemilih

    // Kolom 'alamat' untuk menyimpan alamat pemilih.
    @ColumnInfo(name = "alamat")
    val alamat: String, // Alamat pemilih

    // Kolom 'jenis_kelamin' untuk menyimpan jenis kelamin pemilih.
    @ColumnInfo(name = "jenis_kelamin")
    val jenisKelamin: String // Jenis kelamin pemilih
)