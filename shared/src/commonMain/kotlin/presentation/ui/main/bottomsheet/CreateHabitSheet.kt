package presentation.ui.main.bottomsheet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.stringResource
import presentation.component.core.ButtonSize
import presentation.component.core.DefaultButton
import presentation.component.core.DefaultTextField
import presentation.component.core.Spacer_16dp
import presentation.component.core.Spacer_32dp
import presentation.component.core.Spacer_8dp
import presentation.component.ui.PopularHabitCard
import presentation.component.ui.SelectableText
import presentation.theme.Black40
import presentation.ui.main.MainEvent
import presentation.ui.main.MainState.CreateHabitSheetState
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.create_habit_title
import routinist.shared.generated.resources.goal
import routinist.shared.generated.resources.new_good_habit
import routinist.shared.generated.resources.popular_habits
import routinist.shared.generated.resources.unit

@Composable
fun CreateHabitSheet(
    state: CreateHabitSheetState,
    events: (MainEvent) -> Unit
) {
    Text(
        stringResource(Res.string.new_good_habit).uppercase(),
        style = MaterialTheme.typography.labelSmall.copy(color = Black40)
    )
    Spacer_8dp()
    Text(
        stringResource(Res.string.popular_habits).uppercase(),
        style = MaterialTheme.typography.labelSmall.copy(color = Black40)
    )
    Spacer_8dp()
    LazyRow(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(12.dp)) {
        items(state.popularHabits, { it.id }) { habit ->
            PopularHabitCard(
                Modifier.width(128.dp),
                isSelected = state.selectedHabit?.id == habit.id,
                habit = habit,
                onClick = {
                    events.invoke(MainEvent.OnHabitChosen(habit))
                }
            )
        }
    }

    AnimatedVisibility(visible = state.selectedHabit != null) {
        Column(Modifier.fillMaxWidth()) {
            Spacer_16dp()
            DefaultTextField(
                modifier = Modifier.wrapContentHeight().fillMaxWidth(),
                label = stringResource(Res.string.goal),
                suffix = state.selectedUnit?.symbol,
                showClearText = false,
                value = state.goal,
                onValueChange = {
                    events(MainEvent.OnGoalChange(it))
                }
            )
            Spacer_16dp()
            Text(
                stringResource(Res.string.unit).uppercase(),
                style = MaterialTheme.typography.labelSmall
            )
            LazyRow(modifier = Modifier, horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                items(state.selectedHabit?.units ?: listOf()) {
                    SelectableText(
                        id = it,
                        modifier = Modifier,
                        isSelected = it == state.selectedUnit,
                        label = it.symbol,
                        onClick = { events(MainEvent.OnUnitChosen(it)) }
                    )
                }
            }
            Spacer_32dp()
            DefaultButton(
                progressBarState = state.progressBarState,
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.create_habit_title),
                size = ButtonSize.Large,
                onClick = { events(MainEvent.OnCreateHabit) }
            )
        }
    }
}