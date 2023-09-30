package pt.isel.gomoku.utils

import android.content.Context
import android.media.MediaPlayer

fun playSound(ctx: Context, id: Int) {
    val mediaPlayer = MediaPlayer.create(ctx, id)

    mediaPlayer.seekTo(0)
    mediaPlayer.start()
}