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
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarDefaults
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
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
import com.composables.core.BottomSheet
import com.composables.core.BottomSheetState
import com.composables.core.SheetDetent.Companion.FullyExpanded
import com.composables.core.SheetDetent.Companion.Hidden
import com.composables.core.rememberBottomSheetState
import domain.model.HabitProgressModel
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import presentation.component.core.ButtonSize
import presentation.component.core.DefaultButton
import presentation.component.core.DefaultTextField
import presentation.component.core.IconButton
import presentation.component.core.Spacer_12dp
import presentation.component.core.Spacer_16dp
import presentation.component.core.Spacer_32dp
import presentation.navigation.BottomNavigation
import presentation.theme.Black10
import presentation.theme.Black20
import presentation.theme.Black40
import presentation.theme.DefaultCardColorsTheme
import presentation.theme.DefaultNavigationBarItemTheme
import presentation.theme.primaryGradient
import presentation.ui.main.home.HomeScreen
import presentation.util.noDecimal
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.progress
import routinist.shared.generated.resources.progress_hint
import routinist.shared.generated.resources.update_progress

@Composable
fun MainScreen(
    mainViewModel: MainViewModel = koinInject(),
    navigateToCreateHabit: () -> Unit,
) {
    val navBottomBarController = rememberNavController()
    val bottomSheetState = rememberBottomSheetState(
        initialDetent = Hidden,
    )
    var showBottomNav by remember(bottomSheetState) { mutableStateOf(true) }
    val coroutineScope = rememberCoroutineScope()
    var navBarHeight by remember { mutableIntStateOf(0) }
    val sheetExpandProgress =
        if (bottomSheetState.isIdle && bottomSheetState.currentDetent == Hidden) {
            0f
        } else {
            bottomSheetState.progress
        }
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

    LaunchedEffect(mainViewModel) {
        mainViewModel.action.onEach { effect ->
            when (effect) {
                MainAction.HideBottomSheet -> {
                    expandBottomSheet(false)
                    homeRefresh = !homeRefresh
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
                    modifier = Modifier.onGloballyPositioned {
                        navBarHeight = it.size.height
                    }.offset { IntOffset(x = 0, navBarHeight.times(sheetExpandProgress).toInt()) },
                    navigateToCreateHabit = navigateToCreateHabit,
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
                            bottomSheetState.targetDetent = FullyExpanded
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
            Box(
                modifier =
                    Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.5f.times(sheetExpandProgress)))
                        .clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null,
                            onClick = { expandBottomSheet(expand = false) },
                        ),
            )
        }

        ProgressBottomSheet(
            sheetState = bottomSheetState,
            state = mainViewModel.state.value.progressSheetState,
            onCloseClick = { bottomSheetState.targetDetent = Hidden },
            events = mainViewModel::setEvent,
            data = mainViewModel.state.value.progressSheetState.data
        )
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProgressBottomSheet(
    sheetState: BottomSheetState,
    data: HabitProgressModel?,
    onCloseClick: () -> Unit = {},
    events: (MainEvent) -> Unit = {},
    state: MainState.ProgressSheetState
) {
    if (data == null) return
    BottomSheet(
        state = sheetState,
        modifier = Modifier
            .fillMaxWidth().clip(RoundedCornerShape(24.dp)).background(Color.White),
    ) {
        Column(Modifier.padding(24.dp)) {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Text(
                        stringResource(Res.string.update_progress),
                        style = MaterialTheme.typography.headlineMedium
                    )
                },
                actions = {
                    IconButton(
                        imageVector = Icons.Default.Close,
                        onClick = {
                            onCloseClick.invoke()
                        },
                    )
                },
            )
            Spacer_32dp()
            Row(
                modifier = Modifier.fillMaxWidth(),
            ) {
                Box(Modifier.size(36.dp), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(
                        gapSize = 0.dp,
                        progress = {
                            data.progress / data.goal
                        },
                        trackColor = Black10,
                        color = MaterialTheme.colorScheme.primary,
                        strokeWidth = 2.dp
                    )
                    Text(data.icon, style = MaterialTheme.typography.bodyMedium)
                }
                Spacer_12dp()
                Column(Modifier.weight(1f)) {
                    Text(
                        text = data.name,
                        style = MaterialTheme.typography.bodyLarge
                    )
                    Text(
                        text = "${data.progress.noDecimal()}/${data.goal.noDecimal()} ${data.unit}",
                        style = MaterialTheme.typography.labelMedium,
                        color = Black40
                    )
                }
            }
            Spacer_16dp()
            DefaultTextField(
                modifier = Modifier.fillMaxWidth(),
                label = stringResource(Res.string.progress),
                hint = stringResource(Res.string.progress_hint),
                value = state.progress,
                suffix = data.unit,
                onValueChange = {
                    events(MainEvent.OnProgressChange(it.toIntOrNull() ?: 0))
                }
            )
            Spacer_16dp()
            DefaultButton(
                progressBarState = state.progressBarState,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.update_progress),
                size = ButtonSize.Large,
                onClick = {
                    events(MainEvent.UpdateProgress)
                }
            )
            Spacer_16dp()
        }
    }
}
@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    navigateToCreateHabit: () -> Unit,
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
                        navigateToCreateHabit.invoke()
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