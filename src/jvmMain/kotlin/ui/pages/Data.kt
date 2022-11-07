package ui.pages

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.files.matchSchedule
import io.files.settings
import io.files.team
import io.files.timData
import io.files.timDataCols
import org.jetbrains.kotlinx.dataframe.api.first
import org.jetbrains.kotlinx.dataframe.api.firstOrNull
import org.jetbrains.kotlinx.dataframe.api.update
import org.jetbrains.kotlinx.dataframe.api.where
import org.jetbrains.kotlinx.dataframe.api.with
import ui.CheckBox
import ui.TextDataField

@Composable
fun DataPage(modifier: Modifier) {
    val teams = matchSchedule!![settings!!.match.toString()]?.teams?.filter {
        it.color == settings!!.alliance
    }
    Row(modifier = Modifier.fillMaxSize().padding(vertical = 50.dp))  {
        Column(modifier = Modifier.fillMaxWidth().weight(0.5f),
            horizontalAlignment = Alignment.CenterHorizontally) {
            Spacer(modifier = Modifier.weight(.5f))
            for (currentTeam in teams ?: emptyList()) {
                Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
                    Text("${currentTeam.number}")
                }
            }
        }
    }

    for (col in timDataCols.filter { it.name() != team.name()}) {
        Column(modifier = Modifier.fillMaxHeight().padding(horizontal = 5.dp)) {
            Box(modifier = Modifier.weight(0.5f), contentAlignment = Alignment.Center) {
                Text(col.name())
            }
            for (currentTeam in teams ?: emptyList()) {
                if (col.name() != "Played Defense" && col.name() != "Shooting Hub" ) {
                    TextDataField(
                        initialData = timData!!
                            .firstOrNull { it[team] == currentTeam.number }
                            ?.get(col)
                            ?.toString() ?: "",
                        onChange = { new ->
                            timData = timData!!.update(col)
                                .where { team() == currentTeam.number}
                                .with { new }
                        },
                        modifier = Modifier.weight(1f).wrapContentHeight()
                    )
                } else {
                    CheckBox(
                        initialData  = timData!!
                            .firstOrNull { it[team] == currentTeam.number }
                            ?.get(col) as Boolean,

                        onChange = { new ->
                            timData = timData!!.update(col)
                                .where { team() == currentTeam.number}
                                .with { new }
                        },
                        modifier = Modifier.weight(1f).wrapContentHeight()
                    )
                }
            }
        }
    }

//    Row(modifier = modifier.fillMaxSize(), horizontalArrangement = Arrangement.SpaceEvenly) {
//        var text by remember { mutableStateOf("") }
//        var text2 by remember { mutableStateOf("") }
//        var text3 by remember { mutableStateOf("") }
//        val checkedState = remember { mutableStateOf(true) }
//        val checkedState2 = remember { mutableStateOf(true) }
//        val checkedState3 = remember { mutableStateOf(true) }
//        val checkedState4 = remember { mutableStateOf(true) }
//        val checkedState5 = remember { mutableStateOf(true) }
//        val checkedState6 = remember { mutableStateOf(true) }
//        Column(
//            modifier = Modifier.padding(horizontal = 10.dp).fillMaxHeight().padding(top = 90.dp),
//            verticalArrangement = Arrangement.spacedBy(170.dp)
//        ) {
//            Text("")
//            for (i in 0 until 3) {
//                Text(
//                    matchSchedule!![settings!!.match.toString()]?.teams?.filter {
//                        it.color == settings!!.alliance
//                    }?.get(i)?.number?.toString() ?: "?"
//                )
//            }
//        }
//
//        Column(
//            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
//            verticalArrangement = Arrangement.spacedBy(125.dp)
//        ) {
//            Text("Defense")
//            Checkbox(
//                checked = checkedState.value,
//                modifier = Modifier.padding(16.dp),
//                onCheckedChange = { checkedState.value = it },
//            )
//            Checkbox(
//                checked = checkedState2.value,
//                modifier = Modifier.padding(16.dp),
//                onCheckedChange = { checkedState2.value = it },
//            )
//            Checkbox(
//                checked = checkedState3.value,
//                modifier = Modifier.padding(16.dp),
//                onCheckedChange = { checkedState3.value = it },
//            )
//        }
//        Column(
//            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
//            verticalArrangement = Arrangement.spacedBy(145.dp)
//        ) {
//            Text("Defense Rating")
//            TextField(
//                value = text,
//                onValueChange = { text = it },
//                modifier = Modifier.width(80.dp)
//            )
//            TextField(
//                value = text2,
//                onValueChange = { text2 = it },
//                modifier = Modifier.width(80.dp)
//            )
//            TextField(
//                value = text3,
//                onValueChange = { text3 = it },
//                modifier = Modifier.width(80.dp)
//            )
//        }
//
//        Column(
//            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
//            verticalArrangement = Arrangement.spacedBy(125.dp)
//        ) {
//            Text("Shooting Hub")
//            Checkbox(
//                checked = checkedState4.value,
//                modifier = Modifier.padding(16.dp),
//                onCheckedChange = { checkedState4.value = it },
//            )
//            Checkbox(
//                checked = checkedState5.value,
//                modifier = Modifier.padding(16.dp),
//                onCheckedChange = { checkedState5.value = it },
//            )
//            Checkbox(
//                checked = checkedState6.value,
//                modifier = Modifier.padding(16.dp),
//                onCheckedChange = { checkedState6.value = it },
//            )
//        }
//        Column(
//            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
//            verticalArrangement = Arrangement.spacedBy(145.dp)
//        ) {
//            Text("Time Left to Climb")
//            TextField(
//                value = text,
//                onValueChange = { text = it },
//                modifier = Modifier.width(80.dp)
//            )
//            TextField(
//                value = text2,
//                onValueChange = { text2 = it },
//                modifier = Modifier.width(80.dp)
//            )
//            TextField(
//                value = text3,
//                onValueChange = { text3 = it },
//                modifier = Modifier.width(80.dp)
//            )
//        }
//
//        Column(
//            modifier = Modifier.padding(horizontal = 10.dp).padding(top = 90.dp).fillMaxHeight(),
//            verticalArrangement = Arrangement.spacedBy(145.dp)
//        ) {
//            Text("Notes")
//            TextField(
//                value = text,
//                onValueChange = { text = it },
//                modifier = Modifier.width(330.dp)
//            )
//            TextField(
//                value = text2,
//                onValueChange = { text2 = it },
//                modifier = Modifier.width(330.dp)
//            )
//            TextField(
//                value = text3,
//                onValueChange = { text3 = it },
//                modifier = Modifier.width(330.dp)
//            )
//        }
//    }
}
