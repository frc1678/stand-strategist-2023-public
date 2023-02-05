package io.files

import org.jetbrains.kotlinx.dataframe.api.column

/**
 * All the columns in the [teamData].
 * The keys are accessors for the columns and the values are the default values for the columns.
 */
val teamDataCols: TeamDataCols = mapOf(
    team to 0,
    column<String>("Auto Strategies") to "",
    column<String>("Driving and Scoring Competence") to "",
    column<String>("Strengths/Weaknesses") to "",
    column<String>("Notes") to ""
)

/**
 * All the columns in the [timData].
 * The keys are accessors for the columns and the values are the default values for the columns.
 */
val timDataCols: TimDataCols = mapOf(
    match to 0,
    alliance to "blue",
    team to 0,
    column<Boolean?>("Played Defense") to false,
    column<Int?>("Defense Rating") to 0,
    column<Boolean?>("Drives over charging station?") to false,
    column<Boolean?>("Moves pieces between rows?") to false,
    column<String>("Notes") to ""
)
