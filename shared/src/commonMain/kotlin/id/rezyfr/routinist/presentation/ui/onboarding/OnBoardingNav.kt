package id.rezyfr.routinist.presentation.ui.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import id.rezyfr.routinist.presentation.navigation.OnBoardingNavigation
import id.rezyfr.routinist.presentation.ui.onboarding.login.LoginScreen
import id.rezyfr.routinist.presentation.ui.onboarding.register.RegisterScreen

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
                navigateToMain = { navigateToMain() },
                navigateToLogin = {
                    navigator.navigate(OnBoardingNavigation.Login)
                }
            )
        }
        composable<OnBoardingNavigation.Login> {
            LoginScreen(
                navigateToMain = { navigateToMain() },
                navigateToRegister = { navigator.navigate(OnBoardingNavigation.Register) },
                onBackClick = { navigator.navigateUp() }
            )
        }
        composable<OnBoardingNavigation.Register> {
            RegisterScreen(
                onBackClick = {
                    navigator.navigateUp()
                }
            )
        }
    }
}