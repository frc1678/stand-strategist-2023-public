package io

import java.io.File

/**
 * The main config folder used by the app.
 * Should be something like `C:/Users/username/AppData/Stand Strategist/` or `/home/username/.stand-strategist`.
 */
val MAIN_FOLDER = if (System.getProperty("os.name").contains("win", true)) {
    // windows
    File(File(System.getenv("APPDATA"), "Local"), "Stand Strategist")
} else {
    // macos, linux, etc.
    File(System.getProperty("user.home"), ".stand-strategist")
}

/**
 * The full path to the match schedule file.
 */
val MATCH_SCHEDULE_FILE = File(MAIN_FOLDER, "match_schedule.json")

/**
 * The full path to the Team-In-Match data file.
 */
val TIM_DATA_FILE = File(MAIN_FOLDER, "tim_data.csv")

/**
 * The full path to the team data file.
 */
val TEAM_DATA_FILE = File(MAIN_FOLDER, "team_data.csv")

/**
 * The full path to the settings file.
 */
val SETTINGS_FILE = File(MAIN_FOLDER, "settings.json")

/**
 * The full path to the folder containing the backups.
 */
val BACKUPS_FOLDER = File(MAIN_FOLDER, "backups")
