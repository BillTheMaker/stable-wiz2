package stable.devs.cross.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward // Example for deposit/receive
import androidx.compose.material.icons.filled.ArrowUpward   // Example for withdrawal/send
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

enum class TransactionType {
    DEPOSIT,
    WITHDRAWAL,
    SEND,
    RECEIVE
}

data class TransactionUiItem( // Renamed to avoid conflict if you have a domain model "TransactionItem"
    val id: String,
    val description: String,
    val amount: String, // e.g., "+ $50.00" or "- $20.00"
    val date: String,
    val type: TransactionType,
    val icon: ImageVector // Icon representing the transaction type
)

@Composable
fun TransactionRow(
    item: TransactionUiItem,
    modifier: Modifier = Modifier
    // onClick: () -> Unit // Add if rows should be clickable
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            // Icon with bubble (similar to SettingsScreen)
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)), // Generic bubble color
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = item.icon,
                    contentDescription = item.type.name,
                    modifier = Modifier.size(20.dp),
                    tint = MaterialTheme.colorScheme.secondary // Generic icon color
                )
            }
            Spacer(Modifier.width(12.dp))
            Column {
                Text(
                    text = item.description,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Medium
                )
                Text(
                    text = item.date,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Text(
            text = item.amount,
            style = MaterialTheme.typography.bodyLarge,
            fontWeight = FontWeight.Medium,
            color = MaterialTheme.colorScheme.onSurface //
        )
    }
}