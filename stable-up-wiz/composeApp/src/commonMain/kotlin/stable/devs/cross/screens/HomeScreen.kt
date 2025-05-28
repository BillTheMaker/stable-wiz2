package stable.devs.cross.screens // Or stable.devs.cross.screens if you created a subfolder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import stable.devs.cross.components.InfoCard
import stable.devs.cross.components.TransactionButtons
import stable.devs.cross.ui.TopSettingsMenu

// You'll need to pass the necessary states or callbacks if they are managed in App.kt
// For now, let's assume isDarkMode and onThemeToggle are passed down or handled differently
// For simplicity in this step, I'm temporarily removing isDarkMode/onThemeToggle from TopSettingsMenu call.
// We'll reintegrate theme toggling properly once navigation is set up.

data class HomeScreen(
    val isDarkMode: Boolean,
    val onThemeToggle: () -> Unit,
    val primaryBrandColor: androidx.compose.ui.graphics.Color, // Example, pass necessary colors/data
    val secondaryBrandColor: androidx.compose.ui.graphics.Color
    // Add other parameters if App.kt was passing them down to its children
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow

        // The Column that was previously in App.kt
        // Note: The AtmosphericBackground and Surface with MaterialTheme will now be in App.kt,
        // wrapping the Navigator. So HomeScreen's content starts from what was inside the Surface.
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopSettingsMenu()

            InfoCard(
                modifier = Modifier.padding(top = 16.dp),
                cardBgPrimaryColor = primaryBrandColor,
                cardBgSecondaryColor = secondaryBrandColor
            )

            Spacer(Modifier.height(24.dp))

            TransactionButtons(
                onDepositClick = {
                    navigator.push(DepositScreen) // If DepositScreen is a Voyager Screen
                    println("Deposit clicked on HomeScreen")
                },
                onSendClick = {
                    navigator.push(SendScreen)
                    println("Send clicked on HomeScreen")
                },
                onReceiveClick = {
                    navigator.push(ReceiveScreen)
                    println("Receive clicked on HomeScreen")
                }
            )

            Spacer(Modifier.weight(1f))

        }
    }
}