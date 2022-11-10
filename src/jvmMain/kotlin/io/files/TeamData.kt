package io.files

import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import kotlinx.coroutines.runBlocking
import org.jetbrains.kotlinx.dataframe.AnyFrame
import org.jetbrains.kotlinx.dataframe.DataFrame
import org.jetbrains.kotlinx.dataframe.api.column
import org.jetbrains.kotlinx.dataframe.api.columnOf
import org.jetbrains.kotlinx.dataframe.api.concat
import org.jetbrains.kotlinx.dataframe.api.dataFrameOf
import org.jetbrains.kotlinx.dataframe.api.firstOrNull
import org.jetbrains.kotlinx.dataframe.api.named
import org.jetbrains.kotlinx.dataframe.api.update
import org.jetbrains.kotlinx.dataframe.columns.ColumnAccessor
import org.jetbrains.kotlinx.dataframe.io.readDataFrame
import org.jetbrains.kotlinx.dataframe.io.toCsv

/**
 * [ColumnAccessor] for accessing the 'Team' column.
 * All other columns should be accessed through [teamDataCols] or [timDataCols].
 */
val team by column<Int>("Team")

/**
 * The [ColumnAccessor]s for all the columns in the [teamData].
 * The keys are the [ColumnAccessor]s and the values are the default values for the columns.
 */
val teamDataCols = mutableMapOf<ColumnAccessor<Any?>, Any?>(
    team to 0,
    column<String>("Driving and Scoring Competence") to "",
    column<String>("Strengths/Weaknesses") to "",
    column<String>("Defensive Method") to "",
    column<String>("Notes") to ""
)

/**
 * The main object storing all the team data.
 *
 * To update the data, use [DataFrame.update]:
 * ```kt
 * teamData = teamData!!.update(/* column */)
 *     .where { /* row condition */ }
 *      .with { /* new value */ }
 * ```
 */
var teamData: AnyFrame? = null
    set(value) {
        field = value
        runBlocking { teamDataWriter.writeData(value!!) }
    }

/**
 * Reads the team data from the [TEAM_DATA_FILE] into [teamData]. Creates a new [DataFrame] if the file doesn't exist.
 */
fun readTeamData() {
    teamData = if (TEAM_DATA_FILE.exists()) {
        TEAM_DATA_FILE.readDataFrame()
    } else {
        dataFrameOf(teamDataCols.keys.map { columnOf<Any?>(values = emptyArray()) named it.name() })
    }
}

/**
 * Adds rows for any missing teams to the team data. The rows are appended with the default values.
 */
fun populateTeamData() {
    // Get the full teams list using the match schedule
    val teams = mutableSetOf<Int>()
    matchSchedule!!.values.forEach { match -> match.teams.forEach { team -> teams.add(team.number) } }

    for (teamNum in teams) {
        // Check if there is no row for the team
        if (teamData!!.firstOrNull { team() == teamNum } == null) {
            // Get the default values from the columns
            val defaults = teamDataCols.map { (accessor, default) ->
                if (accessor == team) teamNum else default
            }.toTypedArray()
            // Create a new dataframe with one row of the default values
            val df = dataFrameOf(
                teamDataCols.keys.mapIndexed { i, it ->
                    columnOf(values = arrayOf(defaults[i])) named it.name()
                }
            )
            // Concat the new dataframe
            teamData = teamData!!.concat(df)
        }
    }
}

/**
 * The [DebouncedFileWriter] for the [teamData].
 * Data can be written to the file by calling [DebouncedFileWriter.writeData] on this instance.
 */
val teamDataWriter = DebouncedFileWriter<AnyFrame>(TEAM_DATA_FILE) { it.toCsv() }
