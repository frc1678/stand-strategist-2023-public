package io.files

const val team = "Team"
const val match = "Match Number"
const val alliance = "Alliance"

val teamDataCols = mapOf<Pair<String, DataType>, Any>(
    team(DataType.Str) to "0",
    "Auto Strategies"(DataType.Str) to "",
    "Driving and Scoring Competence"(DataType.Str) to "",
    "Strengths/Weaknesses"(DataType.Str) to "",
    "Notes"(DataType.Str) to ""
)

val timDataCols = mapOf<Pair<String, DataType>, Any>(
    match(DataType.Num) to "",
    alliance(DataType.Str) to "blue",
    team(DataType.Str) to "0",
    "Played Defense"(DataType.Bool) to false,
    "Defense Rating"(DataType.Num) to 0,
    "Drives over charging station?"(DataType.Bool) to false,
    "Moves pieces between rows?"(DataType.Bool) to false,
    "Notes"(DataType.Str) to ""
)

sealed interface DataType {
    object Str : DataType
    object Num : DataType
    object Bool : DataType
}

operator fun String.invoke(dataType: DataType) = this to dataType
