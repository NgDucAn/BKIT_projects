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

    fun playAudio(filePath: String) {
        try {
            PlayerActivity.musicService?.mediaPlayer = MediaPlayer().apply {
                setDataSource(filePath)
                prepareAsync()
                setOnPreparedListener {
                    updateSeekBar()
                    start()
                }
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    fun stopAudio() {
        PlayerActivity.musicService?.mediaPlayer?.apply {
            if (isPlaying) {
                stop()
            }
            release()
        }
    }

    fun pauseAudio() {
        PlayerActivity.isPlaying = false
        PlayerActivity.musicService?.mediaPlayer?.pause()
        PlayerActivity.binding.fbPlayPauseBtn.setIconResource(R.drawable.ic_play)
    }

    fun resumeAudio() {
        PlayerActivity.isPlaying = true
        PlayerActivity.musicService?.mediaPlayer?.start()
        PlayerActivity.musicService?.showNotification(R.drawable.ic_pause)
        PlayerActivity.binding.fbPlayPauseBtn.setIconResource(R.drawable.ic_pause)
        updateSeekBar()
    }
    fun seekForward(milliseconds: Int) {
        PlayerActivity.musicService?.mediaPlayer?.apply {
            if (isPlaying) {
                val currentPosition = currentPosition
                val duration = duration
                val newPosition = currentPosition + milliseconds
                if (newPosition < duration) {
                    seekTo(newPosition)
                } else {
                    // Đặt về cuối nếu vượt quá độ dài
                    seekTo(duration)
                }
            }
        }
    }
    fun seekBackward(milliseconds: Int) {
        PlayerActivity.musicService?.mediaPlayer?.apply {
            if (isPlaying) {
                val currentPosition = currentPosition
                val newPosition = currentPosition - milliseconds
                if (newPosition > 0) {
                    seekTo(newPosition)
                } else {
                    // Đặt về đầu nếu quá thấp
                    seekTo(0)
                }
            }
        }
    }
    private fun updateSeekBar() {
        PlayerActivity.binding.seekBarPA.max = PlayerActivity.musicService?.mediaPlayer?.duration ?: 0
        PlayerActivity.binding.tvSeekbarEnd.text = PlayerActivity.musicService?.mediaPlayer?.duration?.toLong()?.formatDuration()
        val handler = Handler(Looper.getMainLooper())
        handler.postDelayed(object : Runnable {
            override fun run() {
                try {
                    val currentPosition = PlayerActivity.musicService?.mediaPlayer?.currentPosition ?: 0
                    PlayerActivity.binding.seekBarPA.progress = currentPosition
                    PlayerActivity.binding.tvSeekbarStart.text = currentPosition.toLong().formatDuration()
                    handler.postDelayed(this, 1000)
                } catch (e: Exception) {
                    PlayerActivity.binding.seekBarPA.progress = 0
                }
            }
        }, 0)
    }
}