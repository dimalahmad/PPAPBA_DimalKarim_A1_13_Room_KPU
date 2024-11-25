package com.dimalahmad.kpu.PrefManager

import android.content.Context
import android.content.SharedPreferences

// PrefManager adalah kelas yang digunakan untuk mengelola preferensi pengguna secara lokal menggunakan SharedPreferences
// untuk menyimpan status login, username, dan password.
class PrefManager private constructor(context: Context) {

    // Mendeklarasikan variabel SharedPreferences untuk menyimpan data preferensi
    private val sharedPreferences: SharedPreferences

    // Companion object untuk membuat PrefManager sebagai Singleton
    companion object {
        // Nama file SharedPreferences yang digunakan untuk menyimpan data
        private const val PREFS_FILENAME = "AuthAppPrefs"
        // Kunci untuk menyimpan status login
        private const val KEY_IS_LOGGED_IN = "isLoggedIn"
        // Kunci untuk menyimpan username
        private const val KEY_USERNAME = "username"
        // Kunci untuk menyimpan password
        private const val KEY_PASSWORD = "password"

        // Variabel instance yang akan menyimpan instance PrefManager
        @Volatile
        private var instance: PrefManager? = null

        // Fungsi untuk mendapatkan instance PrefManager (Singleton Pattern)
        fun getInstance(context: Context): PrefManager {
            return instance ?: synchronized(this) {
                // Jika instance null, buat PrefManager baru
                instance ?: PrefManager(context.applicationContext).also {
                    instance = it
                }
            }
        }
    }

    // Inisialisasi SharedPreferences pada saat objek PrefManager dibuat
    init {
        sharedPreferences = context.getSharedPreferences(
            PREFS_FILENAME, // Nama file preferensi
            Context.MODE_PRIVATE // Mode akses yang hanya memungkinkan aplikasi ini untuk mengakses file
        )
    }

    // Fungsi untuk mengatur status login
    fun setLoggedIn(isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit() // Mendapatkan editor untuk mengubah preferensi
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn) // Menyimpan status login
        editor.apply() // Menyimpan perubahan
    }

    // Fungsi untuk memeriksa apakah pengguna sudah login
    fun isLoggedIn(): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false) // Mengambil status login dari preferensi
    }

    // Fungsi untuk menyimpan username
    fun saveUsername(username: String) {
        val editor = sharedPreferences.edit() // Mendapatkan editor untuk mengubah preferensi
        editor.putString(KEY_USERNAME, username) // Menyimpan username
        editor.apply() // Menyimpan perubahan
    }

    // Fungsi untuk menyimpan password
    fun savePassword(password: String) {
        val editor = sharedPreferences.edit() // Mendapatkan editor untuk mengubah preferensi
        editor.putString(KEY_PASSWORD, password) // Menyimpan password
        editor.apply() // Menyimpan perubahan
    }

    // Fungsi untuk mendapatkan username yang disimpan
    fun getUsername(): String {
        return sharedPreferences.getString(KEY_USERNAME, "") ?: "" // Mengambil username, atau kosong jika belum diset
    }

    // Fungsi untuk mendapatkan password yang disimpan
    fun getPassword(): String {
        return sharedPreferences.getString(KEY_PASSWORD, "") ?: "" // Mengambil password, atau kosong jika belum diset
    }

    // Fungsi untuk menghapus semua data preferensi
    fun clear() {
        val editor = sharedPreferences.edit() // Mendapatkan editor untuk mengubah preferensi
        editor.clear() // Menghapus semua data
        editor.apply() // Menyimpan perubahan
    }
}
