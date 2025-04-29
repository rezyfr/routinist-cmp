package presentation.ui.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import presentation.navigation.OnBoardingNavigation
import presentation.ui.onboarding.login.LoginScreen

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
                navigateToLogin = {
                    navigator.navigate(OnBoardingNavigation.Login)
                }
            )
        }
        composable<OnBoardingNavigation.Login> {
            LoginScreen(
                navigateToRegister = {
                    navigator.navigate(OnBoardingNavigation.Register)
                },
                onBackClick = {
                    navigator.navigateUp()
                }
            )
        }
    }
}