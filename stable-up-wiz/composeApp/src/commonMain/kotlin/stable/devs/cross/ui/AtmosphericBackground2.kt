// In composeApp/src/commonMain/kotlin/stable/devs/cross/ui/AtmosphericBackground.kt
package stable.devs.cross.ui

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
    baseBackgroundColor: Color
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        // --- Parameters for the "Lit Shape from Right" Effect ---

        // Define the "sphere" or "moon" shape that receives light
        // Let's make it a large oval, mostly off-screen to the right,
        // so its curved lit edge sweeps onto the screen.
        val shapeWidth = canvasWidth * 2.0f // Make the shape wider than the canvas
        val shapeHeight = canvasWidth * 2.0f // Make it taller
        val shapeTopLeftX = canvasWidth * -1.25f // Start the shape from 40% across the screen (so left part is visible)
        val shapeTopLeftY = (canvasHeight - (shapeHeight * 1.35f)) // Center it vertically

        // Center of the light *on this shape*.
        // If light comes from the right, the highlight on the shape will be on its right side.
        // Relative to the shape's own coordinate system (from shapeTopLeftX, shapeTopLeftY)
        val lightHitRelX = shapeWidth * 1.2f // Light hits 85% of the way across the shape (its right side)
        val lightHitRelY = shapeHeight * 0.7f  // Light hits at re

        // Convert to absolute canvas coordinates for the gradient center
        val gradientCenterX = shapeTopLeftX + lightHitRelX
        val gradientCenterY = shapeTopLeftY + lightHitRelY

        // Radius of the gradient *on the shape*. This controls how the light falls off across the shape.
        val gradientRadiusOnShape = shapeWidth * 0.7f // Light spreads over percentage of shape's width

        // Colors for the gradient on the shape
        val highlightColor = glowHintColor.copy(alpha = if (isDarkMode) 0.30f else 0.20f)
            .compositeOver(
                if (isDarkMode) baseBackgroundColor.copy(alpha = 0.3f)
                else Color.White.copy(alpha = 0.2f)
            )
        // The shape fades into the base background color at its "unlit" parts
        val shapeUnlitColor = baseBackgroundColor

        val shapeBrush = Brush.radialGradient(
            // Lit part of the shape
            0.0f to highlightColor,
            // How quickly the highlight fades across the shape.
            // 0.4f means it fades to the shape's unlit color by 40% of gradientRadiusOnShape
            0.9f to shapeUnlitColor,

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