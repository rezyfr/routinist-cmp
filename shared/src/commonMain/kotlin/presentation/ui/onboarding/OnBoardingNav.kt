package presentation.ui.onboarding

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.onEach
import org.koin.compose.koinInject
import presentation.navigation.OnBoardingNavigation
import presentation.ui.onboarding.login.LoginAction
import presentation.ui.onboarding.login.LoginScreen
import presentation.ui.onboarding.login.LoginViewModel
import presentation.ui.onboarding.register.RegisterScreen

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