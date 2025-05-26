// In composeApp/src/commonMain/kotlin/stable/devs/cross/ui/InfoCard.kt
package stable.devs.cross.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
    // Values typically passed from App.kt or a Theme definition
    cardLightModeBaseBgColor: Color,    // e.g., your offWhite Color(0xFFF5FAFD)
    cardGlowHintColor: Color            // e.g., your secondaryBrand Blue Color(0xFF4DB6AC)
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp), // Padding around the card
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent // Card itself is transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(min = 200.dp) // Example minimum height for the card
                .clip(RoundedCornerShape(16.dp))
        ) {
            AtmosphericBackground(
                isAppInDarkMode = false, // This parameter is somewhat overridden by forceLightModeAppearance
                glowHintColor = cardGlowHintColor, // The original blue hue
                baseBackgroundColor = cardLightModeBaseBgColor, // Always use the light version of base
                forceLightModeAppearance = true, // CRUCIAL: Ensures light mode logic is used
                glowIntensityMultiplier = 0.4f // "Less of it" & "Greyed out" - significantly reduce alpha
                // Adjust this (0.3f to 0.6f) for desired subtlety
            )

            // Content of your InfoCard
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "$0.00", // Placeholder
                    fontSize = 32.sp,
                    // Text color should be for a light background.
                    // Use onSurface from your main theme, which will be dark if main theme is light,
                    // or light if main theme is dark. We need to ensure this text is readable.
                    // Forcing dark text here as the card background is always light:
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.87f) // Or a specific dark grey
                )
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Graph Area Placeholder", color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f))
                }
                // ... other card content ...
            }
        }
    }
}