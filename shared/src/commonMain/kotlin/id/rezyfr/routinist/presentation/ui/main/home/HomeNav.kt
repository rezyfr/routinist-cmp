package id.rezyfr.routinist.presentation.ui.main.home

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import id.rezyfr.routinist.domain.model.HabitProgressModel
import id.rezyfr.routinist.presentation.navigation.AppNavigation
import id.rezyfr.routinist.presentation.navigation.HomeNavigation

@Composable
fun HomeNav(
    refresh: Boolean,
    showProgressSheet: (HabitProgressModel) -> Unit,
    onNavigating: () -> Unit = {},
    onBack: () -> Unit = {}
) {
    val navigator = rememberNavController()
    NavHost(
        startDestination = HomeNavigation.Home,
        navController = navigator,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<HomeNavigation.Home> {
            HomeScreen(
                refresh = refresh,
                showProgressSheet = showProgressSheet,
                navigateToMilestone = {
                    navigator.navigate(HomeNavigation.Milestone(it))
                    onNavigating.invoke()
                }
            )
        }
        composable<HomeNavigation.Milestone> {
            val args = it.toRoute<AppNavigation.Milestone>()
            MilestoneScreen(
                onBackClick = {
                    navigator.navigateUp()
                    onBack.invoke()
                },
                milestone = args.milestone
            )
        }
    }
}