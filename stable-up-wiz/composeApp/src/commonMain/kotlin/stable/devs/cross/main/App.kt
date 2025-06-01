package stable.devs.cross.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import cafe.adriel.voyager.navigator.Navigator // Import Voyager Navigator
import org.jetbrains.compose.ui.tooling.preview.Preview
import stable.devs.cross.components.AtmosphericBackground
import stable.devs.cross.screens.HomeScreen
import kotlin.math.round
import kotlin.math.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun App() {
    var isDarkMode by remember { mutableStateOf(true) }
    var fakeBalance by remember { mutableStateOf(10000.24) }


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

    fun formatDoubleToCurrency(value: Double, currencySymbol: String = "$", decimalPlaces: Int = 2): String {
        val factor = 10.0.pow(decimalPlaces.toDouble())
        val roundedValue = round(value * factor) / factor
        val parts = roundedValue.toString().split('.')
        val integerPart = parts[0]
        val actualDecimalPart = if (parts.size > 1) parts[1] else ""
        val decimalPart = if (parts.size > 1) parts[1].padEnd(decimalPlaces, '0') else "".padEnd(decimalPlaces, '0')
        return "$integerPart.$decimalPart"
    }

    val formattedBalanceState: State<String> = remember {
        derivedStateOf {
            formatDoubleToCurrency(fakeBalance)
        }
    }
    println("formattedBalanceState.value for this recomposition = ${formattedBalanceState.value}")

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
                println("Navigator being called with formattedBalanceState.value = ${formattedBalanceState.value}")
                Navigator(
                    screen = HomeScreen(
                        isDarkMode = isDarkMode,
                        onThemeToggle = { isDarkMode = !isDarkMode },
                        primaryBrandColor = primaryBrand,
                        secondaryBrandColor = secondaryBrand,
                        balanceState = formattedBalanceState,
                        onDeposit = { amountString ->
                            amountString.toDoubleOrNull()?.let { amount ->
                                if (amount > 0.0) {
                                    println("Current fakeBalance before deposit: $fakeBalance")
                                    fakeBalance += amount
                                    println("New fakeBalance after deposit: $fakeBalance")
                                }
                            } ?: println("Invalid deposit amount: $amountString")
                        },
                        onWithdraw = { amountString ->
                            amountString.toDoubleOrNull()?.let { amount ->
                                if (amount > 0.0 && fakeBalance >= amount) {
                                    fakeBalance -= amount
                                } else if (fakeBalance < amount) {
                                    println("Insufficient funds for withdrawal.")
                                }
                            } ?: println("Invalid withdrawal amount: $amountString")
                        }
                        // Pass other necessary data
                    ),
                    key = formattedBalanceState.value
                )
            }
        }
    }
    // The commented-out TransactionBottomSheet from your original App.kt is removed.
    // This logic should be moved into the screen that needs it (e.g., HomeScreen or a dedicated transaction screen).
}