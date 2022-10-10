package io

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import files.settings
import files.settingsWriter
import files.timData
import files.timDataWriter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun Observer() {
    LaunchedEffect(true) {
        for (writer in listOf(settingsWriter, timDataWriter)) launch(Dispatchers.IO) {
            writer.start()
        }
    }
    LaunchedEffect(settings) {
        if (settings != null) settingsWriter.writeData(settings!!)
    }
    LaunchedEffect(timData) {
        if (timData != null) timDataWriter.writeData(timData!!)
    }
}
