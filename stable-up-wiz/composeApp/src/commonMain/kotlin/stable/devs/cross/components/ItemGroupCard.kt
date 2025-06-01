package stable.devs.cross.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme // If SettingsItemRow uses it
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ItemGroupCard(
    items: List<SettingsItem>,
    modifier: Modifier = Modifier,
    cardBackgroundColor: Color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.3f), // Default color
    itemRowComposable: @Composable (itemData: SettingsItem) -> Unit = { itemData ->
        SettingsItemRow( // Assuming SettingsItemRow is accessible
            title = itemData.title,
            icon = itemData.icon,
            onClick = itemData.onClick,
            subtitle = itemData.subtitle
        )
    } // Default renderer
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = cardBackgroundColor),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp) // Consistent with previous settings
    ) {
        Column {
            items.forEachIndexed { index, item ->
                // Use the provided itemRowComposable to render the item
                // This makes the card more flexible if you need different row types later
                itemRowComposable(item)

                if (index < items.lastIndex) {
                    HorizontalDivider(modifier = Modifier.padding(start = 56.dp)) // Indent divider
                }
            }
        }
    }
}

// Helper overload if you are always using the default SettingsItemRow with its direct props
// This requires SettingsItemRow to be refactored to take a SettingsItem object, or you construct it here.
// For simplicity, the above version with itemRowComposable lambda is more flexible initially.
// If SettingsItemRow directly takes title, icon etc., you'd call it like:
// itemRowComposable = { item ->
//     SettingsItemRow(
//         title = item.title,
//         icon = item.icon,
//         subtitle = item.subtitle,
//         onClick = item.onClick
//     )
// }