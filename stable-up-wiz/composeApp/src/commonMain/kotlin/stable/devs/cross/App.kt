package stable.devs.cross

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import stable.devs.cross.ui.AuroraBackground

import stable_up_wiz.composeapp.generated.resources.Res
import stable_up_wiz.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    // 1. State to hold whether dark mode is on or off
    var isDarkMode by remember { mutableStateOf(false) }

    // 2. Define your color schemes
    val lightColors = lightColorScheme(
        background = Color.White
        // Optionally customize your light theme colors here
        // primary = ..., surface = ..., background = Color.White, ...
    )
    val darkColors = darkColorScheme(
        background = Color.Black
        // Optionally customize your dark theme colors here
        // primary = ..., surface = ..., background = Color.Black, ...
    )

    // 3. Determine which color scheme to use based on the state
    val currentColorScheme = if (isDarkMode) darkColors else lightColors


    MaterialTheme(colorScheme = currentColorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // This will paint the theme's background
        ) {
        Box(modifier = Modifier.fillMaxSize()) {
            AuroraBackground()

            var showContent by remember { mutableStateOf(false) }
            Column(
                modifier = Modifier
                    .safeContentPadding()
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                var showContent by remember { mutableStateOf(false) }
                Button(onClick = { showContent = !showContent }) {
                    Text("Click me!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        Modifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }

                Spacer(Modifier.weight(1f))

                Button(
                    onClick = { isDarkMode = !isDarkMode },
                    modifier = Modifier.padding(bottom = 16.dp) // Add some padding from the bottom
                ) {
                    Text(if (isDarkMode) "Light Mode" else "Dark Mode")
                }
            }
        }
        }
    }
}