package presentation.ui.habit.create

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronLeft
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import domain.model.HabitModel
import domain.model.UnitModel
import kotlinx.coroutines.flow.onEach
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import presentation.component.core.DefaultScreenUI
import presentation.component.core.DefaultTextField
import presentation.component.core.Spacer_16dp
import presentation.component.core.Spacer_8dp
import presentation.component.ui.PopularHabitCard
import presentation.component.ui.SelectableCard
import presentation.component.ui.SelectableText
import presentation.theme.AppTheme
import presentation.util.noDecimal
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.create_habit_title
import routinist.shared.generated.resources.habit
import routinist.shared.generated.resources.goal
import routinist.shared.generated.resources.unit

@Composable
fun CreateHabitScreen(
    habit: HabitModel,
    viewModel: CreateHabitViewModel = koinInject(),
    onBackClick: () -> Unit = {}
) {
    LaunchedEffect(Unit) {
        viewModel.setEvent(CreateHabitEvent.OnInit(habit))
        viewModel.action.onEach { effect ->
        }.collect {}
    }

    DefaultScreenUI(
        errors = viewModel.errors,
        progressBarState = viewModel.state.value.progressBarState,
        titleToolbar = stringResource(Res.string.create_habit_title),
        startIconToolbar = Icons.Default.ChevronLeft,
        onClickStartIconToolbar = onBackClick
    ) {
        CreateHabitContent(
            state = viewModel.state.value,
            events = viewModel::setEvent,
        )
    }
}

@Composable
fun CreateHabitContent(
    state: CreateHabitState,
    events: (CreateHabitEvent) -> Unit
) {
    if (state.habit == null) return

    Column(Modifier.fillMaxSize().padding(24.dp)) {
        Text(stringResource(Res.string.habit).uppercase(), style = MaterialTheme.typography.labelSmall)
        Spacer_8dp()
        PopularHabitCard(
            modifier = Modifier.width(200.dp),
            isSelected = false,
            habit = state.habit
        ) {}
        Spacer_16dp()
        Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            DefaultTextField(
                modifier = Modifier.wrapContentHeight(),
                label = stringResource(Res.string.goal),
                suffix = state.selectedUnit?.symbol,
                value = state.goal.noDecimal(),
                onValueChange = {

                }
            )
            Column(Modifier.weight(1f)) {
                Text(stringResource(Res.string.unit).uppercase(), style = MaterialTheme.typography.labelSmall)
                LazyRow(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    items(state.habit.units) {
                        SelectableText(
                            id = it,
                            modifier = Modifier,
                            isSelected = it == state.selectedUnit,
                            label = it.symbol,
                            onClick = {  }
                        )
                    }
                }
            }
        }
        Spacer_16dp()
    }
}



@Preview( )
@Composable
fun PreviewCreateHabit() {
    AppTheme {
        DefaultScreenUI(
            titleToolbar = "Create Habit",
            startIconToolbar = Icons.Default.ChevronLeft
        ) {
            CreateHabitContent(
                state = CreateHabitState(
                    habit = HabitModel(
                        id = 1,
                        name = "Sleep",
                        icon = "🛌",
                        units = listOf(
                            UnitModel(
                                id = 1,
                                measurement = "distance",
                                symbol = "km",
                                name = "Kilometre"
                            ),
                            UnitModel(
                                id = 2,
                                measurement = "distance",
                                symbol = "m",
                                name = "Metre"
                            )
                        ),
                        defaultGoal = 8,
                        measurement = "distance",
                        color = 0xFFE8D3FF
                    ),
                )
            ) {
            }
        }
    }
}