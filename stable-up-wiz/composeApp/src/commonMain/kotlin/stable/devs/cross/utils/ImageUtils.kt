package stable.devs.cross.utils

import androidx.compose.ui.graphics.ImageBitmap

/**
 * Converts a ByteArray (assumed to be in a supported image format like PNG)
 * into a Compose ImageBitmap.
 * Returns null if conversion fails.
 */
expect fun ByteArray.toComposeImageBitmap(): ImageBitmap?