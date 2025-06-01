package id.rezyfr.routinist.presentation.ui.main.activity

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.model.ActivitySummaryModel
import id.rezyfr.routinist.domain.model.UserHabitModel
import id.rezyfr.routinist.presentation.component.core.DefaultScreenUI
import id.rezyfr.routinist.presentation.component.core.IconButton
import id.rezyfr.routinist.presentation.component.core.Spacer_12dp
import id.rezyfr.routinist.presentation.component.core.Spacer_8dp
import id.rezyfr.routinist.presentation.component.ui.TextLabelVertical
import id.rezyfr.routinist.presentation.theme.AppTheme
import id.rezyfr.routinist.presentation.theme.Black40
import id.rezyfr.routinist.presentation.theme.Black60
import id.rezyfr.routinist.presentation.theme.BorderColor
import id.rezyfr.routinist.presentation.theme.DarkBlue10
import id.rezyfr.routinist.presentation.theme.Green100
import id.rezyfr.routinist.presentation.theme.Red100
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.activity
import routinist.shared.generated.resources.activity_completed
import routinist.shared.generated.resources.activity_failed
import routinist.shared.generated.resources.activity_points_earned
import routinist.shared.generated.resources.activity_success_rate
import routinist.shared.generated.resources.date
import routinist.shared.generated.resources.summary

@Composable
fun ActivityScreen(
    viewModel: ActivityViewModel = koinInject(),
) {
    val state = viewModel.state.value

    DefaultScreenUI(
        titleToolbar = stringResource(Res.string.activity),
        toolbarContent = {
            DateRangeSelector(
                Modifier.background(Color.White)
                    .padding(top = 12.dp, start = 24.dp, end = 24.dp, bottom = 16.dp),
                state.formattedDate,
                events = viewModel::setEvent
            )
        },
    ) {
        ActivityContent(
            state = state,
            events = viewModel::setEvent,
        )
    }
}
@Composable
fun ActivityContent(
    state: ActivityState,
    events: (ActivityEvent) -> Unit = {},
) {
    Column(Modifier.fillMaxSize().padding(horizontal = 24.dp, vertical = 12.dp)) {
        ActivitySummaryCard(
            Modifier.fillMaxWidth(),
            state = state,
            events = events
        )
    }
}
@Composable
fun DateRangeSelector(
    modifier: Modifier = Modifier,
    date: String = "May 28 - Jun 3",
    events: (ActivityEvent) -> Unit = {}
) {
    Row(
        modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(stringResource(Res.string.date), style = MaterialTheme.typography.bodyLarge)
            Text(date, style = MaterialTheme.typography.bodyMedium, color = Black60)
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                imageVector = Icons.Default.ChevronLeft,
                onClick = { events(ActivityEvent.PreviousWeek) },
                size = 40.dp
            )
            Spacer_8dp()
            IconButton(
                imageVector = Icons.Default.ChevronRight,
                onClick = { events(ActivityEvent.NextWeek) },
                size = 40.dp
            )
        }
    }
}
@Composable
fun ActivitySummaryCard(
    modifier: Modifier = Modifier,
    state: ActivityState,
    events: (ActivityEvent) -> Unit = {}
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(16.dp),
        border = BorderStroke(1.dp, BorderColor),
        colors = CardDefaults.cardColors(containerColor = Color.White),
    ) {
        when (state.summary) {
            is UiResult.Error -> {}
            is UiResult.Loading -> {}
            is UiResult.Success<ActivitySummaryModel> -> {
                Column(Modifier.fillMaxWidth().padding(16.dp)) {
                    SelectedSummaryHeader(
                        data = state.summary.data,
                        userHabits = state.userHabits,
                        isExpanded = state.isUserHabitExpanded,
                        events = events
                    )
                    Spacer_12dp()
                    Row(Modifier.fillMaxWidth()) {
                        TextLabelVertical(
                            modifier = Modifier.weight(1f),
                            label = stringResource(Res.string.activity_success_rate),
                            data = "%${state.summary.data.successRate}",
                            dataColor = Green100
                        )
                        TextLabelVertical(
                            modifier = Modifier.weight(1f),
                            label = stringResource(Res.string.activity_points_earned),
                            data = state.summary.data.pointsEarned.toString(),
                        )
                    }
                    Spacer_8dp()
                    Row(Modifier.fillMaxWidth()) {
                        TextLabelVertical(
                            modifier = Modifier.weight(1f),
                            label = stringResource(Res.string.activity_completed),
                            data = state.summary.data.completed.toString(),
                        )
                        TextLabelVertical(
                            modifier = Modifier.weight(1f),
                            label = stringResource(Res.string.activity_failed),
                            data = state.summary.data.failed.toString(),
                            dataColor = Red100
                        )
                    }
                }
            }

            else -> Unit
        }
    }
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SelectedSummaryHeader(
    modifier: Modifier = Modifier,
    userHabits: UiResult<List<UserHabitModel>>,
    data: ActivitySummaryModel,
    isExpanded: Boolean = false,
    events: (ActivityEvent) -> Unit = {}
) {
    var expanded by remember { mutableStateOf(false) }

    Box(contentAlignment = Alignment.TopStart) {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                Modifier.size(36.dp).background(DarkBlue10, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text(data.icon, style = MaterialTheme.typography.titleMedium)
            }
            Spacer_12dp()
            Column(Modifier.weight(1f)) {
                Text(data.name, style = MaterialTheme.typography.bodyLarge)
                Text(
                    stringResource(Res.string.summary),
                    style = MaterialTheme.typography.labelMedium,
                    color = Black40
                )
            }
            Spacer_12dp()
            IconButton(
                imageVector = Icons.Default.KeyboardArrowDown,
                shape = MaterialTheme.shapes.small,
                size = 36.dp
            ) {
                events(ActivityEvent.OpenUserHabits)
                expanded = !expanded
            }
        }
        if (userHabits is UiResult.Success<List<UserHabitModel>>) {
            println("Habits loaded? ${userHabits is UiResult.Success}")
            DropdownMenu(
                shadowElevation = 0.dp,
                offset = DpOffset(x = 0.dp, y = 0.dp),
                expanded = expanded,
                onDismissRequest = { expanded = false },
                containerColor = Color.White,
                shape = RoundedCornerShape(8.dp),
                border = BorderStroke(1.dp, BorderColor),
            ) {
                userHabits.data.forEach { habit ->
                    DropdownMenuItem(
                        text = {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    Modifier.size(36.dp)
                                        .background(DarkBlue10, RoundedCornerShape(8.dp)),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(habit.icon, style = MaterialTheme.typography.titleMedium)
                                }
                                Spacer_12dp()
                                Text(habit.name, style = MaterialTheme.typography.bodyLarge)
                            }
                        },
                        onClick = {
                            expanded = false
                            events(ActivityEvent.OnHabitSelected(habit.id))
                        },
                        contentPadding = ExposedDropdownMenuDefaults.ItemContentPadding,
                    )
                }
            }
        }
    }
}
@Preview
@Composable
fun ActivityScreenPreview() {
    AppTheme {
        ActivityScreen()
    }
}
@Preview
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SelectedSummaryHeaderPreview() {
    AppTheme {
        SelectedSummaryHeader(
            modifier = Modifier,
            userHabits = UiResult.Success(
                listOf(
                    UserHabitModel.generateDummy(),
                    UserHabitModel.generateDummy()
                )
            ),
            data = ActivitySummaryModel.generateDummy(),
            isExpanded = true,
            events = {}
        )
    }
}