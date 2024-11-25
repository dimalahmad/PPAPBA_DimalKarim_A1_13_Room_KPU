package com.dimalahmad.kpu

import android.view.LayoutInflater
import android.view.ViewGroup
import android.content.Context
import android.content.Intent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dimalahmad.kpu.Database.DataPemilih
import com.dimalahmad.kpu.databinding.ItemDisasterBinding

// Adapter untuk menampilkan data pemilih dalam bentuk daftar menggunakan RecyclerView
class DataPemilihAdapter(
    private val context: Context, // Context digunakan untuk membuat Intent dan aktivitas
    private val deleteAction: (DataPemilih) -> Unit // Callback untuk aksi delete yang dipanggil dari Activity
) : ListAdapter<DataPemilih, DataPemilihAdapter.DataPemilihViewHolder>(DiffCallback) {

    // DiffUtil Callback untuk menghitung perubahan data dalam daftar
    object DiffCallback : DiffUtil.ItemCallback<DataPemilih>() {
        // Mengecek apakah dua item adalah item yang sama berdasarkan ID
        override fun areItemsTheSame(oldItem: DataPemilih, newItem: DataPemilih): Boolean {
            return oldItem.id == newItem.id
        }

        // Mengecek apakah konten dari dua item yang dibandingkan sama
        override fun areContentsTheSame(oldItem: DataPemilih, newItem: DataPemilih): Boolean {
            return oldItem == newItem
        }
    }

    // ViewHolder untuk mengikat data dengan layout item di RecyclerView
    inner class DataPemilihViewHolder(private val binding: ItemDisasterBinding) :
        RecyclerView.ViewHolder(binding.root) {

        // Fungsi untuk mengikat data pada ViewHolder
        fun bind(data: DataPemilih, position: Int)  {
            with(binding) {
                // Mengatur nomor urut di dalam daftar
                nomer.text = "${position + 1}."
                // Mengatur nama pemilih
                nama.text = data.nama

                // Menambahkan event listener untuk tombol Edit
                edit.setOnClickListener {
                    val intent = Intent(context, EditActivity::class.java)
                    // Menyertakan data pemilih untuk diedit
                    intent.putExtra("id", data.id)
                    intent.putExtra("nama", data.nama)
                    intent.putExtra("nik", data.nik)
                    intent.putExtra("jenis_kelamin", data.jenisKelamin)
                    intent.putExtra("alamat", data.alamat)
                    context.startActivity(intent) // Memulai EditActivity
                }

                // Menambahkan event listener untuk tombol View Detail
                viewDetail.setOnClickListener {
                    val intent = Intent(context, DetailActivity::class.java)
                    // Menyertakan data pemilih untuk ditampilkan di detail
                    intent.putExtra("nama", data.nama)
                    intent.putExtra("nik", data.nik)
                    intent.putExtra("jenis_kelamin", data.jenisKelamin)
                    intent.putExtra("alamat", data.alamat)
                    context.startActivity(intent) // Memulai DetailActivity
                }

                // Menambahkan event listener untuk tombol Hapus
                delete.setOnClickListener {
                    // Memanggil aksi delete yang diberikan melalui callback
                    deleteAction(data)
                }
            }
        }
    }

    // Menginflate layout item untuk RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataPemilihViewHolder {
        val binding = ItemDisasterBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DataPemilihViewHolder(binding)
    }

    // Mengikat data ke ViewHolder untuk ditampilkan
    override fun onBindViewHolder(holder: DataPemilihViewHolder, position: Int) {
        holder.bind(getItem(position), position)
    }
}
