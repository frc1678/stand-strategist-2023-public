package ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import files.readSettings

@Composable
fun LoadingScreen(onLoaded: () -> Unit) {
    LaunchedEffect(true) {
        readSettings()
        onLoaded()
    }
    Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
        Text("Loading...", textAlign = TextAlign.Center)
    }
}