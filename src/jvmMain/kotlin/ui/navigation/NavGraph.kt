package ui.navigation

import ui.pages.AllNotesPage
import ui.pages.DataPage
import ui.pages.MatchSelectionPage
import ui.pages.NotesPage
import ui.pages.StartingPage

/**
 * The main navigation graph for all the pages in the app. If a new page is added to the app, it should be registered
 * here. Note that to access the [Destination] object for a given enum constant, you will need to access the
 * [destination] property.
 */
enum class NavGraph(val destination: Destination) {
    STARTING(
        Destination(
            content = { StartingPage() },
            name = "Match Info",
            back = { NOTES },
            next = { DATA },
            onBack = { if (match.toIntOrNull() != null && match.toInt() > 1) match = (match.toInt() - 1).toString() }
        )
    ),
    MATCH_SELECTION(
        Destination(
            content = { MatchSelectionPage() },
            name = "Match Selection",
            back = { STARTING }
        )
    ),
    DATA(
        Destination(
            content = { DataPage() },
            name = "Match Data",
            back = { STARTING },
            next = { NOTES }
        )
    ),
    NOTES(
        Destination(
            content = { NotesPage() },
            name = "Team Notes",
            back = { DATA },
            next = { STARTING },
            onNext = { if (match.toIntOrNull() != null && match.toInt() <= 200) match = (match.toInt() + 1).toString() }
        )
    ),
    ALL_NOTES(
        Destination(
            content = { AllNotesPage() },
            name = "All Team Notes",
            back = { NOTES }
        )
    )
}
