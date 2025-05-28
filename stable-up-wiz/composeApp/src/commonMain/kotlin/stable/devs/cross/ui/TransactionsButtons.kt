package stable.devs.cross.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDownward // For Receive
import androidx.compose.material.icons.automirrored.filled.Send // For Send (AutoMirrored)
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val DarkGrayButtonBackground = Color(0xFF2C2C2E) // A common dark gray, adjust as needed
private val WhiteIconColor = Color.White

@Composable
fun TransactionButtons(
    onDepositClick: () -> Unit,
    onSendClick: () -> Unit,
    onReceiveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp), // Added some vertical padding for the section
        horizontalArrangement = Arrangement.SpaceEvenly, // Distribute buttons evenly
        verticalAlignment = Alignment.Top // Align items to the top if labels have different heights
    ) {
        ActionButton(
            icon = Icons.Filled.Add,
            label = "Deposit",
            onClick = onDepositClick
        )
        ActionButton(
            icon = Icons.AutoMirrored.Filled.Send,
            label = "Send",
            onClick = onSendClick
        )
        ActionButton(
            icon = Icons.Filled.ArrowDownward, // Example icon for Receive
            label = "Receive",
            onClick = onReceiveClick
        )
    }
}

@Composable
private fun ActionButton(
    icon: ImageVector,
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Button(
            onClick = onClick,
            shape = CircleShape,
            modifier = Modifier.size(56.dp), // Standard FAB size, adjust as needed
            colors = ButtonDefaults.buttonColors(
                containerColor = DarkGrayButtonBackground,
                contentColor = WhiteIconColor
            ),
            elevation = ButtonDefaults.buttonElevation(
                defaultElevation = 16.dp,
                pressedElevation = 0.dp,
                hoveredElevation = 8.dp,
                focusedElevation = 8.dp
            ),
            contentPadding = PaddingValues(0.dp) // Remove default padding to center icon better
        ) {
            Icon(
                imageVector = icon,
                contentDescription = label,
                modifier = Modifier.size(24.dp) // Adjust icon size as needed
            )
        }
        Spacer(Modifier.height(8.dp)) // Space between button and label
        Text(
            text = label,
            style = MaterialTheme.typography.bodySmall, // Or another style like caption
            fontSize = 12.sp, // Explicit font size for label
            color = MaterialTheme.colorScheme.onBackground // Or a specific color for labels
        )
    }
}