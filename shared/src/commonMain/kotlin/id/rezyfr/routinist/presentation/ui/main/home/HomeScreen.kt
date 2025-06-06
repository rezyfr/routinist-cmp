package id.rezyfr.routinist.presentation.ui.main.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import id.rezyfr.routinist.domain.model.HabitProgressModel
import id.rezyfr.routinist.domain.model.HabitSummaryModel
import id.rezyfr.routinist.presentation.component.core.DefaultScreenUI
import id.rezyfr.routinist.presentation.component.core.Spacer_12dp
import id.rezyfr.routinist.presentation.component.core.Spacer_16dp
import id.rezyfr.routinist.presentation.component.core.Spacer_4dp
import id.rezyfr.routinist.presentation.component.ui.CalendarCard
import id.rezyfr.routinist.presentation.component.ui.HabitProgressItem
import id.rezyfr.routinist.presentation.component.ui.TextViewAll
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.koinInject
import id.rezyfr.routinist.presentation.theme.Black40
import id.rezyfr.routinist.presentation.theme.Blue10
import id.rezyfr.routinist.presentation.theme.BorderColor
import id.rezyfr.routinist.presentation.theme.Primary40
import id.rezyfr.routinist.presentation.theme.primaryGradient
import id.rezyfr.routinist.presentation.util.getTodayNumber
import id.rezyfr.routinist.presentation.util.noDecimal
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.habits
import routinist.shared.generated.resources.home_slogan

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinInject(),
    refresh: Boolean,
    navigateToMilestone: (Int) -> Unit,
    showProgressSheet: (HabitProgressModel) -> Unit,
) {
    LaunchedEffect(refresh) {
        viewModel.onTriggerEvent(HomeEvent.Refresh)
    }

    LaunchedEffect(Unit) {
        viewModel.action.onEach { effect ->
            when (effect) {
                is HomeAction.Refresh -> {
                    viewModel.getTodayHabits()
                    viewModel.getHabitSummary()
                }

                is HomeAction.NavigateToMilestone -> {
                    navigateToMilestone.invoke(effect.milestone)
                }
            }
        }.collect {}
    }

    DefaultScreenUI(
        errors = viewModel.errors,
        progressBarState = viewModel.state.value.progressBarState
    ) {
        HomeContent(
            state = viewModel.state.value,
            events = viewModel::setEvent,
            showProgressSheet = showProgressSheet
        )
    }
}
@Composable
fun HomeContent(
    state: HomeState,
    events: (HomeEvent) -> Unit,
    showProgressSheet: (HabitProgressModel) -> Unit,
) {
    Column(Modifier.fillMaxSize()) {
        HomeHeaderSection()
        Spacer_16dp()
        HomeMainSection(
            summary = state.summary,
            today = state.today,
            calendars = state.calendars,
            updatingProgressId = state.updatingProgressId,
            events = events,
            showProgressSheet = {
                showProgressSheet.invoke(it)
            }
        )
    }
}
@Composable
fun HomeHeaderSection(modifier: Modifier = Modifier) {
    Column(modifier.background(Color.White)) {
        Row(
            Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                Text(
                    "Hi, John\uD83D\uDC4B ",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer_4dp()
                Text(
                    stringResource(Res.string.home_slogan),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Black40
                )
            }

            Box(
                Modifier.size(48.dp)
                    .background(color = Blue10, shape = CircleShape)
            ) {
                Text(
                    "😇",
                    style = MaterialTheme.typography.headlineMedium,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        Box(Modifier.fillMaxWidth(1f).height(1.dp).background(BorderColor))
    }
}
@Composable
fun HomeMainSection(
    summary: HabitSummaryModel,
    today: List<HabitProgressModel>,
    calendars: List<Pair<String, String>> = emptyList(),
    showProgressSheet: (HabitProgressModel) -> Unit,
    events: (HomeEvent) -> Unit,
    updatingProgressId: Long
) {
    LazyRow(
        Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        userScrollEnabled = false
    ) {
        items(calendars) {
            CalendarCard(
                date = it.second,
                day = it.first,
                isSelected = it.second == getTodayNumber()
            )
        }
    }
    Spacer_16dp()
    HomeSummary(summary)
    Spacer_16dp()
    TextViewAll(
        modifier = Modifier.fillMaxWidth().padding(horizontal = 24.dp),
        text = stringResource(Res.string.habits)
    )
    Spacer_4dp()
    LazyColumn(
        Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal =  24.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items(today, key = { it.id }) {
            HabitProgressItem(
                data = it,
                onClickFinish = {
                    events.invoke(HomeEvent.OnProgressFinished(it))
                },
                onClickAdd = {
                    showProgressSheet.invoke(it)
                },
                isLoading = updatingProgressId == it.id
            )
        }
    }
}
@Composable
fun HomeSummary(
    summary: HabitSummaryModel
) {
    Box(
        modifier = Modifier
            .padding(horizontal = 24.dp)
            .background(
                brush = Brush.verticalGradient(colors = primaryGradient),
                shape = RoundedCornerShape(16.dp)
            )
            .fillMaxWidth(),
    ) {
        Row(
            Modifier.padding(16.dp)
        ) {
            Box(Modifier.size(40.dp)) {
                CircularProgressIndicator(
                    gapSize = 0.dp,
                    progress = {
                        summary.completedHabits.toFloat() / summary.totalHabits.toFloat()
                    },
                    trackColor = Primary40,
                    color = Color.White,
                    strokeWidth = 2.dp
                )
                Text(
                    "%${summary.percentage.noDecimal()}",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
            Spacer_12dp()
            Column(Modifier.weight(1f)) {
                Text(
                    "Your daily goals ${
                        if (summary.percentage > 100) "done! \uD83D\uDE0A"
                        else if (summary.isAlmostDone) "almost done!\uD83D\uDD25"
                        else "not done yet."
                    }",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Text(
                    "${summary.completedHabits} / ${summary.totalHabits} completed",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
    }
}

