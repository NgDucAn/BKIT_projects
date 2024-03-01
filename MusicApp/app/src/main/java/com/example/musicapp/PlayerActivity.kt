package com.example.musicapp

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Looper
import android.util.Log
import android.widget.SeekBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.musicapp.Contains.PATH
import com.example.musicapp.Utils.formatDuration
import com.example.musicapp.Utils.pauseAudio
import com.example.musicapp.Utils.playAudio
import com.example.musicapp.Utils.resumeAudio
import com.example.musicapp.Utils.seekBackward
import com.example.musicapp.Utils.seekForward
import com.example.musicapp.Utils.stopAudio
import com.example.musicapp.databinding.ActivityPlayerBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.IOException

class PlayerActivity : AppCompatActivity() {
    companion object {
//        var mediaPlayer: MediaPlayer? = null
        lateinit var musicList : ArrayList<Music>
        var songPosition: Int = 0
        var isPlaying: Boolean = true
        var notificationId = 1
        var musicService : MusicService? = null
        var isServiceConnected : Boolean = false
        @SuppressLint("StaticFieldLeak")
        lateinit var binding: ActivityPlayerBinding
    }

    var serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            val myBinder = iBinder as MusicService.MyBinder
            musicService = myBinder.getMusicService()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            //musicService = null
            isServiceConnected = false
        }

    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filePath = intent.getStringExtra(PATH)
        Log.d("filePath", filePath.toString())
        if (filePath != null) {
            playAudio(filePath)
            val intentService = Intent(this@PlayerActivity, MusicService::class.java)
            startService(intentService)
        } else {
            Toast.makeText(this, "Đường dẫn tệp không hợp lệ", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.ibBackBtn.setOnClickListener {
            finish()
        }
        binding.seekBarPA.progress = musicService?.mediaPlayer?.currentPosition!!
        binding.seekBarPA.max = musicService?.mediaPlayer?.duration!!
        binding.fbPlayPauseBtn.setOnClickListener {
            if (isPlaying) {
                Log.d("HGHHH","")
                pauseAudio()
            } else {
                resumeAudio()
                Log.d("aaaaa","")
            }
        }
        binding.fbPreviousBtn.setOnClickListener {
            seekBackward(5000)
        }
        binding.fbNextBtn.setOnClickListener {
            seekForward(5000)
        }
        binding.seekBarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sneekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    musicService?.mediaPlayer?.seekTo(progress)
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) = Unit

            override fun onStopTrackingTouch(sneekBar: SeekBar?) = Unit
        })
    }

//    private fun playAudio(filePath: String) {
//        try {
//            mediaPlayer = MediaPlayer().apply {
//                setDataSource(filePath)
//                prepareAsync()
//                setOnPreparedListener {
//                    updateSeekBar()
//                    start()
//                }
//            }
//        } catch (e: IOException) {
//            e.printStackTrace()
//        }
//    }
//
//    private fun stopAudio() {
//        mediaPlayer?.apply {
//            if (isPlaying) {
//                stop()
//            }
//            release()
//        }
//        mediaPlayer = null
//    }
//
//    private fun pauseAudio() {
//        isPlaying = false
//        mediaPlayer?.pause()
//        binding.fbPlayPauseBtn.setIconResource(R.drawable.ic_play)
//    }
//
//    private fun resumeAudio() {
//        isPlaying = true
//        mediaPlayer?.start()
//        binding.fbPlayPauseBtn.setIconResource(R.drawable.ic_pause)
//        updateSeekBar()
//    }
//
//    private fun seekForward(milliseconds: Int) {
//        mediaPlayer?.apply {
//            if (isPlaying) {
//                val currentPosition = currentPosition
//                val duration = duration
//                val newPosition = currentPosition + milliseconds
//                if (newPosition < duration) {
//                    seekTo(newPosition)
//                } else {
//                    // Đặt về cuối nếu vượt quá độ dài
//                    seekTo(duration)
//                }
//            }
//        }
//    }
//
//    private fun seekBackward(milliseconds: Int) {
//        mediaPlayer?.apply {
//            if (isPlaying) {
//                val currentPosition = currentPosition
//                val newPosition = currentPosition - milliseconds
//                if (newPosition > 0) {
//                    seekTo(newPosition)
//                } else {
//                    // Đặt về đầu nếu quá thấp
//                    seekTo(0)
//                }
//            }
//        }
//    }
//    private fun updateSeekBar() {
//        binding.seekBarPA.max = mediaPlayer?.duration ?: 0
//        binding.tvSeekbarEnd.text = mediaPlayer?.duration?.toLong()?.formatDuration()
//        val handler = Handler(Looper.getMainLooper())
//        handler.postDelayed(object : Runnable {
//            override fun run() {
//                try {
//                    val currentPosition = mediaPlayer?.currentPosition ?: 0
//                    binding.seekBarPA.progress = currentPosition
//                    binding.tvSeekbarStart.text = currentPosition.toLong().formatDuration()
//                    handler.postDelayed(this, 1000)
//                } catch (e: Exception) {
//                    binding.seekBarPA.progress = 0
//                }
//            }
//        }, 0)
//    }

    override fun onDestroy() {
        super.onDestroy()
        musicService?.let {
            stopAudio()
        }
    }
}