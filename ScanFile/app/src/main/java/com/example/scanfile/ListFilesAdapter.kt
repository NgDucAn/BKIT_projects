package com.example.scanfile

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.scanfile.databinding.ItemFileBinding

class ListFilesAdapter(private var fileList: List<FileInfo>) : RecyclerView.Adapter<ListFilesAdapter.ListFilesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListFilesViewHolder {
        val binding = ItemFileBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListFilesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ListFilesViewHolder, position: Int) {
        val fileInfo = fileList[position]
        holder.bind(fileInfo)
    }

    override fun getItemCount(): Int {
        return fileList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFiles(newFileList: List<FileInfo>) {
        fileList = newFileList
        notifyDataSetChanged()
    }

    inner class ListFilesViewHolder(val binding: ItemFileBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(fileInfo: FileInfo) {
            binding.tvFilePath.text = fileInfo.path
        }
    }
}