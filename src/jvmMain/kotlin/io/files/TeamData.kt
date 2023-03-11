package io.files

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import kotlinx.coroutines.runBlocking

/**
 * The main object storing all the team data.
 */
var teamData: List<Map<String, String>>? = null
    set(value) {
        field = value
        runBlocking { teamDataWriter.writeData(value!!) }
    }

/**
 * Reads the team data from the [TEAM_DATA_FILE] into [teamData].
 */
fun readTeamData() {
    teamData = if (TEAM_DATA_FILE.exists()) {
        var data = csvReader().readAllWithHeader(TEAM_DATA_FILE)
        teamDataCols.forEach { (column, default) ->
            if (column.first !in data.first().keys) {
                data = data.map { it.toMutableMap().apply { set(column.first, default.toString()) } }
            }
        }
        data
    } else {
        listOf(teamDataCols.mapKeys { (dataPoint, _) -> dataPoint.first }.mapValues { "" })
    }
}

/**
 * Adds rows for any missing teams to the team data. The rows are appended with the default values.
 */
fun populateTeamData() {
    // Get the full teams list using the match schedule
    val teams = mutableSetOf<String>()
    matchSchedule!!.values.forEach { match -> match.teams.forEach { team -> teams.add(team.number) } }

    for (teamNum in teams) {
        // Check if there is no row for the team
        if (teamData!!.firstOrNull { it[team] == teamNum } == null) {
            // Get the default values from the columns
            val entry = teamDataCols
                .mapKeys { (dataPoint, _) -> dataPoint.first }
                .mapValues { (dataPoint, default) -> if (dataPoint == team) teamNum else default.toString() }
            // Add the new entry
            teamData = teamData!!.toMutableList().apply { add(entry) }
        }
    }
    // Remove empty rows
    teamData = teamData!!.filter { it[team] != "" }
}

/**
 * The [DebouncedFileWriter] for the [teamData].
 * Data can be written to the file by calling [DebouncedFileWriter.writeData] on this instance.
 */
val teamDataWriter = DebouncedFileWriter<List<Map<String, String>>>(TEAM_DATA_FILE) { data ->
    csvWriter().writeAllAsString(listOf(data.first().keys.toList())) +
        csvWriter().writeAllAsString(data.map { it.values.toList() })
}
