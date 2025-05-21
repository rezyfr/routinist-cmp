package presentation.ui.main

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.composables.core.BottomSheetState
import com.composables.core.SheetDetent.Companion.FullyExpanded
import com.composables.core.SheetDetent.Companion.Hidden
import com.composables.core.rememberBottomSheetState
import domain.model.HabitModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import presentation.component.core.DefaultBottomSheet
import presentation.navigation.BottomNavigation
import presentation.navigation.MainSheet
import presentation.theme.Black20
import presentation.theme.DefaultCardColorsTheme
import presentation.theme.DefaultNavigationBarItemTheme
import presentation.theme.primaryGradient
import presentation.ui.main.bottomsheet.CreateHabitSheet
import presentation.ui.main.bottomsheet.CreateProgressSheetContent
import presentation.ui.main.home.HomeScreen

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinInject(),
    navigateToCreateHabit: (HabitModel) -> Unit,
) {
    val navBottomBarController = rememberNavController()
    val coroutineScope = rememberCoroutineScope()
    val bottomSheetState = rememberBottomSheetState(initialDetent = Hidden)
    var currentBottomSheet: MainSheet? by remember { mutableStateOf(null) }
    var showBottomNav by remember(bottomSheetState) { mutableStateOf(true) }
    val sheetExpandProgress =
        if (bottomSheetState.isIdle && bottomSheetState.currentDetent == Hidden) {
            0f
        } else {
            bottomSheetState.progress
        }
    var navBarHeight by remember { mutableIntStateOf(0) }
    var homeRefresh by remember { mutableStateOf(false) }

    fun expandBottomSheet(expand: Boolean) {
        coroutineScope.launch {
            if (expand) {
                bottomSheetState.animateTo(FullyExpanded)
            } else {
                bottomSheetState.animateTo(Hidden)
            }
        }
    }

    LaunchedEffect(Unit) {
        mainViewModel.action.onEach { effect ->
            when (effect) {
                is MainAction.HideBottomSheet -> {
                    expandBottomSheet(false)
                    homeRefresh = !homeRefresh
                }

                is MainAction.NavigateToCreateHabit -> {
                    navigateToCreateHabit.invoke(effect.data)
                }

                is MainAction.ShowCreateHabitSheet -> {
                    currentBottomSheet = MainSheet.CreateHabit
                    expandBottomSheet(true)
                }
            }
        }.collect {}
    }

    Scaffold(
        bottomBar = {
            AnimatedVisibility(
                visible = showBottomNav,
                enter = slideInVertically { navBarHeight },
                exit = slideOutVertically { navBarHeight },
            ) {
                BottomNavigationBar(
                    modifier = Modifier
                        .onGloballyPositioned {
                            navBarHeight = it.size.height
                        }
                        .offset {
                            IntOffset(x = 0, navBarHeight.times(sheetExpandProgress).toInt())
                        },
                    showCreateHabitSheet = {
                        mainViewModel.setEvent(MainEvent.ShowCreateHabitSheet)
                    },
                    navController = navBottomBarController
                )
            }
        }
    ) { innerPadding ->
        Box(modifier = Modifier.padding(innerPadding)) {
            NavHost(
                startDestination = BottomNavigation.Home.route,
                navController = navBottomBarController,
                modifier = Modifier.fillMaxSize()
            ) {
                composable(route = BottomNavigation.Home.route) {
                    HomeScreen(
                        refresh = homeRefresh,
                        showProgressSheet = {
                            currentBottomSheet = MainSheet.CreateProgress
                            expandBottomSheet(true)
                            mainViewModel.setEvent(MainEvent.ShowProgressSheet(it))
                        }
                    )
                }
                composable(route = BottomNavigation.Explore.route) {
                }
                composable(route = BottomNavigation.Add.route) {
                }
                composable(route = BottomNavigation.Challenge.route) {
                }
                composable(route = BottomNavigation.Profile.route) {
                }
            }
        }
        if (sheetExpandProgress != 0f) {
            val interactionSource = remember { MutableInteractionSource() }
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f.times(sheetExpandProgress)))
                        .clickable(
                            interactionSource = interactionSource,
                            indication = null,
                            onClick = { expandBottomSheet(expand = false) },
                        ),
            )
        }

        MainSheet(
            sheetState = bottomSheetState,
            events = mainViewModel::setEvent,
            sheet = currentBottomSheet,
            onCloseClick = { expandBottomSheet(false) },
            state = mainViewModel.state.value
        )
    }
}

@Composable
fun MainSheet(
    sheetState: BottomSheetState,
    onCloseClick: () -> Unit = {},
    events: (MainEvent) -> Unit,
    sheet: MainSheet?,
    state: MainState
) {
    sheet?.let {
        DefaultBottomSheet(
            title = stringResource(sheet.title),
            onCloseClick = onCloseClick,
            sheetState = sheetState
        ) {
            when (it) {
                MainSheet.CreateHabit -> CreateHabitSheet(
                    state = state.createHabitSheetState,
                    events = events
                )
                MainSheet.CreateProgress -> CreateProgressSheetContent(
                    state = state.progressSheetState,
                    events = events,
                    data = state.progressSheetState.data
                )
            }
        }
    }
}

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    showCreateHabitSheet: () -> Unit,
    navController: NavController,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Card(
        colors = DefaultCardColorsTheme(),
        modifier = modifier.fillMaxWidth().padding(20.dp),
        elevation = CardDefaults.cardElevation(0.dp),
        shape = RoundedCornerShape(64.dp),
        border = BorderStroke(0.5.dp, Black20)
    ) {
        Box(Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier.fillMaxWidth()
                    .windowInsetsPadding(NavigationBarDefaults.windowInsets)
                    .selectableGroup()
            ) {
                val items = listOf(
                    BottomNavigation.Home,
                    BottomNavigation.Explore,
                    BottomNavigation.Add,
                    BottomNavigation.Challenge,
                    BottomNavigation.Profile,
                )
                items.forEach {
                    NavigationBarItem(
                        colors = DefaultNavigationBarItemTheme(),
                        selected = it.route == currentRoute,
                        icon = {
                            Image(
                                painterResource(if (it.route == currentRoute) it.selectedIcon else it.unselectedIcon),
                                it.route,
                                modifier = Modifier.size(32.dp)
                            )
                        },
                        onClick = {
                            if (currentRoute != it.route) {
                                navController.navigate(it.route) {
                                    navController.graph.startDestinationRoute?.let { route ->
                                        popUpTo(route) {
                                            saveState = true
                                        }
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        })
                }
            }

            Box(
                Modifier
                    .align(Alignment.Center)
                    .size(48.dp)
                    .clip(CircleShape)
                    .background(
                        brush = Brush.verticalGradient(colors = primaryGradient)
                    )
                    .clickable {
                        showCreateHabitSheet()
                    }
            ) {
                Icon(
                    Icons.Rounded.Add,
                    modifier = Modifier.size(36.dp).align(Alignment.Center),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.background
                )
            }
        }
    }
}