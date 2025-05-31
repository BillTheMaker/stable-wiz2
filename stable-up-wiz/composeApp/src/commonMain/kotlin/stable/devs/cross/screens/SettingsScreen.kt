package stable.devs.cross.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn // Import LazyColumn
import androidx.compose.foundation.lazy.items // Import items extension for LazyColumn
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
import androidx.compose.material.icons.filled.ArrowForwardIos // For trailing arrow
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow

// Data classes to represent the structure of items in the LazyColumn
sealed class SettingsListItem {
    data class Header(val title: String) : SettingsListItem()
    data class Item(
        val title: String,
        val icon: ImageVector,
        val onClick: () -> Unit,
        val subtitle: String? = null // For items like "Theme: Dark"
    ) : SettingsListItem()
}

object SettingsScreen : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        // Define the list of settings items based on the mockup
        val settingsItems = rememberSettingsItems(navigator)

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
                            Icon(Icons.Filled.AccountCircle, "User Avatar", modifier = Modifier.size(32.dp))
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
                contentPadding = PaddingValues(vertical = 16.dp) // Padding for the list itself
            ) {
                items(settingsItems) { listItem ->
                    when (listItem) {
                        is SettingsListItem.Header -> {
                            SettingsHeader(title = listItem.title)
                        }
                        is SettingsListItem.Item -> {
                            SettingsItemRow(
                                title = listItem.title,
                                icon = listItem.icon,
                                subtitle = listItem.subtitle,
                                onClick = listItem.onClick
                            )
                        }
                    }
                    if (listItem is SettingsListItem.Item) { // Add divider after items, not headers
                        HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun rememberSettingsItems(navigator: cafe.adriel.voyager.navigator.Navigator): List<SettingsListItem> {
    // In a real app, these onClick lambdas would navigate to other screens:
    // e.g., navigator.push(PersonalDetailsScreen)
    return listOf(
        SettingsListItem.Header("Promotions"),
        SettingsListItem.Item("Invite a fren", Icons.Filled.Star, onClick = { println("Invite clicked") }),
        SettingsListItem.Header("Profile"),
        SettingsListItem.Item("Personal details", Icons.Filled.AccountCircle, onClick = { navigator.push(YourProfileScreen()) }), // Assuming YourProfileScreen exists
        SettingsListItem.Item("Account details", Icons.Filled.CreditCard, onClick = { println("Account details clicked") }),
        SettingsListItem.Item("Interest calculator", Icons.Filled.FavoriteBorder, onClick = { println("Interest Calc clicked") }), // Replaced Currency
        SettingsListItem.Item("Statements", Icons.Filled.ReceiptLong, onClick = { println("Statements clicked") }),
        SettingsListItem.Header("Settings"),
        SettingsListItem.Item("Theme", Icons.Filled.Palette, subtitle = "Dark", onClick = { println("Theme clicked") }), // Example subtitle
        SettingsListItem.Item("Notifications", Icons.Filled.Notifications, onClick = { println("Notifications clicked") }),
        SettingsListItem.Item("About StableUp", Icons.Filled.FavoriteBorder, onClick = { navigator.push(AboutStableUpScreen()) }), // Assuming AboutStableUpScreen exists
        SettingsListItem.Item("Important documents", Icons.Filled.ReceiptLong, onClick = { println("Docs clicked") }),
        // You can add a Spacer or another Header for "Log out" if you want it visually separated more
        SettingsListItem.Item("Log out", Icons.AutoMirrored.Filled.ArrowBack, onClick = { println("Log out clicked") }) // Using ArrowBack for logout as an example
    )
}


@Composable
fun SettingsHeader(title: String) {
    Text(
        text = title,
        style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp)
    )
}

@Composable
fun SettingsItemRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    subtitle: String? = null
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 16.dp, vertical = 20.dp), // Consistent padding
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = title,
            modifier = Modifier.size(24.dp),
            tint = MaterialTheme.colorScheme.primary // Or another appropriate color
        )
        Spacer(Modifier.width(16.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = title, style = MaterialTheme.typography.bodyLarge)
            if (subtitle != null) {
                Spacer(Modifier.height(2.dp))
                Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        Icon(
            imageVector = Icons.Filled.ArrowForwardIos, // Trailing arrow
            contentDescription = null, // Decorative
            modifier = Modifier.size(16.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// Define placeholder screens for navigation targets (these would be in their own files)
// These are just to make the navigation calls compile.
class YourProfileScreen : Screen {
    @Composable override fun Content() { Text("Your Profile Screen Placeholder") }
}
class AboutStableUpScreen : Screen {
    @Composable override fun Content() { Text("About StableUp Screen Placeholder") }
}