package ui.navigation

import ui.pages.DataPage
import ui.pages.NotesPage
import ui.pages.StartingPage

enum class NavGraph(val destination: Destination) {
    STARTING(
        Destination(
            content = { StartingPage() },
            name = "Match Info",
            back = { NOTES },
            next = { DATA },
            onBack = { if (match > 1) match-- }
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
            onNext = { if (match <= 200) match++ }
        )
    )
}
