package files

import java.io.File

val MAIN_FOLDER = if (System.getProperty("os.name").contains("win", true)) {
    // windows
    File(File(System.getenv("APPDATA"), "Local"), "Stand Strategist")
} else {
    // macos, linux, etc.
    File(System.getProperty("user.home"), ".stand-strategist")
}

val MATCH_SCHEDULE_FILE = File(MAIN_FOLDER, "match_schedule.json")

val TIM_DATA_FILE = File(MAIN_FOLDER, "tim_data.csv")

val TEAM_DATA_FILE = File(MAIN_FOLDER, "team_data.csv")
