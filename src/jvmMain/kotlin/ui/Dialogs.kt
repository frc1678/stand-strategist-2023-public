package ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.onClick
import androidx.compose.foundation.window.WindowDraggableArea
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.WindowScope

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun WindowScope.Dialog(
    allowCancel: Boolean = true,
    onCancel: () -> Unit = {},
    content: @Composable BoxScope.() -> Unit
) {
    Box(contentAlignment = Alignment.Center) {
        WindowDraggableArea {
            Box(
                modifier = Modifier.background(MaterialTheme.colors.surface.copy(alpha = 0.7f)).fillMaxSize().onClick {
                    if (allowCancel) onCancel()
                }
            )
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.background(MaterialTheme.colors.surface).onClick {}
        ) {
            Box(modifier = Modifier.padding(50.dp), content = content)
        }
    }
}
