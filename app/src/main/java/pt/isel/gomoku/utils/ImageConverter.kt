package pt.isel.gomoku.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Base64

fun base64ToBitmap(base64: String): Bitmap {
    val decodedImage = Base64.decode(base64, Base64.DEFAULT)
    return BitmapFactory.decodeByteArray(decodedImage, 0, decodedImage.size)
}