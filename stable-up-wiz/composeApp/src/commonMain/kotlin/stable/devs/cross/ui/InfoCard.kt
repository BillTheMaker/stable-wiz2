package stable.devs.cross.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
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
    cardBgPrimaryColor: Color,
    cardBgSecondaryColor: Color
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 8.dp), // Padding around the card
        shape = RoundedCornerShape(16.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent
        )
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(225.dp) // Set to a fixed height
                .clip(RoundedCornerShape(16.dp))
                .background(Color.Black)
                .background(cardBgSecondaryColor.copy(alpha = 0.1f))
        ) {
            OvalGradientBackground(
                modifier = Modifier.fillMaxSize(),
                primaryColor = cardBgPrimaryColor,
                secondaryColor = cardBgSecondaryColor,
                primaryOvalRotationDegrees = -15f,  // Example: Rotate primary oval by 15 degrees
                secondaryOvalRotationDegrees = 30f // Example: Rotate secondary oval by -10 degrees
            )
            // Content of your InfoCard
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        "$4,789.57",
                        fontSize = 32.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}