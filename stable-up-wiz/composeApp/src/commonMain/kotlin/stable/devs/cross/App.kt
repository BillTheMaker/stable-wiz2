package stable.devs.cross

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
import stable.devs.cross.ui.TransactionBottomSheet
import stable.devs.cross.ui.TopSettingsMenu


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    var isDarkMode by remember { mutableStateOf(true) }

    val offBlack = Color(0xFF1A1F24)
    val offWhite = Color(0xFFF5FAFD)
    val primaryBrand = Color(0xFFc770c5)   // purple
    val secondaryBrand = Color(0xFF6248fb) // blue
    val cardGradientStart = Color(0xFF1E2A4D) // A dark navy/purple
    val cardGradientEnd = Color(0xFF2C3A65) // a dark blue
    val lightSkyBlue = Color(0xFFB0E0E6) // A nice Powdery Blue for the sky
    val moonGray = Color(0xFFB0B8C0)

    val lightColors = lightColorScheme(
        background = lightSkyBlue, // Main app background in light mode
        surface = offWhite,       // Surface color (e.g., for cards if they don't have their own bg)
        primary = primaryBrand,
        secondary = secondaryBrand,
        onPrimary = Color.White,    // Text on primaryBrand buttons
        onSecondary = Color.White,  // Text on secondaryBrand elements
        onBackground = Color.Black,    // Text on the light background
        onSurface = Color.Black        // Text on default surfaces in light mode
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

                val atmosphericShapeBaseColor = if (isDarkMode) {
                    MaterialTheme.colorScheme.surface // e.g., offBlack for the dark "globe"
                } else {
                    moonGray // The gray "moon" body for light mode
                }

                AtmosphericBackground(
                    modifier = Modifier.fillMaxSize(),
                    isDarkMode = isDarkMode,
                    glowHintColor = secondaryBrand,
                    baseBackgroundColor = atmosphericShapeBaseColor
                )
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .safeContentPadding(), // Original padding for the entire content column
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    TopSettingsMenu(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode }
                    )

                    InfoCard(
                        modifier = Modifier.padding(top = 16.dp),
                        cardBgPrimaryColor = primaryBrand, // Pass the new start color
                        cardBgSecondaryColor = secondaryBrand
                    )

                    Spacer(Modifier.height(24.dp)) // Space between InfoCard and new buttons

                    // New Deposit and Withdrawal Buttons
                    TransactionButtons(
                        onDepositClick = {
                            transactionType = "Deposit"
                            showTransactionSheet = true
                        },
                        onSendClick = {
                            // You'll need to define what "Send" does.
                            // For now, let's set it up for the bottom sheet like Deposit.
                            transactionType = "Send"
                            showTransactionSheet = true
                            // Or, println("Send clicked")
                        },
                        onReceiveClick = {
                            // You'll need to define what "Receive" does.
                            // For now, let's set it up for the bottom sheet like Deposit.
                            transactionType = "Receive"
                            showTransactionSheet = true
                            // Or, println("Receive clicked")
                        }
                    )

                    // The "Show me the $$!" Button and AnimatedVisibility section are now REMOVED.

                    Spacer(Modifier.weight(1f)) // This pushes the theme toggle button to the bottom


                }
            }
        }
    }

//    if (showTransactionSheet) {
//        TransactionBottomSheet(
//            transactionType = transactionType,
//            sheetState = sheetState,
//            onDismiss = { showTransactionSheet = false },
//            onConfirm = { amount ->
//                println("$transactionType Confirmed: $$amount") // Placeholder action
//                showTransactionSheet = false
//            }
//        )
//    }
}