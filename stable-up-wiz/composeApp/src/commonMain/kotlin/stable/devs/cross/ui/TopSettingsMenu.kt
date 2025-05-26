package stable.devs.cross.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu // Hamburger icon
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopSettingsMenu(
    isDarkMode: Boolean,
    onThemeToggle: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showMenu by remember { mutableStateOf(false) }

    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp, top = 16.dp) // Padding for the bar area
    ) {
        IconButton(
            onClick = { showMenu = true },
            modifier = Modifier.align(Alignment.CenterStart) // Align to top-left
        ) {
            Icon(
                imageVector = Icons.Default.Menu,
                contentDescription = "Settings"
            )
        }

        DropdownMenu(
            expanded = showMenu,
            onDismissRequest = { showMenu = false }
        ) {
            // You can add a header or title for "Settings" if you like
            // DropdownMenuItem(onClick = {}, enabled = false) { Text("Settings", fontWeight = FontWeight.Bold) }
            // Divider()

            DropdownMenuItem(
                text = { Text(if (isDarkMode) "Switch to Light Mode" else "Switch to Dark Mode") },
                onClick = {
                    onThemeToggle()
                    showMenu = false // Close menu after selection
                }
            )
            // Future settings options can be added here as more DropdownMenuItems
            // e.g., DropdownMenuItem(text = { Text("Profile") }, onClick = { /* ... */ showMenu = false })
            //      DropdownMenuItem(text = { Text("About") }, onClick = { /* ... */ showMenu = false })
        }
    }
}