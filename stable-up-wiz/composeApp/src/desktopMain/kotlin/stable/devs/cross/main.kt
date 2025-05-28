package stable.devs.cross

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import stable.devs.cross.main.App

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "stable-up-wiz",
    ) {
        App()
    }
}