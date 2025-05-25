package stable.devs.cross.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card // Using Material3 Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import stable.devs.cross.ui.AuroraBackground

@Composable
fun InfoCard(
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp), // Padding around the card
        shape = RoundedCornerShape(16.dp), // Rounded corners like in the examples
        elevation = CardDefaults.cardElevation(defaultElevation = 24.dp) // Optional shadow
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(400.dp) // Example height, make it dynamic or fixed as needed
                .clip(RoundedCornerShape(16.dp)) // Clip the Aurora to the card's shape
        ) {
            AuroraBackground() // Aurora is the background of this Box

            // Content of the card goes here, on top of Aurora
            Column(
                modifier = Modifier.fillMaxSize().padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.SpaceBetween // Example arrangement
            ) {
                Text(
                    text = "$0.00", // Placeholder for balance
                    fontSize = 32.sp,
                    color = MaterialTheme.colorScheme.onSurface // Use theme color for text on card
                )
                // Placeholder for graph
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f)
                        .padding(vertical = 8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Graph Area", color = MaterialTheme.colorScheme.onSurface)
                }
                // Placeholder for APY/Boost buttons if needed, like Fido example
                Row {
                    Text("6% APY", color = MaterialTheme.colorScheme.onSurface, modifier = Modifier.padding(end = 8.dp))
                    Text("0% Boost", color = MaterialTheme.colorScheme.onSurface)
                }
            }
        }
    }
}