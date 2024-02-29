package com.example.scanfile

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanfile.databinding.ItemFileBinding
import kotlinx.coroutines.withContext
import kotlin.math.ceil

class ListFilesAdapter(val context: Context, var fileList: ArrayList<FileInfo>) :
    RecyclerView.Adapter<ListFilesAdapter.ListFilesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFilesViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFilesViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ListFilesViewHolder, position: Int) {
        val fileInfo = fileList[position]
        with(holder.binding) {
            tvFileName.text = fileInfo.getName()
            tvFilePath.text = fileInfo.path
            var fileSize = fileInfo.getFileSize().toDouble() / 1024
            fileSize = ceil(fileSize * 100) / 100
            tvFileSize.text = fileSize.toString() + "KB"
        }
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    inner class ListFilesViewHolder(val binding: ItemFileBinding) :
        RecyclerView.ViewHolder(binding.root)
}