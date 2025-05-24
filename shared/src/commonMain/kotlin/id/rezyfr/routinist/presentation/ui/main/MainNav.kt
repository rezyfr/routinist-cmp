package id.rezyfr.routinist.presentation.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import id.rezyfr.routinist.domain.model.HabitModel
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString
import id.rezyfr.routinist.presentation.navigation.AppNavigation
import id.rezyfr.routinist.presentation.ui.create.CreateHabitScreen
import id.rezyfr.routinist.presentation.ui.main.home.MilestoneScreen

@Composable
fun MainNav() {
    val navigator = rememberNavController()

    NavHost(
        startDestination = AppNavigation.Main,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<AppNavigation.Main> {
            MainScreen(
                navigateToCreateHabit = {
                    val jsonHabit = Json.encodeToString<HabitModel>(it)
                    navigator.navigate(AppNavigation.CreateHabit(jsonHabit))
                },
                navigateToMilestone = {
                    navigator.navigate(AppNavigation.Milestone(it))
                }
            )
        }
        composable<AppNavigation.CreateHabit> {
            val args = it.toRoute<AppNavigation.CreateHabit>()
            val habit = Json.decodeFromString<HabitModel>(args.habit)
            CreateHabitScreen(
                habit = habit
            )
        }
        composable<AppNavigation.Milestone> {
            val args = it.toRoute<AppNavigation.Milestone>()
            MilestoneScreen(
                onBackClick = { navigator.navigateUp() },
                milestone = args.milestone
            )
        }
    }
}