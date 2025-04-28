package presentation.ui.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import presentation.navigation.OnBoardingNavigation

@Composable
fun OnBoardingNav(
    navigateToMain: () -> Unit
) {
    val navigator = rememberNavController()
    NavHost(
        startDestination = OnBoardingNavigation.OnBoarding,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<OnBoardingNavigation.OnBoarding> {
            OnBoardingScreen(
                navigateToRegister = {
                    navigator.navigate(OnBoardingNavigation.Register)
                }
            )
        }
    }
}