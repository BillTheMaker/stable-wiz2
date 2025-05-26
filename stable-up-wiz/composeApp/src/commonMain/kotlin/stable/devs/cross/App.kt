package stable.devs.cross

// Imports from your App.kt in Turn 65, plus new ones for bottom sheet/buttons
// Ensure all Material3 components like Button, Surface, Text, ColorSchemes are imported
// Remove AnimatedVisibility, Image, painterResource, and related Res imports if no longer used
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height // Keep if used by Spacers
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.* // For Button, MaterialTheme, Surface, Text, ColorSchemes, etc.
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.ui.tooling.preview.Preview

// Your UI components
import stable.devs.cross.ui.AtmosphericBackground
import stable.devs.cross.ui.InfoCard
import stable.devs.cross.ui.TransactionButtons // For Deposit/Withdrawal
import stable.devs.cross.ui.TransactionBottomSheet // For the new bottom sheet


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    var isDarkMode by remember { mutableStateOf(true) }

    // Your color definitions (as per your Turn 65 App.kt)
    // Assuming offWhite hex was meant to be valid, e.g., 0xFFF5FAFD
    val offBlack = Color(0xFF1A1F24)
    val offWhite = Color(0xFFF5FAFD) // Used for InfoCard light bg (was F5FFAFD in your file)
    val primaryBrand = Color(0xFF4CAF50)   // Your chosen less saturated green
    val secondaryBrand = Color(0xFF4DB6AC) // Your chosen blue

    val lightColors = lightColorScheme(
        background = Color.White, // Main app background in light mode
        surface = offWhite,       // Surface color (e.g., for cards if they don't have their own bg)
        primary = primaryBrand,
        secondary = secondaryBrand,
        onPrimary = Color.White,    // Text on primaryBrand buttons
        onSecondary = Color.White,  // Text on secondaryBrand elements
        onBackground = offBlack,    // Text on main app's light background
        onSurface = offBlack        // Text on default surfaces in light mode
    )
    val darkColors = darkColorScheme(
        background = offBlack,     // Main app background in dark mode
        surface = offBlack,        // Surface color in dark mode
        primary = primaryBrand,
        secondary = secondaryBrand,
        onPrimary = Color.White,
        onSecondary = Color.White,
        onBackground = offWhite,
        onSurface = offWhite
    )
    val currentColorScheme = if (isDarkMode) darkColors else lightColors

    // State for the Transaction Bottom Sheet
    var showTransactionSheet by remember { mutableStateOf(false) }
    var transactionType by remember { mutableStateOf("") } // "Deposit" or "Withdrawal"
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

    MaterialTheme(colorScheme = currentColorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // Main screen background
        ) {
            Box(modifier = Modifier.fillMaxSize()) { // For layering AtmosphericBackground
                AtmosphericBackground(
                    isAppInDarkMode = isDarkMode, // Main atmospheric background reacts to theme
                    glowHintColor = secondaryBrand,
                    baseBackgroundColor = MaterialTheme.colorScheme.background
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeContentPadding(), // Original padding for the entire content column
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    InfoCard(
                        modifier = Modifier.padding(top = 16.dp), // This was the padding in your Turn 65 App.kt
                        cardLightModeBaseBgColor = offWhite,   // Pass your defined light off-white
                        cardGlowHintColor = secondaryBrand      // Pass your defined blue
                    )

                    Spacer(Modifier.height(24.dp)) // Space between InfoCard and new buttons

                    // New Deposit and Withdrawal Buttons
                    TransactionButtons(
                        onDepositClick = {
                            transactionType = "Deposit"
                            showTransactionSheet = true
                        },
                        onWithdrawalClick = {
                            transactionType = "Withdrawal"
                            showTransactionSheet = true
                        }
                    )

                    // The "Show me the $$!" Button and AnimatedVisibility section are now REMOVED.

                    Spacer(Modifier.weight(1f)) // This pushes the theme toggle button to the bottom

                    Button( // Original theme toggle button at the bottom
                        onClick = { isDarkMode = !isDarkMode },
                        modifier = Modifier.padding(bottom = 16.dp),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 12.dp, // From your Turn 65 App.kt
                            pressedElevation = 0.dp,
                            disabledElevation = 4.dp
                        ),
                        colors = ButtonDefaults.buttonColors( // Ensure button colors are themed
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) {
                        Text(if (isDarkMode) "Light Mode" else "Dark Mode")
                    }
                }
            }
        }
    }

    if (showTransactionSheet) {
        TransactionBottomSheet(
            transactionType = transactionType,
            sheetState = sheetState,
            onDismiss = { showTransactionSheet = false },
            onConfirm = { amount ->
                println("$transactionType Confirmed: $$amount") // Placeholder action
                showTransactionSheet = false
            }
        )
    }
}