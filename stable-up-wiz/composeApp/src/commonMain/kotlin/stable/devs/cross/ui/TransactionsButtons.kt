package stable.devs.cross.ui // Or your chosen package for UI elements

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TransactionButtons(
    onDepositClick: () -> Unit,
    onWithdrawalClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(modifier = modifier.padding(top = 8.dp)) { // Added some top padding
        Button(
            onClick = onDepositClick,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 12.dp, // Using elevation from your App.kt
                pressedElevation = 0.dp,
                disabledElevation = 4.dp
            )
            // You might want to set colors here using MaterialTheme.colorScheme.primary,
            // or ensure your Button a MaterialTheme context that applies them.
        ) {
            Text("Deposit")
        }
        Spacer(Modifier.width(16.dp)) // Space between buttons
        Button(
            onClick = onWithdrawalClick,
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 12.dp,
                pressedElevation = 0.dp,
                disabledElevation = 4.dp
            )
        ) {
            Text("Withdrawal")
        }
    }
}