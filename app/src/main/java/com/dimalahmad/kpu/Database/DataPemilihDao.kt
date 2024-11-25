package com.dimalahmad.kpu.Database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

// Menandakan bahwa interface ini adalah Data Access Object (DAO) yang digunakan oleh Room untuk melakukan operasi database
@Dao
interface DataPemilihDao {

    // Fungsi untuk menyisipkan DataPemilih ke dalam database.
    // Jika ada konflik (misalnya duplikasi ID), data baru akan diabaikan.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(datapemilih: DataPemilih)

    // Fungsi untuk memperbarui data DataPemilih yang sudah ada di database
    // Akan memperbarui baris yang memiliki ID yang sama dengan objek yang diterima
    @Update
    fun update(datapemilih: DataPemilih)

    // Fungsi untuk menghapus DataPemilih dari database
    @Delete
    fun delete(datapemilih: DataPemilih)

    // Fungsi untuk mengambil semua data DataPemilih dari tabel 'dataPemilih_table'
    // Hasilnya akan disortir berdasarkan ID dalam urutan menaik (ASC)
    @Query("SELECT * from dataPemilih_table ORDER BY id ASC")
    fun getAllDataPemilih(): LiveData<List<DataPemilih>>
}
