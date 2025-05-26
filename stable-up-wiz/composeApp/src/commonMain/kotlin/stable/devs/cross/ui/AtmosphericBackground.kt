// In composeApp/src/commonMain/kotlin/stable/devs/cross/ui/AtmosphericBackground.kt
package stable.devs.cross.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

@Composable
fun AtmosphericBackground(
    modifier: Modifier = Modifier,
    isAppInDarkMode: Boolean, // Renamed for clarity: the app's actual dark mode state
    glowHintColor: Color,
    baseBackgroundColor: Color,
    // New optional parameters for overriding appearance:
    forceLightModeAppearance: Boolean = false, // If true, internal logic uses light mode alphas/bases
    glowIntensityMultiplier: Float = 1.0f // Scale the glow's alpha (0.0 to 1.0)
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // Determine effective mode for appearance based on override
        val useEffectiveDarkMode = if (forceLightModeAppearance) false else isAppInDarkMode

        // Use the passed baseBackgroundColor directly. If forceLightModeAppearance is true,
        // the caller (InfoCard) will be responsible for passing the light mode baseBackgroundColor.
        val effectiveBaseBackgroundColor = baseBackgroundColor

        val highlightCenterX = canvasWidth * 0.70f
        val highlightCenterY = canvasHeight * 0.45f
        val lightEffectRadius = canvasWidth * 0.85f

        // Adjust alpha values based on effective mode and intensity multiplier
        val highlightAlpha = (if (useEffectiveDarkMode) 0.28f else 0.20f) * glowIntensityMultiplier
        val midToneAlpha = (if (useEffectiveDarkMode) 0.12f else 0.08f) * glowIntensityMultiplier

        // Define colors for the gradient
        val highlightColorEffective = glowHintColor.copy(alpha = highlightAlpha)
            .compositeOver(
                // Use a brighter compositing base if we are rendering for light mode appearance
                if (!useEffectiveDarkMode) Color.White.copy(alpha = 0.25f)
                else effectiveBaseBackgroundColor.copy(alpha = 0.3f)
            )

        val midToneColorEffective = glowHintColor.copy(alpha = midToneAlpha)
            .compositeOver(
                if (!useEffectiveDarkMode) effectiveBaseBackgroundColor.copy(alpha = 0.6f) // Light mode base with decent alpha
                else effectiveBaseBackgroundColor.copy(alpha = 0.7f) // Dark mode base with decent alpha
            )

        val ambientColor = effectiveBaseBackgroundColor

        val atmosphericBrush = Brush.radialGradient(
            0.00f to highlightColorEffective,
            0.30f to midToneColorEffective,
            0.80f to ambientColor,
            center = Offset(highlightCenterX, highlightCenterY),
            radius = lightEffectRadius
        )

        drawRect(brush = atmosphericBrush, size = size)
    }
}