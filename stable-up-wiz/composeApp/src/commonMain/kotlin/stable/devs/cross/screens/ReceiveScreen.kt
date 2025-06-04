package stable.devs.cross.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember // To remember the generated QR code
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import cafe.adriel.voyager.core.screen.Screen
import cafe.adriel.voyager.navigator.LocalNavigator
import cafe.adriel.voyager.navigator.currentOrThrow
import stable.devs.cross.utils.toComposeImageBitmap
import qrcode.QRCode
import stable.devs.cross.components.AtmosphericBackground

// Update ReceiveScreen to accept theme parameters
data class ReceiveScreen(
    val onConfirm: (String) -> Unit, // Kept for now to match HomeScreen's call; can be repurposed or removed if not used
    val isDarkMode: Boolean,
    val primaryBrandColor: Color,
    val secondaryBrandColor: Color
) : Screen {

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    override fun Content() {
        val navigator = LocalNavigator.currentOrThrow
        val qrCodeUrl = "https://StableUp.com"

        // Generate QR code bytes and then convert to ImageBitmap
        val qrCodeBitmap: ImageBitmap? = remember(qrCodeUrl) {
            try {
                val qrCode = QRCode(qrCodeUrl)
                // Generate as PNG bytes. You can customize colors, size, etc. here.
                val pngBytes: ByteArray? = qrCode.renderToBytes()
                pngBytes?.toComposeImageBitmap()
            } catch (e: Exception) {
                println("Error generating QR Code: ${e.message}")
                e.printStackTrace()
                null
            }
        }

        Box(modifier = Modifier.fillMaxSize()) {
            AtmosphericBackground(
                modifier = Modifier.fillMaxSize(),
                isDarkMode = isDarkMode,
                glowHintColor = secondaryBrandColor, // Or a specific color for this screen's mood
                baseBackgroundColor = MaterialTheme.colorScheme.background,
                // Optional: Add custom light positions for ReceiveScreen
                // customLightHitRelXFactor = 0.5f,
                // customLightHitRelYFactor = 0.8f
            )

            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Receive Funds") },
                        navigationIcon = {
                            IconButton(onClick = { navigator.pop() }) {
                                Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                            }
                        },
                        colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Transparent)
                    )
                },
                containerColor = Color.Transparent // Make Scaffold transparent
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding)
                        .padding(all = 24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        "Scan QR code to send funds", // Or "Your Address"
                        style = MaterialTheme.typography.titleMedium,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(bottom = 20.dp)
                    )

                    // Display the QR Code
                    if (qrCodeBitmap != null) {
                        Box( // Add a white background for scannability if QR itself doesn't have it
                            modifier = Modifier
                                .size(220.dp) // Adjust overall size for the QR code + padding
                                .background(Color.White, RoundedCornerShape(8.dp))
                                .padding(10.dp) // Padding between white box and QR code itself
                        ) {
                            Image(
                                bitmap = qrCodeBitmap,
                                contentDescription = "QR Code for $qrCodeUrl",
                                modifier = Modifier.fillMaxSize(),
                                contentScale = ContentScale.Fit
                            )
                        }
                    } else {
                        // Placeholder if QR generation failed or not implemented for platform
                        Box(
                            modifier = Modifier
                                .size(220.dp)
                                .background(MaterialTheme.colorScheme.surfaceVariant, RoundedCornerShape(8.dp))
                                .padding(10.dp),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                "QR Code for:\n$qrCodeUrl\n(Preview not available)",
                                textAlign = TextAlign.Center,
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }

                    Spacer(Modifier.height(20.dp))

                    Text(
                        text = qrCodeUrl,
                        style = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                        textAlign = TextAlign.Center
                    )
                    Text(
                        text = "(Scan with your camera or QR app)",
                        style = MaterialTheme.typography.bodySmall,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }
        }
    }
}