package presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface OnBoardingNavigation {

    @Serializable
    data object OnBoarding: OnBoardingNavigation
    @Serializable
    data object Register: OnBoardingNavigation
    @Serializable
    data object Login: OnBoardingNavigation
}