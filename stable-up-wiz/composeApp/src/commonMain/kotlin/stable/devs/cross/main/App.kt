package stable.devs.cross.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator // Import Voyager Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview

// Import your existing UI components if App.kt still directly uses any (it shouldn't much after this)
import stable.devs.cross.components.AtmosphericBackground
// Import your new HomeScreen
import stable.devs.cross.screens.HomeScreen // Or stable.devs.cross.screens.HomeScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    var isDarkMode by remember { mutableStateOf(true) }

    val offBlack = Color(0xFF1A1F24)
    val offWhite = Color(0xFFF5FAFD)
    val primaryBrand = Color(0xFFc770c5)
    val secondaryBrand = Color(0xFF6248fb)
    val lightSkyBlue = Color(0xFFB0E0E6)
    val moonGray = Color(0xFFB0B8C0)

    val lightColors = lightColorScheme(
        background = lightSkyBlue,
        surface = offWhite,
        primary = primaryBrand,
        secondary = secondaryBrand,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = Color.Black,
        onSurface = Color.Black
    )
    val darkColors = darkColorScheme(
        background = offBlack,
        surface = offBlack,
        primary = primaryBrand,
        secondary = secondaryBrand,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = offWhite,
        onSurface = offWhite
    )
    val currentColorScheme = if (isDarkMode) darkColors else lightColors

    // The TransactionBottomSheet state and related variables (showTransactionSheet, transactionType, sheetState)
    // are removed from here for now. They would typically live within the screen that triggers them (e.g., HomeScreen)
    // or be managed by a ViewModel/ScreenModel if the logic is complex or shared.

    MaterialTheme(colorScheme = currentColorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                val atmosphericShapeBaseColor = if (isDarkMode) {
                    MaterialTheme.colorScheme.surface
                } else {
                    moonGray
                }

                AtmosphericBackground(
                    modifier = Modifier.fillMaxSize(),
                    isDarkMode = isDarkMode,
                    glowHintColor = secondaryBrand,
                    baseBackgroundColor = atmosphericShapeBaseColor
                )

                // Initialize Navigator with HomeScreen
                // Pass the necessary parameters to HomeScreen
                Navigator(
                    screen = HomeScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        primaryBrandColor = primaryBrand,
                        secondaryBrandColor = secondaryBrand
                        // Pass other necessary data
                    )
                )
            }
        }
    }
    // The commented-out TransactionBottomSheet from your original App.kt is removed.
    // This logic should be moved into the screen that needs it (e.g., HomeScreen or a dedicated transaction screen).
}