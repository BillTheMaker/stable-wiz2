package stable.devs.cross.ui // Or your chosen package

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionBottomSheet(
    transactionType: String,
    sheetState: SheetState, // From androidx.compose.material3.SheetState
    onDismiss: () -> Unit,
    onConfirm: (amount: String) -> Unit
) {
    var amount by remember { mutableStateOf("") }
    var showOptionsMenu by remember { mutableStateOf(false) }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        // Consider customizing containerColor based on the theme if needed
        // containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(3.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp) // Adjusted padding
                .fillMaxWidth()
                .navigationBarsPadding(), // Good for handling system navigation overlaps
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = transactionType,
                    style = MaterialTheme.typography.headlineSmall,
                    modifier = Modifier.padding(bottom = 16.dp)
                )
                Box {
                    IconButton(onClick = { showOptionsMenu = true }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "More options")
                    }
                    DropdownMenu(
                        expanded = showOptionsMenu,
                        onDismissRequest = { showOptionsMenu = false }
                    ) {
                        DropdownMenuItem(text = { Text("Mastercard ...6837") }, onClick = { /* TODO */ showOptionsMenu = false })
                        DropdownMenuItem(text = { Text("Visa ... 2347") }, onClick = { /* TODO */ showOptionsMenu = false })
                        // Add more relevant options here
                    }
                }
            }

            TextField(
                value = amount,
                onValueChange = { amount = it.filter { char -> char.isDigit() || char == '.' } }, // Allow digits and one decimal
                label = { Text("Enter Amount ($)") },
                singleLine = true,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp)
            )

            Button(
                onClick = { onConfirm(amount) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(if (transactionType == "Deposit") "Confirm Deposit" else "Confirm Withdrawal")
            }
            Spacer(Modifier.height(8.dp))
            TextButton( // Using TextButton for Cancel for less visual weight
                onClick = onDismiss,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cancel")
            }
        }
    }
}