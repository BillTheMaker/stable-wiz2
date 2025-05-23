package stable.devs.cross

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "stable-up-wiz",
    ) {
        App()
    }
}