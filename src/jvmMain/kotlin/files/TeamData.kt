package files

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.io.readDataFrame
import org.jetbrains.kotlinx.dataframe.io.toCsv
import util.emptyCol

/**
 * The main object storing all the team data.
 *
 * Don't directly make edits to this object, use [editTeamData].
 */
var teamData: AnyFrame? by mutableStateOf(null)

fun readTeamData() {
    teamData = if (TEAM_DATA_FILE.exists()) {
        TEAM_DATA_FILE.readDataFrame()
    } else {
        dataFrameOf(
            emptyCol<String>("competence"),
            emptyCol<String>("strengthsWeaknesses"),
            emptyCol<String>("defensiveMethod"),
            emptyCol<String>("notes")
        )
    }
}

fun editTeamData(edit: AnyFrame.() -> Unit) {
    teamData = dataFrameOf(teamData!!.columns()).apply(edit)
}

val teamDataWriter = DebouncedFileWriter<AnyFrame>(TEAM_DATA_FILE) { it.toCsv() }
