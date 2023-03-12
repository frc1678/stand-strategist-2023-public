package ui

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEvent
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.isAltPressed
import androidx.compose.ui.input.key.isCtrlPressed
import androidx.compose.ui.input.key.isMetaPressed
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.type
import composeWindow
import io.files.editSettings
import io.saveDialog
import ui.theme.resetZoom
import ui.theme.zoomIn
import ui.theme.zoomOut

/**
 * Called when the user presses a key on the keyboard. Used to handle keyboard shortcuts.
 */
@OptIn(ExperimentalComposeUiApi::class)
val onKeyEvent: (KeyEvent) -> Boolean = {
    if ((it.isMetaPressed || it.isCtrlPressed) && it.type == KeyEventType.KeyDown) {
        when (it.key) {
            Key.S -> saveDialog(composeWindow!!)
            Key.Equals -> zoomIn()
            Key.Minus -> zoomOut()
            Key.Zero -> resetZoom()
            Key.DirectionRight -> editSettings {
                if (screen.next() != null) {
                    screen.apply { onNext() }
                    screen = screen.next()!!.destination
                }
            }

            Key.DirectionLeft -> editSettings {
                screen.apply { onBack() }
                screen = screen.back().destination
            }
        }
    }
    true
}

@OptIn(ExperimentalComposeUiApi::class)
fun getOnWidgetKeyEvent(focusManager: FocusManager): (KeyEvent) -> Boolean = {
    if (it.isAltPressed && it.type == KeyEventType.KeyDown) {
        when (it.key) {
            Key.DirectionLeft -> focusManager.moveFocus(FocusDirection.Left)
            Key.DirectionRight -> focusManager.moveFocus(FocusDirection.Right)
            Key.DirectionUp -> focusManager.moveFocus(FocusDirection.Up)
            Key.DirectionDown -> focusManager.moveFocus(FocusDirection.Down)
        }
    }
    false
}
