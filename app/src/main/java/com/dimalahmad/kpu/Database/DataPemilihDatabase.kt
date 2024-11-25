package com.dimalahmad.kpu.Database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Menandakan bahwa kelas ini adalah kelas database Room dengan entitas dan versi yang ditentukan
@Database(entities = [DataPemilih::class], version = 1, exportSchema = false)
abstract class DataPemilihDatabase : RoomDatabase() {

    // Mendeklarasikan metode abstrak untuk mengakses DAO DataPemilihDao
    abstract fun dataPemilihDao(): DataPemilihDao

    companion object {
        // Variabel INSTANCE untuk menyimpan satu-satunya instance dari database
        // @Volatile memastikan bahwa perubahan terhadap INSTANCE terlihat oleh semua thread
        @Volatile
        private var INSTANCE: DataPemilihDatabase? = null

        // Fungsi untuk mendapatkan instance database, mengimplementasikan pola Singleton
        fun getDatabase(context: Context): DataPemilihDatabase? {
            // Jika instance belum ada, buat instance baru
            if (INSTANCE == null) {
                // synchronized block untuk mencegah lebih dari satu thread membuat instance pada waktu yang sama
                synchronized(DataPemilihDatabase::class.java) {
                    // Membangun instance database menggunakan Room.databaseBuilder
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DataPemilihDatabase::class.java, // Kelas database
                        "data_pemilih_database" // Nama file database
                    ).build() // Membangun database
                }
            }
            // Mengembalikan instance yang telah dibuat atau null jika gagal
            return INSTANCE
        }
    }
}
