package com.example.musicapp

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import com.example.musicapp.Utils.formatDuration
import java.io.IOException
import java.util.concurrent.TimeUnit

object Utils {
    fun Long.formatDuration(): String {
        val minutes = TimeUnit.MILLISECONDS.toMinutes(this) % 60
        val seconds = TimeUnit.MILLISECONDS.toSeconds(this) % 60
        // Format minutes and seconds as usual
        val minutesStr = String.format("%02d:", minutes)
        val secondsStr = String.format("%02d", seconds)

        return "$minutesStr$secondsStr"
    }
}