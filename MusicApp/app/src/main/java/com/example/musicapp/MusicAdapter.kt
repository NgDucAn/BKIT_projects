package com.example.musicapp

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.NotificationCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.musicapp.Contains.SONGPOSITION
import com.example.musicapp.databinding.MusicViewBinding
import com.example.musicapp.Utils.formatDuration
import java.io.File

class MusicAdapter(
    private val context: Context,
    private var musicList: ArrayList<Music>,
    private var onClickItemMusicListener: OnClickItemMusicListener
) : RecyclerView.Adapter<MusicAdapter.MyHolder>() {

    class MyHolder(binding: MusicViewBinding) : RecyclerView.ViewHolder(binding.root) {
        val title = binding.tvSongNameMV
        val album = binding.tvSongAlbumMV
        val image = binding.ivImageMV
        val duration = binding.tvSongDuration
        val root = binding.root
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        return MyHolder(MusicViewBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun getItemCount(): Int {
        return musicList.size
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        holder.title.text = musicList[position].title
        holder.album.text = musicList[position].album
        holder.duration.text = musicList[position].duration.formatDuration() // Định dạng lại theo yêu cầu
        Glide.with(context)
            .load(Uri.fromFile(File(musicList[position].path)))
            .apply(RequestOptions().placeholder(R.drawable.music_player_icon_slash_screen).centerCrop())
            .into(holder.image)

        holder.itemView.setOnClickListener {
            onClickItemMusicListener.onClickResult(musicList[position].path)
        }
    }
}
interface OnClickItemMusicListener {
    fun onClickResult(path : String)
}


