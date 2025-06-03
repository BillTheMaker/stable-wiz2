package stable.devs.cross.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayCircleOutline // Example Icon for Play Store
import androidx.compose.material.icons.filled.Group // Example Icon for Community
import androidx.compose.material.icons.filled.Info // Example Icon for Twitter (placeholder)
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import stable.devs.cross.components.GenericSettingsDetailUi // Import the generic UI
import stable.devs.cross.components.SettingsItem // Import your SettingsItem model

object AboutStableUpScreen : Screen { // Or data class if it needs parameters

    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        // Define the specific items for the "About StableUp" screen
        // This list structure is based on your "About Stables" screenshot
        val aboutItems = remember {
            listOf(
                SettingsItem(
                    title = "Rate us on Google Play",
                    icon = Icons.Filled.PlayCircleOutline, // Placeholder, find a better one if needed
                    onClick = { println("Rate us clicked") /* TODO: Open Play Store */ }
                ),
                SettingsItem(
                    title = "Follow us on Twitter",
                    icon = Icons.Filled.Info, // Placeholder, find Twitter icon
                    onClick = { println("Follow Twitter clicked") /* TODO: Open Twitter link */ }
                ),
                SettingsItem(
                    title = "Join the community",
                    icon = Icons.Filled.Group, // Placeholder
                    onClick = { println("Join community clicked") /* TODO: Open community link */ }
                )
                // Add "Important Documents" if it belongs here or another specific screen
            )
        }

        GenericSettingsDetailUi(
            screenTitle = "About StableUp",
            items = aboutItems,
            navigator = navigator
            // Pass theme/background params if GenericSettingsDetailUi expects them
        )
    }
}