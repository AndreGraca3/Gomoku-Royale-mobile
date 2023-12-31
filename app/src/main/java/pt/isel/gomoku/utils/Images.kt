package pt.isel.gomoku.utils

import android.content.ContentResolver
import android.net.Uri
import android.util.Base64
import pt.isel.gomoku.R
import java.io.InputStream

fun getRankIconByName(name: String): Int {
    return when (name) {
        "Grand Champion" -> R.drawable.grand_champion
        "Champion" -> R.drawable.champion
        "Diamond" -> R.drawable.diamond
        "Platinum" -> R.drawable.platinum
        "Gold" -> R.drawable.gold
        "Silver" -> R.drawable.silver
        "Bronze" -> R.drawable.bronze
        else -> throw IllegalArgumentException("Invalid rank name")
    }
}

fun convertImageToBase64(contentResolver: ContentResolver, uri: Uri): String? {

    return try {
        val inputStream: InputStream? = contentResolver.openInputStream(uri)
        val bytes: ByteArray = inputStream?.readBytes() ?: return null

        // Convert the bytes to base64
        val base64Image: String = Base64.encodeToString(bytes, Base64.DEFAULT)

        inputStream.close()
        val mimeType = contentResolver.getType(uri)
        "data:$mimeType;base64,$base64Image"
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}