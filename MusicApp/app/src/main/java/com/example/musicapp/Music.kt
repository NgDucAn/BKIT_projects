package com.example.musicapp

import android.media.MediaMetadataRetriever
import java.io.Serializable

data class Music(
    val id: String,
    val title: String,
    val album: String,
    val artist: String,
    val duration: Long = 0,
    val path: String
) : Serializable {
    fun getImgArt(path: String): ByteArray? {
        val retriever = MediaMetadataRetriever()
        retriever.setDataSource(path)
        return retriever.embeddedPicture
    }
}

