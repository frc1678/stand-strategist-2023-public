package io.files

import com.github.doyaaaaaken.kotlincsv.dsl.csvReader
import com.github.doyaaaaaken.kotlincsv.dsl.csvWriter
import io.DebouncedFileWriter
import io.TEAM_DATA_FILE
import io.TIM_DATA_FILE
import kotlinx.coroutines.runBlocking

/**
 * The main object storing all the Team-In-Match data.
 */
var timData: List<Map<String, String>>? = null
    set(value) {
        field = value
        runBlocking { timDataWriter.writeData(value!!) }
    }

/**
 * Reads the team data from the [TEAM_DATA_FILE] into [teamData].
 */
fun readTimData() {
    timData = if (TIM_DATA_FILE.exists()) {
        var data = csvReader().readAllWithHeader(TIM_DATA_FILE)
        timDataCols.forEach { (column, default) ->
            if (column.first !in data.first().keys) {
                data = data.map { it.toMutableMap().apply { set(column.first, default.toString()) } }
            }
        }
        data
    } else {
        listOf(timDataCols.mapKeys { (dataPoint, _) -> dataPoint.first }.mapValues { "" })
    }
}

/**
 * Adds rows for any missing teams in matches to the TIM data. The rows are appended with the default values.
 */
fun populateTimData() {
    // Loop over teams and matches in the match schedule
    for ((matchNum, matchObj) in matchSchedule!!) {
        for (teamObj in matchObj.teams) {
            // See if the corresponding row exists yet
            if (timData!!.firstOrNull {
                matchNum == it[match] && teamObj.number == it[team] && teamObj.color == it[alliance]
            } == null
            ) {
                // Get the default values for the row
                val entry = timDataCols
                    .mapKeys { (dataPoint, _) -> dataPoint.first }
                    .mapValues { (dataPoint, default) ->
                        when (dataPoint) {
                            match -> matchNum
                            team -> teamObj.number
                            alliance -> teamObj.color
                            else -> default.toString()
                        }
                    }
                // Add the new entry
                timData = timData?.toMutableList()?.apply { add(entry) }
            }
        }
    }
    // Remove empty rows
    teamData = teamData!!.filter { it[team] != "" }
}

/**
 * The [DebouncedFileWriter] for the [timData].
 * Data can be written to the file by calling [DebouncedFileWriter.writeData] on this instance.
 */
val timDataWriter = DebouncedFileWriter<List<Map<String, String>>>(TIM_DATA_FILE) { data ->
    csvWriter().writeAllAsString(listOf(data.first().keys.toList())) +
        csvWriter().writeAllAsString(data.map { it.values.toList() })
}
