package stable.devs.cross.screens // Or stable.devs.cross.screens if you created a subfolder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.ExperimentalMaterial3Api
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
import stable.devs.cross.components.TopSettingsMenu
import stable.devs.cross.screens.DepositScreen
import stable.devs.cross.screens.SendScreen
import stable.devs.cross.screens.ReceiveScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward // For example transaction
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card // For the new card
import androidx.compose.material3.CardDefaults // For card styling
import androidx.compose.material3.HorizontalDivider // For divider in card
import androidx.compose.material3.MaterialTheme // For TextStyles and Colors
import androidx.compose.material3.Text // For new Text elements
import androidx.compose.runtime.mutableStateOf // For showTransactionSheet state
import androidx.compose.runtime.remember // For showTransactionSheet and exampleTransaction
import androidx.compose.runtime.setValue // For showTransactionSheet state
import androidx.compose.material3.rememberModalBottomSheetState // For bottom sheet state
import androidx.compose.ui.graphics.Color // For primaryBrandColor, etc.
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Imports for the TransactionRow and related data (assuming they are in your 'components' package)
import stable.devs.cross.components.TransactionRow
import stable.devs.cross.components.TransactionUiItem
import stable.devs.cross.components.TransactionType
// Import for TransactionBottomSheet (assuming it's in 'ui' package as per your TopSettingsMenu import)
import stable.devs.cross.components.TransactionBottomSheet
import stable.devs.cross.components.buttonSpacing

// --- END OF NEW IMPORTS ---



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

        val navigator = LocalNavigator.currentOrThrow

        var showTransactionSheet by remember { mutableStateOf(false) }
        val bottomSheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)

        val recentTransactions = remember { // Renamed from exampleTransaction to recentTransactions
            listOf(
                TransactionUiItem(
                    id = "exTx1",
                    description = "Received from Friend",
                    amount = "+ $75.00",
                    date = "May 31, 2025",
                    type = TransactionType.RECEIVE,
                    icon = Icons.Filled.ArrowDownward
                ),
                TransactionUiItem(
                    id = "exTx2",
                    description = "Payment to Store",
                    amount = "- $30.25",
                    date = "May 30, 2025",
                    type = TransactionType.SEND, // Or WITHDRAWAL
                    icon = Icons.Filled.ArrowUpward // Example icon for outgoing
                )
            )
        }
        val recentActivityCardColor = Color(0xFF2C2C2E)

        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeContentPadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            TopSettingsMenu()

            InfoCard(
                modifier = Modifier.padding(top = 8.dp),
                cardBgPrimaryColor = primaryBrandColor,
                cardBgSecondaryColor = secondaryBrandColor,
                balanceToDisplay = currentBalanceValue
            )

            Spacer(Modifier.height(16.dp))

            TransactionButtons(
                onDepositClick = {
                    println("HomeScreen (hashCode: $screenInstanceHashCode, key: $key): Deposit button clicked.")
                    navigator.push(DepositScreen(onConfirm = { amountStr ->
                        onDeposit(amountStr) // This updates fakeBalance in App.kt
                        navigator.pop()      // This pops DepositScreen, returning to this HomeScreen
                        },
                        isDarkMode = isDarkMode, // Pass isDarkMode from HomeScreen
                        primaryBrandColor = primaryBrandColor, // Pass primaryBrandColor from HomeScreen
                        secondaryBrandColor = secondaryBrandColor
                    ))
                },
                onSendClick = {
                    navigator.push(SendScreen(onConfirm = { amountStr ->
                        onWithdraw(amountStr) // Call lambda from App.kt
                        navigator.pop()
                    },
                        isDarkMode = isDarkMode, // Pass isDarkMode from HomeScreen
                        primaryBrandColor = primaryBrandColor, // Pass primaryBrandColor from HomeScreen
                        secondaryBrandColor = secondaryBrandColor
                    ))

                },
                // In HomeScreen.kt, within TransactionButtons
                onReceiveClick = {
                    println("HomeScreen (hashCode: $screenInstanceHashCode, key: $key): Receive button clicked.")
                    navigator.push(ReceiveScreen(
                        onConfirm = { amountStr ->
                            // This onConfirm might now be vestigial for ReceiveScreen's current purpose
                            // or could be repurposed if receiving implies some other action.
                            println("HomeScreen (hashCode: $screenInstanceHashCode, key: $key): onConfirm from ReceiveScreen. Calling onDeposit.")
                            onDeposit(amountStr) // Or handle differently
                            navigator.pop()
                        },
                        isDarkMode = isDarkMode,
                        primaryBrandColor = primaryBrandColor,
                        secondaryBrandColor = secondaryBrandColor
                    ))
                }
            )

            Spacer(Modifier.height(8.dp))

            Text(
                "Recent Activity",
                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp)
            )
            Spacer(Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 4.dp),
                shape = RoundedCornerShape(12.dp),
                colors = CardDefaults.cardColors(containerColor = recentActivityCardColor),
                elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
            ) {
                Column(modifier = Modifier.padding(vertical = 8.dp)) {

                    Text(
                        "May 2025", // Example Month
                        style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.Medium),
                        modifier = Modifier.padding(horizontal = 16.dp, vertical = 16.dp)
                    )
                    if (recentTransactions.isEmpty()) {
                        Text(
                            "No recent transactions.",
                            modifier = Modifier.padding(16.dp).align(Alignment.CenterHorizontally)
                        )
                    } else {
                        recentTransactions.forEachIndexed { index, transaction ->
                            TransactionRow(item = transaction)
                            if (index < recentTransactions.lastIndex) { // Add divider if not the last item
                                HorizontalDivider(modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp))
                            }
                        }
                    }
                    // "More transactions..." clickable text
                    Text(
                        text = "More transactions...",
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .clickable { showTransactionSheet = true }
                            .padding(vertical = 8.dp)
                    )
                }
            }
            // --- END OF NEW "RECENT ACTIVITY" SECTION ---

            Spacer(Modifier.height(8.dp)) // This remains at the end
        }

        // --- NEW TRANSACTION BOTTOM SHEET CALL ---
        // This will show your existing TransactionBottomSheet UI when "More transactions..." is clicked.
        // You will later refactor TransactionBottomSheet to display a list of transactions.
        if (showTransactionSheet) {
            TransactionBottomSheet(
                transactionType = "All Transactions", // Placeholder title for now
                sheetState = bottomSheetState,
                onDismiss = { showTransactionSheet = false },
                onConfirm = { _ -> // The current onConfirm might not be relevant for just viewing a list
                    showTransactionSheet = false
                    println("Transaction Bottom Sheet (for 'All Transactions') actioned/closed.")
                }
            )
        }}}