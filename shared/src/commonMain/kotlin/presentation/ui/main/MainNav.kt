package presentation.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import domain.model.HabitModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
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
                val jsonHabit = Json.encodeToString<HabitModel>(it)
                navigator.navigate(AppNavigation.CreateHabit(jsonHabit))
            }
        }
        composable<AppNavigation.CreateHabit> {
            val args = it.toRoute<AppNavigation.CreateHabit>()
            val habit = Json.decodeFromString<HabitModel>(args.habit)
            CreateHabitScreen(
                habit = habit
            )
        }
    }
}