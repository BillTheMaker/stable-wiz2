@file:JvmName("SharedBackgrounds2Kt")

package stable.devs.cross.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlin.jvm.JvmName


@Composable
fun AuroraBackground3() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        // stuff

        // Define your colors - feel free to experiment!
        val color1 = Color(0xFF7E57C2).copy(alpha = 1f) //
        val color2 = Color(0xFF42A5F5).copy(alpha = 1f) //


        // Adjust centers, radii, and colors to get the look you want.
        val radiusMultiplier = 0.8f

        // Large blue blob
        drawCircle(
            brush = Brush.radialGradient(
                // Provide color stops as a list of Pairs: (stopPosition to color)
                    0.0f to color2.copy(alpha = .3f),    // Color at stop 0.0f
                    0.7f to color2.copy(alpha = .2f),   // Color at stop 0.7f
                    1f to Color.Transparent            // Color at stop 1.0f
                ,
                center = Offset(canvasWidth * 1f, canvasHeight * 0.15f), // Your existing center
                radius = canvasWidth * 0.7f // Larger radius for more diffusion
            ),
            radius = canvasWidth * 0.7f,
            center = Offset(canvasWidth * 0.8f, canvasHeight * 0.2f)
        )

        // Purple blob
        drawCircle(
            brush = Brush.radialGradient(
                0.0f to color1.copy(alpha = .45f),    // Color at stop 0.0f
                0.7f to color1.copy(alpha = 0.2f),   // Color at stop 0.7f
                1f to Color.Transparent,
                center = Offset(canvasWidth * 0.5f, canvasHeight * 0.6f),
                radius = canvasWidth * radiusMultiplier
            ),
            radius = canvasWidth * radiusMultiplier,
            center = Offset(canvasWidth * 0.5f, canvasHeight * 0.85f)
        )
    }
}

@Composable
fun SimpleGradientBackground(
    modifier: Modifier = Modifier, // Allow passing a modifier if needed, defaults to fillMaxSize
    startColor: Color,
    endColor: Color,
    isVertical: Boolean = true // Default to vertical, can be made more flexible
    // You could add parameters for startOffset and endOffset for Brush.linearGradient if needed
) {
    Box(
        modifier = modifier
            .fillMaxSize() // This background will fill the space it's placed in
            .background(
                brush = if (isVertical) {
                    Brush.verticalGradient(colors = listOf(startColor, endColor))
                } else {
                    // Example for horizontal, can be expanded for diagonal
                    Brush.horizontalGradient(colors = listOf(startColor, endColor))
                }
            )
    )
    // This Box is just for applying the background.
    // Content will be drawn on top of it by the parent composable.
}