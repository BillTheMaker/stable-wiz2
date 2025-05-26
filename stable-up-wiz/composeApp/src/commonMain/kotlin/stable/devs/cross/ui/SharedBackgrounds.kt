package stable.devs.cross.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable
fun AuroraBackground() {
    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Define your colors - feel free to experiment!
        val color1 = Color(0xFF00FF00).copy(alpha = 0.1f) // Example: Purple, slightly transparent
        val color2 = Color(0xFFFFEB3B).copy(alpha = 0.1f) // Green/Yellow, slightly transparent

        // Draw soft, overlapping circles with radial gradients
        // Adjust centers, radii, and colors to get the look you want.
        val radiusMultiplier = 0.8f

        // Large purple blob
        drawCircle(
            brush = Brush.radialGradient(
                // Provide color stops as a list of Pairs: (stopPosition to color)
                    0.0f to color1.copy(alpha = 0.1f),    // Color at stop 0.0f
                    0.7f to color1.copy(alpha = 0.02f),   // Color at stop 0.7f
                    2.0f to Color.Transparent            // Color at stop 1.0f
                ,
                center = Offset(canvasWidth * 0.1f, canvasHeight * 0.2f), // Your existing center
                radius = canvasWidth * 0.7f // Larger radius for more diffusion
            ),
            radius = canvasWidth * 0.7f,
            center = Offset(canvasWidth * 0.1f, canvasHeight * 0.2f)
        )

        // Magenta blob
        drawCircle(
            brush = Brush.radialGradient(
                0.0f to color1.copy(alpha = 0.15f),    // Color at stop 0.0f
                0.7f to color1.copy(alpha = 0.02f),   // Color at stop 0.7f
                1.0f to Color.Transparent,
                center = Offset(canvasWidth * 0.9f, canvasHeight * 0.3f),
                radius = canvasWidth * radiusMultiplier
            ),
            radius = canvasWidth * 0.65f,
            center = Offset(canvasWidth * 0.5f, canvasHeight * 0.3f)
        )

        // Blue blob
        drawCircle(
            brush = Brush.radialGradient(
                0.0f to color1.copy(alpha = 0.1f),    // Color at stop 0.0f
                0.7f to color1.copy(alpha = 0.02f),   // Color at stop 0.7f
                1.0f to Color.Transparent,
                center = Offset(canvasWidth * 0.5f, canvasHeight * 0.85f),
                radius = canvasWidth * radiusMultiplier
            ),
            radius = canvasWidth * radiusMultiplier,
            center = Offset(canvasWidth * 0.5f, canvasHeight * 0.85f)
        )

        // Smaller Orange accent blob
        drawCircle(
            brush = Brush.radialGradient(
                0.0f to color2.copy(alpha = 0.1f),    // Color at stop 0.0f
                0.7f to color2.copy(alpha = 0.02f),   // Color at stop 0.7f
                1.0f to Color.Transparent,
                center = Offset(canvasWidth * 0.25f, canvasHeight * 0.7f),
                radius = canvasWidth * 0.4f
            ),
            radius = canvasWidth * radiusMultiplier,
            center = Offset(canvasWidth * 0.25f, canvasHeight * 0.7f)
        )
    }
}