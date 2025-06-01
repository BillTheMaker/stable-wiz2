package stable.devs.cross.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForwardIos
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

class SettingsListItems {
}

data class SettingsItem(
    val title: String,
    val icon: ImageVector,
    val onClick: () -> Unit,
    val subtitle: String? = null
)

@Composable
fun SettingsItemRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    subtitle: String? = null
) {
    val iconColor = MaterialTheme.colorScheme.secondary
    val iconBubbleBackgroundColor = iconColor.copy(alpha = 0.15f)

    Row(
        modifier = Modifier.Companion
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp), // Consistent padding
        verticalAlignment = Alignment.Companion.CenterVertically
    ) {
        Box(
            modifier = Modifier.Companion
                .size(40.dp) // Size of the bubble, adjust as needed
                .clip(CircleShape) // Make it circular
                .background(iconBubbleBackgroundColor),
            contentAlignment = Alignment.Companion.Center // Center the icon within the Box
        ) {
            Icon(
                imageVector = icon,
                contentDescription = title, // Content description can remain on the icon
                modifier = Modifier.Companion.size(20.dp), // Icon size, should be smaller than the Box
                tint = iconColor // The actual icon color
            )
        }

        Spacer(Modifier.Companion.width(16.dp))

        Column(modifier = Modifier.Companion.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            if (subtitle != null) {
                Spacer(Modifier.Companion.height(2.dp))
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Icon(
            imageVector = Icons.Filled.ArrowForwardIos, // Trailing arrow
            contentDescription = null, // Decorative
            modifier = Modifier.Companion.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}