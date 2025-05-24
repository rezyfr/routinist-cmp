package id.rezyfr.routinist.presentation.navigation

import org.jetbrains.compose.resources.StringResource
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.create_habit
import routinist.shared.generated.resources.update_progress

sealed class MainSheet(
    val title: StringResource,
    val type: String
) {

    data object CreateProgress : MainSheet(
        title = Res.string.update_progress,
        type = "create_progress"
    )

    data object CreateHabit : MainSheet(
        title = Res.string.create_habit,
        type = "create_habit"
    )
}