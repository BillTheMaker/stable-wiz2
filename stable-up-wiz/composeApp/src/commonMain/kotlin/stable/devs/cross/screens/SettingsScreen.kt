package stable.devs.cross.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // Import LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccountCircle // Example Icon
import androidx.compose.material.icons.filled.CreditCard // Example Icon
import androidx.compose.material.icons.filled.FavoriteBorder // Example Icon
import androidx.compose.material.icons.filled.Notifications // Example Icon
import androidx.compose.material.icons.filled.Palette // Example Icon
import androidx.compose.material.icons.filled.ReceiptLong // Example Icon
import androidx.compose.material.icons.filled.Star // Example Icon
import androidx.compose.material.icons.filled.* // Wildcard for brevity if you have many
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import stable.devs.cross.components.SettingsItem
import stable.devs.cross.components.SettingsItemRow
import stable.devs.cross.screens.AboutStableUpScreen

// New data class to represent a group of items that will go into a card
data class SettingsGroup(
    val groupTitle: String, // e.g., "Promotions", "Profile", "Settings"
    val items: List<SettingsItem>
)

object SettingsScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val settingsGroups = rememberSettingsGroups(navigator) // Updated function name


        val cardBackgroundColor =
            MaterialTheme.colorScheme.onSurface.copy(alpha = 0.05f) // Adjust alpha for desired grayness
        val cardContainerColor = Color(0xFF2C2C2E)

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("User Name") }, // As per mockup
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    },
                    actions = { // Placeholder for the profile picture icon on the right
                        IconButton(onClick = { /* TODO: Profile picture action */ }) {
                            Icon(
                                Icons.Filled.AccountCircle,
                                "User Avatar",
                                modifier = Modifier.size(32.dp)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        titleContentColor = MaterialTheme.colorScheme.onSurface,
                        navigationIconContentColor = MaterialTheme.colorScheme.onSurface
                    )
                )
            }
        ) { innerPadding ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding),
                contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                settingsGroups.forEach { settingsGroup ->

                    item {
                        SettingsHeader(title = settingsGroup.groupTitle)
                    }
                    item { // Each card is an item in LazyColumn
                        Card(
                            modifier = Modifier.fillMaxWidth(),
                            shape = RoundedCornerShape(12.dp), // Rounded corners for the card
                            colors = CardDefaults.cardColors(containerColor = cardContainerColor),
                            elevation = CardDefaults.cardElevation(defaultElevation = 1.dp) // Optional: subtle elevation
                        ) {
                            Column { // Column to hold items within the card
                                settingsGroup.items.forEachIndexed { index, item ->
                                    SettingsItemRow( // Your existing SettingsItemRow
                                        title = item.title,
                                        icon = item.icon,
                                        subtitle = item.subtitle,
                                        onClick = item.onClick
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberSettingsGroups(navigator: cafe.adriel.voyager.navigator.Navigator): List<SettingsGroup> {
    return listOf(
        SettingsGroup(
            groupTitle = "Promotions",
            items = listOf(
                SettingsItem(
                    "Invite a fren",
                    Icons.Filled.Star,
                    onClick = { println("Invite clicked") })
            )
        ),
        SettingsGroup(
            groupTitle = "Profile",
            items = listOf(
                SettingsItem(
                    "Personal details",
                    Icons.Filled.AccountCircle,
                    onClick = { navigator.push(YourProfileScreen()) }),
                SettingsItem(
                    "Account details",
                    Icons.Filled.CreditCard,
                    onClick = { println("Account details clicked") }),
                SettingsItem(
                    "Interest calculator",
                    Icons.Filled.FavoriteBorder,
                    onClick = { println("Interest Calc clicked") }),
                SettingsItem(
                    "Statements",
                    Icons.Filled.ReceiptLong,
                    onClick = { println("Statements clicked") })
            )
        ),
        SettingsGroup(
            groupTitle = "Settings",
            items = listOf(
                SettingsItem(
                    "Theme",
                    Icons.Filled.Palette,
                    subtitle = "Dark",
                    onClick = { println("Theme clicked") }),
                SettingsItem(
                    "Notifications",
                    Icons.Filled.Notifications,
                    onClick = { println("Notifications clicked") }),
                SettingsItem(
                    "About StableUp",
                    Icons.Filled.Info,
                    onClick = { navigator.push(AboutStableUpScreen) }), // Changed icon
                SettingsItem(
                    "Important documents",
                    Icons.Filled.Description,
                    onClick = { println("Docs clicked") }) // Changed icon
            )
        ),
        SettingsGroup( // Log out as its own group/card for similar styling, or handle differently
            groupTitle = "", // No title, or a "Session" title if preferred
            items = listOf(
                SettingsItem(
                    "Log out",
                    Icons.AutoMirrored.Filled.Logout,
                    onClick = { println("Log out clicked") }) // Specific Logout icon
            )
        )
    )
}



@Composable
fun SettingsHeader(title: String) {
    if (title.isNotBlank()) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 0.dp, top = 12.dp, bottom = 8.dp, end = 0.dp)
        )
    }
}

// Define placeholder screens for navigation targets (these would be in their own files)
// These are just to make the navigation calls compile.
class YourProfileScreen : Screen {
    @Composable override fun Content() { Text("Your Profile Screen Placeholder") }
}
