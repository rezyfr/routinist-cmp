package presentation.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import presentation.navigation.AppNavigation
import presentation.ui.habit.create.CreateHabitScreen

@Composable
fun MainNav(
) {
    val navigator = rememberNavController()

    NavHost(
        startDestination = AppNavigation.Main,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<AppNavigation.Main> {
            MainScreen {
                navigator.navigate(AppNavigation.CreateHabit)
            }
        }
        composable<AppNavigation.CreateHabit> {
            CreateHabitScreen()
        }
    }
}