package com.example.musicapp

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent?.action
        if (action != null && context != null) {
            val musicServiceIntent = Intent(context, MusicService::class.java)
            when (action) {
                "PLAY_PAUSE" -> {
                    musicServiceIntent.action = "PLAY_PAUSE" // Thay đổi action thành "PLAY_PAUSE"
                }
                // Xử lý các sự kiện khác nếu cần
            }
            context.startService(musicServiceIntent)
        }
    }
}
