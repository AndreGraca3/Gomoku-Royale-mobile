package pt.isel.gomoku.utils

import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder
import android.util.Log
import pt.isel.gomoku.R

fun Context.playSound(id: Int) {
    val mediaPlayer = MediaPlayer.create(this, id)

    mediaPlayer.seekTo(0)
    mediaPlayer.start()
}

class MusicService() : Service() {

    companion object {
        const val ACTION_PAUSE = "ACTION_PAUSE"
        const val ACTION_RESUME = "ACTION_RESUME"
    }

    private var player: MediaPlayer? = null

    override fun onCreate() {
        super.onCreate()
        player = MediaPlayer.create(this, R.raw.background_music)
        player!!.isLooping = true
        player!!.setVolume(40F, 40F)
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_PAUSE -> {
                player?.pause()
            }
            ACTION_RESUME -> {
                player?.start()
            }
            else -> {
                player!!.start()
            }
        }
        return START_STICKY
    }

    override fun onDestroy() {
        player?.stop()
        player?.release()
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onLowMemory() {
    }
}