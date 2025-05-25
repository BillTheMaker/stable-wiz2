package stable.devs.cross.ui


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme // Needed if you want to access theme colors directly here
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp

@Composable
fun AtmosphericBackground(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    brandYellow: Color,
    baseBackgroundColor: Color // Pass the current theme's background color
) {
    val configuration = LocalConfiguration.current
    val density = LocalDensity.current
    val screenWidthPx = with(density) { configuration.screenWidthDp.dp.toPx() }
    val screenHeightPx = with(density) { configuration.screenHeightDp.dp.toPx() }

    val atmosphericBrush = Brush.radialGradient(
        colors = if (isDarkMode) {
            listOf(
                // Center of the "moon reflection" glow in dark mode:
                // A very subtle yellow glow composited onto a slightly lighter version of the base background
                baseBackgroundColor.copy(alpha = 0.95f).compositeOver(brandYellow.copy(alpha = 0.04f)),
                baseBackgroundColor // Fades to the main dark base background
            )
        } else {
            listOf(
                // Center of the subtle glow in light mode:
                // A brighter, very subtle yellow tinted glow composited onto a whiter base
                Color.White.copy(alpha = 0.8f).compositeOver(brandYellow.copy(alpha = 0.03f)),
                baseBackgroundColor // Fades to the main light base background
            )
        },
        center = Offset(screenWidthPx / 2f, screenHeightPx * 0.4f), // Centered H, slightly above vertical center
        radius = screenWidthPx * 1.0f // Make radius large for a diffuse effect
    )

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(brush = atmosphericBrush)
    )
}
