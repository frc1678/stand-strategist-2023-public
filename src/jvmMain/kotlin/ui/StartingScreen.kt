package ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.TextStyle

@Composable
fun StartingScreen(modifier: Modifier) {

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()
    ) {
        var text by remember { mutableStateOf("Hello") }
        var text2 by remember { mutableStateOf("Hello") }
        TextField(
            value = text,
            onValueChange = { text = it },
            label = { Text("Input Name:") },
            textStyle = (TextStyle(fontSize = 100.sp)),
            modifier = Modifier.padding(50.dp)
        )

        var matchNum = 1
        TextField(
            value = text2,
            onValueChange = { text2 = it },
            label = { Text("Match Number: ") },
            textStyle = (TextStyle(fontSize = 100.sp)),
            modifier = Modifier.padding(50.dp)
        )

        Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {
            Text("1", fontSize = 100.sp)
            Text("2", fontSize = 100.sp)
            Text("3", fontSize = 100.sp)
        }
    }

}


