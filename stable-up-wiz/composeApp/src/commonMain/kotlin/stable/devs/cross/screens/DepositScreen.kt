package stable.devs.cross.screens
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
// Import a painter for the image. For now, a placeholder or you'll add an image to resources.
// import org.jetbrains.compose.resources.painterResource // If using composeResources
// import com.example.yourapp.R // For Android specific resources, if not using KMP resources

import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import stable.devs.cross.components.AtmosphericBackground

data class DepositScreen(
    val onConfirm: (String) -> Unit,
    val isDarkMode: Boolean, // Passed from App.kt via HomeScreen or directly
    val primaryBrandColor: Color, // Example theme color
    val secondaryBrandColor: Color // Example theme color
    // Add other necessary parameters, like the actual theme colors from App.kt
) : Screen { // Or data class DepositScreen(...) if it needs params

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        var amount by remember { mutableStateOf("") }

        val darkGrayButtonColor = Color(0xFF2C2C2E) // Example dark gray, adjust as needed
        val whiteContentColor = Color.White

        Box(modifier = Modifier.fillMaxSize()) {
            // Call AtmosphericBackground first, so it's in the back
            AtmosphericBackground(
                modifier = Modifier.fillMaxSize(),
                isDarkMode = isDarkMode,
                glowHintColor = secondaryBrandColor, // Or a specific color for this screen
                baseBackgroundColor = MaterialTheme.colorScheme.background, // Example base colors
                customLightHitRelX = 1.2f, // Example: Custom light position for SendScreen
                customLightHitRelY = 0.9f  // Example: Custom light position for SendScreen
                // Omit custom params to use defaults
            )
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text("Savings") }, // Changed title
                    navigationIcon = {
                        IconButton(onClick = { navigator.pop() }) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                        }
                    }
                )
            },
            containerColor = Color.Transparent
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(start = 24.dp, end = 24.dp, top = 0.dp, bottom = 16.dp), // Adjusted padding
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Spacer(Modifier.height(24.dp))
                // 1. Card Picture (Placeholder)
                Box(
                    modifier = Modifier
                        .fillMaxWidth(0.9f) // Adjust width as needed
                        .aspectRatio(1.0786f) // Typical card aspect ratio
                        .background(MaterialTheme.colorScheme.secondaryContainer, RoundedCornerShape(12.dp))
                        .padding(8.dp),
                    contentAlignment = Alignment.Center
                ) {
                    // Replace with actual Image composable when you have an image
                    // Image(painter = painterResource("drawable/your_card_image.png"), contentDescription = "Card")
                    Text("Card Image Placeholder", color = MaterialTheme.colorScheme.onSecondaryContainer)
                }

                Spacer(Modifier.height(24.dp))

                // 2. Descriptive Text 1
                Text(
                    text = "Deposit USDC into\nyour savings account",
                    fontSize = 30.sp,
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontSize = 28.sp,
                        lineHeight = 32.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.height(16.dp))

                // (Optional but recommended) Amount Input Field
                OutlinedTextField(
                    value = amount,
                    onValueChange = { amount = it.filter { char -> char.isDigit() || char == '.' } },
                    label = { Text("Enter Amount ($)") },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(12.dp),
                    colors = OutlinedTextFieldDefaults.colors( // Or TextFieldDefaults.colors for the filled version
                        // For Material 3 OutlinedTextField, you might use these:
                        unfocusedContainerColor = darkGrayButtonColor,
                        focusedContainerColor = darkGrayButtonColor,
                        disabledContainerColor = darkGrayButtonColor, // Optional: if you have a disabled state

                        // You might also want to adjust text, label, and cursor colors
                        // for better contrast against the new dark gray background.
                        // For example, if your default text color doesn't show up well:
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White.copy(alpha = 0.8f),
                        disabledTextColor = Color.Gray,
                        cursorColor = Color.White, // Or your primary brand color

                        // And label colors:
                        focusedLabelColor = Color.White.copy(alpha = 0.7f), // Or your primary brand color
                        unfocusedLabelColor = Color.White.copy(alpha = 0.5f),

                        // And border colors if needed, though the default might be fine or you might want them to match text
                        focusedBorderColor = MaterialTheme.colorScheme.primary, // Or white/light gray
                        unfocusedBorderColor = Color.White.copy(alpha = 0.3f) // Or a lighter gray
                    )
                )

                Spacer(Modifier.height(24.dp))

                // Buttons Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp) // Adds space between buttons
                ) {
                    // Cancel Button
                    Button(
                        onClick = { navigator.pop() },
                        modifier = Modifier
                            .weight(1f) // Each button takes half the width
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = darkGrayButtonColor,
                            contentColor = whiteContentColor
                        )
                    ) {
                        Text(
                            "Cancel",
                            fontSize = 16.sp
                        )
                    }

                    // Confirm Button
                    Button(
                        onClick = {
                            if (amount.isNotBlank()) {
                                onConfirm(amount) // <-- Call the lambda passed from HomeScreen
                            }
                        },
                        modifier = Modifier
                            .weight(1f) // Each button takes half the width
                            .height(48.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = darkGrayButtonColor,
                            contentColor = whiteContentColor
                        )
                    ) {
                        Text(
                            "Confirm",
                            fontSize = 16.sp
                        )
                    }
                }
                Spacer(Modifier.height(24.dp))
               // 3. Descriptive Text 2
                Text(
                    text = "Earn 7% APY on your balance. Interest is paid out monthly",
                    style = MaterialTheme.typography.bodyMedium.copy(fontSize = 16.sp),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )

                Spacer(Modifier.weight(4f)) // Pushes buttons to the bottom


            }}
        }
    }
}