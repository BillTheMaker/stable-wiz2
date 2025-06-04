package stable.devs.cross.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap // For converting Skia Image
import kotlinx.cinterop.ExperimentalForeignApi
import kotlinx.cinterop.addressOf
import kotlinx.cinterop.usePinned
import org.jetbrains.skia.Image // Skia Image
import platform.Foundation.NSData
import platform.Foundation.dataWithBytes // Correct way to create NSData from bytes
import platform.UIKit.UIImage

@OptIn(ExperimentalForeignApi::class) // For usePinned and addressOf
actual fun ByteArray.toComposeImageBitmap(): ImageBitmap? {
    return try {
        // The direct conversion from ByteArray to Skia Image is often more straightforward
        // if the ByteArray is a standard encoded format (like PNG, which qrcode-kotlin can output).
        val skiaImage = Image.makeFromEncoded(this)
        skiaImage.toComposeImageBitmap()

        // Alternative approach via UIImage, if direct Skia decoding fails or for other reasons:
        /*
        this.usePinned { pinned ->
            val nsData = NSData.dataWithBytes(pinned.addressOf(0), this.size.toULong())
            UIImage.imageWithData(nsData)?.let { uiImage ->
                // This conversion from UIImage to Skia Image might need a helper if not direct
                // For now, prefering Image.makeFromEncoded(this) directly.
                // If that fails, one would need a more elaborate UIImage -> Skia Image conversion.
                // As a fallback, if Image.makeFromEncoded directly works, the UIImage step is not needed.
                // If Image.makeFromEncoded is problematic, this path needs to be fleshed out.
                // For example, one might need to get CGImage and then convert.
                // However, Skia's makeFromEncoded is quite capable.
                null // Placeholder for more complex UIImage to Skia Image if needed
            }
        }
        */
    } catch (e: Exception) {
        println("Error decoding ByteArray to ImageBitmap on iOS: ${e.message}")
        e.printStackTrace()
        null
    }
}