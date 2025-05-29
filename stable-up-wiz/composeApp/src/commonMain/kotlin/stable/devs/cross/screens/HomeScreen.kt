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
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import stable.devs.cross.components.InfoCard
import stable.devs.cross.components.TransactionButtons
import stable.devs.cross.ui.TopSettingsMenu
import stable.devs.cross.screens.DepositScreen
import stable.devs.cross.screens.SendScreen
import stable.devs.cross.screens.ReceiveScreen




class HomeScreen(
    val isDarkMode: Boolean,
    val onThemeToggle: () -> Unit,
    val primaryBrandColor: androidx.compose.ui.graphics.Color,
    val secondaryBrandColor: androidx.compose.ui.graphics.Color,
    val balanceState: State<String>,          //
    val onDeposit: (String) -> Unit,     // Lambda to call for deposits
    val onWithdraw: (String) -> Unit     // Lambda to call for withdrawals
) : Screen {

    override val key: String = "HomeScreen-${balanceState.value}"

    init {
        println("HomeScreen instance CREATED (hashCode: ${this.hashCode()}, key: $key). Initial currentBalance from state.value = ${balanceState.value}")
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val currentBalanceValue by balanceState
        val screenInstanceHashCode = this.hashCode()

        SideEffect {
            println("---- HomeScreen Content executing (hashCode: $screenInstanceHashCode, key: $key). currentBalanceValue = $currentBalanceValue ----")
        }

        val navigator = LocalNavigator.currentOrThrow

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopSettingsMenu()

            println("HomeScreen (hashCode: $screenInstanceHashCode, key: $key): Passing to InfoCard, balanceToDisplay = $currentBalanceValue")
            InfoCard(
                modifier = Modifier.padding(top = 16.dp),
                cardBgPrimaryColor = primaryBrandColor,
                cardBgSecondaryColor = secondaryBrandColor,
                balanceToDisplay = currentBalanceValue
            )

            Spacer(Modifier.height(24.dp))

            TransactionButtons(
                onDepositClick = {
                    println("HomeScreen (hashCode: $screenInstanceHashCode, key: $key): Deposit button clicked.")
                    navigator.push(DepositScreen(onConfirm = { amountStr ->
                        println("HomeScreen (hashCode: $screenInstanceHashCode, key: $key): onConfirm from DepositScreen (amount: '$amountStr'). Calling onDeposit.")
                        onDeposit(amountStr) // This updates fakeBalance in App.kt
                        navigator.pop()      // This pops DepositScreen, returning to this HomeScreen
                        println("HomeScreen (hashCode: $screenInstanceHashCode, key: $key): Popped DepositScreen.")
                    }))
                },
                onSendClick = {
                    navigator.push(SendScreen)
//                    navigator.push(SendScreen(onConfirm = { amountStr ->
//                        onWithdraw(amountStr) // Call lambda from App.kt
//                        navigator.pop()
//                    }))
                    println("Send clicked on HomeScreen")
                },
                onReceiveClick = {
                    navigator.push(ReceiveScreen)
//                    navigator.push(ReceiveScreen(onConfirm = { amountStr ->
//                        onDeposit(amountStr) // Call lambda from App.kt
//                        navigator.pop()
//                    }))
                    println("Receive clicked on HomeScreen")
                }
            )

            Spacer(Modifier.weight(1f))

        }
    }
}