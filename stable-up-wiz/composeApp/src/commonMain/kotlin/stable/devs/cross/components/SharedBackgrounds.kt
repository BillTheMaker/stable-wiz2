package stable.devs.cross.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.lerp // Needed for the darken function

/**
 * Darkens a color by a given fraction (0.0 to 1.0).
 * 0.0 means no change, 1.0 means black.
 */
fun Color.darkenBy(fraction: Float): Color {
    val darkFraction = fraction.coerceIn(0f, 1f)
    return lerp(this, Color.Black, darkFraction)
}

/**
 * Creates an Aurora-like background with two radial gradient blobs,
 * configurable with primary and secondary colors.
 * This version explicitly defines three color stops for each gradient,
 * mirroring the structure of the original AuroraBackground.
 */
@Composable
fun AuroraBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color,    // e.g., your brand purple
    secondaryColor: Color,  // e.g., your brand blue
    // Input list of (stopFraction, alphaValue) for the primary color blob.
    // The first three elements are used to define three color stops.
    primaryAlphaStopsInput: List<Pair<Float, Float>> = listOf(0.0f to 0.45f, 0.7f to 0.2f, 1.0f to 0.0f),
    // Input list of (stopFraction, alphaValue) for the secondary color blob.
    // The first three elements are used to define three color stops.
    secondaryAlphaStopsInput: List<Pair<Float, Float>> = listOf(0.0f to 0.3f, 0.7f to 0.2f, 1.0f to 0.0f)
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height
        val radiusMultiplier = 0.5f

        val darkPrimaryBase = primaryColor.darkenBy(0.3f)
        val darkSecondaryBase = secondaryColor.darkenBy(0.1f)


        // Define default stop information if input lists are too short
        val defaultPrimaryStop1 = 0.0f to 0.25f
        val defaultPrimaryStop2 = 0.35f to 0.2f
        val defaultPrimaryStop3 = 1.0f to 0.0f // Alpha 0.0 means transparent

        val defaultSecondaryStop1 = 0.0f to 0.45f
        val defaultSecondaryStop2 = 0.7f to 0.2f
        val defaultSecondaryStop3 = 1.0f to 0.0f // Alpha 0.0 means transparent

        // Primary color stops
        val pStop1Info = primaryAlphaStopsInput.getOrElse(0) { defaultPrimaryStop1 }
        val pStop2Info = primaryAlphaStopsInput.getOrElse(1) { defaultPrimaryStop2 }
        val pStop3Info = primaryAlphaStopsInput.getOrElse(2) { defaultPrimaryStop3 }

        val primaryStop1 = pStop1Info.first to darkPrimaryBase.copy(alpha = pStop1Info.second)
        val primaryStop2 = pStop2Info.first to darkPrimaryBase.copy(alpha = pStop2Info.second)
        val primaryStop3 = pStop3Info.first to darkPrimaryBase.copy(alpha = pStop3Info.second)

        // Secondary color stops
        val sStop1Info = secondaryAlphaStopsInput.getOrElse(0) { defaultSecondaryStop1 }
        val sStop2Info = secondaryAlphaStopsInput.getOrElse(1) { defaultSecondaryStop2 }
        val sStop3Info = secondaryAlphaStopsInput.getOrElse(2) { defaultSecondaryStop3 }

        val secondaryStop1 = sStop1Info.first to darkSecondaryBase.copy(alpha = sStop1Info.second)
        val secondaryStop2 = sStop2Info.first to darkSecondaryBase.copy(alpha = sStop2Info.second)
        val secondaryStop3 = sStop3Info.first to darkSecondaryBase.copy(alpha = sStop3Info.second)

        // Secondary color blob (e.g., Blue)
        drawCircle(
            brush = Brush.radialGradient(
                secondaryStop1,
                secondaryStop2,
                secondaryStop3,
                center = Offset(canvasWidth * .2f, canvasHeight * 0.7f),
                radius = canvasWidth * radiusMultiplier
            ),
            radius = canvasWidth * radiusMultiplier * 10,
            center = Offset(canvasWidth * 0.6f, canvasHeight * .3f)
        )

        // Primary color blob (e.g., Purple)
        drawCircle(
            brush = Brush.radialGradient(
                primaryStop1,
                primaryStop2,
                primaryStop3,
                center = Offset(canvasWidth * 0.75f, canvasHeight * 0.5f),
                radius = canvasWidth * radiusMultiplier
            ),
            radius = canvasWidth * radiusMultiplier,
            center = Offset(canvasWidth * 0.75f, canvasHeight * 0.5f)
        )
    }
}

@Composable
fun OvalGradientBackground(
    modifier: Modifier = Modifier,
    primaryColor: Color,
    secondaryColor: Color,
    primaryAlphaStopsInput: List<Pair<Float, Float>> = listOf(0.0f to 0.45f, 0.5f to 0.2f, 1.0f to 0.0f),
    secondaryAlphaStopsInput: List<Pair<Float, Float>> = listOf(0.0f to 0.45f, 0.5f to 0.2f, 1.0f to 0.0f),
    // Rotation parameters for the ovals
    primaryOvalRotationDegrees: Float = 0f,   // Rotation for the primary oval
    secondaryOvalRotationDegrees: Float = 0f, // Rotation for the secondary oval
    gradientBrushRadiusFactor: Float = .75f,
    gradientBrushRadiusFactor2: Float = .75f,
    secondaryOvalWidthFactor: Float = 5f,
    secondaryOvalHeightFactor: Float = 5f,
    primaryOvalWidthFactor: Float = 2f,
    primaryOvalHeightFactor: Float = 2f
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val canvasWidth = size.width
        val canvasHeight = size.height

        val darkenedPrimaryBase = primaryColor.darkenBy(0.4f)
        val darkenedSecondaryBase = secondaryColor.darkenBy(0.2f)

        // Color stop definitions (as in your previous AuroraBackground for ovals)
        val defaultPrimaryStop1 = 0.0f to 0.3f; val defaultPrimaryStop2 = 0.8f to 0.7f; val defaultPrimaryStop3 = 1f to 0.0f
        val defaultSecondaryStop1 = 0.0f to 0.2f; val defaultSecondaryStop2 = 0.7f to 0.3f; val defaultSecondaryStop3 = 1f to 0.0f

        val pStop1Info = primaryAlphaStopsInput.getOrElse(0) { defaultPrimaryStop1 }
        val pStop2Info = primaryAlphaStopsInput.getOrElse(1) { defaultPrimaryStop2 }
        val pStop3Info = primaryAlphaStopsInput.getOrElse(2) { defaultPrimaryStop3 }
        val primaryStop1 = pStop1Info.first to darkenedPrimaryBase.copy(alpha = pStop1Info.second)
        val primaryStop2 = pStop2Info.first to darkenedPrimaryBase.copy(alpha = pStop2Info.second)
        val primaryStop3 = pStop3Info.first to darkenedPrimaryBase.copy(alpha = pStop3Info.second)

        val sStop1Info = secondaryAlphaStopsInput.getOrElse(0) { defaultSecondaryStop1 }
        val sStop2Info = secondaryAlphaStopsInput.getOrElse(1) { defaultSecondaryStop2 }
        val sStop3Info = secondaryAlphaStopsInput.getOrElse(2) { defaultSecondaryStop3 }
        val secondaryStop1 = sStop1Info.first to darkenedSecondaryBase.copy(alpha = sStop1Info.second)
        val secondaryStop2 = sStop2Info.first to darkenedSecondaryBase.copy(alpha = sStop2Info.second)
        val secondaryStop3 = sStop3Info.first to darkenedSecondaryBase.copy(alpha = sStop3Info.second)

        // --- Secondary color oval (e.g., Blue) ---
        val secondaryOvalWidth = canvasWidth * secondaryOvalWidthFactor
        val secondaryOvalHeight = canvasWidth * secondaryOvalHeightFactor
        val secondaryOvalCenterMid = Offset(canvasWidth * .1f, canvasHeight * .9f)
        val secondaryOvalBrushCenter = Offset(canvasWidth * .25f, canvasHeight * .4f)
        val secondaryOvalCenterRight = Offset(canvasWidth * .35f, canvasHeight * 0.65f)
        val secondaryOvalTopLeft = Offset(secondaryOvalCenterMid.x - secondaryOvalWidth / 2f, secondaryOvalCenterMid.y - secondaryOvalHeight / 5f)
        val secondaryOvalSize = Size(secondaryOvalWidth, secondaryOvalHeight)

        rotate(degrees = secondaryOvalRotationDegrees, pivot = secondaryOvalCenterMid) {
            drawOval(
                brush = Brush.radialGradient(
                    secondaryStop1, secondaryStop2, secondaryStop3,
                    center = secondaryOvalBrushCenter,
                    radius = canvasWidth * gradientBrushRadiusFactor2
                ),
                topLeft = secondaryOvalTopLeft,
                size = secondaryOvalSize
            )
        }





        // --- Primary color oval (e.g., Purple) ---
        val primaryOvalWidth = canvasWidth * primaryOvalWidthFactor
        val primaryOvalHeight = canvasWidth * primaryOvalHeightFactor
        val primaryOvalCenterTop = Offset(canvasWidth * 0.8f, canvasHeight * 0.3f)
        val primaryOvalCenterMid = Offset(canvasWidth * 0.65f, canvasHeight * 0f)
        val primaryOvalBrushCenter = Offset(canvasWidth * .55f, canvasHeight * .45f)
        val primaryOvalTopLeft = Offset(primaryOvalCenterMid.x - primaryOvalWidth / 2f, primaryOvalCenterMid.y - primaryOvalHeight / 2f)
        val primaryOvalSize = Size(primaryOvalWidth, primaryOvalHeight)

        rotate(degrees = primaryOvalRotationDegrees, pivot = primaryOvalCenterMid) {
            drawOval(
                brush = Brush.radialGradient(
                    primaryStop1, primaryStop2, primaryStop3,
                    center = primaryOvalBrushCenter,
                    radius = canvasWidth * gradientBrushRadiusFactor
                ),
                topLeft = primaryOvalTopLeft,
                size = primaryOvalSize
            )
        }
    }
}

/**
 * Creates a linear gradient Brush using two main colors (e.g., brand colors),
 * allowing for darkening and alpha modification of each, and specifying start/end offsets.
 * Useful for card backgrounds or other elements.
 */
fun createBrandLinearGradientBrush(
    color1: Color,
    color2: Color,
    startOffset: Offset,
    endOffset: Offset,
    color1Darken: Float = 0.0f,   // Fraction to darken color1 (0.0 to 1.0)
    color2Darken: Float = 0.0f,   // Fraction to darken color2 (0.0 to 1.0)
    color1Alpha: Float = 1.0f,    // Alpha for color1
    color2Alpha: Float = 1.0f     // Alpha for color2
): Brush {
    val colors = listOf(
        color1.darkenBy(color1Darken).copy(alpha = color1Alpha),
        color2.darkenBy(color2Darken).copy(alpha = color2Alpha)
    )
    return Brush.linearGradient(
        colors = colors,
        start = startOffset,
        end = endOffset
    )
}