package stable.devs.cross.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import stable.devs.cross.components.AtmosphericBackground

data class SendScreen(
    val onConfirm: (String) -> Unit,
    val isDarkMode: Boolean, // Passed from App.kt via HomeScreen or directly
    val primaryBrandColor: Color, // Example theme color
    val secondaryBrandColor: Color // Example theme color
    // Add other necessary parameters, like the actual theme colors from App.kt
) : Screen { // Using 'object' as it takes no parameters for now

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        Box(modifier = Modifier.fillMaxSize()) {
            // Call AtmosphericBackground first, so it's in the back
            AtmosphericBackground(
                modifier = Modifier.fillMaxSize(),
                isDarkMode = isDarkMode,
                glowHintColor = secondaryBrandColor, // Or a specific color for this screen
                baseBackgroundColor = MaterialTheme.colorScheme.background, // Example base colors
                customLightHitRelX = 1.2f, // Example: Custom light position for SendScreen
                customLightHitRelY = 0.15f  // Example: Custom light position for SendScreen
                // Omit custom params to use defaults
            )

        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Transfer Funds") },
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) { // Pop back to previous screen
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { paddingValues ->
            Column(
                modifier = Modifier.fillMaxSize().padding(paddingValues),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("This is the Send Screen")
                // Add your deposit UI elements here
            }
        }
    }
}}