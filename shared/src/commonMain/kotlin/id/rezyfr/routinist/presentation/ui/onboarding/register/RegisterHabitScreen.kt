package id.rezyfr.routinist.presentation.ui.onboarding.register

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import id.rezyfr.routinist.domain.UiResult
import id.rezyfr.routinist.domain.model.HabitModel
import id.rezyfr.routinist.presentation.component.core.Spacer_16dp
import id.rezyfr.routinist.presentation.component.core.Spacer_4dp
import id.rezyfr.routinist.presentation.component.ui.SelectableCard
import org.jetbrains.compose.resources.stringResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import routinist.shared.generated.resources.Res
import routinist.shared.generated.resources.register_habit_desc
import routinist.shared.generated.resources.register_habit_title

@Composable
fun RegisterHabitScreen(
    modifier: Modifier = Modifier,
    state: RegisterState,
    events: (RegisterEvent) -> Unit = {}
) {
    val selectedItem = remember { mutableStateOf<Long?>(null) }

    Column(modifier) {
        Text(
            stringResource(Res.string.register_habit_title),
            style = MaterialTheme.typography.titleMedium
        )
        Spacer_4dp()
        Text(
            stringResource(Res.string.register_habit_desc),
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer_16dp()
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.fillMaxWidth().weight(1f),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            if (state.habits is UiResult.Success) {
                items(state.habits.data, key = { it.id }) { habit ->
                    val isSelected = selectedItem.value == habit.id

                    SelectableCard<Long>(
                        emoji = habit.icon,
                        label = habit.name,
                        isSelected = isSelected,
                        onClick = {
                            selectedItem.value = habit.id
                            events.invoke(RegisterEvent.OnHabitChange(habit.id))
                                  },
                        id = habit.id
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun RegisterHabitScreenPreview() {
    RegisterHabitScreen(state = RegisterState(
        habits = UiResult.Success(
            listOf(
                HabitModel(
                    id = 1,
                    name = "Eat healthy",
                    icon = "\uD83E\uDD26\u200D♂\uFE0F",
                    isSelected = true,
                    defaultGoal = 100,
                    units = listOf(),
                    measurement = "metre"
                ),
                HabitModel(
                    id = 1,
                    name = "Eat healthy",
                    icon = "\uD83E\uDD26\u200D♂\uFE0F",
                    isSelected = true,
                    defaultGoal = 100,
                    units = listOf(),
                    measurement = "metre"
                ),
                HabitModel(
                    id = 1,
                    name = "Eat healthy",
                    icon = "\uD83E\uDD26\u200D♂\uFE0F",
                    isSelected = true,
                    defaultGoal = 100,
                    units = listOf(),
                    measurement = "metre"
                ),
                HabitModel(
                    id = 1,
                    name = "Eat healthy",
                    icon = "\uD83E\uDD26\u200D♂\uFE0F",
                    isSelected = true,
                    defaultGoal = 100,
                    units = listOf(),
                    measurement = "metre"
                ),
                HabitModel(
                    id = 1,
                    name = "Eat healthy",
                    icon = "\uD83E\uDD26\u200D♂\uFE0F",
                    isSelected = true,
                    defaultGoal = 100,
                    units = listOf(),
                    measurement = "metre"
                ),
                HabitModel(
                    id = 1,
                    name = "Eat healthy",
                    icon = "\uD83E\uDD26\u200D♂\uFE0F",
                    isSelected = true,
                    defaultGoal = 100,
                    units = listOf(),
                    measurement = "metre"
                ),
                HabitModel(
                    id = 1,
                    name = "Eat healthy",
                    icon = "\uD83E\uDD26\u200D♂\uFE0F",
                    isSelected = true,
                    defaultGoal = 100,
                    units = listOf(),
                    measurement = "metre"
                ),
                HabitModel(
                    id = 1,
                    name = "Eat healthy",
                    icon = "\uD83E\uDD26\u200D♂\uFE0F",
                    isSelected = true,
                    defaultGoal = 100,
                    units = listOf(),
                    measurement = "metre"
                ),
            )
        )
    ))
}