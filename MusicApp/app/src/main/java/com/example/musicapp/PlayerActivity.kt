package com.example.musicapp

import android.content.ComponentName
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.musicapp.Contains.PATH
import com.example.musicapp.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPlayerBinding
    private var musicService: MusicService? = null
    private var isServiceConnected = false
    private var isPlaying = false

    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(componentName: ComponentName?, iBinder: IBinder?) {
            val myBinder = iBinder as MusicService.MyBinder
            musicService = myBinder.getMusicService()
            isServiceConnected = true
        }

        override fun onServiceDisconnected(componentName: ComponentName?) {
            musicService = null
            isServiceConnected = false
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val filePath = intent.getStringExtra(PATH)
        if (filePath != null) {
            val intentService = Intent(this@PlayerActivity, MusicService::class.java)
            intentService.putExtra(PATH, filePath)
            bindService(intentService, serviceConnection, BIND_AUTO_CREATE)
            startService(intentService)
        } else {
            Toast.makeText(this, "Invalid file path", Toast.LENGTH_SHORT).show()
            finish()
        }

        binding.fbPlayPauseBtn.setOnClickListener {
            if (isPlaying) {
                musicService?.pauseAudio()
            } else {
                musicService?.resumeAudio()
            }
            isPlaying = !isPlaying
        }

        binding.fbPreviousBtn.setOnClickListener {
            musicService?.seekBackward(5000)
        }

        binding.fbNextBtn.setOnClickListener {
            musicService?.seekForward(5000)
        }

        binding.seekBarPA.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(sneekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {

                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        if (isServiceConnected) {
            unbindService(serviceConnection)
            isServiceConnected = false
        }
    }
}
