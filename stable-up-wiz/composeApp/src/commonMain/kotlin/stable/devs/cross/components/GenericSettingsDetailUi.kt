package stable.devs.cross.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.navigator.Navigator // Import Navigator

// Assuming SettingsItem and ItemGroupCard are accessible from this package
// e.g., import stable.devs.cross.components.SettingsItem
//      import stable.devs.cross.components.ItemGroupCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenericSettingsDetailUi(
    screenTitle: String,
    items: List<SettingsItem>, // The list of items for the ItemGroupCard
    navigator: Navigator, // To handle the back navigation
    // Optional: Parameters if these screens also need their own AtmosphericBackground
    // isDarkMode: Boolean,
    // primaryBrandColor: Color,
    // secondaryBrandColor: Color,
    // atmosphericLightX: Float? = null,
    // atmosphericLightY: Float? = null
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(screenTitle) },
                navigationIcon = {
                    IconButton(onClick = { navigator.pop() }) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
                // Add colors = TopAppBarDefaults.topAppBarColors(...) if needed
            )
        },
        // If this screen has its own AtmosphericBackground, Scaffold would be transparent
        // containerColor = Color.Transparent
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(horizontal = 16.dp, vertical = 16.dp) // Overall padding for content
        ) {
            // If you want the ItemGroupCard to be the only content:
            ItemGroupCard(items = items)

            // Or, if you might have other content above/below the card:
            // Text("Some introductory text for $screenTitle", style = MaterialTheme.typography.bodyMedium)
            // Spacer(Modifier.height(16.dp))
            // ItemGroupCard(items = items)
            // Spacer(Modifier.weight(1f))
            // Button(onClick = { /* Save settings? */ }) { Text("Apply") }
        }
    }
}