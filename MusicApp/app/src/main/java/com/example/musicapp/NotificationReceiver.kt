package com.example.musicapp

import android.app.Application
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.musicapp.MyApplication.Companion.EXIT
import com.example.musicapp.MyApplication.Companion.NEXT
import com.example.musicapp.MyApplication.Companion.PLAY
import com.example.musicapp.MyApplication.Companion.PREVIOUS
import com.example.musicapp.Utils.pauseAudio
import com.example.musicapp.Utils.resumeAudio
import com.example.musicapp.Utils.seekBackward
import com.example.musicapp.Utils.seekForward

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        when(intent?.action){
            PLAY -> if (PlayerActivity.isPlaying) pauseAudio() else resumeAudio()
            NEXT -> if (PlayerActivity.isPlaying) seekForward(5000)
            PREVIOUS -> if (PlayerActivity.isPlaying) seekBackward(5000)
        }
    }
}