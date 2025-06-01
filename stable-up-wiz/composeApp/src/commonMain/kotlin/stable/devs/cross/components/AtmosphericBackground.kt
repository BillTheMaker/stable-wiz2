// In composeApp/src/commonMain/kotlin/stable/devs/cross/ui/AtmosphericBackground.kt
package stable.devs.cross.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.compositeOver

@Composable
fun AtmosphericBackground(
    modifier: Modifier = Modifier,
    isDarkMode: Boolean,
    glowHintColor: Color, // Your secondaryBrand (Blue)
    baseBackgroundColor: Color,
    customLightHitRelX: Float? = null,  // Nullable
    customLightHitRelY: Float? = null, // Nullable
    customGradientRadiusFactor: Float? = null, // Default will be 0.85f if null
    customUnlitColorStop: Float? = null       // Default will be 0.9f if null
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // --- Parameters for the "Lit Shape from Right" Effect ---

        val shapeWidth = canvasWidth * 2.50f // Make the shape wider than the canvas
        val shapeHeight = canvasWidth * 2.50f // Make it taller
        val shapeTopLeftX = canvasWidth * -1.5f // Start the shape from 40% across the screen (so left part is visible)
        val shapeTopLeftY = (canvasHeight - (shapeHeight * 1f)) // Center it vertically

        val lightHitRelX: Float
        val lightHitRelY: Float

        if (customLightHitRelX != null && customLightHitRelY != null) {
            lightHitRelX = shapeWidth * customLightHitRelX // Assume custom values are factors of shapeWidth/Height
            lightHitRelY = shapeHeight * customLightHitRelY
        } else {
            if (isDarkMode) {
                // Dark Mode: Light "behind" - interpret as coming from the far left
                // This will place the highlight on the far-left side of the large shape.
                lightHitRelX = shapeWidth * 1.35f // Closer to the shape's own left edge
                lightHitRelY = shapeHeight * 0.3f // Centered vertically on the shape
            } else {
                // Light Mode: Light "in front but off screen" - similar to original, from the right
                lightHitRelX = shapeWidth * -.5f
                lightHitRelY = shapeHeight * 0.2f // Original Y position
            }
        }
        // Convert to absolute canvas coordinates for the gradient center
        val gradientCenterX = shapeTopLeftX + lightHitRelX
        val gradientCenterY = shapeTopLeftY + lightHitRelY

        // Radius of the gradient *on the shape*. This controls how the light falls off across the shape.
        val gradientRadiusFactorToUse = customGradientRadiusFactor ?: 0.85f
        val gradientRadiusOnShape = shapeWidth * gradientRadiusFactorToUse

        // Colors for the gradient on the shape
        val highlightColor = glowHintColor.copy(alpha = if (isDarkMode) 0.30f else 0.20f)
            .compositeOver(
                if (isDarkMode) baseBackgroundColor.copy(alpha = 0.3f)
                else Color.White.copy(alpha = 0.2f)
            )
        // The shape fades into the base background color at its "unlit" parts
        val shapeUnlitColor = baseBackgroundColor

        val unlitColorStopToUse = customUnlitColorStop ?: 0.8f

        val shapeBrush = Brush.radialGradient(
            // Lit part of the shape
            0.0f to highlightColor,
            // How quickly the highlight fades across the shape.
            // 0.4f means it fades to the shape's unlit color by 40% of gradientRadiusOnShape
            unlitColorStopToUse to shapeUnlitColor,

            center = Offset(gradientCenterX, gradientCenterY), // Center of light ON THE SHAPE
            radius = gradientRadiusOnShape
        )

        // Draw the shape (e.g., an oval) that represents our lit celestial body/effect
        drawOval(
            brush = shapeBrush,
            topLeft = Offset(shapeTopLeftX, shapeTopLeftY),
            size = Size(shapeWidth, shapeHeight)
        )

        // Optionally: Draw a very subtle, nearly transparent full-screen layer of the baseBackgroundColor
        // on top of everything IF you want to mute the whole effect slightly further,
        // or ensure edges blend perfectly if the shape doesn't cover everything.
        // For now, let's see how the shape looks directly on the implicit background.
        // If your Surface in App.kt already has baseBackgroundColor, that will be behind this.
    }
}