package presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RegisterNavigation {
    @Serializable
    data object Info : RegisterNavigation
    @Serializable
    data object Gender : RegisterNavigation
    @Serializable
    data object Habit : RegisterNavigation
}