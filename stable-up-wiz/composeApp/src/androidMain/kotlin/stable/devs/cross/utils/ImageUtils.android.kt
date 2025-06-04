package stable.devs.cross.utils

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap

/**
 * Android-specific implementation to convert ByteArray to ImageBitmap.
 */
actual fun ByteArray.toComposeImageBitmap(): ImageBitmap? {
    return try {
        BitmapFactory.decodeByteArray(this, 0, this.size)?.asImageBitmap()
    } catch (e: Exception) {
        // Log error or handle
        println("Error decoding ByteArray to ImageBitmap on Android: ${e.message}")
        null
    }
}