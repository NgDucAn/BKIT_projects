package com.example.musicapp

import android.annotation.SuppressLint
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.AudioManager
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.example.musicapp.Contains.PATH

class MusicService : Service(), AudioManager.OnAudioFocusChangeListener {
    private var myBinder = MyBinder()
    var mediaPlayer: MediaPlayer?=null
    lateinit var audioManager: AudioManager

    inner class MyBinder: Binder() {
        fun getMusicService(): MusicService {
            return this@MusicService
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    @SuppressLint("ForegroundServiceType")
    private fun showNotification() {
        val intent = Intent(baseContext, MainActivity::class.java)

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }

        val contentIntent = PendingIntent.getActivity(this, 0, intent, flag)

        val prevIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(MyApplication.PREVIOUS)
        val prevPendingIntent = PendingIntent.getBroadcast(baseContext, 0, prevIntent, flag)

        val playIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(MyApplication.PLAY)
        val playPendingIntent = PendingIntent.getBroadcast(baseContext, 0, playIntent, flag)

        val nextIntent = Intent(baseContext, NotificationReceiver::class.java).setAction(MyApplication.NEXT)
        val nextPendingIntent = PendingIntent.getBroadcast(baseContext, 0, nextIntent, flag)

        val image = BitmapFactory.decodeResource(resources, R.drawable.music_player_icon_slash_screen)

        val notification = NotificationCompat.Builder(baseContext, MyApplication.CHANNEL_ID)
            .setContentIntent(contentIntent)
            .setContentTitle(PlayerActivity.musicList[PlayerActivity.songPosition].title)
            .setContentText(PlayerActivity.musicList[PlayerActivity.songPosition].artist)
            .setSmallIcon(R.drawable.ic_music)
            .setLargeIcon(image)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setOnlyAlertOnce(true)
            .addAction(R.drawable.ic_previous, "Previous", prevPendingIntent)
            .addAction(R.drawable.ic_pause, "Play", playPendingIntent)
            .addAction(R.drawable.ic_next, "Next", nextPendingIntent)
            .build()

        startForeground(13, notification)
    }

    private fun playAudio(filePath: String) {
        mediaPlayer = MediaPlayer().apply {
            setDataSource(filePath)
            prepare()
            start()
        }
    }

    override fun onCreate() {
        super.onCreate()
        mediaPlayer?.isLooping = true
    }
    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer?.release()
    }

    override fun onBind(intent: Intent?): IBinder {
        return myBinder
    }

    override fun onAudioFocusChange(p0: Int) {

    }
}