package com.example.musicapp

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.example.musicapp.Contains.PATH

class MusicService : Service() {
    private lateinit var mediaPlayer: MediaPlayer
    private val myBinder = MyBinder()

    inner class MyBinder : Binder() {
        fun getMusicService(): MusicService {
            return this@MusicService
        }
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer = MediaPlayer()
        mediaPlayer.isLooping = true
    }

    override fun onBind(intent: Intent?): IBinder {
        return myBinder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val filePath = intent?.getStringExtra(PATH)
        val action = intent?.action
        if (filePath != null) {
            playAudio(filePath)
        } else if (action == "PLAY_PAUSE") { // Thêm xử lý cho action "PLAY_PAUSE"
            togglePlayPause()
        }
        return START_STICKY
    }

    @SuppressLint("WrongConstant")
    private fun playAudio(filePath: String) {
        try {
            mediaPlayer.reset()
            mediaPlayer.setDataSource(filePath)
            mediaPlayer.prepare()
            mediaPlayer.start()
            showNotification(R.drawable.ic_pause) // Hiển thị thông báo khi bắt đầu phát nhạc
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun pauseAudio() {
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
            showNotification(R.drawable.ic_play) // Hiển thị thông báo khi tạm dừng nhạc
        }
    }

    fun resumeAudio() {
        if (!mediaPlayer.isPlaying) {
            mediaPlayer.start()
            showNotification(R.drawable.ic_pause) // Hiển thị thông báo khi tiếp tục phát nhạc
        }
    }

    fun togglePlayPause() {
        if (mediaPlayer.isPlaying) {
            pauseAudio()
        } else {
            resumeAudio()
        }
    }

    fun seekForward(millis: Int) {
        mediaPlayer.seekTo(mediaPlayer.currentPosition + millis)
    }

    fun seekBackward(millis: Int) {
        mediaPlayer.seekTo(mediaPlayer.currentPosition - millis)
    }

    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.release()
    }

    @SuppressLint("StaticFieldLeak")
    fun showNotification(playPauseBtn: Int) {
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "MusicPlayer"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Music Player", NotificationManager.IMPORTANCE_LOW)
            notificationManager.createNotificationChannel(channel)
        }

        val notificationIntent = Intent(this, PlayerActivity::class.java)
        notificationIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        val pendingIntent = PendingIntent.getActivity(this, 0, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val playPauseIntent = Intent(this, NotificationReceiver::class.java).setAction("PLAY_PAUSE")
        val playPausePendingIntent = PendingIntent.getBroadcast(this, 0, playPauseIntent, PendingIntent.FLAG_UPDATE_CURRENT)

        val image = BitmapFactory.decodeResource(resources, R.drawable.ic_music)

        val notification = NotificationCompat.Builder(this, channelId)
            .setContentIntent(pendingIntent)
            .setContentTitle("Music Player")
            .setContentText("Now playing...")
            .setSmallIcon(R.drawable.ic_music)
            .setLargeIcon(image)
            .setOnlyAlertOnce(true)
            .setShowWhen(false)
            .setColor(ContextCompat.getColor(this, R.color.cool_green))
            .addAction(R.drawable.ic_previous, "Previous", null) // Thêm các nút điều khiển khác nếu cần
            .addAction(playPauseBtn, "Play/Pause", playPausePendingIntent)
            .addAction(R.drawable.ic_next, "Next", null) // Thêm các nút điều khiển khác nếu cần
            .build()

        startForeground(13, notification)
    }
}
