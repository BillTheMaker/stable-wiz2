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
import stable.devs.cross.ui.InfoCard

import stable_up_wiz.composeapp.generated.resources.Res
import stable_up_wiz.composeapp.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    // 1. State to hold whether dark mode is on or off
    var isDarkMode by remember { mutableStateOf(false) }

    // 2. Define your color schemes
    val lightColors = lightColorScheme(
        background = Color(0xFFFEFBF0),
        surface = Color(0xFFFFFDF5), // Card background can be surface
        primary = Color(0xFF00FF00), // Your green
        secondary = Color(0xFFFFD700), // Example Yellow - we can adjust
        onPrimary = Color.Black, // Text on green button
        onSecondary = Color.Black, // Text on yellow
        onBackground = Color(0xFF1C1B10), // Text on white background
        onSurface = Color(0xFF1C1B10) // Text on cards
    )
    val darkColors = darkColorScheme(
        background = Color(0xFF2A281E),
        surface = Color(0xFF2A281E), // Slightly off-black for cards in dark mode
        primary = Color(0xFF00FF00), // Your green (might need a slightly less intense version for dark mode, or keep it vibrant)
        secondary = Color(0xFFFFD700),
        onPrimary = Color.Black,
        onSecondary = Color.Black,
        onBackground = Color(0xFFE8E2D9),
        onSurface = Color(0xFFE8E2D9)
    )

    // 3. Determine which color scheme to use based on the state
    val currentColorScheme = if (isDarkMode) darkColors else lightColors


    MaterialTheme(colorScheme = currentColorScheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background // This will paint the theme's background
        ) {
            Column( // Main layout column for the screen
                modifier = Modifier
                    .fillMaxSize()
                    .safeContentPadding(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                // Place the InfoCard here
                InfoCard(modifier = Modifier.padding(top = 16.dp))
                Spacer(Modifier.height(8.dp)) // Space between InfoCard and button
                var showContent by remember { mutableStateOf(false) } // This state should be outside if it affects things outside this immediate button
                Button(onClick = { showContent = !showContent }) {
                    Text("Show me the $$!")
                }
                AnimatedVisibility(showContent) {
                    val greeting = remember { Greeting().greet() }
                    Column(
                        Modifier.fillMaxWidth().padding(top = 16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Image(painterResource(Res.drawable.compose_multiplatform), null)
                        Text("Compose: $greeting")
                    }
                }

                Spacer(Modifier.weight(1f)) // Pushes theme toggle to bottom

                Button(
                    onClick = { isDarkMode = !isDarkMode },
                    modifier = Modifier.padding(bottom = 16.dp)
                ) {
                    Text(if (isDarkMode) "Light Mode" else "Dark Mode")
                }
            }
        }
    }
}
