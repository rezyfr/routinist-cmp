package id.rezyfr.routinist.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface AppNavigation {

    @Serializable
    data object OnBoarding : AppNavigation

    @Serializable
    data object Main : AppNavigation

    @Serializable
    data class CreateHabit(
        val habit: String
    ) : AppNavigation

    @Serializable
    data class Milestone(
        val milestone: Int
    ) : AppNavigation
}
