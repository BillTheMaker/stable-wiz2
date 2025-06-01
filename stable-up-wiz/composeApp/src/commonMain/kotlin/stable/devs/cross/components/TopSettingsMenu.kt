package stable.devs.cross.components // Ensure this is the correct package

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle // Changed from Menu to AccountCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.* // Removed unused Text and DropdownMenu related imports
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import stable.devs.cross.screens.SettingsScreen // Import your new SettingsScreen

@Composable
fun TopSettingsMenu(
    // isDarkMode and onThemeToggle are no longer needed here if theme is handled globally
    // or on the SettingsScreen itself. For now, let's remove them from this component's
    // direct responsibility, as it's now just a navigation button.
    // If the icon color needs to change based on dark mode, MaterialTheme.colorScheme.onBackground
    // should handle it.
    modifier: Modifier = Modifier
) {
    val navigator = LocalNavigator.currentOrThrow // Get the navigator

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 8.dp)
    ) {
        IconButton(
            onClick = { navigator.push(SettingsScreen) }, // Navigate to SettingsScreen
            modifier = Modifier.align(Alignment.CenterStart)
        ) {
            Icon(
                imageVector = Icons.Default.AccountCircle, // Use a profile icon
                contentDescription = "Settings",
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.onBackground
            )
        }
        // DropdownMenu and its related state (showMenu) are removed.
    }
}