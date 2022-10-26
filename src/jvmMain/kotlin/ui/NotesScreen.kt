package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun NotesScreen() {
    Row(modifier = Modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
        Columns(0)



        Columns(5)


    }

}
