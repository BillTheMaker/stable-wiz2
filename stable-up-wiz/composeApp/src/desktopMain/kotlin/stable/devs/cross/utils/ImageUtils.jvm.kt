package stable.devs.cross.utils

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap // For converting Skia Image to Compose ImageBitmap
import org.jetbrains.skia.Image
import org.jetbrains.skia.Bitmap as SkiaBitmap // Alias to avoid confusion if AWT Bitmap is also in scope
import org.jetbrains.skiko.toBitmap // Skiko extension function to convert AWT BufferedImage to Skia Bitmap
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import javax.imageio.ImageIO

/**
 * Desktop (JVM) specific implementation to convert ByteArray to ImageBitmap
 * using AWT ImageIO and Skiko/Skia interop.
 */
actual fun ByteArray.toComposeImageBitmap(): ImageBitmap? {
    return try {
        val awtBufferedImage: BufferedImage? = ImageIO.read(ByteArrayInputStream(this))
        awtBufferedImage?.let {
            // 1. Convert AWT BufferedImage to Skia Bitmap using Skiko extension
            val skiaBitmap: SkiaBitmap = it.toBitmap() // This is org.jetbrains.skiko.toBitmap

            // 2. Convert Skia Bitmap to Skia Image
            val skiaImage: Image = Image.makeFromBitmap(skiaBitmap)

            // 3. Convert Skia Image to Compose ImageBitmap
            skiaImage.toComposeImageBitmap()
        }
    } catch (e: Exception) {
        println("Error decoding ByteArray to ImageBitmap on Desktop: ${e.message}")
        e.printStackTrace() // Good for debugging
        null
    }
}
